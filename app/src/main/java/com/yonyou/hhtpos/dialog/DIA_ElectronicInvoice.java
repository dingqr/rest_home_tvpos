package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.DIA_Base;

import butterknife.Bind;

/**
 * 电子发票弹窗
 * 作者：liushuofei on 2017/7/21 16:57
 */
public class DIA_ElectronicInvoice extends DIA_Base {

    @Bind(R.id.tv_dialog_title)
    TextView mDialogTitle;

    public DIA_ElectronicInvoice(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dia_electronic_invoice;
    }

    public Dialog getDialog() {
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        // lp.dimAmount = 0.0f; 背景灰度
        lp.width = 780; // 设置宽度
        lp.height = 766; // 设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialogTitle.setText(mContext.getString(R.string.electronic_invoice));
        return mDialog;
    }
}
