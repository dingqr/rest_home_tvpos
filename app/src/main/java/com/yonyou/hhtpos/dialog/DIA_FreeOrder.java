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
import com.yonyou.hhtpos.widgets.FiltrationView;

import static com.yonyou.hhtpos.util.FiltrationUtil.getOrderOptions;
import static com.yonyou.hhtpos.util.FiltrationUtil.getRefundReason;

/**
 * 免单处理对话框
 * 作者：ybing on 2017/7/20 13:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_FreeOrder implements View.OnClickListener{
    /**上下文 */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**界面控件 */
    private RadioButton rbConfirmRefund;
    private ImageButton ibClose;
    private FiltrationView fvRefundReason;
    private FilterItemEntity filterItemEntity;
    private EditText etEnterRefundReason;

    public DIA_FreeOrder(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_free_order, null);
        mDialog.setContentView(mContentView);

        fvRefundReason =(FiltrationView) mContentView.findViewById(R.id.fv_refund_reason);
        rbConfirmRefund =(RadioButton) mContentView.findViewById(R.id.rb_confirm_refund);
        ibClose =(ImageButton) mContentView.findViewById(R.id.ib_close);
        etEnterRefundReason =(EditText) mContentView.findViewById(R.id.et_enter_refund_reason);

        ibClose.setOnClickListener(this);
        rbConfirmRefund.setOnClickListener(this);

        filterItemEntity = new FilterItemEntity();
        filterItemEntity.setTitle(mContext.getString(R.string.refund_reason));
        filterItemEntity.setOptions(getRefundReason());
        fvRefundReason.setData(filterItemEntity);

        if (fvRefundReason.getSelectedData().getOption() == "其他原因"){
            etEnterRefundReason.setFocusable(true);
            etEnterRefundReason.setFocusableInTouchMode(true);
            etEnterRefundReason.setLongClickable(true);
        }else{
            etEnterRefundReason.setFocusable(false);
            etEnterRefundReason.setFocusableInTouchMode(false);
            etEnterRefundReason.setLongClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_confirm_refund:
                break;
            default:
                break;
        }
    }
    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 680;
        lp.height= 600;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

}
