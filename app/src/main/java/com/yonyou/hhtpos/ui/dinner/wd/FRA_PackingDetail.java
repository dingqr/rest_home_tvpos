package com.yonyou.hhtpos.ui.dinner.wd;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
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
import com.yonyou.hhtpos.adapter.ADA_WDDetailPayType;
import com.yonyou.hhtpos.bean.wd.DishDetaiListlEntity;
import com.yonyou.hhtpos.bean.wd.OrderListEntity;
import com.yonyou.hhtpos.bean.wd.WDOrderDetailEntity;
import com.yonyou.hhtpos.presenter.IOrderDetailPresenter;
import com.yonyou.hhtpos.presenter.Impl.OrderDetailPresenterImpl;
import com.yonyou.hhtpos.view.IWDOrderDetailView;
import com.yonyou.hhtpos.widgets.BanSlideListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by zj on 2017/7/4.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单明细-马诗雨
 */
public class FRA_PackingDetail extends BaseFragment implements IWDOrderDetailView {
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
    @Bind(R.id.tv_pay_status)
    TextView tvPayStatus;
    @Bind(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @Bind(R.id.tv_reduce_money)
    TextView tvReduceMoney;
    @Bind(R.id.tv_discount_money)
    TextView tvDiscountMoney;
    @Bind(R.id.tv_ignore_money)
    TextView tvIgnoreMoney;
    @Bind(R.id.tv_real_receive_amount)
    TextView tvRealReceiveAmount;
    @Bind(R.id.tv_cashier)
    TextView tvCashier;
    @Bind(R.id.tv_not_pay)
    TextView tvNotPay;
    @Bind(R.id.tv_wait_receive_money)
    TextView tvWaitReceiveMoney;
    //支付方式
    @Bind(R.id.lv_pay_type)
    BanSlideListView mLvPayType;
    //支付信息
    @Bind(R.id.layout_pay_info)
    LinearLayout layoutPayInfo;
    private ADA_WDDetailPayType mPaytypeAdapter;
    private ADA_OrderDishesDetail mAdapter;
    private ArrayList<DishDetaiListlEntity> dataList = new ArrayList<>();

    //请求外带详情接口
    private IOrderDetailPresenter mPresenter;

    /**
     * 左侧外带订单列表是否为空
     *
     * @param dataList
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onLeftOrderList(List<OrderListEntity> dataList) {
        if (dataList == null || dataList.size() == 0) {
            showEmpty(R.drawable.default_no_order_detail, mContext.getResources().getString(R.string.empty_msg), ContextCompat.getColor(mContext, R.color.color_e9e9e9), ContextCompat.getColor(mContext, R.color.color_222222), mContext.getResources().getString(R.string.empty_msg_other));
        }
    }


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
        mPresenter = new OrderDetailPresenterImpl(getActivity(), this);

        //有数据页面
        mAdapter = new ADA_OrderDishesDetail(mContext);
        lvOrderDishes.setAdapter(mAdapter);
        //设置支付方式
        mPaytypeAdapter = new ADA_WDDetailPayType(mContext);
        mLvPayType.setAdapter(mPaytypeAdapter);

        // 无数据页面
//        showEmpty(R.drawable.default_no_order_detail, mContext.getResources().getString(R.string.empty_msg), ContextCompat.getColor(mContext, R.color.color_e9e9e9), ContextCompat.getColor(mContext, R.color.color_222222),mContext.getResources().getString(R.string.empty_msg_other));
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
        mPresenter.requestWDOrderDetail(tableBillId);
    }

    /**
     * 外带订单详情
     *
     * @param orderDetailEntity
     */
    @Override
    public void requestWDOrderDetail(WDOrderDetailEntity orderDetailEntity) {
        if (orderDetailEntity != null) {
            //开单服务员
            tvOpenOrderWaiter.setText(orderDetailEntity.waiterName);
            //开单时间
            tvOpenOrderTime.setText(String.valueOf(AppDateUtil.getTimeStamp(orderDetailEntity.openTime, AppDateUtil.YYYY_MM_DD_HH_MM_SS)));
            //开单服务员
            tvOpenOrderPersonNum.setText(StringUtil.getString(orderDetailEntity.personNum));
            //支付状态
            if (orderDetailEntity.payStatus.equals("Y")) {
                tvPayStatus.setText(mContext.getResources().getString(R.string.string_pay_successful));
                tvNotPay.setVisibility(View.GONE);
                tvWaitReceiveMoney.setVisibility(View.GONE);
                layoutPayInfo.setVisibility(View.VISIBLE);
            } else {
                tvNotPay.setVisibility(View.VISIBLE);
                tvWaitReceiveMoney.setVisibility(View.VISIBLE);
                //待收金额
//                tvWaitReceiveMoney.setText();
                tvPayStatus.setText(mContext.getResources().getString(R.string.string_wait_pay));
                layoutPayInfo.setVisibility(View.GONE);
            }

            //会员手机号
            if (orderDetailEntity.memberPhone.length() == 11) {
                String maskNumber = orderDetailEntity.memberPhone.substring(0, 3) + "****" + orderDetailEntity.memberPhone.substring(7, orderDetailEntity.memberPhone.length());
                tvPhoneNumber.setText(maskNumber);
            }
            //优惠金额
            tvReduceMoney.setText("￥" + orderDetailEntity.getReduceMoney());
            //折扣
            tvDiscountMoney.setText("￥" + orderDetailEntity.getDiscountMoney());
            //抹零
            tvIgnoreMoney.setText("￥" + orderDetailEntity.getIgnoreMoney());
            //实际支付
            tvRealReceiveAmount.setText("￥" + orderDetailEntity.getRealReceiveAmount());

            //支付方式-可能组合
            if (orderDetailEntity.payTypeList != null && orderDetailEntity.payTypeList.size() > 0) {
                mPaytypeAdapter.update(orderDetailEntity.payTypeList, true);
            }
            //收银员名称
            tvCashier.setText(orderDetailEntity.waiterName);
            //点菜明细列表
            ArrayList<DishDetaiListlEntity> dishListDetail = orderDetailEntity.dishListDetail;
            if (dishListDetail != null && dishListDetail.size() > 0) {
                mAdapter.update(dishListDetail, true);
            }
        }
    }
}

