package com.mi.FoodChoice;

import android.os.Bundle;
import com.mi.FoodChoice.data.Constants;

public class CommentActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.KEY_COMMENT, getIntent().getParcelableArrayListExtra(Constants.KEY_COMMENT));
        ShopDealsFragment shopDealsFragment = new ShopDealsFragment();
        shopDealsFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(android.R.id.content, shopDealsFragment)
                .commit();
    }
}
