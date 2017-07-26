package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.EmployeeEntity;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.interfaces.EmployeeCallback;
import com.yonyou.hhtpos.widgets.FiltrationView;

/**
 * 员工筛选对话框
 * 作者：ybing on 2017/7/26 13:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_EmployeeFiltration implements View.OnClickListener {
    /**
     * 上下文
     */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**
     * 界面控件
     */
    private RadioButton rbFinish;
    private ImageButton ibClose;
    private FiltrationView fvWorkState;
    private FiltrationView fvEmployeePosition;

    private FilterItemEntity workState;
    private FilterItemEntity employeePosition;

    private EmployeeCallback employeeCallback;

    private boolean potisitonEmptyFlag;
    private boolean stateEmptyFlag;

    public DIA_EmployeeFiltration(Context mContext, FilterItemEntity workState, FilterItemEntity employeePosition) {
        this.mContext = mContext;
        this.workState = workState;
        this.employeePosition = employeePosition;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_employee_filtration, null);
        mDialog.setContentView(mContentView);

        fvWorkState = (FiltrationView) mContentView.findViewById(R.id.fv_work_state);
        fvEmployeePosition = (FiltrationView) mContentView.findViewById(R.id.fv_employee_position);
        rbFinish = (RadioButton) mContentView.findViewById(R.id.rb_finish);
        ibClose = (ImageButton) mContentView.findViewById(R.id.ib_close);

        ibClose.setOnClickListener(this);
        rbFinish.setOnClickListener(this);

        if (workState != null) {
            fvWorkState.setData(workState);
        } else {
            stateEmptyFlag = true;
            fvWorkState.setVisibility(View.GONE);
        }

        if (employeePosition != null) {
            fvEmployeePosition.setData(employeePosition);
        } else {
            potisitonEmptyFlag = true;
            fvEmployeePosition.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;
            case R.id.rb_finish:
                EmployeeEntity employeeEntity = initEmployee();
                if (employeeEntity != null && verifyInput()) {
                    employeeCallback.sendItems(employeeEntity);
                    fvEmployeePosition.reset();
                    fvWorkState.reset();
                    mDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    private EmployeeEntity initEmployee() {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        if (verifyInput()) {
            if (!stateEmptyFlag){
                employeeEntity.setWorkState(fvWorkState.getSelectedData().getOption());
            }
            if (!potisitonEmptyFlag){
                employeeEntity.setEmployeePosition(fvEmployeePosition.getSelectedData().getOption());
            }
            return employeeEntity;
        }else{
            return null;
        }

    }

    private boolean verifyInput() {
        if (!stateEmptyFlag && fvWorkState.getSelectedData() == null) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.work_state_empty), false);
            return false;
        }
        if (!potisitonEmptyFlag && fvEmployeePosition.getSelectedData() == null) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.employee_position_empty), false);
            return false;
        }
        return true;
    }

    public Dialog getDialog() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 680;
        lp.height = 616;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    public void setEmployeeCallback(EmployeeCallback employeeCallback) {
        this.employeeCallback = employeeCallback;
    }
}