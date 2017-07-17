package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nostra13.universalimageloader.utils.L;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_SetMealGrid;
import com.yonyou.hhtpos.adapter.ADA_SetMealList;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.SetMealGridEntity;
import com.yonyou.hhtpos.bean.SetMealListEntity;
import com.yonyou.hhtpos.widgets.FilterWheelView;

import java.util.ArrayList;

import static com.yonyou.hhtpos.util.FiltrationUtil.getSetMealGrid;
import static com.yonyou.hhtpos.util.FiltrationUtil.getSetMealList;
import static com.yonyou.hhtpos.util.FiltrationUtil.getSetMealType;

/**
 * 服务员点菜全家福弹框
 * 作者：ybing on 2017/7/12
 * 邮箱：ybing@yonyou.com
 */

public class DIA_FamilySetMeal implements View.OnClickListener{
    /**上下文 */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**界面控件 */
    private LinearLayout llFinishSelect;
    private ImageButton ibClose;
    private FilterWheelView fvSetDetailSelect;
    private RecyclerView rvDishGrid;
    private RecyclerView rvDishList;
    private TextView tvDishSelectedPrice;
    private TextView tvDishTotalCount;


    /**选项数据*/
    private FilterItemEntity mealSetDetailOption;
    private ArrayList<SetMealGridEntity> setMealGridEntities;
    private ArrayList<SetMealListEntity> setMealListEntities;

    /**数据适配器*/
    private ADA_SetMealGrid adaSetMealGrid;
    private ADA_SetMealList adaSetMealList;

    public DIA_FamilySetMeal(Context mContext) {
        this.mContext = mContext;
        initView();
        initData();
    }
    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_family_set_meal, null);
        mDialog.setContentView(mContentView);

        llFinishSelect = (LinearLayout)mContentView.findViewById(R.id.ll_finish_select);
        ibClose =(ImageButton) mContentView.findViewById(R.id.ib_close);
        fvSetDetailSelect =(FilterWheelView) mContentView.findViewById(R.id.fv_set_detail_select);
        ibClose.setOnClickListener(this);
        llFinishSelect.setOnClickListener(this);

        rvDishGrid = (RecyclerView) mContentView.findViewById(R.id.rv_set_meal_grid);
        rvDishList = (RecyclerView) mContentView.findViewById(R.id.rv_set_meal_list);
        tvDishSelectedPrice = (TextView) mContentView.findViewById(R.id.tv_dish_selected_price);
        tvDishTotalCount = (TextView) mContentView.findViewById(R.id.tv_dish_whole_count);
    }
    private void initData() {
        mealSetDetailOption = new FilterItemEntity();
        mealSetDetailOption.setTitle(mContext.getString(R.string.set_detail_option));
        mealSetDetailOption.setOptions(getSetMealType());
        fvSetDetailSelect.setData(mealSetDetailOption);

        RecyclerView.LayoutManager gridManager = new GridLayoutManager(mContext,3);
        rvDishGrid.setLayoutManager(gridManager);
        setMealGridEntities = getSetMealGrid();
        adaSetMealGrid = new ADA_SetMealGrid(mContext,setMealGridEntities);
        rvDishGrid.setAdapter(adaSetMealGrid);

        rvDishList.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        setMealListEntities = getSetMealList();
        adaSetMealList = new ADA_SetMealList(mContext,setMealListEntities);
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
//        lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 9; // 设置宽度
//        lp.height = ScreenUtil.getScreenHeight((Activity)mContext)/ 10 * 8; // 设置高度
        lp.width = 1686;
        lp.height= 980;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

}
