package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.widgets.FiltrationView;

import static com.yonyou.hhtpos.util.FiltrationUtil.getOrderOptions;
import static com.yonyou.hhtpos.util.FiltrationUtil.getRefundReason;

/**
 * 免单处理对话框
 * 作者：ybing on 2017/7/20 13:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_FreeOrder implements View.OnClickListener,FiltrationView.SelectCallback {
    /**
     * 上下文
     */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**
     * 界面控件
     */
    private RadioButton rbConfirmRefund;
    private ImageButton ibClose;
    private FiltrationView fvFreeReason;
    private FilterItemEntity freeOrderReason;
    private EditText etEnterFreeReason;

    public DIA_FreeOrder(Context mContext, FilterItemEntity freeOrderReason) {
        this.mContext = mContext;
        this.freeOrderReason = freeOrderReason;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_free_order, null);
        mDialog.setContentView(mContentView);

        fvFreeReason = (FiltrationView) mContentView.findViewById(R.id.fv_free_reason);
        rbConfirmRefund = (RadioButton) mContentView.findViewById(R.id.rb_confirm_refund);
        ibClose = (ImageButton) mContentView.findViewById(R.id.ib_close);
        etEnterFreeReason = (EditText) mContentView.findViewById(R.id.et_enter_free_reason);

        ibClose.setOnClickListener(this);
        rbConfirmRefund.setOnClickListener(this);


        fvFreeReason.setData(freeOrderReason);
        fvFreeReason.setSelectCallback(this);

        etEnterFreeReason.setFocusable(false);
        etEnterFreeReason.setFocusableInTouchMode(false);
        etEnterFreeReason.setLongClickable(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_confirm_refund:
                break;
            default:
                break;
        }
    }

    public Dialog getDialog() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 830;
        lp.height = 680;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    @Override
    public void sendSelectedItem(FilterOptionsEntity foe) {
        if (foe != null && (mContext.getString(R.string.other_reason).equals(foe.getOption())
                || mContext.getString(R.string.other_reason)==(foe.getOption()))) {
                etEnterFreeReason.setFocusable(true);
                etEnterFreeReason.setFocusableInTouchMode(true);
                etEnterFreeReason.setLongClickable(true);
        }else{
            etEnterFreeReason.setFocusable(false);
            etEnterFreeReason.setFocusableInTouchMode(false);
            etEnterFreeReason.setLongClickable(false);
        }
    }
}