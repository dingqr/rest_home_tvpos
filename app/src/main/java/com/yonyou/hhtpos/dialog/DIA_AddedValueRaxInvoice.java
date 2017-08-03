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
import android.widget.TextView;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.RaxInvoiceCallBackEntity;
import com.yonyou.hhtpos.bean.dish.WMRefundFreeReasonCallbackEntity;
import com.yonyou.hhtpos.interfaces.RaxCallback;

/**
 * 普通增值税发票 纸质票 弹框
 * 作者：ybing on 2017/8/2 17:32
 * 邮箱：ybing@yonyou.com
 */

public class DIA_AddedValueRaxInvoice implements View.OnClickListener {
    /**
     * 上下文
     */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**
     * 界面控件
     */
    private RadioButton rbSave;
    private ImageButton ibClose;
    private TextView tvRaxId;
    private EditText etInvoiceTitle;

    /**
     * 数据回调接口
     */
    private RaxCallback raxCallback;
    /**
     * 数据回调数据状态
     */
    private boolean flag = true;

    public DIA_AddedValueRaxInvoice(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_added_value_rax_invoice, null);
        mDialog.setContentView(mContentView);

        rbSave = (RadioButton) mContentView.findViewById(R.id.rb_save);
        ibClose = (ImageButton) mContentView.findViewById(R.id.ib_close);
        tvRaxId = (TextView) mContentView.findViewById(R.id.tv_rax_id);
        etInvoiceTitle = (EditText) mContentView.findViewById(R.id.et_invoice_title);

        ibClose.setOnClickListener(this);
        rbSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_confirm_refund:
                mDialog.dismiss();
                break;
            default:
                break;
        }
    }

//    private RaxInvoiceCallBackEntity initRaxCallbackEntity() {
//        RaxInvoiceCallBackEntity raxInvoiceCallBackEntity = new RaxInvoiceCallBackEntity();
//        String otherRemark = etInvoiceTitle.getText().toString().trim();
//
//        if (fvRefundReason.getSelectedData() != null ) {
//            String reason = fvRefundReason.getSelectedData().getOption();
//            reasonCallbackEntity.setRefundReason(reason);
//            flag = true;
//            //退款原因
//            reasonCallbackEntity.setRefundReason(String.valueOf(reason));
//            if (!TextUtils.isEmpty(otherRemark)) {
//                //其他原因
//                reasonCallbackEntity.setOtherReason(otherRemark);
//            }
//        } else {
//            flag = false;
//            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_refund_reason), false);
//        }
//        return reasonCallbackEntity;
//    }

    public Dialog getDialog() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 660;
        lp.height = 508;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    public void setRaxCallback(RaxCallback raxCallback) {
        this.raxCallback = raxCallback;
    }
}
