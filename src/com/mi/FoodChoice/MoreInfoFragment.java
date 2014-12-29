package com.mi.FoodChoice;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;

public class MoreInfoFragment extends Fragment implements View.OnClickListener {

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setFitsSystemWindows(true);
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
                getFragmentManager().beginTransaction()
                        .replace(R.id.setting_content, new UnExpectedShopFragment())
                        .setCustomAnimations(FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                                FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .addToBackStack(null).commit();
                break;
            case R.id.feedback:
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                intent.putExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID, new FeedbackAgent(getActivity()).getDefaultConversation().getId());
                startActivity(intent);
                break;
        }
    }
}
