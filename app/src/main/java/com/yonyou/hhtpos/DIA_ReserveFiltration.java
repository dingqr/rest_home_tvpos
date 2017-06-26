package com.yonyou.hhtpos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import com.yonyou.framework.library.common.utils.ScreenUtil;
import com.yonyou.hhtpos.bean.FilterDataEntity;
import com.yonyou.hhtpos.widgets.FiltrationView;

import java.util.ArrayList;

/**
 * 预定总览筛选对话框
 * 作者：ybing on 2017/6/26 10:16
 * 邮箱：ybing@yonyou.com
 */
public class DIA_ReserveFiltration implements View.OnClickListener{

    /**传入参数 */
    protected Context mContext;

    private String dishTypeTitle;
    private String dishAreaTitle;
    private String reserveStatusTitle;
    private ArrayList<FilterDataEntity> dishTypeList;
    private ArrayList<FilterDataEntity> dishAreaList;
    private ArrayList<FilterDataEntity> reserveStatusList;

    /**布局管理器*/
    private RecyclerView.LayoutManager gridLayoutManger1;
    private RecyclerView.LayoutManager gridLayoutManger2;
    private RecyclerView.LayoutManager gridLayoutManger3;

    protected Dialog mDialog;
    protected View mContentView;

    /**界面控件 */

    private FiltrationView flvChooseDishType;
    private FiltrationView flvChooseDishArea;
    private FiltrationView flvReserveStatus;
    private RadioButton btn_confirm;
    private RadioButton btn_cancel;

    public DIA_ReserveFiltration(Context context,  ArrayList<FilterDataEntity> dishTypeList,
                                 ArrayList<FilterDataEntity> dishAreaList, ArrayList<FilterDataEntity> reserveStatusList,String title1,String title2,String title3) {
        this.mContext = context;
        this.dishTypeList = dishTypeList;
        this.dishAreaList = dishAreaList;
        this.reserveStatusList = reserveStatusList;
        this.dishTypeTitle = title1;
        this.dishAreaTitle = title2;
        this.reserveStatusTitle = title3;
        initView();
        initData();
    }
    private void initView(){
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_test_filtration,null);
        mDialog.setContentView(mContentView);

        flvChooseDishType = (FiltrationView) mContentView.findViewById(R.id.flv_dish_type);
        flvChooseDishArea = (FiltrationView) mContentView.findViewById(R.id.flv_dish_area);
        flvReserveStatus = (FiltrationView) mContentView.findViewById(R.id.flv_reserve_status);
        btn_confirm = (RadioButton) mContentView.findViewById(R.id.btn_confirm);
        btn_cancel = (RadioButton) mContentView.findViewById(R.id.btn_cancel);

        // 设置监听
        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        //设置布局管理器
        gridLayoutManger1 = new GridLayoutManager(mContext,3);
        gridLayoutManger2 = new GridLayoutManager(mContext,3);
        gridLayoutManger3 = new GridLayoutManager(mContext,3);

        flvChooseDishType.setLayoutManager(gridLayoutManger1);
        flvChooseDishArea.setLayoutManager(gridLayoutManger2);
        flvReserveStatus.setLayoutManager(gridLayoutManger3);
    }
    private void initData() {
        // 用餐类别列表
        flvChooseDishType.setData(dishTypeList,dishTypeTitle);

        // 餐区列表
        flvChooseDishArea.setData(dishAreaList,dishAreaTitle);

        // 预定状态列表
        flvReserveStatus.setData(reserveStatusList,reserveStatusTitle);
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
                FilterDataEntity selectedDishType = flvChooseDishType.getSelectedData();
                FilterDataEntity selectedDishArea = flvChooseDishArea.getSelectedData();
                FilterDataEntity selectedReserveStatus = flvReserveStatus.getSelectedData();
                mDialog.dismiss();
                break;

            default:
                break;
        }
    }

    public interface ChooseTimeSelector {
        void chooseTime(String date, String time);
    }
}

