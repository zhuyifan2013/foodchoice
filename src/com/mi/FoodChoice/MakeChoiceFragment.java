package com.mi.FoodChoice;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.mi.FoodChoice.data.Constants;
import com.mi.FoodChoice.ui.CircleButton;

import java.util.Random;

public class MakeChoiceFragment extends Fragment {

    private TextView mResultView;

    private CircleButton mChoiceButton;
    private ProgressBar mProgressBar;
    private ShopListManager mShopListManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.MSG_INIT_FINISH:
                    mProgressBar.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShopListManager = ShopListManager.getInstance();
        SharedPreferences setting = getActivity().getSharedPreferences(Constants.PREF_FILE_NAME, 0);
        if (setting.getBoolean(Constants.PREF_FIRST_LAUNCH, true)) {
            setting.edit().putBoolean(Constants.PREF_FIRST_LAUNCH, false);
            setting.edit().apply();
            firstLaunchInit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_choice, container, false);
        mChoiceButton = (CircleButton) view.findViewById(R.id.make_choice);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        mResultView = (TextView) view.findViewById(R.id.result);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initShopList();
        mChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int index = random.nextInt(mShopListManager.getShopList().size());
                mResultView.setText(getResources().getString(R.string.make_choice_result, mShopListManager.getShopList().get(index).getShopName()));
            }
        });
    }

    private void firstLaunchInit() {

    }

    private void initShopList(){
        mProgressBar.setProgress(0);
        (new InitShopListThread()).run();
    }

    private class InitShopListThread implements Runnable{

        @Override
        public void run() {
            Message msg = new Message();
            mShopListManager.setShopList(FoodHelper.getShopList(getActivity()));
            msg.what = Constants.MSG_INIT_FINISH;
            mHandler.sendMessage(msg);
        }
    }

    private void showToast(CharSequence msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
