package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.utils.ReturnObject;
import com.yonyou.framework.library.common.utils.ValidateRule;
import com.yonyou.framework.library.common.utils.Validator;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.DistributeTimeEntity;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.wm.OpenOrderEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.ui.dinner.wm.FRA_TakeOutLeft;
import com.yonyou.hhtpos.widgets.FiltrationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * 外卖开单对话框
 * 作者：ybing on 2017/6/29 13:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_TakeOutOpenOrder implements View.OnClickListener {
    /**
     * 上下文
     */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**
     * 界面控件
     */
    private RadioButton rbReserveTime;
    private RadioButton rbRightNow;
    private RadioButton rbConfirmOpenOrder;
    private ImageButton ibClose;
    private FiltrationView filtrationView;
    private FilterItemEntity takeoutCompanies;
    private ScrollView scrollView;


    private TextView etSelectDeliverTime;
    private EditText etEnterReceiverName;
    private EditText etEnterReceiverPhone;
    private EditText etEnterReceiverAddress;
    private EditText etDinnerNumber;

    /**
     * 外卖开单信息
     */
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String dinnerNumber;
    private String takeOutCompanyId;
    private String reserveTime;
    private DistributeTimeEntity deliverTime;
    private FilterOptionsEntity takeoutCompany;
    private WmCallback wmCallback;

    public DIA_TakeOutOpenOrder(Context mContext, FilterItemEntity takeoutCompanies) {
        this.mContext = mContext;
        this.takeoutCompanies = takeoutCompanies;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_take_out_open_order, null);
        mDialog.setContentView(mContentView);

        filtrationView = (FiltrationView) mContentView.findViewById(R.id.fv_order_source);
        rbReserveTime = (RadioButton) mContentView.findViewById(R.id.rb_reserve_time);
        rbRightNow = (RadioButton) mContentView.findViewById(R.id.rb_right_now);
        rbConfirmOpenOrder = (RadioButton) mContentView.findViewById(R.id.rb_confirm_open_order);
        ibClose = (ImageButton) mContentView.findViewById(R.id.ib_close);
        etSelectDeliverTime = (TextView) mContentView.findViewById(R.id.et_select_deliver_time);
        scrollView = (ScrollView) mContentView.findViewById(R.id.sv_content);

        etEnterReceiverName = (EditText) mContentView.findViewById(R.id.et_enter_receiver_name);
        etEnterReceiverPhone = (EditText) mContentView.findViewById(R.id.et_enter_receiver_phone);
        etEnterReceiverAddress = (EditText) mContentView.findViewById(R.id.et_enter_receiver_address);
        etDinnerNumber = (EditText) mContentView.findViewById(R.id.et_dinner_number);

        ibClose.setOnClickListener(this);
        rbReserveTime.setOnClickListener(this);
        rbRightNow.setOnClickListener(this);
        etSelectDeliverTime.setOnClickListener(this);
        rbConfirmOpenOrder.setOnClickListener(this);

        filtrationView.setData(takeoutCompanies);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;
            case R.id.rb_reserve_time:
                scrollView.scrollTo(300, 1080);
                etSelectDeliverTime.setVisibility(View.VISIBLE);
                break;
            case R.id.et_select_deliver_time:
                DIA_ChooseTime dia_chooseTime = new DIA_ChooseTime(mContext, null, null);
                dia_chooseTime.show();
                dia_chooseTime.setOnTimeSelectedListener(new DIA_ChooseTime.OnTimeSelectedListener() {
                    @Override
                    public void onTimeSelected(DistributeTimeEntity timeEntity) {
                        deliverTime = timeEntity;
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String reserveTime = sdf.format(calendar.getTime()).substring(0, 11) + deliverTime.hour + ":" + deliverTime.minute;
                        etSelectDeliverTime.setText(reserveTime);
                    }
                });
                break;
            case R.id.rb_right_now:
                etSelectDeliverTime.setVisibility(View.GONE);
                break;
            case R.id.rb_confirm_open_order:
                OpenOrderEntity wmooe = initEntity();
                if (wmCallback != null && wmooe != null) {
                    wmCallback.sendWmEntity(wmooe);
                    filtrationView.reset();
                    mDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    private boolean verifyInput() {
        takeoutCompany = filtrationView.getSelectedData();
        receiverName = etEnterReceiverName.getText().toString().trim();
        receiverAddress = etEnterReceiverAddress.getText().toString().trim();
        dinnerNumber = etDinnerNumber.getText().toString().trim();
        if (takeoutCompany == null) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.take_out_company_error), false);
            return false;
        }
        if (TextUtils.isEmpty(receiverName)) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.receiver_name_empty), false);
            return false;
        }
        if (!doValidatePhone()) {
            return false;
        }
        if (TextUtils.isEmpty(receiverAddress)) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.receiver_address_empty), false);
            return false;
        }
        if (TextUtils.isEmpty(dinnerNumber)) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.receiver_num_empty), false);
            return false;
        }
        if (etSelectDeliverTime.getVisibility() == View.VISIBLE) {
            reserveTime = etSelectDeliverTime.getText().toString().trim();
            if (TextUtils.isEmpty(reserveTime)) {
                CommonUtils.makeEventToast(mContext, mContext.getString(R.string.reserve_time_error), false);
                return false;
            }
        }

        return true;
    }

    private OpenOrderEntity initEntity() {
        OpenOrderEntity wmooe = new OpenOrderEntity();
        receiverPhone = etEnterReceiverPhone.getText().toString().trim();
        if (verifyInput()) {
            takeOutCompanyId = takeoutCompany.getOptionId();
            wmooe.setShopId(API.shopId);
            wmooe.setTakeOutCompanyId(takeOutCompanyId);
            wmooe.setName(receiverName);
            wmooe.setPhone(receiverPhone);
            wmooe.setAddress(receiverAddress);
            wmooe.setPersonNum(dinnerNumber);

            //是否立即送餐
            if (etSelectDeliverTime.getVisibility() == View.GONE) {
                wmooe.setSendNow("Y");
            } else {
                wmooe.setReserveTime(reserveTime);
            }
            return wmooe;
        } else {
            return null;
        }
    }

    public Dialog getDialog() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 580;
        lp.height = 920;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    /**
     * 验证手机号 是否为空，是否满足手机号的规则
     *
     * @return
     */
    private boolean doValidatePhone() {
        Validator validator = new Validator();
        TextView[] widgets = new TextView[]{etEnterReceiverPhone};
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
    public interface WmCallback {
        void sendWmEntity(OpenOrderEntity wmOpenOrderEntity);
    }

    public void setWmCallback(WmCallback wmCallback) {
        this.wmCallback = wmCallback;
    }
}
