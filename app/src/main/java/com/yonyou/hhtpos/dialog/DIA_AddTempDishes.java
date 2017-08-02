package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.DIA_Base;
import com.yonyou.hhtpos.widgets.DialogInputView;

import butterknife.Bind;

/**
 * Created by zj on 2017/7/13.
 * 邮箱：zjuan@yonyou.com
 * 描述：添加临时菜弹窗
 */
public class DIA_AddTempDishes extends DIA_Base implements View.OnClickListener {

    @Bind(R.id.tv_dialog_title)
    TextView mDialogTitle;
    @Bind(R.id.iv_close)
    ImageView mClose;
    @Bind(R.id.input_dish_name)
    DialogInputView inputDishName;
    @Bind(R.id.input_dish_price)
    DialogInputView inputDishPrice;
    @Bind(R.id.rb_confirm_add)
    RadioButton rbConfirmAdd;
    private OnAddTempDishResultListener mListener;

    public DIA_AddTempDishes(Context context) {
        super(context);
        inputDishName.getInputEdit().setInputType(InputType.TYPE_CLASS_TEXT); //输入类型
        inputDishName.getInputEdit().setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)}); //最大输入长度
        inputDishPrice.getInputEdit().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); //输入类型
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dia_add_temp_dishes;
    }

    public Dialog getDialog() {
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        // lp.dimAmount = 0.0f; 背景灰度
        lp.width = 620; // 设置宽度
        lp.height = 500; // 设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialogTitle.setText(mContext.getString(R.string.string_add_temp_dish));
        mClose.setOnClickListener(this);
        rbConfirmAdd.setOnClickListener(this);
        return mDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                if (null != mDialog) {
                    mDialog.dismiss();
                }
                break;
            case R.id.rb_confirm_add:
                if (doValidNoEmpty() && mListener != null) {
                    mListener.tempDishResult(inputDishName.getInputText(), inputDishPrice.getInputText());
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                }
                break;
        }
    }

    /**
     * @return
     */
    private boolean doValidNoEmpty() {
        if (TextUtils.isEmpty(inputDishName.getInputText())) {
            CommonUtils.makeEventToast(mContext, mContext.getResources().getString(R.string.string_please_input_dish_name), false);
            return false;
        }
        if (TextUtils.isEmpty(inputDishPrice.getInputText())) {
            CommonUtils.makeEventToast(mContext, mContext.getResources().getString(R.string.string_input_dish_price), false);
            return false;
        }
        return true;
    }

    /**
     * 临时菜信息结果回调
     */
    public interface OnAddTempDishResultListener {
        void tempDishResult(String tempDishName, String tempDishPrice);
    }

    public void setOnAddTempDishResultListener(OnAddTempDishResultListener listener) {
        this.mListener = listener;
    }
}
