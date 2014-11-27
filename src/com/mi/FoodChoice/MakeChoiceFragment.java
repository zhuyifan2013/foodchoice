package com.mi.FoodChoice;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.mi.FoodChoice.data.ShopItem;
import com.mi.FoodChoice.ui.CircleButton;

import java.util.ArrayList;
import java.util.Random;

public class MakeChoiceFragment extends Fragment {

    private CircleButton mChoiceButton;
    private ArrayList<ShopItem> mShopItemArray = new ArrayList<ShopItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_choice, container, false);
        mChoiceButton = (CircleButton) view.findViewById(R.id.make_choice);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MakeChoiceTask().execute(getActivity());
            }
        });
    }

    private class MakeChoiceTask extends AsyncTask<Context,Void,ShopItem>{
        @Override
        protected ShopItem doInBackground(Context... params) {
            ArrayList<ShopItem> shopArray = new ArrayList<ShopItem>();
            shopArray = FoodHelper.initShopArray(params[0]);
            Random random = new Random();
            int index = random.nextInt(shopArray.size());
            return shopArray.get(index);
        }

        @Override
        protected void onPostExecute(ShopItem shopItem) {
            showToast("店铺为" + shopItem.getShopName());
        }
    }
    private void showToast(CharSequence msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
