package com.mi.FoodChoice;

import android.content.Context;
import android.mtp.MtpObjectInfo;
import android.os.Bundle;
import android.view.MenuItem;
import com.mi.FoodChoice.data.Constants;
import com.umeng.analytics.MobclickAgent;

public class ShopDealsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.KEY_DEALS, getIntent().getParcelableArrayListExtra(Constants.KEY_DEALS));
        ShopDealsFragment shopDealsFragment = new ShopDealsFragment();
        shopDealsFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(android.R.id.content, shopDealsFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }
}
