package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.DIA_Base;

/**
 * 升级弹窗
 * 作者：liushuofei on 2017/8/8 11:29
 */
public class DIA_Upgrade extends DIA_Base {

    public DIA_Upgrade(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dia_upgrade;
    }

    public Dialog getDialog() {
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        // lp.dimAmount = 0.0f; 背景灰度
        lp.width = 560; // 设置宽度
        lp.height = 550; // 设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }
}
