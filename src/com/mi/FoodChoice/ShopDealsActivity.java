package com.mi.FoodChoice;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import com.mi.FoodChoice.data.Constants;

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
}
