package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.widgets.NumberKeybordView;

import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by zj on 2017/7/27.
 * 邮箱：zjuan@yonyou.com
 * 描述：服务员结账-现金结账弹窗
 */
public class DIA_CheckOutByCash {
    @Bind(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @Bind(R.id.et_money)
    EditText etMoney;
    @Bind(R.id.rb_confirm_receive_money)
    RadioButton rbConfirmRreceiveMoney;
    private Dialog mDialog;
    private View mContentView;
    private ImageView ivClose;
    private Context mContext;
    private NumberKeybordView numberGridView;
    private OnReceiveMoneyListener mListener;
    private String mMaxMoney;

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


//        InputManager.getInstances(mContext).hideSoftInput(etMoney);
        //不使用系统软件盘输入
        disableShowInput(etMoney);

    }

    /**
     * 设置最大可输入的金额
     *
     * @param maxMoney
     */
    public void setMaxInputMoneyHint(String maxMoney) {
        this.mMaxMoney = maxMoney;
        etMoney.setHint(mContext.getResources().getString(R.string.string_unpaid_money) + mContext.getResources().getString(R.string.RMB_symbol) + maxMoney);
        etMoney.setText(maxMoney);
        numberGridView.setEtMoney(etMoney);
        numberGridView.setInputMode(NumberKeybordView.DECIMAL);
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
    }

    @OnClick({R.id.rb_confirm_receive_money})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_confirm_receive_money:
                if (TextUtils.isEmpty(etMoney.getText().toString())) {
                    CommonUtils.makeEventToast(mContext, mContext.getResources().getString(R.string.string_format_money), false);
                    return;
                }
                double et_money = Double.parseDouble(etMoney.getText().toString());
                if (!TextUtils.isEmpty(mMaxMoney) && et_money > Double.parseDouble(mMaxMoney)) {
                    CommonUtils.makeEventToast(mContext, mContext.getResources().getString(R.string.string_format_money), false);
                    return;
                }
                if (mListener != null) {
                    mListener.onReceiveMoney(etMoney.getText().toString());
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                }
                break;
        }
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
//        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(etMoney.getWindowToken(), 0);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                    boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(etMoney, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 收到钱的点击事件回调
     */
    public interface OnReceiveMoneyListener {
        void onReceiveMoney(String money);
    }

    public void setOnReceiveMoneyListener(OnReceiveMoneyListener listener) {
        this.mListener = listener;
    }
}
