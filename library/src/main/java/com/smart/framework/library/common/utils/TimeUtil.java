package com.smart.framework.library.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.smart.framework.library.R;


public class TimeUtil extends CountDownTimer {

    private TextView view;
    private int color;
    private String txt;
    private Context context;

    public TimeUtil(long millisInFuture, long countDownInterval, Context context) {
        super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        this.context = context;
    }

    public void setView(TextView view) {
        this.view = view;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onTick(long millisUntilFinished) {// 计时过程显示
        view.setEnabled(false);
        view.setTextColor(ContextCompat.getColor(context, R.color.color_4e4e4e));
        String text = context.getString(R.string.string_re_try);
        text += "(" + millisUntilFinished / 1000 + "s" + ")";
        view.setText(text);
    }

    @Override
    public void onFinish() {// 计时完毕时触发
        view.setEnabled(true);
        view.setText(txt);
        view.setTextColor(color);
    }

}
