package com.yonyou.hhtpos;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;

import java.util.ArrayList;

import butterknife.Bind;

//测试筛选布局
public class ACT_TestFiltration extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    private String title1 = "餐别";
    private String title2 = "餐区";
    private String title3 = "预定状态";

    private ArrayList<FilterOptionsEntity> dishType = new ArrayList<>();
    private ArrayList<FilterOptionsEntity> dishArea = new ArrayList<>();
    private ArrayList<FilterOptionsEntity> reserveStatus = new ArrayList<>();


//    /**布局管理器*/
    private RecyclerView.LayoutManager gridLayoutManger1;
    private RecyclerView.LayoutManager gridLayoutManger2;
    private RecyclerView.LayoutManager gridLayoutManger3;

    private ArrayList<FilterItemEntity> filterItemList;
    DIA_ReserveFiltration dia_reserveFiltration;

    private final int VIEWTYPE_DISHTYPE = 0;
    private final int VIEWTYPE_DISHAREA = 1;
    private final int VIEWTYPE_RESERVESTATUS = 2;

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

        FilterOptionsEntity fde1 = new FilterOptionsEntity("早餐",VIEWTYPE_DISHTYPE,true);
        FilterOptionsEntity fde2 = new FilterOptionsEntity("午餐",VIEWTYPE_DISHTYPE,false);
        FilterOptionsEntity fde3 = new FilterOptionsEntity("晚餐",VIEWTYPE_DISHTYPE,false);
        FilterOptionsEntity fde4 = new FilterOptionsEntity("夜宵",VIEWTYPE_DISHTYPE,false);

        FilterOptionsEntity fde5 = new FilterOptionsEntity("全部餐区",VIEWTYPE_DISHAREA,true);
        FilterOptionsEntity fde6 = new FilterOptionsEntity("一楼",VIEWTYPE_DISHAREA,false);
        FilterOptionsEntity fde7 = new FilterOptionsEntity("二楼",VIEWTYPE_DISHAREA,false);
        FilterOptionsEntity fde8 = new FilterOptionsEntity("三楼",VIEWTYPE_DISHAREA,false);
        FilterOptionsEntity fde9 = new FilterOptionsEntity("包房",VIEWTYPE_DISHAREA,false);

        FilterOptionsEntity fde10 = new FilterOptionsEntity("预约过期关闭",VIEWTYPE_RESERVESTATUS,true);
        FilterOptionsEntity fde11= new FilterOptionsEntity("客人已取消",VIEWTYPE_RESERVESTATUS,false);
        FilterOptionsEntity fde12 = new FilterOptionsEntity("等待客人中",VIEWTYPE_RESERVESTATUS,false);
        FilterOptionsEntity fde13 = new FilterOptionsEntity("客人已到达",VIEWTYPE_RESERVESTATUS,false);

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

        gridLayoutManger1 = new GridLayoutManager(this,3);
        gridLayoutManger2 = new GridLayoutManager(this,3);
        gridLayoutManger3 = new GridLayoutManager(this,3);

        btnConfirm.setOnClickListener(this);

        FilterItemEntity fie1 = new FilterItemEntity(dishType,title1,gridLayoutManger1);
        FilterItemEntity fie2 = new FilterItemEntity(dishArea,title2,gridLayoutManger2);
        FilterItemEntity fie3 = new FilterItemEntity(reserveStatus,title3,gridLayoutManger3);

        filterItemList = new ArrayList<>();

        filterItemList.add(fie1);
        filterItemList.add(fie2);
        filterItemList.add(fie3);
//        dia_reserveFiltration.setFilterItemList(filterItemList);
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
                dia_reserveFiltration = new DIA_ReserveFiltration(mContext,filterItemList);
                dia_reserveFiltration.getDialog().show();
                break;

            default:
                break;
        }
    }
}
