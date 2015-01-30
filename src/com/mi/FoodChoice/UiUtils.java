package com.mi.FoodChoice;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class UiUtils {
    public static void replaceFragment(Activity activity, Fragment f) {
        replaceFragment(activity, f, false, false, true);
    }

    public static void replaceFragment(Activity activity, Fragment f, boolean hasAnim) {
        replaceFragment(activity, f, false, false, hasAnim);
    }

    public static void replaceFragment(Activity activity, Fragment f, boolean popupTop,
            boolean addToBackStack) {
        replaceFragment(activity, f, popupTop, addToBackStack, true);
    }

    public static void replaceFragment(Activity activity, Fragment f,
            boolean popupTop, boolean addToBackStack, boolean hasAnim) {
        if (activity == null) {
            return;
        }
        FragmentManager fm = activity.getFragmentManager();
        if (popupTop) {
            fm.popBackStack();
        }

        FragmentTransaction transaction = fm.beginTransaction();
        if (hasAnim) {
            transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        }
        transaction.replace(android.R.id.content, f,
                f.getClass().getName());
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }
}
