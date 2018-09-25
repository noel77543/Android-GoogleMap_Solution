package com.sung.noel.tw.googlemapsolution.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.sung.noel.tw.googlemapsolution.R;
import com.sung.noel.tw.googlemapsolution.main.model.PlaceInfo;

/**
 * Created by noel on 2017/9/20.
 */

public class TargetChooseDialog extends Dialog {

    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.number_picker)
    NumberPicker numberPicker;

    private OnAcceptClickListener acceptClickListener;
    //-----------------------------------------------

    public TargetChooseDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_target_choose);
        ButterKnife.bind(this);
        initStationPicker();
        setDialogGravity();
    }
    //-----------------------------------------------

    public void setTitleType(String title) {
        tvType.setText(title);
    }

    //-----------------------------------------------

    /**
     * 控制dialog位置及大小
     */
    private void setDialogGravity() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams latouyParams = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(latouyParams);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

    }


    //------------------

    /**
     * 設置numberPicker的內容
     */
    public void setStationData(PlaceInfo placeInfo) {
        String[] names = getPlaceNamesArray(placeInfo);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(names.length - 1);
        numberPicker.setDisplayedValues(names);
    }
    //-----------------------------

    /**
     * 取得名稱
     */
    private String[] getPlaceNamesArray(PlaceInfo placeInfo) {
        String[] names = new String[placeInfo.getResults().size()];
        for (int i = 0; i < placeInfo.getResults().size(); i++) {
            names[i] = placeInfo.getResults().get(i).getName();
        }
        return names;
    }
    //------------------

    /**
     * 初始化numberPicker設定
     */
    private void initStationPicker() {

        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        try {
            Class internalRID = Class.forName("com.android.internal.R$id");
            Field edit = internalRID.getField("numberpicker_input");
            EditText editText = (EditText) numberPicker.findViewById(edit.getInt(null));
            editText.setVisibility(View.GONE);
            editText.setClickable(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //-----------------------------------------------

    @OnClick({R.id.btn_accept})
    public void onViewClicked(View view) {
        acceptClickListener.OnAcceptClick(numberPicker.getValue());
        dismiss();
    }

    //-----------------------
    // 點擊事件
    public void setOnAcceptClickListener(OnAcceptClickListener acceptClickListener) {
        this.acceptClickListener = acceptClickListener;
    }

    public interface OnAcceptClickListener {
        void OnAcceptClick(int index);
    }
}