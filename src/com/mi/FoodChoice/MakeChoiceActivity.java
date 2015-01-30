package com.mi.FoodChoice;

import android.os.Bundle;
import com.umeng.analytics.MobclickAgent;

public class MakeChoiceActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.replaceFragment(this, new MakeChoiceFragment());
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
