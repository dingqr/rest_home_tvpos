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

/**
 * 会员登录弹框
 * 作者：ybing on 2017/8/2 15:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_VipLogin implements View.OnClickListener{
    /**
     * 上下文
     */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**
     * 界面控件
     */
    private RadioButton rbCheckOut;
    private ImageButton ibClose;
    private EditText etVipPhone;
    private EditText etVipPassword;

    public DIA_VipLogin(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_vip_login, null);
        mDialog.setContentView(mContentView);

        rbCheckOut = (RadioButton) mContentView.findViewById(R.id.rb_no_vip_check_out);
        ibClose = (ImageButton) mContentView.findViewById(R.id.ib_close);
        etVipPhone = (EditText) mContentView.findViewById(R.id.et_vip_phone);
        etVipPassword = (EditText) mContentView.findViewById(R.id.et_vip_password);

        ibClose.setOnClickListener(this);
        rbCheckOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_no_vip_check_out:

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