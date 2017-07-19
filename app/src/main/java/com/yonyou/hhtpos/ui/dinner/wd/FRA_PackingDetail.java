package com.yonyou.hhtpos.ui.dinner.wd;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_OrderDishesDetail;
import com.yonyou.hhtpos.bean.wd.DishDetaiListlEntity;
import com.yonyou.hhtpos.bean.wd.OrderDetailEntity;
import com.yonyou.hhtpos.presenter.IWDOrderDetailPresenter;
import com.yonyou.hhtpos.presenter.Impl.WDOrderDetailPresenterImpl;
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
    //详情字段
    @Bind(R.id.tv_open_order_waiter)
    TextView tvOpenOrderWaiter;
    @Bind(R.id.tv_open_order_time)
    TextView tvOpenOrderTime;
    @Bind(R.id.tv_open_order_person_num)
    TextView tvOpenOrderPersonNum;
    @Bind(R.id.tv_phone_number)
    TextView tvPhoneNumber;

    private ADA_OrderDishesDetail mAdapter;
    private ArrayList<DishDetaiListlEntity> dataList = new ArrayList<>();

    //请求外带详情接口
    private IWDOrderDetailPresenter mPresenter;

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
        mPresenter = new WDOrderDetailPresenterImpl(getActivity(), this);
        //有数据页面
        mAdapter = new ADA_OrderDishesDetail(mContext);
        lvOrderDishes.setAdapter(mAdapter);
//        setData();


        // 无数据页面
//        showEmpty(R.drawable.default_no_order_detail, mContext.getResources().getString(R.string.empty_msg), ContextCompat.getColor(mContext, R.color.color_e9e9e9), ContextCompat.getColor(mContext, R.color.color_222222),mContext.getResources().getString(R.string.empty_msg_other));
    }

    private void setData() {
        for (int i = 0; i < 10; i++) {
            DishDetaiListlEntity dishDetaiListlEntity = new DishDetaiListlEntity();
            dishDetaiListlEntity.dishName = "肉菜" + i;
            dataList.add(dishDetaiListlEntity);
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
        mPresenter.requestOrderDetail(tableBillId);
    }

    /**
     * 外带订单详情
     *
     * @param orderDetailEntity
     */
    @Override
    public void requestOrderDetail(OrderDetailEntity orderDetailEntity) {
        if (orderDetailEntity != null) {
            tvOpenOrderTime.setText(String.valueOf(AppDateUtil.getTimeStamp(orderDetailEntity.openTime, AppDateUtil.YYYY_MM_DD_HH_MM_SS)));
            tvOpenOrderPersonNum.setText(StringUtil.getString(orderDetailEntity.personNum));
            //会员手机号
            if (orderDetailEntity.memberPhone.length() == 11) {
                String maskNumber = orderDetailEntity.memberPhone.substring(0, 3) + "****" + orderDetailEntity.memberPhone.substring(7, orderDetailEntity.memberPhone.length());
                tvPhoneNumber.setText(maskNumber);
            }
            //点菜明细列表
            ArrayList<DishDetaiListlEntity> dishListDetail = orderDetailEntity.dishListDetail;
            if (dishListDetail != null && dishListDetail.size() > 0) {
                mAdapter.update(dishListDetail, true);
            }
        }
    }
}

