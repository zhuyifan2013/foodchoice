package com.mi.FoodChoice;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.mi.FoodChoice.data.ShopItem;

import java.util.ArrayList;

public class ShopEditFragment extends Fragment {

    private ListView mListView;
    private MyAdapter mAdapter;

    private ArrayList<ShopItem> mshopArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_edit, container, false);
        mListView = (ListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new MyAdapter(mshopArray, getActivity());
        mListView.setAdapter(mAdapter);
    }

    private class MyAdapter extends BaseAdapter {

        private ArrayList<ShopItem> mShopArray;
        private Context mContext;

        public  MyAdapter(ArrayList<ShopItem> shopArray, Context context) {
            mShopArray = shopArray;
            mContext = context;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.shop_item, null);
                viewHolder = new ViewHolder();
                viewHolder.shopName = (TextView) convertView.findViewById(R.id.shop_name);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.shopName.setText(mShopArray.get(position).getShopName());
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
}
