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
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_ChooseCashType;
import com.yonyou.hhtpos.adapter.ADA_SelectTableArea;
import com.yonyou.hhtpos.adapter.ADA_SelectTableCapacity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.TableCapacityEntity;
import com.yonyou.hhtpos.bean.TableEntity;
import com.yonyou.hhtpos.bean.mine.CashTypeEntity;

import java.util.ArrayList;

/**
 * 选择收款类型对话框
 * 作者：ybing on 2017/8/8 16:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_ChooseCashType implements View.OnClickListener, ADA_ChooseCashType.OnCashTypeClickListener{
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;
    private TextView tvConfirm;

    private ADA_ChooseCashType mAdapter;

    private RecyclerView rvCashType;

    private ArrayList<CashTypeEntity> cashTypeEntities;

    private CashTypeEntity currentCashType;

    public DIA_ChooseCashType(Context mContext ) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_choose_cash_type, null);
        mDialog.setContentView(mContentView);
        rvCashType = (RecyclerView)mContentView.findViewById(R.id.rlv_cash_type);
        tvConfirm = (TextView)mContentView.findViewById(R.id.tv_confirm);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,4);
        rvCashType.setLayoutManager(gridLayoutManager);
    }

    public void setData( ArrayList<CashTypeEntity> cashTypeEntities) {
        mAdapter = new ADA_ChooseCashType(mContext,cashTypeEntities);
        rvCashType.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(this);
    }

    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 840;
        lp.height = 520;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close:
                mDialog.dismiss();
                break;
            case R.id.confirm:
                getSelectedItems();
                mDialog.dismiss();
                break;
            default:
                break;
        }
    }

    private CashTypeEntity getSelectedItems() {
        return new CashTypeEntity(currentCashType.getCashTypeName(),true);
    }



    @Override
    public void onCashTypeClick(View view, int position) {
        currentCashType = cashTypeEntities.get(position);
    }
}
