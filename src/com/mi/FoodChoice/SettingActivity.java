package com.mi.FoodChoice;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        getFragmentManager().beginTransaction().add(R.id.setting_content, new SettingFragment())
                .commit();
    }


}
