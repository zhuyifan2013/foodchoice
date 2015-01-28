package com.mi.FoodChoice;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.mi.FoodChoice.data.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FoodApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences setting = getSharedPreferences(Constants.PREF_FILE_NAME, 0);
        if (setting.getBoolean(Constants.PREF_FIRST_LAUNCH, true)) {
            setting.edit().putBoolean(Constants.PREF_FIRST_LAUNCH, false);
            setting.edit().apply();
            firstLaunchInit();
        }
        initImageLoader(this);
        FoodHelper.initUnExpectShopIdList(this);
    }

    private static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(context);
        ImageLoader.getInstance().init(config);
    }

    private void firstLaunchInit(){

    }
}
