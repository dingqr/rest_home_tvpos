package com.smart.framework.library.loading;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.smart.framework.library.R;


/**
 * 作者：addison on 11/12/15 14:32
 * 邮箱：lsf@yonyou.com
 * 背景可见的loading dialog,内容可扩展，只需传入自定义的view布局
 * 框架中提供了
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 构造函数
     *
     * @param context
     *            上下文
     * @param cancelable
     *            返回键是否可以取消对话框
     */
    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /** 定义Dialog构造器 */
    public static class Builder {

        /** Context上下文 */
        private Context mContext;
        /** 对话框布局 */
        private View mContentView;
        /** 对话框显示提示内容 */
        private TextView mMessage;
        /** 分割线 */
        private View mLine;
        /** 是否可以按返回键取消对话框 */
        private boolean mCancelable = false;

        /**
         * 构造函数
         *
         * @param context
         *            上下文
         */
        public Builder(Context context) {
            mContext = context;
            LayoutInflater inflater = LayoutInflater.from(mContext);
            mContentView = inflater.inflate(R.layout.dialog_loading, null);
            mMessage = (TextView) mContentView.findViewById(R.id.message_txt);
        }


        /**
         * 设置提示信息
         *
         * @param message
         *            提示信息
         * @return 当前对象
         */
        public Builder setMessage(String message) {
            if (null != message && null != mMessage){
                mMessage.setText(message);
            }
            return this;
        }


        /**
         * 是否可以返回取消对话框
         * @param able
         */
        public Builder setCancelable(boolean able) {
            mCancelable = able;
            return this;
        }

        /**
         * 创建对话框
         *
         * @return 自定义对话框实例
         */
        public LoadingDialog create() {
            LoadingDialog dialog = new LoadingDialog(mContext, R.style.BaseDialog);
            dialog.setContentView(mContentView);
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }

}
