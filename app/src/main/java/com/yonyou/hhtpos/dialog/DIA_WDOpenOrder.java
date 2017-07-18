package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.DIA_Base;
import com.yonyou.hhtpos.util.VerifyUtil;
import com.yonyou.hhtpos.widgets.DialogInputView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：liushuofei on 2017/7/3 19:38
 * 邮箱：lsf@yonyou.com
 */
public class DIA_WDOpenOrder extends DIA_Base implements View.OnClickListener {

    @Bind(R.id.tv_dialog_title)
    TextView mDialogTitle;
    @Bind(R.id.iv_close)
    ImageView mClose;
    @Bind(R.id.div_dinner_count)
    DialogInputView divDinnerCount;
    @Bind(R.id.div_phone_number)
    DialogInputView divPhoneNumber;

    private OnSelectedListener mOnSelectedListener;

    public DIA_WDOpenOrder(Context context, OnSelectedListener onSelectedListener) {
        super(context);

        mOnSelectedListener = onSelectedListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dia_take_out_info;
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
        mDialogTitle.setText(mContext.getString(R.string.packing_info));
        mClose.setOnClickListener(this);
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

            default:
                break;
        }
    }

    @OnClick(R.id.rb_confirm)
    public void onClick() {
        String dinnerCount = divDinnerCount.getInputText();
        String phone = divPhoneNumber.getInputText();

        if (TextUtils.isEmpty(dinnerCount)){
            CommonUtils.makeEventToast(mContext, divDinnerCount.getHint(), false);
            return;
        }

        if (TextUtils.isEmpty(phone)){
            CommonUtils.makeEventToast(mContext, divPhoneNumber.getHint(), false);
            return;
        }

        if (!VerifyUtil.checkMobile(phone)){
            CommonUtils.makeEventToast(mContext, "手机号不合法", false);
            return;
        }

        mOnSelectedListener.confirm(dinnerCount, phone);


        if (null != mDialog) {
            mDialog.dismiss();
        }
    }

    public interface OnSelectedListener{
        void confirm(String dinnerCount, String phone);
    }
}
