package com.smart.framework.library.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.framework.library.R;


/**
 * 作者：liushuofei on 2016/12/12 15:34
 * 邮箱：lsf@yonyou.com
 */
public class ToastUtil {
    private View mView;
    private static Toast toast;

    private ToastUtil(Context context, String text, boolean isLong) {

        mView = LayoutInflater.from(context).inflate(R.layout.toast_center_view, null);
        TextView textView = (TextView) mView.findViewById(R.id.toast_txt);
        textView.setText(text);

        //每次创建Toast时先做一下判断
        //如果前面已经有Toast在显示，则只是更新toast内容，而不再创建，提升用户体验
        if (toast != null) {
            textView.setText(text);
        } else {
            toast = Toast.makeText(context, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        }

        toast.setView(mView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static ToastUtil makeText(Context context, String text, boolean isLong) {
        ToastUtil toastCustom = new ToastUtil(context, text, isLong);
        return toastCustom;
    }

}
