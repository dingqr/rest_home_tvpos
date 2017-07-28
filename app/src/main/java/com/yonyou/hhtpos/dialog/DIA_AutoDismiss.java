package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.hhtpos.R;

/**
 * Created by zj on 2017/6/26.
 * 邮箱：zjuan@yonyou.com
 * 描述：3秒后自动关闭的弹窗
 */
public class DIA_AutoDismiss {
    protected Dialog mDialog;
    protected View mContentView;
    protected Context mContext;
    private ImageView ivIcon;
    private TextView lineOneContent;
    private TextView lineTwoContent;

    public DIA_AutoDismiss(Context context, String str) {
        this(context, 0, str, "");
    }

    public DIA_AutoDismiss(Context context, int resId, String lineOne, String lineTwo) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_auto_dismiss, null);
        //设置图片
        ivIcon = (ImageView) mContentView.findViewById(R.id.iv_icon);
        //设置第一行字
        lineOneContent = (TextView) mContentView.findViewById(R.id.line_one_content);
        //设置第二行字
        lineTwoContent = (TextView) mContentView.findViewById(R.id.line_two_content);
        //resId传0,则不显示图片
        if (resId != 0) {
            ivIcon.setVisibility(View.VISIBLE);
            ivIcon.setImageResource(R.drawable.ic_error_icon);
        } else {
            ivIcon.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(lineOne)) {
            lineOneContent.setVisibility(View.GONE);
        } else {
            lineOneContent.setVisibility(View.VISIBLE);
            lineOneContent.setText(lineOne);
        }
        if (TextUtils.isEmpty(lineTwo)) {
            lineTwoContent.setVisibility(View.GONE);
        } else {
            lineTwoContent.setVisibility(View.VISIBLE);
            lineTwoContent.setText(lineTwo);
        }

        mDialog.setContentView(mContentView);
    }

    public Dialog show() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.0f; //背景灰度 -0.0全透明
//        lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 9; // 设置宽度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lp.alpha = 0.8f;//dialog内容的透明度
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
            }
        }, 3000);
        return mDialog;
    }

    /**
     * 设置弹窗的内容
     *
     * @param context
     * @param resId
     * @param lineOne
     * @param lineTwo
     * @return
     */
    public static DIA_AutoDismiss setDialogContent(Context context, int resId, String lineOne, String lineTwo) {
        return new DIA_AutoDismiss(context, resId, lineOne, lineTwo);
    }
}
