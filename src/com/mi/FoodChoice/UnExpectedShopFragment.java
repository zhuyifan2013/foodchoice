package com.mi.FoodChoice;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mi.FoodChoice.data.Constants;
import com.mi.FoodChoice.data.UnExpShop;

import java.util.List;

public class UnExpectedShopFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyAdapter adapter = new MyAdapter(getActivity(), R.layout.unexpcted_shop_list_item, FoodHelper.getUnexpectedShopList());
        setListAdapter(adapter);
    }

    private class MyAdapter extends ArrayAdapter<UnExpShop> {

        private LayoutInflater inflater;
        public MyAdapter(Context context, int resource, List<UnExpShop> objects) {
            super(context, resource, objects);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            UnExpShop unExpShop = getItem(position);
            String shopName = unExpShop.getShopName();
            String addDate = FoodHelper.getDateString(unExpShop.getAddDate(), Constants.DATE_PATTEN_1);

            if(convertView == null) {
                convertView = inflater.inflate(R.layout.unexpcted_shop_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.shopName = (TextView)convertView.findViewById(R.id.shop_name);
                viewHolder.addDate = (TextView)convertView.findViewById(R.id.add_date);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.shopName.setText(shopName);
            viewHolder.addDate.setText(addDate);
            return convertView;
        }

        class ViewHolder{
            TextView shopName;
            TextView addDate;
        }
    }
}
