package com.yonyou.hhtpos;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.bean.FilterDataEntity;
import com.yonyou.hhtpos.widgets.FiltrationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

//测试筛选布局
public class ACT_TestFiltration extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.flv_flitration1)
    FiltrationView mFiltrationView1;
    @Bind(R.id.flv_flitration2)
    FiltrationView mFiltrationView2;
    @Bind(R.id.flv_flitration3)
    FiltrationView mFiltrationView3;

    @Bind(R.id.btn_confirm)
    RadioButton btnConfirm;
    @Bind(R.id.btn_cancel)
    RadioButton btnCancel;

    private String title = "餐别";

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_test_filtration;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
         List<FilterDataEntity> options1 = new ArrayList<>();
         List<FilterDataEntity> options2 = new ArrayList<>();

        FilterDataEntity fde1 = new FilterDataEntity("早餐",true);
        FilterDataEntity fde2 = new FilterDataEntity("午餐",false);
        FilterDataEntity fde3 = new FilterDataEntity("晚餐",false);
        FilterDataEntity fde4 = new FilterDataEntity("夜宵",false);

        FilterDataEntity fde5 = new FilterDataEntity("早餐",true);
        FilterDataEntity fde6 = new FilterDataEntity("午餐",false);
        FilterDataEntity fde7 = new FilterDataEntity("晚餐",false);
        FilterDataEntity fde8 = new FilterDataEntity("夜宵",false);


        options1.add(fde1);
        options1.add(fde2);
        options1.add(fde3);
        options1.add(fde4);

        options2.add(fde5);
        options2.add(fde6);
        options2.add(fde7);
        options2.add(fde8);
//
//
//        options3.add(fde1);
//        options3.add(fde2);
//        options3.add(fde3);
//        options3.add(fde4);

        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this,3);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        layoutManager2.setAutoMeasureEnabled(true);
//        RecyclerView.LayoutManager layoutManager3 = new GridLayoutManager(this,3);

        mFiltrationView1.setLayoutManager(layoutManager1);
        mFiltrationView1.setTitle(title);
        mFiltrationView1.setData(options1);
        mFiltrationView2.setLayoutManager(layoutManager2);
        mFiltrationView2.setTitle(title);
        mFiltrationView2.setData(options2);
//        mFiltrationView3.setLayoutManager(layoutManager3);
//        mFiltrationView3.setTitle(title);
//        mFiltrationView3.setData(options3);

        btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                FilterDataEntity dataBean1 = mFiltrationView1.getSelectedData();
                FilterDataEntity dataBean2 = mFiltrationView2.getSelectedData();
                //获取了两个筛选框的结果。
                break;
            case R.id.btn_cancel:
                break;
            default:
                break;
        }
    }
}
