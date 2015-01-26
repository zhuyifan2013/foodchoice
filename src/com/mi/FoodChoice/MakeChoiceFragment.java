package com.mi.FoodChoice;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.google.gson.Gson;
import com.mi.FoodChoice.Handler.DianPingHandler;
import com.mi.FoodChoice.data.*;
import com.mi.FoodChoice.ui.CircleButton;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MakeChoiceFragment extends Fragment implements View.OnClickListener,
        AMapLocationListener {

    private CircleButton mChocieBtn, mFirstBtn, mTrashBtn, mLocationBtn, mComment;
    private ImageButton mSettingBtn;
    private ImageView mLatterEmpty;
    private TextView mShopName, mShopPrice, mShopDistance, mDealListTitle;
    private LinearLayout mShopDetailsLayout, mDealsListLayout;

    private AMapLocation mLocation;
    private LocationManagerProxy mLocationManagerProxy;
    private Gson mGson;

    private int mChoiceCount = 0;

    private Shop mChoiceShop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGson = new Gson();
        initLocation();

    }

    private void initLocation() {
        mLocationManagerProxy = LocationManagerProxy.getInstance(getActivity());
        mLocationManagerProxy
                .requestLocationData(LocationProviderProxy.AMapNetwork, 5000, 15, this);
        mLocationManagerProxy.setGpsEnable(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_choice, container, false);
        mTrashBtn = (CircleButton) view.findViewById(R.id.trash);
        mChocieBtn = (CircleButton) view.findViewById(R.id.make_choice);
        mFirstBtn = (CircleButton) view.findViewById(R.id.first_click);
        mLocationBtn = (CircleButton) view.findViewById(R.id.location);
        mComment = (CircleButton) view.findViewById(R.id.comment);
        mSettingBtn = (ImageButton) view.findViewById(R.id.setting);
        mLatterEmpty = (ImageView) view.findViewById(R.id.latter_empty);
        mShopName = (TextView) view.findViewById(R.id.shop_name);
        mShopPrice = (TextView) view.findViewById(R.id.shop_price);
        mShopDistance = (TextView) view.findViewById(R.id.shop_distance);
        mDealListTitle = (TextView) view.findViewById(R.id.deals_list_title);
        mDealsListLayout = (LinearLayout) view.findViewById(R.id.deals_list_item);
        mShopDetailsLayout = (LinearLayout) view.findViewById(R.id.shop_details);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mChocieBtn.setOnClickListener(this);
        mFirstBtn.setOnClickListener(this);
        mSettingBtn.setOnClickListener(this);
        mTrashBtn.setOnClickListener(this);
        mLocationBtn.setOnClickListener(this);
        mDealsListLayout.setOnClickListener(this);
        mComment.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!FoodHelper.isNetWorkConnected(getActivity())) {
            showToast(getString(R.string.toast_no_network));
        }
    }

    @Override
    public void onPause() {
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
        super.onPause();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_CHOICE_FINISH: {

                    mShopName.setText(mChoiceShop.getName());
                    mShopPrice.setText(String.format(getString(R.string.shop_avg_price),
                            Integer.toString(mChoiceShop.getAvg_price())));
                    mShopDistance.setText(String.format(getString(R.string.shop_distance),
                            Float.toString(mChoiceShop.getDistance())));

                    if (mChoiceShop.getHas_deal() == 1) {
                        mDealsListLayout.setClickable(true);
                        mDealListTitle.setText(
                                String.format(getString(R.string.shop_deals_item_title),
                                        Integer.toString(mChoiceShop.getDeal_count())));
                    } else {
                        mDealListTitle.setText(getString(R.string.shop_deals_item_title_empty));
                    }

                    if (!mDealsListLayout.isShown() || !mShopDetailsLayout.isShown()) {
                        mDealsListLayout.setVisibility(View.VISIBLE);
                        mShopDetailsLayout.setVisibility(View.VISIBLE);
                    }

                    if (mChoiceShop.getReview_count() == 0) {
                        mComment.setVisibility(View.GONE);
                    } else {
                        if (mComment.getVisibility() == View.GONE) {
                            mComment.setVisibility(View.VISIBLE);
                        }
                    }

                    if (mFirstBtn.isShown() || mLatterEmpty.isShown()) {
                        mFirstBtn.setVisibility(View.GONE);
                        mLatterEmpty.setVisibility(View.GONE);
                    }
                    break;
                }
                case Constants.MSG_NO_SUITABLE_SHOP: {
                    showToast(getString(R.string.toast_no_suitable_shop));
                    break;
                }

                case Constants.MSG_LOCATION_SEARCHED: {
                    break;
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            mLocation = aMapLocation;
            Message msg = new Message();
            msg.what = Constants.MSG_LOCATION_SEARCHED;
            mHandler.sendMessage(msg);
        }
    }

    private void showToast(CharSequence msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int viewId = v.getId();
        switch (viewId) {

            // shuffle from shop list
            case R.id.make_choice: {
                mDealsListLayout.setClickable(false);
                makeChoice();
                break;
            }

            // 将不想再出现的商店放在UnexpectedShopList中，从ShopList中剔除
            case R.id.trash:
                if (FoodHelper.getShopList().isEmpty()) {
                    showToast(getString(R.string.toast_no_suitable_shop));
                    break;
                }
                UnExpShop unExpShop = new UnExpShop();
                unExpShop.setBusinessId(mChoiceShop.getBusiness_id());
                unExpShop.setShopName(mChoiceShop.getName());
                unExpShop.setAddDate(System.currentTimeMillis());
                unExpShop.setIsExcluded(1);
                FoodHelper.getUnexpectedShopList().add(unExpShop);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ContentValues values = new ContentValues();
                        values.put(FoodDatabase.UnexpectedShop.BUSINESS_ID,
                                mChoiceShop.getBusiness_id());
                        values.put(FoodDatabase.UnexpectedShop.SHOP_NAME, mChoiceShop.getName());
                        values.put(FoodDatabase.UnexpectedShop.IS_EXCLUDED, 1);
                        values.put(FoodDatabase.UnexpectedShop.ADD_DATE,
                                System.currentTimeMillis());
                        getActivity().getContentResolver()
                                .insert(FoodDatabase.UnexpectedShop.URI_UNEXPECTED_TABLE, values);
                    }
                }).run();
                //showToast(getString(R.string.toast_remove_shop));
                makeChoice();
                break;

            // big button, first time to make choice
            case R.id.first_click: {
                mDealsListLayout.setClickable(false);
                mChoiceCount++;
                searchNearShop();
                break;
            }

            case R.id.setting:
                startActivity((new Intent()).setClass(getActivity(), MoreInfoActivity.class));
                break;

            case R.id.deals_list_item:
                intent = new Intent();
                intent.putParcelableArrayListExtra(Constants.KEY_DEALS, mChoiceShop.getDeals());
                intent.setClass(getActivity(), ShopDealsActivity.class);
                startActivity(intent);
                break;

            case R.id.location:
                String realUrl =
                        FoodHelper.GAODE_URI + "q=" + mChoiceShop.getLatitude() + ","
                                + mChoiceShop.getLongitude() + "&name="
                                + mChoiceShop.getName() + "&dev=0";
                intent = new Intent();
                intent.putExtra(Constants.KEY_URI, realUrl);
                intent.setClass(getActivity(), WebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.comment:
                intent = new Intent();
                intent.putExtra(Constants.KEY_URI, mChoiceShop.getReview_list_url());
                intent.setClass(getActivity(), WebViewActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void makeChoice() {
        MobclickAgent.onEvent(getActivity(), Constants.EVENT_CLICK_PER_LANCH);
        Message msg = new Message();
        mChoiceCount++;
        switch (mChoiceCount) {
            case 3:
                showToast(getString(R.string.toast_three_times));
                break;
            case 6:
                showToast(getString(R.string.toast_six_times));
                break;
            case 10:
                showToast(getString(R.string.toast_ten_times));
                break;
        }

        if (FoodHelper.getShopList() != null && !FoodHelper.getShopList().isEmpty()) {
            mChoiceShop = getChoiceShop(FoodHelper.getShopList());
            if (mChoiceShop == null) {
                msg.what = Constants.MSG_NO_SUITABLE_SHOP;
                mHandler.sendMessage(msg);
                return;
            }
            msg.what = Constants.MSG_CHOICE_FINISH;
            mHandler.sendMessage(msg);
        }
    }

    private void searchNearShop() {
        if (mLocation == null) {
            showToast(getString(R.string.toast_no_network));
            return;
        }
        MobclickAgent.onEvent(getActivity(), Constants.EVENT_CLICK_PER_LANCH);
        final Map<String, String> map = new HashMap<String, String>();
        map.put("latitude", Double.toString(mLocation.getLatitude()));
        map.put("longitude", Double.toString(mLocation.getLongitude()));
        map.put("offset_type", "1");
        map.put("category", "美食");
        map.put("radius", "1000");
        map.put("sort", "7");
        map.put("limit", "40");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                NearShopResponse nearShopResponse = mGson.fromJson(
                        DianPingHandler.searchNearShop(map), NearShopResponse.class);
                FoodHelper.setShopList(nearShopResponse.getBusinesses());
                mChoiceShop = getChoiceShop(FoodHelper.getShopList());
                if (mChoiceShop == null) {
                    msg.what = Constants.MSG_NO_SUITABLE_SHOP;
                    mHandler.sendMessage(msg);
                    return;
                }
                msg.what = Constants.MSG_CHOICE_FINISH;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    private Shop getChoiceShop(List<Shop> shopList) {
        Shop choiceShop;
        for (Shop aShopList : shopList) {
            choiceShop = shopList.get((new Random()).nextInt(FoodHelper.getShopList().size()));
            if (!FoodHelper.getUnexpectedShopList().contains(FoodHelper.getUnExpShopByBusinessId(
                    choiceShop.getBusiness_id()))) {
                return choiceShop;
            }
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
