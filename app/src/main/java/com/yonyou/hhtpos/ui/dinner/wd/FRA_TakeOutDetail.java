package com.yonyou.hhtpos.ui.dinner.wd;

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_OrderDishesDetail;
import com.yonyou.hhtpos.bean.OrderDishesEntity;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by zj on 2017/7/4.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单明细
 */
public class FRA_TakeOutDetail extends BaseFragment {
    @Bind(R.id.rl_root_view)
    RelativeLayout rlRootView;
    @Bind(R.id.lv_order_detail)
    ListView lvOrderDishes;
    private ADA_OrderDishesDetail mAdapter;
    private ArrayList<OrderDishesEntity> dataList = new ArrayList<>();

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return rlRootView;
    }

    @Override
    protected void initViewsAndEvents() {
        //有数据页面
        mAdapter = new ADA_OrderDishesDetail(mContext);
        lvOrderDishes.setAdapter(mAdapter);
        setData();
        mAdapter.update(dataList);

        // 无数据页面
//        showEmpty(R.drawable.default_no_order_detail, mContext.getString(R.string.order_no_detail), ContextCompat.getColor(mContext, R.color.color_e9e9e9), ContextCompat.getColor(mContext, R.color.color_222222));
    }

    private void setData() {
        for (int i = 0; i < 10; i++) {
            OrderDishesEntity orderDishesEntity = new OrderDishesEntity();
            orderDishesEntity.dishes_name = "肉菜" + i;
            dataList.add(orderDishesEntity);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_take_out_detail;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }
}
