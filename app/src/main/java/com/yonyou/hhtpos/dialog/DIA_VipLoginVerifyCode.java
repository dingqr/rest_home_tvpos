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
import android.widget.TextView;

import com.yonyou.hhtpos.R;

/**
 * 会员用验证码登录弹框
 * 作者：ybing on 2017/8/2 15:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_VipLoginVerifyCode implements View.OnClickListener{
    /**
     * 上下文
     */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**
     * 界面控件
     */
    private RadioButton rbSubmit;
    private ImageButton ibClose;
    private EditText etVipPhone;
    private EditText etVipPassword;
    private TextView tvGetVerifyCode;
    private TextView tvLoginThroughPassword;

    public DIA_VipLoginVerifyCode(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_vip_login_verify_code, null);
        mDialog.setContentView(mContentView);

        rbSubmit = (RadioButton) mContentView.findViewById(R.id.rb_vip_login_submit);
        ibClose = (ImageButton) mContentView.findViewById(R.id.ib_close);
        etVipPhone = (EditText) mContentView.findViewById(R.id.et_vip_phone);
        etVipPassword = (EditText) mContentView.findViewById(R.id.et_vip_password);
        tvGetVerifyCode = (TextView) mContentView.findViewById(R.id.tv_get_verify_code);
        tvLoginThroughPassword = (TextView) mContentView.findViewById(R.id.tv_login_through_password);

        ibClose.setOnClickListener(this);
        rbSubmit.setOnClickListener(this);
        tvGetVerifyCode.setOnClickListener(this);
        tvLoginThroughPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_vip_login_submit:
                break;
            case R.id.tv_get_verify_code:
                break;
            case R.id.tv_login_through_password:
                break;
            default:
                break;
        }
    }

    public Dialog getDialog() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 880;
        lp.height = 642;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

}