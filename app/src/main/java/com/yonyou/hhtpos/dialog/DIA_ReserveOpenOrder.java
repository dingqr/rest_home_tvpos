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
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.framework.library.common.utils.ReturnObject;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.common.utils.ValidateRule;
import com.yonyou.framework.library.common.utils.Validator;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.bean.WaiterEntity;
import com.yonyou.hhtpos.bean.ts.OpenOrderEntity;
import com.yonyou.hhtpos.interfaces.OpenOrderCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 预定单开单对话框
 * 作者：ybing on 2017/6/29 13:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_ReserveOpenOrder implements View.OnClickListener, DIA_ChooseWaiter.OnWaiterSelectedListener {
    /**
     * 页面控件
     */
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_user_number)
    EditText etUserNumber;
    @Bind(R.id.et_user_phone)
    EditText etUserPhone;
    @Bind(R.id.et_reserve_order_id)
    EditText etReserveOrderId;
    @Bind(R.id.et_waiter)
    EditText etWaiter;
    @Bind(R.id.rb_confirm_open_order)
    RadioButton rbConfirmOpenOrder;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.et_dinner_remark)
    EditText etBillRemark;

    /**
     * 上下文
     */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;


    /**
     * 开单数据接口
     */
    private OpenOrderCallback tsCallback;

    /**
     * 开单数据
     */
    private String dinnerNumber;
    private String userPhone;
    private WaiterEntity waiterEntity;
    private String billNo;
    private String waiter;
    private List<WaiterEntity> waiterList;
    private String billRemark;
    private CanteenTableEntity canteenTableEntity;

    public DIA_ReserveOpenOrder(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    public DIA_ReserveOpenOrder setData(CanteenTableEntity canteenTableEntity, List<WaiterEntity> waiterList) {
        if (canteenTableEntity != null) {
            if (!TextUtils.isEmpty(canteenTableEntity.tableName)) {
                tvTitle.setText(mContentView.getResources().getString(R.string.canteen_billing) + "(" + canteenTableEntity.tableName + ")");

            } else {
                tvTitle.setText(mContentView.getResources().getString(R.string.canteen_billing));
            }
            etReserveOrderId.setText(StringUtil.getString(canteenTableEntity.billNo));
        }
        this.waiterList = waiterList;
        this.canteenTableEntity = canteenTableEntity;
        return this;
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_reserve_open_order, null);
        ButterKnife.bind(this, mContentView);
        mDialog.setContentView(mContentView);

        ivClose.setOnClickListener(this);
        rbConfirmOpenOrder.setOnClickListener(this);
        etWaiter.setOnClickListener(this);

    }

    private boolean verifyInput() {
        userPhone = etUserPhone.getText().toString().trim();
        waiter = etWaiter.getText().toString().trim();
        dinnerNumber = etUserNumber.getText().toString().trim();
        billRemark = etBillRemark.getText().toString().trim();
        if (TextUtils.isEmpty(dinnerNumber)) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.receiver_num_empty), false);
            return false;
        }
        if (!TextUtils.isEmpty(userPhone) && !doValidatePhone()) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.user_phone_error), false);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;

            case R.id.rb_confirm_open_order:
                OpenOrderEntity tsooe = initEntity();
                if (tsCallback != null && tsooe != null) {
                    rbConfirmOpenOrder.setChecked(true);
                    tsCallback.sendTsEntity(tsooe);
                    mDialog.dismiss();
                }else rbConfirmOpenOrder.setChecked(false);
                break;

            case R.id.et_waiter:
                DIA_ChooseWaiter dia_chooseWaiter = new DIA_ChooseWaiter(mContext);
                dia_chooseWaiter.setData(waiterList);
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
            tsooe.setTableStatus(5);
            tsooe.setPersonNum(dinnerNumber);
            tsooe.setMemberId(StringUtil.getString(userPhone));
            if (waiterEntity == null) {
                tsooe.setWaiterId("");
            } else {
                tsooe.setWaiterId(StringUtil.getString(waiterEntity.waiterId));
            }
            tsooe.setBillRemark(StringUtil.getString(billRemark));
            tsooe.setOpenTime(AppDateUtil.getTimeStamp(System.currentTimeMillis(), AppDateUtil.YYYY_MM_DD_HH_MM_SS));
            tsooe.setTableNo(canteenTableEntity.getTableID());
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
        lp.height = 920;
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

    public void setTsCallback(OpenOrderCallback tsCallback) {
        this.tsCallback = tsCallback;
    }
}
