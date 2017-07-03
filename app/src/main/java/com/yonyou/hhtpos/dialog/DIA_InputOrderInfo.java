package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.yonyou.hhtpos.R;


/**
 * Created by zj on 2017/6/27.
 * 邮箱：zjuan@yonyou.com
 * 描述：顾客预订录入
 */
public class DIA_InputOrderInfo {
    private Dialog mDialog;
    private View mContentView;
    private ImageView ivClose;
    private Context mContext;
    private RadioButton rbMan;
    private RadioButton rbWomen;

    public DIA_InputOrderInfo(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_input_orderinfo, null);
        ivClose = (ImageView) mContentView.findViewById(R.id.iv_close);
        rbMan = (RadioButton) mContentView.findViewById(R.id.rb_man);
        rbWomen = (RadioButton) mContentView.findViewById(R.id.rb_women);
        mDialog.setContentView(mContentView);

        initListener();


    }
    /**
     * 设置监听
     */
    private void initListener() {
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });

    }

    public Dialog show() {
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 600; // 设置宽度
        lp.height = 900;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        return mDialog;
    }

    public interface OnChoosStoreListener {
        void onChooseStore(String storeName);
    }

}
