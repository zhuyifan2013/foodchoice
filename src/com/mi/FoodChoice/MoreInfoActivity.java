package com.mi.FoodChoice;

import android.os.Bundle;

public class MoreInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        getFragmentManager().beginTransaction().add(R.id.setting_content, new MoreInfoFragment())
                .commit();
    }


}
