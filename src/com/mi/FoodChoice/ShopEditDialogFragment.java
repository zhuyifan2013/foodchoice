package com.mi.FoodChoice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.mi.FoodChoice.data.Constants;
import com.mi.FoodChoice.data.FoodDatabase;
import com.mi.FoodChoice.data.ShopItem;
import com.mi.FoodChoice.ui.CircleButton;
import org.w3c.dom.Text;

public class ShopEditDialogFragment extends DialogFragment {

    private static int mActionId;
    private static ShopItem mShopItem;

    public static ShopEditDialogFragment newInstance(Bundle bundle) {
        ShopEditDialogFragment frag = new ShopEditDialogFragment();
        frag.setArguments(bundle);
        return frag;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionId = getArguments().getInt(Constants.ACTION_ID);
        mShopItem = new ShopItem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view;
        switch (mActionId) {
            case Constants.ACTION_ADD:
                view = inflater.inflate(R.layout.dialog_add_shop, container, false);
                break;
            case Constants.ACTION_LONG_PRESS:
                view = inflater.inflate(R.layout.dialog_delete_confirm, container, false);
                break;
            default:
                return super.onCreateView(inflater, container, savedInstanceState);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (mActionId) {
            case Constants.ACTION_ADD:
                showAddShopDialog(view);
                break;
            case Constants.ACTION_LONG_PRESS:
                showDeleteConfirmDialog(view);
        }
    }

    private void showAddShopDialog(View view) {
        ArrayAdapter<CharSequence> adapter;
        getDialog().setTitle(R.string.shop_edit_dialog_add_shop_title);

        Spinner tasteSpinner = (Spinner) view.findViewById(R.id.taste_select);
        Spinner priceSpinner = (Spinner) view.findViewById(R.id.price_select);
        Spinner distanceSpinner = (Spinner) view.findViewById(R.id.distance_select);

        final EditText shopNameText = (EditText) view.findViewById(R.id.name);
        CircleButton okBtn = (CircleButton) view.findViewById(R.id.ok);
        CircleButton noBtn = (CircleButton) view.findViewById(R.id.no);

        tasteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mShopItem.setTaste(
                        getActivity().getString(FoodHelper.getTasteIdAtPostion(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showToast(getString(R.string.shop_edit_nothing_select_tip));
            }
        });

        priceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mShopItem.setPrice(
                        getActivity().getString(FoodHelper.getPriceIdAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showToast(getString(R.string.shop_edit_nothing_select_tip));
            }
        });

        distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mShopItem.setDistance(
                        getActivity().getString(FoodHelper.getDistanceIdAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showToast(getString(R.string.shop_edit_nothing_select_tip));
            }
        });

        //set adapters for these spinner
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.taste_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tasteSpinner.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.price_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceSpinner.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.distance_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(adapter);

        //set button listener
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shopName = shopNameText.getText().toString();
                if (shopName.trim().isEmpty()) {
                    showToast(getString(R.string.shop_edit_text_empty_toast));
                    return;
                }
                final ContentValues values = new ContentValues();
                values.put(FoodDatabase.ShopTable.NAME, shopName);
                values.put(FoodDatabase.ShopTable.PRICE, mShopItem.getPrice());
                values.put(FoodDatabase.ShopTable.TASTE_TYPE, mShopItem.getTaste());
                values.put(FoodDatabase.ShopTable.DISTANCE, mShopItem.getDistance());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().getContentResolver()
                                .insert(FoodDatabase.ShopTable.URI_SHOP_TABLE, values);
                    }
                }).run();
                getDialog().dismiss();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }

    private void showDeleteConfirmDialog(View view) {
        final ShopItem shopItem = getArguments().getParcelable(Constants.SHOP_ITEM);
        getDialog().setTitle((shopItem.getShopName()));
        TextView deleteView = (TextView) view.findViewById(R.id.delete);
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String selection = FoodDatabase.ShopTable._ID + "=?";
                        String[] selectionArgs = new String[] { Integer.toString(shopItem.getId())
                        };
                        getActivity().getContentResolver()
                                .delete(FoodDatabase.ShopTable.URI_SHOP_TABLE, selection,
                                        selectionArgs);
                    }
                }).run();
                getDialog().dismiss();
            }
        });
    }

    private void showToast(CharSequence msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
