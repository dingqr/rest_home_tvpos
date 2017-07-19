package com.yonyou.hhtpos.ui.dinner.wd;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_OrderDishesDetail;
import com.yonyou.hhtpos.bean.DishDetailEntity;
import com.yonyou.hhtpos.bean.wd.OrderDetailEntity;
import com.yonyou.hhtpos.presenter.IOrderDetailPresenter;
import com.yonyou.hhtpos.presenter.Impl.OrderDetailPresenterImpl;
import com.yonyou.hhtpos.view.IOrderDetailView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by zj on 2017/7/4.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单明细
 */
public class FRA_PackingDetail extends BaseFragment implements IOrderDetailView {
    @Bind(R.id.rl_root_view)
    RelativeLayout rlRootView;
    @Bind(R.id.lv_order_detail)
    ListView lvOrderDishes;
    private ADA_OrderDishesDetail mAdapter;
    private ArrayList<DishDetailEntity> dataList = new ArrayList<>();

    //请求接口
    private IOrderDetailPresenter mPresenter;
    //测试参数
    private String tableBillId = "C4A99303500000009A000000001B8000";

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
//        mPresenter = new OrderDetailPresenterImpl(getActivity(), this);
//        mPresenter.requestOrderDetail(tableBillId);
        //有数据页面
        mAdapter = new ADA_OrderDishesDetail(mContext);
        lvOrderDishes.setAdapter(mAdapter);
        setData();
        mAdapter.update(dataList);

        // 无数据页面
//        showEmpty(R.drawable.default_no_order_detail, mContext.getResources().getString(R.string.empty_msg), ContextCompat.getColor(mContext, R.color.color_e9e9e9), ContextCompat.getColor(mContext, R.color.color_222222),mContext.getResources().getString(R.string.empty_msg_other));
    }

    private void setData() {
        for (int i = 0; i < 10; i++) {
            DishDetailEntity orderDishesEntity = new DishDetailEntity();
            orderDishesEntity.dishes_name = "肉菜" + i;
            dataList.add(orderDishesEntity);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_packing_detail;
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

    public void requestPackingDetail(String tableBillId) {
//        mPresenter = new OrderDetailPresenterImpl(mContext, this);
//        mPresenter.requestOrderDetail(tableBillId);
    }

    /**
     * 外带订单详情
     *
     * @param orderDetailEntity
     */
    @Override
    public void requestOrderDetail(OrderDetailEntity orderDetailEntity) {
//        Log.e("TAG", "orderDetailEntity=" + orderDetailEntity);
    }
}

