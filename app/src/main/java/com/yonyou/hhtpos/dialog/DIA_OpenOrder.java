package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.wm.OpenOrderEntity;

/**
 * 开单对话框
 * 作者：ybing on 2017/6/29 13:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_OpenOrder implements View.OnClickListener{
    /**上下文 */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**页面控件*/
    private TextView tvTitle;
    private EditText etUserNumber;
    private EditText etUserPhoneName;
    private EditText etReserveOrderId;

    public DIA_OpenOrder(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_open_order, null);
        mDialog.setContentView(mContentView);

        tvTitle = (TextView) mContentView.findViewById(R.id.tv_title);
        etUserNumber = (EditText) mContentView.findViewById(R.id.et_user_number);
        etUserPhoneName = (EditText) mContentView.findViewById(R.id.et_user_phone_name);
        etReserveOrderId = (EditText) mContentView.findViewById(R.id.et_reserve_order_id);
    }

    @Override
    public void onClick(View v) {

    }
    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 620;
        lp.height= 728;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    /**
     * 获取开单数据后传递数据用的接口
     */
    public interface TSCallback {
        void sendTsEntity(OpenOrderEntity wmOpenOrderEntity);
    }
}
