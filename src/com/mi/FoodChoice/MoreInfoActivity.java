package com.mi.FoodChoice;

import android.os.Bundle;
import com.umeng.analytics.MobclickAgent;

public class MoreInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        getFragmentManager().beginTransaction().add(R.id.setting_content, new MoreInfoFragment())
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
