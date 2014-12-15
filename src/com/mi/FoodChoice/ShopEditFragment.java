package com.mi.FoodChoice;

import android.app.Fragment;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.*;
import com.mi.FoodChoice.data.Constants;
import com.mi.FoodChoice.data.FoodDatabase;
import com.mi.FoodChoice.data.ShopItem;

import java.util.ArrayList;

public class ShopEditFragment extends Fragment implements View.OnClickListener {

    private ListView mListView;
    private Button mGroupBtn;
    private PopupWindow mPopupWindow;

    private MyAdapter mAdapter;

    private Context mContext;
    private ContentObserver mObserver;

    private ShopListManager mShopListManager = ShopListManager.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_edit, container, false);
        mListView = (ListView) view.findViewById(R.id.list);
        mGroupBtn = (Button) view.findViewById(R.id.group_filter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mGroupBtn.setOnClickListener(this);

        if (mShopListManager.getShopList() == null) {
            showToast("出错了!");
        }

        mAdapter = new MyAdapter(mShopListManager.getShopList(), mContext);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                    long id) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ACTION_ID, Constants.ACTION_LONG_PRESS);
                bundle.putParcelable(Constants.SHOP_ITEM, (ShopItem) mAdapter.getItem(position));
                ShopEditDialogFragment frag = ShopEditDialogFragment.newInstance(bundle);
                frag.show(getFragmentManager(), "item_long_press");
                return true;
            }
        });
        mObserver = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                mShopListManager.getShopList().clear();
                mShopListManager.setShopList(FoodHelper.getShopList(getActivity()));
                mAdapter.updateDataSet(mShopListManager.getShopList());
                mAdapter.notifyDataSetChanged();
            }
        };
        mContext.getContentResolver()
                .registerContentObserver(FoodDatabase.ShopTable.URI_SHOP_TABLE, true, mObserver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ACTION_ID, Constants.ACTION_ADD);
                ShopEditDialogFragment frag = ShopEditDialogFragment.newInstance(bundle);
                frag.show(getFragmentManager(), "add_shop");
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.group_filter:
            case R.id.price_filter:
                if (initPopupWindow(v)) {
                    mPopupWindow.showAsDropDown(v, 10, 10);
                }
                break;
        }
    }

    private boolean initPopupWindow(View v) {
        if (mPopupWindow != null) {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
                return false;
            }
        }

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View popUpView;

        switch (v.getId()) {
            case R.id.group_filter:
                popUpView = inflater.inflate(R.layout.popup_group, null);
                mPopupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_bg));
                mPopupWindow.setOutsideTouchable(true);
        }
        return true;
    }

    private class MyAdapter extends BaseAdapter {

        private ArrayList<ShopItem> mShopArray;
        private Context mContext;

        public MyAdapter(ArrayList<ShopItem> shopArray, Context context) {
            mShopArray = shopArray;
            mContext = context;
        }

        public void updateDataSet(ArrayList<ShopItem> shopArray) {
            mShopArray = shopArray;
        }

        @Override
        public int getCount() {
            return mShopArray.size();
        }

        @Override
        public Object getItem(int position) {
            return mShopArray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.shop_item, null);
                viewHolder = new ViewHolder();
                viewHolder.shopName = (TextView) convertView.findViewById(R.id.shop_name);
                viewHolder.priceText = (TextView) convertView.findViewById(R.id.price);
                viewHolder.tasteText = (TextView) convertView.findViewById(R.id.taste);
                viewHolder.distanceText = (TextView) convertView.findViewById(R.id.distance);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.shopName.setText(mShopArray.get(position).getShopName());
            viewHolder.priceText
                    .setText(FoodHelper.getPriceStrById(mShopArray.get(position).getPrice()));
            viewHolder.tasteText
                    .setText(FoodHelper.getTasteStrById(mShopArray.get(position).getTaste()));
            viewHolder.distanceText
                    .setText(FoodHelper.getDistanceStrById(mShopArray.get(position).getDistance()));

            return convertView;
        }

        private class ViewHolder {
            ImageView shopLogo;
            TextView shopName;
            TextView priceText;
            TextView tasteText;
            TextView distanceText;
        }
    }

    private void showToast(CharSequence msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
