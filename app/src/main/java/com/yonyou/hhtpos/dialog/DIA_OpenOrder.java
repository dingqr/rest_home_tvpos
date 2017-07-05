package com.yonyou.hhtpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.yonyou.framework.library.common.utils.ScreenUtil;
import com.yonyou.hhtpos.R;

/**
 * 开单对话框
 * 作者：ybing on 2017/6/29 13:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_OpenOrder implements View.OnClickListener{
    /**上下文 */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    public DIA_OpenOrder(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_open_order, null);
        mDialog.setContentView(mContentView);
    }

    @Override
    public void onClick(View v) {

    }
    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
//        lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 9; // 设置宽度
//        lp.height = ScreenUtil.getScreenHeight((Activity)mContext)/ 10 * 8; // 设置高度
        lp.width = 620;
        lp.height= 728;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }
}
