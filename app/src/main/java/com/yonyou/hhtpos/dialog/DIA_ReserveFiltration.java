package com.yonyou.hhtpos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.yonyou.framework.library.common.utils.ScreenUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.widgets.MultipleFiltrationView;

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
    private MultipleFiltrationView mfv_options;
    private RadioButton btnReset;
    private RadioButton btnFinish;
    private ImageButton close;

    public DIA_ReserveFiltration(Context context , ArrayList<FilterItemEntity> filterItemList) {
        this.mContext = context;
        this.filterItemList = filterItemList;
        initView();
        initData();
    }
    private void initView(){
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_multiple_filtration, null);
        mDialog.setContentView(mContentView);
        close = (ImageButton)mContentView.findViewById(R.id.iv_close);
        close.setOnClickListener(this);
        mfv_options = (MultipleFiltrationView) mContentView.findViewById(R.id.mfv_options);

        btnReset = (RadioButton) mContentView.findViewById(R.id.btn_reset);
        btnFinish = (RadioButton) mContentView.findViewById(R.id.btn_finish);

        // 设置监听
        btnReset.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

    }
    private void initData() {
        mfv_options.setFilterItemLists(filterItemList);
    }

    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.RIGHT);
        mDialog.getWindow().setWindowAnimations(R.style.style_right_in_anim);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.5f;// 背景灰度
//        lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 9; // 设置宽度
//        lp.height = ScreenUtil.getScreenHeight((Activity)mContext)/ 10 * 8; // 设置高度
        lp.width = 590;
        lp.height = 970;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reset:
                mDialog.dismiss();
                break;
            case R.id.iv_close:
                mDialog.dismiss();
                break;
            case R.id.btn_finish:
                ArrayList<FilterOptionsEntity> selectedList = mfv_options.getSelectedItems();
                mDialog.dismiss();
                break;

            default:
                break;
        }
    }

}

