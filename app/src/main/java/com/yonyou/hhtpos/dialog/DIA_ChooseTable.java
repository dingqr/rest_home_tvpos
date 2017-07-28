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

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_SelectTableArea;
import com.yonyou.hhtpos.adapter.ADA_SelectTableCapacity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.TableCapacityEntity;
import com.yonyou.hhtpos.bean.TableEntity;

import java.util.ArrayList;

/**
 * 选择桌台对话框
 * 作者：ybing on 2017/6/29 16:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_ChooseTable implements View.OnClickListener, ADA_SelectTableCapacity.OnCapacityClickListener,ADA_SelectTableArea.OnItemClickListener{
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    private ADA_SelectTableArea areaAdapter;
    private ADA_SelectTableCapacity capacityAdapter;

    private RecyclerView rvTableArea;
    private RecyclerView rvTableCapacity;

    private ArrayList<FilterOptionsEntity> areaDatas;
    private ArrayList<TableCapacityEntity> capacityDatas;

    private FilterOptionsEntity currentArea;
    private TableCapacityEntity currentCapacity;

    public DIA_ChooseTable(Context mContext, ArrayList<FilterOptionsEntity> areaDatas, ArrayList<TableCapacityEntity> capacityDatas) {
        this.mContext = mContext;
        this.areaDatas = areaDatas;
        this.capacityDatas = capacityDatas;
        initView();
        initData();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_select_table, null);
        mDialog.setContentView(mContentView);
        rvTableArea =(RecyclerView) mContentView.findViewById(R.id.rlv_table_area);
        rvTableCapacity = (RecyclerView)mContentView.findViewById(R.id.rlv_table_capacity);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTableArea.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,4);
        rvTableCapacity.setLayoutManager(gridLayoutManager);
    }

    private void initData() {
        areaAdapter = new ADA_SelectTableArea(mContext,areaDatas);
        rvTableArea.setAdapter(areaAdapter);
        areaAdapter.setmOnItemClickListener(this);
        capacityAdapter = new ADA_SelectTableCapacity(mContext,capacityDatas);
        rvTableCapacity.setAdapter(capacityAdapter);
        capacityAdapter.setmOnItemClickListener(this);
    }

    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 1010;
        lp.height = 710;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
                getSelectedItems();
                mDialog.dismiss();
                break;
            default:
                break;
        }
    }

    private TableEntity getSelectedItems() {
        return new TableEntity(currentArea,currentCapacity);
    }

    @Override
    public void onItemClick(View view, int position) {
        currentArea = areaDatas.get(position);
    }

    @Override
    public void onCapacityClick(View view, int position) {
        currentCapacity = capacityDatas.get(position);
    }
}
