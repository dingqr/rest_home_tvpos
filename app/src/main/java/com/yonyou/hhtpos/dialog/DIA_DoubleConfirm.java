package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.DIA_Base;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 二次确认弹窗
 * 作者：liushuofei on 2017/7/1 10:33
 */
public class DIA_DoubleConfirm extends DIA_Base {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_double_confirm_right)
    TextView tvRight;

    private String title;
    private OnSelectedListener onSelectedListener;
    private String right;

    public DIA_DoubleConfirm(Context context, String title, OnSelectedListener onSelectedListener) {
        super(context);

        // 标题
        tvTitle.setText(StringUtil.getString(title));

        this.onSelectedListener = onSelectedListener;
    }
    public DIA_DoubleConfirm(Context context, String title,String right, OnSelectedListener onSelectedListener) {
        super(context);

        // 标题
        tvTitle.setText(StringUtil.getString(title));
        tvRight.setText(StringUtil.getString(right));

        this.onSelectedListener = onSelectedListener;
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
        return R.layout.dia_double_confirm;
    }

    @OnClick({R.id.rl_double_confirm_left, R.id.rl_double_confirm_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_double_confirm_left:
                if (null != mDialog){
                    mDialog.dismiss();
                }
                break;
            case R.id.rl_double_confirm_right:
                if (null != mDialog){
                    mDialog.dismiss();
                }

                if (null != onSelectedListener){
                    onSelectedListener.confirm();
                }
                break;
        }
    }

    public interface OnSelectedListener {
        void confirm();
    }
}
