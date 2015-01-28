package com.mi.FoodChoice;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.mi.FoodChoice.Handler.DianPingHandler;
import com.mi.FoodChoice.data.Constants;
import com.mi.FoodChoice.data.FoodDatabase;
import com.mi.FoodChoice.data.Shop;
import com.mi.FoodChoice.data.UnExpShop;
import com.mi.FoodChoice.model.SingleShopResponse;

import java.util.List;

public class UnExpectedShopFragment extends ListFragment {

    private TextView mEmptyView;
    private MyAdapter myAdapter;
    private Gson mGson;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_ERROR:
                    showToast(getString(R.string.toast_error));
                    break;
                case Constants.MSG_SHOP_RECOVERED:
                    showToast(getString(R.string.toast_recovered));
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGson = new Gson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unexpected_shop_listview, null);
        mEmptyView = (TextView) view.findViewById(android.R.id.empty);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setEmptyView(mEmptyView);
        getListView().setOnItemLongClickListener(myOnItemLongClickListener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myAdapter = new MyAdapter(getActivity(), R.layout.unexpcted_shop_list_item,
                FoodHelper.getUnexpectedShopList());
        setListAdapter(myAdapter);
    }

    private AdapterView.OnItemLongClickListener myOnItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position,
                long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.dialog_title_revert));
            builder.setMessage(getString(R.string.dialog_message_revert));
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    updateData();
                    final UnExpShop unExpShop = FoodHelper.getUnExpShopByBusinessId(
                            myAdapter.getItem(position).getBusiness_id());
                    FoodHelper.getUnexpectedShopList().remove(unExpShop);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SingleShopResponse shopResponse = mGson.fromJson(
                                    DianPingHandler.searchShopById(Integer.toString(
                                            unExpShop.getBusiness_id())), SingleShopResponse.class);
                            if (shopResponse == null) {
                                mHandler.sendEmptyMessage(Constants.MSG_ERROR);
                                return;
                            }
                            Shop shop = shopResponse.getBusinesses();
                            if (FoodHelper.extendShopList(shop, false)) {
                                mHandler.sendEmptyMessage(Constants.MSG_SHOP_RECOVERED);
                            }
                        }
                    }).start();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ContentValues values = new ContentValues();
                            values.put(FoodDatabase.UnexpectedShop.BUSINESS_ID,
                                    unExpShop.getBusiness_id());
                            values.put(FoodDatabase.UnexpectedShop.ADD_DATE,
                                    unExpShop.getAddDate());
                            values.put(FoodDatabase.UnexpectedShop.SHOP_NAME,
                                    unExpShop.getName());
                            values.put(FoodDatabase.UnexpectedShop.IS_EXCLUDED, 0);
                            String selection =
                                    FoodDatabase.UnexpectedShop.BUSINESS_ID + "=?";
                            String[] selectionArgs = new String[] {
                                    Integer.toString(unExpShop.getBusiness_id())
                            };
                            getActivity().getContentResolver()
                                    .update(FoodDatabase.UnexpectedShop.URI_UNEXPECTED_TABLE,
                                            values, selection, selectionArgs);
                        }
                    }).start();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
    };

    private void updateData() {
        myAdapter = new MyAdapter(getActivity(), R.layout.unexpcted_shop_list_item,
                FoodHelper.getUnexpectedShopList());
        setListAdapter(myAdapter);
    }

    private class MyAdapter extends ArrayAdapter<UnExpShop> {

        private LayoutInflater inflater;

        public MyAdapter(Context context, int resource, List<UnExpShop> objects) {
            super(context, resource, objects);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public UnExpShop getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            UnExpShop unExpShop = getItem(position);
            String shopName = unExpShop.getName();
            String addDate = FoodHelper
                    .getDateString(unExpShop.getAddDate(), Constants.DATE_PATTEN_1);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.unexpcted_shop_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.shopName = (TextView) convertView.findViewById(R.id.shop_name);
                viewHolder.addDate = (TextView) convertView.findViewById(R.id.add_date);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.shopName.setText(shopName);
            viewHolder.addDate.setText(addDate);
            return convertView;
        }

        class ViewHolder {
            TextView shopName;
            TextView addDate;
        }
    }

    private void showToast(CharSequence msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
