package com.mi.FoodChoice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;

public class HomeActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        showSlogan();
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(HomeActivity.this, MakeChoiceActivity.class));
                finish();
            }
        },4000);
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

    private void showSlogan() {
        TextView mFirstView = (TextView) findViewById(R.id.slogan_first);
        TextView mSecondaryView = (TextView) findViewById(R.id.slogan_secondary);
        TextView mThirdView = (TextView) findViewById(R.id.slogan_third);
        AlphaAnimation fadeIn1 = new AlphaAnimation(0.0f, 1.0f);
        AlphaAnimation fadeIn2 = new AlphaAnimation(0.0f, 1.0f);
        AlphaAnimation fadeIn3 = new AlphaAnimation(0.0f, 1.0f);
        fadeIn1.setDuration(2000);
        fadeIn2.setDuration(2000);
        fadeIn3.setDuration(2000);
        fadeIn1.setFillAfter(true);
        fadeIn2.setFillAfter(true);
        fadeIn3.setFillAfter(true);
        fadeIn2.setStartOffset(800);
        fadeIn3.setStartOffset(1600);
        mFirstView.startAnimation(fadeIn1);
        mSecondaryView.startAnimation(fadeIn2);
        mThirdView.startAnimation(fadeIn3);
    }
}
