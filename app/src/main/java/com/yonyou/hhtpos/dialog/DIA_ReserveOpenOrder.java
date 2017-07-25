package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.utils.ReturnObject;
import com.yonyou.framework.library.common.utils.ValidateRule;
import com.yonyou.framework.library.common.utils.Validator;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.bean.WaiterEntity;
import com.yonyou.hhtpos.bean.ts.OpenOrderEntity;

/**
 * 预定单开单对话框
 * 作者：ybing on 2017/6/29 13:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_ReserveOpenOrder implements View.OnClickListener, DIA_ChooseWaiter.OnWaiterSelectedListener {
    /**
     * 上下文
     */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**
     * 页面控件
     */
    private TextView tvTitle;
    private EditText etUserNumber;
    private EditText etUserPhone;
    private EditText etReserveOrderId;
    private EditText etWaiter;
    private RadioButton rbConfirmOpenOrder;

    /**
     * 开单数据接口
     */
    private TSCallback tsCallback;

    /**
     * 开单数据
     */
    private String dinnerNumber;
    private String userPhone;
    private WaiterEntity waiterEntity;
    private String reserveOrderId;
    private String waiter;

    public DIA_ReserveOpenOrder(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    public DIA_ReserveOpenOrder setData(CanteenTableEntity canteenTableEntity) {
        if (canteenTableEntity != null) {
            if (!TextUtils.isEmpty(canteenTableEntity.tableName)) {
                tvTitle.setText(mContentView.getResources().getString(R.string.canteen_billing) + "(" + canteenTableEntity.tableName + ")");
            } else {
                tvTitle.setText(mContentView.getResources().getString(R.string.canteen_billing));
            }
           //TODO 设置预订单号码
        }
        return this;
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_reserve_open_order, null);
        mDialog.setContentView(mContentView);

        tvTitle = (TextView) mContentView.findViewById(R.id.tv_title);
        etUserNumber = (EditText) mContentView.findViewById(R.id.et_user_number);
        etUserPhone = (EditText) mContentView.findViewById(R.id.et_user_phone);
        etReserveOrderId = (EditText) mContentView.findViewById(R.id.et_reserve_order_id);
        etWaiter = (EditText) mContentView.findViewById(R.id.et_waiter);
        rbConfirmOpenOrder = (RadioButton) mContentView.findViewById(R.id.rb_confirm_open_order);

        rbConfirmOpenOrder.setOnClickListener(this);
        etWaiter.setOnClickListener(this);
    }

    private boolean verifyInput() {
        userPhone = etUserPhone.getText().toString().trim();
        waiter = etWaiter.getText().toString().trim();
        dinnerNumber = etUserNumber.getText().toString().trim();

        if (TextUtils.isEmpty(waiter)) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.waiter_name_empty), false);
            return false;
        }
        if (!doValidatePhone()) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.user_name_empty), false);
            return false;
        }
        if (TextUtils.isEmpty(dinnerNumber)) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.receiver_num_empty), false);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_confirm_open_order:
                OpenOrderEntity tsooe = initEntity();
                if (tsCallback != null && tsooe != null) {
                    tsCallback.sendTsEntity(tsooe);
                    mDialog.dismiss();
                }
                break;
            case R.id.et_waiter:
                DIA_ChooseWaiter dia_chooseWaiter = new DIA_ChooseWaiter(mContext);
                dia_chooseWaiter.setOnWaiterSelectedListener(this);
                dia_chooseWaiter.show();
                break;
            default:
                break;
        }

    }

    private OpenOrderEntity initEntity() {
        OpenOrderEntity tsooe = new OpenOrderEntity();
        userPhone = etUserPhone.getText().toString().trim();
        if (verifyInput()) {

            tsooe.setPersonNum(dinnerNumber);
            tsooe.setMemberId(userPhone);
            tsooe.setWaiterId(waiterEntity.waiterId);

            return tsooe;
        } else {
            return null;
        }
    }

    public Dialog getDialog() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 620;
        lp.height = 728;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    @Override
    public void onWaiterSelected(WaiterEntity waiterEntity) {
        if (waiterEntity != null) {
            this.waiterEntity = waiterEntity;
            etWaiter.setText(waiterEntity.waiterName);
        }
    }


    /**
     * 验证手机号 是否为空，是否满足手机号的规则
     *
     * @return
     */
    private boolean doValidatePhone() {
        Validator validator = new Validator();
        TextView[] widgets = new TextView[]{etUserPhone};
        validator.addRules(ValidateRule.IS_NOT_EMPTY);
        for (TextView w : widgets) {
            ReturnObject ro = validator.val(w.getText());
            if (!ro.isSuccess) {
                CommonUtils.makeEventToast(mContext, w.getHint().toString(), false);
                w.requestFocus();
                return false;
            }
        }
        validator.addRules(ValidateRule.IS_MOBILE_NUMBER);
        for (TextView w : widgets) {
            ReturnObject ro = validator.val(w.getText());
            if (!ro.isSuccess) {
                CommonUtils.makeEventToast(mContext, ro.getErrorMessage(), false);
                w.requestFocus();
                return false;
            }
        }
        return true;
    }

    /**
     * 获取开单数据后传递数据用的接口
     */
    public interface TSCallback {
        void sendTsEntity(OpenOrderEntity tsOpenOrderEntity);
    }

    public void setTsCallback(TSCallback tsCallback) {
        this.tsCallback = tsCallback;
    }

}
