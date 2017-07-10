package com.yonyou.hhtpos.ui.dinner.ts;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_MealArea;
import com.yonyou.hhtpos.bean.MealAreaEntity;
import com.yonyou.hhtpos.widgets.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 堂食页面
 * 作者：liushuofei on 2017/7/8 10:42
 */
public class ACT_Canteen extends BaseActivity{

    @Bind(R.id.lv_meal_area)
    ListView mListView;
    @Bind(R.id.psts_tab)
    PagerSlidingTabStrip mTab;

    private List<MealAreaEntity> dataList;
    private ADA_MealArea mAdapter;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_canteen;
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
        mAdapter = new ADA_MealArea(mContext);
        mListView.setAdapter(mAdapter);

        // 假数据
        dataList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            MealAreaEntity bean = new MealAreaEntity();
            if (i == 0) {
                bean.setCheck(true);
            }
            dataList.add(bean);
        }
        mAdapter.update(dataList);
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
}
