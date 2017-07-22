package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.dish.WMRefundFreeReasonCallbackEntity;
import com.yonyou.hhtpos.util.WMReasonsCallback;
import com.yonyou.hhtpos.widgets.FiltrationView;

/**
 * 外卖退款处理对话框
 * 作者：ybing on 2017/7/7 13:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_TakeOutRefund implements View.OnClickListener, FiltrationView.SelectCallback {
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
    private FiltrationView fvRefundReason;

    /**
     * 选项数据
     */
    private FilterItemEntity filterItemEntity;
    private EditText etEnterRefundReason;

    /**
     * 数据回调接口
     */
    private WMReasonsCallback wmReasonsCallback;
    /**
     * 数据回调数据状态
     */
    private boolean flag = true;

    public DIA_TakeOutRefund(Context mContext, FilterItemEntity filterItemEntity) {
        this.mContext = mContext;
        this.filterItemEntity = filterItemEntity;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_take_out_refund, null);
        mDialog.setContentView(mContentView);

        fvRefundReason = (FiltrationView) mContentView.findViewById(R.id.fv_refund_reason);
        fvRefundReason.setData(filterItemEntity);
        fvRefundReason.setSelectCallback(this);

        rbConfirmRefund = (RadioButton) mContentView.findViewById(R.id.rb_confirm_refund);
        ibClose = (ImageButton) mContentView.findViewById(R.id.ib_close);
        etEnterRefundReason = (EditText) mContentView.findViewById(R.id.et_enter_refund_reason);

        ibClose.setOnClickListener(this);
        rbConfirmRefund.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_confirm_refund:
                WMRefundFreeReasonCallbackEntity reasonCallbackEntity = initReasonCallbackEntity();
                if (flag) {
                    if (wmReasonsCallback != null) {
                        wmReasonsCallback.sendItems(reasonCallbackEntity);
                    }
                    fvRefundReason.reset();
                    etEnterRefundReason.setText("");
                    mDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    private WMRefundFreeReasonCallbackEntity initReasonCallbackEntity() {
        WMRefundFreeReasonCallbackEntity reasonCallbackEntity = new WMRefundFreeReasonCallbackEntity();
        String otherRemark = etEnterRefundReason.getText().toString().trim();

        if (fvRefundReason.getSelectedData() != null ) {
            String reason = fvRefundReason.getSelectedData().getOption();
            reasonCallbackEntity.setRefundReason(reason);
            flag = true;
            //退款原因
            reasonCallbackEntity.setRefundReason(String.valueOf(reason));
            if (!TextUtils.isEmpty(otherRemark)) {
                //其他原因
                reasonCallbackEntity.setOtherReason(otherRemark);
            }
        } else {
            flag = false;
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_refund_reason), false);
        }
        return reasonCallbackEntity;
    }

    public Dialog getDialog() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 680;
        lp.height = 600;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    @Override
    public void sendSelectedItem(FilterOptionsEntity foe) {
        if (foe != null && (mContext.getString(R.string.other_reason).equals(foe.getOption())
                || mContext.getString(R.string.other_reason) == (foe.getOption()))) {
            etEnterRefundReason.setFocusable(true);
            etEnterRefundReason.setFocusableInTouchMode(true);
            etEnterRefundReason.setLongClickable(true);
        } else {
            etEnterRefundReason.setFocusable(false);
            etEnterRefundReason.setFocusableInTouchMode(false);
            etEnterRefundReason.setLongClickable(false);
        }
    }

    public void setWmReasonsCallback(WMReasonsCallback wmReasonsCallback) {
        this.wmReasonsCallback = wmReasonsCallback;
    }
}
