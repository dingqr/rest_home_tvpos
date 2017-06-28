package com.yonyou.hhtpos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import com.yonyou.framework.library.common.utils.ScreenUtil;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.widgets.MultFiltrationView;

import java.util.ArrayList;

/**
 * 预定总览筛选对话框
 * 作者：ybing on 2017/6/26 10:16
 * 邮箱：ybing@yonyou.com
 */
public class DIA_ReserveFiltration implements View.OnClickListener{

    /**传入参数 */
    protected Context mContext;

    private ArrayList<FilterItemEntity> filterItemList;


    protected Dialog mDialog;
    protected View mContentView;

    /**界面控件 */
    private MultFiltrationView mfv_options;
    private RadioButton btn_confirm;
    private RadioButton btn_cancel;

    public DIA_ReserveFiltration(Context context ,ArrayList<FilterItemEntity> filterItemList) {
        this.mContext = context;
        this.filterItemList = filterItemList;
        initView();
        initData();
    }
    private void initView(){
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_test_filtration, null);
        mDialog.setContentView(mContentView);

        mfv_options = (MultFiltrationView) mContentView.findViewById(R.id.mfv_options);

        btn_confirm = (RadioButton) mContentView.findViewById(R.id.btn_confirm);
        btn_cancel = (RadioButton) mContentView.findViewById(R.id.btn_cancel);

        // 设置监听
        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }
    private void initData() {
        mfv_options.setFilterItemLists(filterItemList);
    }

    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 9; // 设置宽度
        lp.height = ScreenUtil.getScreenHeight((Activity)mContext)/ 10 * 8; // 设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                mDialog.dismiss();
                break;

            case R.id.btn_confirm:
                ArrayList<FilterOptionsEntity> selectedList = mfv_options.getSelectedItems();
                mDialog.dismiss();
                break;

            default:
                break;
        }
    }

}

