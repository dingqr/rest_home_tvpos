package com.yonyou.hhtpos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.bean.FilterDataEntity;

import java.util.ArrayList;

import butterknife.Bind;

//测试筛选布局
public class ACT_TestFiltration extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    private String title1 = "餐别";
    private String title2 = "餐区";
    private String title3 = "预定状态";

    private ArrayList<FilterDataEntity> dishType = new ArrayList<>();
    private ArrayList<FilterDataEntity> dishArea = new ArrayList<>();
    private ArrayList<FilterDataEntity> reserveStatus = new ArrayList<>();

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


        FilterDataEntity fde1 = new FilterDataEntity("早餐",0,true);
        FilterDataEntity fde2 = new FilterDataEntity("午餐",0,false);
        FilterDataEntity fde3 = new FilterDataEntity("晚餐",0,false);
        FilterDataEntity fde4 = new FilterDataEntity("夜宵",0,false);

        FilterDataEntity fde5 = new FilterDataEntity("全部餐区",1,true);
        FilterDataEntity fde6 = new FilterDataEntity("一楼",1,false);
        FilterDataEntity fde7 = new FilterDataEntity("二楼",1,false);
        FilterDataEntity fde8 = new FilterDataEntity("三楼",1,false);
        FilterDataEntity fde9 = new FilterDataEntity("包房",1,false);

        FilterDataEntity fde10 = new FilterDataEntity("预约过期关闭",2,true);
        FilterDataEntity fde11= new FilterDataEntity("客人已取消",2,false);
        FilterDataEntity fde12 = new FilterDataEntity("等待客人中",2,false);
        FilterDataEntity fde13 = new FilterDataEntity("客人已到达",2,false);

        dishType.add(fde1);
        dishType.add(fde2);
        dishType.add(fde3);
        dishType.add(fde4);

        dishArea.add(fde5);
        dishArea.add(fde6);
        dishArea.add(fde7);
        dishArea.add(fde8);
        dishArea.add(fde9);

        reserveStatus.add(fde10);
        reserveStatus.add(fde11);
        reserveStatus.add(fde12);
        reserveStatus.add(fde13);

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
                DIA_ReserveFiltration dia_reserveFiltration = new DIA_ReserveFiltration(mContext,dishType,dishArea,reserveStatus,title1,title2,title3);
                dia_reserveFiltration.getDialog().show();
                break;

            default:
                break;
        }
    }
}
