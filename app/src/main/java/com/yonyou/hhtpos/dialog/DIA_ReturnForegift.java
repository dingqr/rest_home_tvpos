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

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.PayTypeEntity;
import com.yonyou.hhtpos.widgets.NumberKeybordView;

import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * Created by zj on 2017/7/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：退预定押金弹窗
 */
public class DIA_ReturnForegift {
    private EditText etMoney;
    private Dialog mDialog;
    private View mContentView;
    private ImageView ivClose;
    private Context mContext;
    private NumberKeybordView numberGridView;
    private String[] payTypes = {"现金支付", "畅捷支付", "支付宝", "微信支付"};
    private ArrayList<PayTypeEntity> payTypeList = new ArrayList<>();

    public DIA_ReturnForegift(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dia_return_foregift, null);
        numberGridView = (NumberKeybordView) mContentView.findViewById(R.id.number_keybord_view);
        ivClose = (ImageView) mContentView.findViewById(R.id.iv_close);
        mDialog.setContentView(mContentView);

        initData();
        initListener();


        //不使用系统软件盘输入
//        disableShowInput(etMoney);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (int i = 0; i < payTypes.length; i++) {
            PayTypeEntity payTypeEntity = new PayTypeEntity();
            payTypeEntity.pay_type_name = payTypes[i];
            payTypeList.add(payTypeEntity);
        }
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
        lp.height = 910;//设置高度
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
