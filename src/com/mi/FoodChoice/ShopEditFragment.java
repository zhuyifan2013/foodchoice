package com.mi.FoodChoice;

import android.app.Fragment;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.mi.FoodChoice.data.Constants;
import com.mi.FoodChoice.data.FoodDatabase;
import com.mi.FoodChoice.data.ShopItem;

import java.util.ArrayList;

public class ShopEditFragment extends Fragment {

    private ListView mListView;
    private MyAdapter mAdapter;

    private Context mContext;
    private ContentObserver mObserver;
    private ArrayList<ShopItem> mshopArray = new ArrayList<ShopItem>();

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
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initArray();
        mAdapter = new MyAdapter(mshopArray, mContext);
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
                mshopArray.clear();
                initArray();
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

    private void initArray() {
        ShopItem shopItem;
        Cursor cursor;
        cursor = mContext.getContentResolver()
                .query(FoodDatabase.ShopTable.URI_SHOP_TABLE, null, null, null, null);
        if (cursor == null) {
            return;
        }

        while (cursor.moveToNext()) {
            shopItem = new ShopItem();
            shopItem.setId(cursor.getInt(cursor.getColumnIndex(FoodDatabase.ShopTable._ID)));
            shopItem.setShopName(
                    cursor.getString(cursor.getColumnIndex(FoodDatabase.ShopTable.NAME)));
            shopItem.setPrice(
                    cursor.getString(cursor.getColumnIndex(FoodDatabase.ShopTable.PRICE)));
            shopItem.setTaste(
                    cursor.getString(cursor.getColumnIndex(FoodDatabase.ShopTable.TASTE_TYPE)));
            shopItem.setDistance(
                    cursor.getString(cursor.getColumnIndex(FoodDatabase.ShopTable.DISTANCE)));
            mshopArray.add(0, shopItem);
        }
    }

    private class MyAdapter extends BaseAdapter {

        private ArrayList<ShopItem> mShopArray;
        private Context mContext;

        public MyAdapter(ArrayList<ShopItem> shopArray, Context context) {
            mShopArray = shopArray;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mshopArray.size();
        }

        @Override
        public Object getItem(int position) {
            return mshopArray.get(position);
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
            viewHolder.priceText.setText(mShopArray.get(position).getPrice());
            viewHolder.tasteText.setText(mShopArray.get(position).getTaste());
            viewHolder.distanceText.setText(mShopArray.get(position).getDistance());

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
