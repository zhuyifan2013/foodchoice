package com.mi.FoodChoice;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.mi.FoodChoice.Handler.DianPingHandler;
import com.mi.FoodChoice.data.Constants;
import com.mi.FoodChoice.data.Shop;
import com.mi.FoodChoice.data.ShopDeal;
import com.mi.FoodChoice.model.DealsResponse;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ShopDealsFragment extends ListFragment {

    private List<ShopDeal> mShopDealList;
    private Gson mGson;
    private MyAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShopDealList = getArguments().getParcelableArrayList(Constants.KEY_DEALS);
        mGson = new Gson();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setFitsSystemWindows(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new MyAdapter(getActivity(), R.layout.shop_deal_list_item, mShopDealList);
        setListAdapter(mAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < mShopDealList.size(); i++) {
                    stringBuilder.append(mShopDealList.get(i).getId());
                    if (i != mShopDealList.size() - 1) {
                        stringBuilder.append(",");
                    }
                }
                DealsResponse dealsResponse = mGson
                        .fromJson(DianPingHandler.searchBatchDealsById(stringBuilder.toString()),
                                DealsResponse.class);
                if (dealsResponse == null) {
                    mHandler.sendEmptyMessage(Constants.MSG_ERROR);
                    return;
                }
                mShopDealList = dealsResponse.getDeals();
                mHandler.sendEmptyMessage(Constants.MSG_SEARCH_FINISH);
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_SEARCH_FINISH:
                    mAdapter = new MyAdapter(getActivity(), R.layout.shop_deal_list_item,
                            mShopDealList);
                    setListAdapter(mAdapter);
                    break;
                case Constants.MSG_ERROR:
                    //TODO 需要测试下这里
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(Constants.KEY_URI, mShopDealList.get(position).getDeal_h5_url());
        startActivity(intent);
    }

    private class MyAdapter extends ArrayAdapter<ShopDeal> {

        private LayoutInflater inflater;

        public MyAdapter(Context context, int resource,
                List<ShopDeal> objects) {
            super(context, resource, objects);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.shop_deal_list_item, null);
                holder.dealLogo = (ImageView) convertView.findViewById(R.id.deal_image);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.description = (TextView) convertView.findViewById(R.id.description);
                holder.originPrice = (TextView) convertView.findViewById(R.id.origin_price);
                holder.currentPrice = (TextView) convertView.findViewById(R.id.current_price);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ShopDeal shopDeal = getItem(position);

            ImageLoader.getInstance().displayImage(shopDeal.getImage_url(), holder.dealLogo,
                    FoodHelper.DEFAULT_DISPLAY_OPTIONS);
            holder.title.setText(shopDeal.getTitle());
            holder.description.setText(shopDeal.getDescription());
            holder.originPrice.setText(String.format(getString(R.string.deal_origin_price,
                    Float.toString(shopDeal.getList_price()))));
            holder.currentPrice.setText(String.format(getString(R.string.deal_current_price,
                    Float.toString(shopDeal.getCurrent_price()))));
            return convertView;
        }

        class ViewHolder {
            ImageView dealLogo;
            TextView title;
            TextView description;
            TextView originPrice;
            TextView currentPrice;
        }

    }
}
