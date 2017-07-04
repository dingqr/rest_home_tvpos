package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.DIA_Base;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 一次确认弹窗
 * 作者：liushuofei on 2017/7/1 09:29
 */
public class DIA_OnceConfirm extends DIA_Base {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_desc)
    TextView tvDesc;

    public DIA_OnceConfirm(Context context, String title, String desc) {
        super(context);

        // 标题
        tvTitle.setText(StringUtil.getString(title));
        // 描述
        tvDesc.setText(StringUtil.getString(desc));
    }

    public Dialog getDialog() {
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        // lp.dimAmount = 0.0f; 背景灰度
        lp.width = 600; // 设置宽度
        lp.height = 360; // 设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dia_once_confirm;
    }

    @OnClick(R.id.tv_confirm)
    public void onClick() {
        mDialog.dismiss();
    }
}
