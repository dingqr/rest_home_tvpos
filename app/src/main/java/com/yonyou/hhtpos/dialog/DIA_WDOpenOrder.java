package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.DIA_Base;

import butterknife.Bind;

/**
 * 作者：liushuofei on 2017/7/3 19:38
 * 邮箱：lsf@yonyou.com
 */
public class DIA_WDOpenOrder extends DIA_Base implements View.OnClickListener{

    @Bind(R.id.tv_dialog_title)
    TextView mDialogTitle;
    @Bind(R.id.iv_close)
    ImageView mClose;

    public DIA_WDOpenOrder(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dia_take_out_info;
    }

    public Dialog getDialog() {
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        // lp.dimAmount = 0.0f; 背景灰度
        lp.width = 620; // 设置宽度
        lp.height = 500; // 设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialogTitle.setText(mContext.getString(R.string.packing_info));
        mClose.setOnClickListener(this);
        return mDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close:
                if (null != mDialog){
                    mDialog.dismiss();
                }
                break;

            default:
                break;
        }
    }
}
