package com.mi.FoodChoice;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.umeng.fb.FeedbackAgent;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private LinearLayout mUnExpShopLayout, mFeedback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, null);
        mUnExpShopLayout = (LinearLayout) view.findViewById(R.id.unexpected_shop_list);
        mFeedback = (LinearLayout) view.findViewById(R.id.feedback);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUnExpShopLayout.setOnClickListener(this);
        mFeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.unexpected_shop_list:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, new UnExpectedShopFragment())
                        .setCustomAnimations(FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                                FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.feedback:
                FeedbackAgent agent = new FeedbackAgent(getActivity());
                agent.startFeedbackActivity();
                break;
        }
    }
}
