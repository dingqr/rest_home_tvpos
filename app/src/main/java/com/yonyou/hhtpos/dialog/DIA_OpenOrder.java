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

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.framework.library.common.utils.ReturnObject;
import com.yonyou.framework.library.common.utils.ScreenUtil;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.common.utils.ValidateRule;
import com.yonyou.framework.library.common.utils.Validator;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.bean.WaiterEntity;
import com.yonyou.hhtpos.bean.ts.OpenOrderEntity;
import com.yonyou.hhtpos.interfaces.OpenOrderCallback;
import com.yonyou.hhtpos.presenter.ITSCancelSplitPresenter;
import com.yonyou.hhtpos.presenter.Impl.TSCancelSplitPresenterImpl;
import com.yonyou.hhtpos.view.ITSCancelSplitOrderView;

import java.util.List;

/**
 * 堂食开单对话框
 * 作者：ybing on 2017/6/29 13:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_OpenOrder implements View.OnClickListener, DIA_ChooseWaiter.OnWaiterSelectedListener, ITSCancelSplitOrderView {
    /**
     * 上下文
     */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**
     * 页面控件
     */
    private ImageView ivClose;
    private TextView tvTitle;
    private EditText etUserNumber;
    private EditText etUserPhone;
    private EditText etBillRemark;
    private EditText etWaiter;
    private RadioButton tvConfirmOpenOrder;

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
    private String billRemark;
    private String waiter;
    private List<WaiterEntity> waiterList;
    private CanteenTableEntity canteenTableEntity;
    private DIA_ChooseWaiter dia_chooseWaiter;

    private ITSCancelSplitPresenter cancelSplitPresenter;

    public DIA_OpenOrder(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    public DIA_OpenOrder setData(CanteenTableEntity canteenTableEntity, List<WaiterEntity> waiterList) {
        if (canteenTableEntity != null) {
            if (TextUtils.isEmpty(canteenTableEntity.tableName)) {
                tvTitle.setText(mContentView.getResources().getString(R.string.canteen_billing));
            }
            if (!TextUtils.isEmpty(canteenTableEntity.tableName) && 3 != canteenTableEntity.getTableOption()) {
                tvTitle.setText(mContentView.getResources().getString(R.string.canteen_billing) + "(" + canteenTableEntity.tableName + ")");
            }
            if (!TextUtils.isEmpty(canteenTableEntity.tableName) && 3 == canteenTableEntity.getTableOption()) {
                String tableName = getSplitName(canteenTableEntity.tableName);
                tvTitle.setText(mContentView.getResources().getString(R.string.canteen_billing) + "(" + tableName + ")");

            }
        }
        this.waiterList = waiterList;
        this.canteenTableEntity = canteenTableEntity;
        return this;
    }

    private String getSplitName(String tableName) {
        String lastCharacter = tableName.substring(tableName.length() - 1, tableName.length());
        String tmp;
        char lastE = 'Z';
        int lastEnglish = (int) lastE;
        char[] c = lastCharacter.toCharArray();
        int now = (int) c[0];
        if (now >= lastEnglish) {
            tmp = "A";
            return tableName + tmp;
        } else {
            char uppercase = (char) (now + 1);
            tmp = String.valueOf(uppercase);
            return tableName.substring(0, tableName.length() - 1) + tmp;
        }

    }

    public DIA_OpenOrder setData(CanteenTableEntity canteenTableEntity, List<WaiterEntity> waiterList, String tableOption) {
        if (canteenTableEntity != null) {
            if (!TextUtils.isEmpty(canteenTableEntity.tableName)) {
                tvTitle.setText(mContentView.getResources().getString(R.string.canteen_billing) + "(" + canteenTableEntity.tableName + ")");
            } else {
                tvTitle.setText(mContentView.getResources().getString(R.string.canteen_billing));
            }
        }
        this.waiterList = waiterList;
        this.canteenTableEntity = canteenTableEntity;
        return this;
    }


    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_open_order, null);
        mDialog.setContentView(mContentView);

        tvTitle = (TextView) mContentView.findViewById(R.id.tv_title);
        etUserNumber = (EditText) mContentView.findViewById(R.id.et_user_number);
        etUserPhone = (EditText) mContentView.findViewById(R.id.et_user_phone);
        etBillRemark = (EditText) mContentView.findViewById(R.id.et_dinner_remark);
        etWaiter = (EditText) mContentView.findViewById(R.id.et_waiter);
        tvConfirmOpenOrder = (RadioButton) mContentView.findViewById(R.id.tv_confirm_open_order);
        ivClose = (ImageView) mContentView.findViewById(R.id.iv_close);

        cancelSplitPresenter = new TSCancelSplitPresenterImpl(mContext, this);

        tvConfirmOpenOrder.setOnClickListener(this);
        etWaiter.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        dia_chooseWaiter = new DIA_ChooseWaiter(mContext);
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
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.user_name_empty), false);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                if (mDialog != null) {
                    if (canteenTableEntity.getTableOption() == 3) {
                        //调用取消拼台接口
                        cancelSplitPresenter.cancelSplit(canteenTableEntity.tableID);
                    }
                }
                break;
            case R.id.tv_confirm_open_order:
                OpenOrderEntity tsooe = initEntity();
                if (tsCallback != null && tsooe != null) {
                    tsCallback.sendTsEntity(tsooe);
                    mDialog.dismiss();
                }
                break;
            case R.id.et_waiter:
                if (waiterList!=null && waiterList.size()>0){
                dia_chooseWaiter.setData(waiterList);
                dia_chooseWaiter.setOnWaiterSelectedListener(this);
                dia_chooseWaiter.show();}
                else{
                    CommonUtils.makeEventToast(mContext,mContext.getString(R.string.waiter_list_empty),false);
                }
                break;
            default:
                break;
        }

    }

    public Dialog getDialog() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 620;
        lp.height = 775;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    @Override
    public void onWaiterSelected(WaiterEntity waiterEntity) {
        if (waiterEntity != null) {
            this.waiterEntity = waiterEntity;
            etWaiter.setText(waiterEntity.waiterName);
        } else {
            this.waiterEntity.waiterId = "1";
            this.waiterEntity.waiterName = "fakeData";
            etWaiter.setText(this.waiterEntity.waiterName);
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

    private OpenOrderEntity initEntity() {
        OpenOrderEntity tsooe = new OpenOrderEntity();
        userPhone = etUserPhone.getText().toString().trim();
        if (verifyInput()) {
            tsooe.setTableStatus(0);
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

    public void setTsCallback(OpenOrderCallback tsCallback) {
        this.tsCallback = tsCallback;
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showException(String msg) {

    }

    @Override
    public void showNetError() {

    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    public void showDialogLoading(String msg) {

    }

    @Override
    public void dismissDialogLoading() {

    }

    @Override
    public void cancelSplitTable() {
        mDialog.dismiss();
    }
}
