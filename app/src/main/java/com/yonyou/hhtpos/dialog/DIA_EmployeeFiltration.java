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

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.widgets.FiltrationView;

/**
 * 员工筛选对话框
 * 作者：ybing on 2017/7/26 13:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_EmployeeFiltration implements View.OnClickListener,FiltrationView.SelectCallback {
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

    public DIA_EmployeeFiltration(Context mContext, FilterItemEntity workState,FilterItemEntity employeePosition) {
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

        fvWorkState.setData(workState);
        fvWorkState.setSelectCallback(this);

        fvEmployeePosition.setData(employeePosition);
        fvEmployeePosition.setSelectCallback(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_finish:
                break;
            default:
                break;
        }
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

    @Override
    public void sendSelectedItem(FilterOptionsEntity foe) {

    }
}