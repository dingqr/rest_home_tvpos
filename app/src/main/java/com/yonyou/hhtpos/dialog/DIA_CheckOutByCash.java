package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.widgets.NumberKeybordView;

import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：服务员点菜-菜品转台弹窗
 */
public class DIA_CheckOutByCash {
    @Bind(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    private Dialog mDialog;
    private View mContentView;
    private ImageView ivClose;
    private Context mContext;
    private NumberKeybordView numberGridView;

    public DIA_CheckOutByCash(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dia_checkout_by_cash, null);
        ButterKnife.bind(this, mContentView);
        numberGridView = (NumberKeybordView) mContentView.findViewById(R.id.number_keybord_view);
        ivClose = (ImageView) mContentView.findViewById(R.id.iv_close);
        mDialog.setContentView(mContentView);

        tvDialogTitle.setText(mContext.getResources().getString(R.string.string_check_out_cash));
        initListener();


        //不使用系统软件盘输入
//        disableShowInput(etMoney);

    }

    private void initListener() {

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });
        //数字键盘点击
        numberGridView.setOnKeybordListener(new NumberKeybordView.onKeybordClickListener() {
            @Override
            public void onNumberReturn(String number) {
                CommonUtils.makeEventToast(mContext, number, false);
            }

            @Override
            public void clearNumber() {
                CommonUtils.makeEventToast(mContext, "清除文本", false);
            }

            @Override
            public void onLongClickClearAllNumber(boolean b) {

            }
        });
    }

    public Dialog show() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 1200; // 设置宽度
//        lp.height = 910;//设置高度
        lp.height = 950;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        return mDialog;
    }

    /**
     * 禁止使用系统软件盘
     *
     * @param editText
     */
    public void disableShowInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
            }
        }
    }
}
