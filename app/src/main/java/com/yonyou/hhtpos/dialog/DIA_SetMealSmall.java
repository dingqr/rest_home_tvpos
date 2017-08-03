package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_SetMealList;
import com.yonyou.hhtpos.adapter.ADA_SetMealSmallList;
import com.yonyou.hhtpos.bean.SetMealListEntity;

import java.util.ArrayList;

/**
 * 服务员点菜套餐弹框3
 * 作者：ybing on 2017/8/3
 * 邮箱：ybing@yonyou.com
 */

public class DIA_SetMealSmall implements View.OnClickListener {
    /**上下文 */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**界面控件 */
    private LinearLayout llFinishSelect;
    private ImageButton ibClose;
    private RecyclerView rvDishList;
    private TextView tvDishTotalCount;


    /**选项数据*/
    private ArrayList<SetMealListEntity> setMealListEntities;

    /**数据适配器*/
    private ADA_SetMealSmallList adaSetMealList;

    public DIA_SetMealSmall(Context mContext, ArrayList<SetMealListEntity> setMealListEntities) {
        this.mContext = mContext;
        this.setMealListEntities = setMealListEntities;
        initView();
        initData();

    }
    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_set_meal_small, null);
        mDialog.setContentView(mContentView);

        llFinishSelect = (LinearLayout)mContentView.findViewById(R.id.ll_finish_select);
        ibClose =(ImageButton) mContentView.findViewById(R.id.ib_close);

        ibClose.setOnClickListener(this);
        llFinishSelect.setOnClickListener(this);

        rvDishList = (RecyclerView) mContentView.findViewById(R.id.rv_set_meal_list);
        tvDishTotalCount = (TextView) mContentView.findViewById(R.id.tv_dish_whole_count);
    }
    private void initData() {
        rvDishList.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        adaSetMealList = new ADA_SetMealSmallList(mContext,setMealListEntities);
        rvDishList.setAdapter(adaSetMealList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.ll_finish_select:
                break;
            default:
                break;
        }
    }
    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 730;
        lp.height= 865;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

}
