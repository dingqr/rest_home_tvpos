package com.yonyou.hhtpos.ui.dinner.wm;

import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_TakeOutOrderDetail;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.dish.WMRefundFreeReasonCallbackEntity;
import com.yonyou.hhtpos.bean.wm.RefundReasonEntity;
import com.yonyou.hhtpos.bean.wm.WMDishDetailEntity;
import com.yonyou.hhtpos.bean.wm.WMOrderDetailEntity;
import com.yonyou.hhtpos.dialog.DIA_TakeOutRefund;
import com.yonyou.hhtpos.interfaces.WMReasonsCallback;
import com.yonyou.hhtpos.presenter.IOrderDetailPresenter;
import com.yonyou.hhtpos.presenter.IWMRefundReasonPresenter;
import com.yonyou.hhtpos.presenter.Impl.OrderDetailPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.WMRefundReasonPresenterImpl;
import com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes;
import com.yonyou.hhtpos.view.IWMOrderDetailView;
import com.yonyou.hhtpos.view.IWMRefundReasonView;
import com.yonyou.hhtpos.widgets.FiltrationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.yonyou.hhtpos.R.id.ll_pay_type;

/**
 * Created by zj on 2017/7/6.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖订单详情页面-袁波
 */
public class FRA_TakeOutDetail extends BaseFragment implements IWMOrderDetailView, IWMRefundReasonView, WMReasonsCallback {
    @Bind(R.id.rl_root_view)
    RelativeLayout rlRootView;
    //列表上层悬浮的标题，默认隐藏
    @Bind(R.id.suspension_title)
    LinearLayout suspension_title;
    @Bind(R.id.lv_wm_order_detail)
    ListView wmListView;
    private ADA_TakeOutOrderDetail mAdapter;
    private List<WMDishDetailEntity> dataList = new ArrayList<>();
    //请求外面订单详情接口
    private IOrderDetailPresenter mPresenter;
    //请求外卖退款原因接口
    private IWMRefundReasonPresenter refundReasonPresenter;

    //测试参数
    private String tableBillId = "C50242AC980000009200000000257000";
    private String extendsTypeId = "C4C10A43B8000000EDC0000000288000";
    private String pageNum = "1";
    private String pageSize = "8";


    //设置接口返回数据
    private TextView tvTakeoutCompanyName;
    private TextView tvOrderStatus;
    private TextView tvCreateTime;
    private TextView tvBillMoney;
    private TextView tvRealReceiveMoney;
    private TextView tvReceiveTime;
    private TextView tvRefundTime;
    private TextView tvRefundMoney;
    private TextView tvRefundReason;

    //退款弹框
    DIA_TakeOutRefund dia_takeOutRefund;

    //设置接口返回数据
    @Bind(R.id.tv_arrive_time)//送达时间
            TextView tvArriveTime;
    @Bind(R.id.tv_send_now)
    TextView tvSendNow;//是否立即送餐
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_customer_name)
    TextView tvCustomerName;
    //人数和就餐时段
    @Bind(R.id.tv_person_and_dinner_type)
    TextView tvPersonAndDinnerType;
    @Bind(R.id.tv_arrive_address)
    TextView tvArriveAddress;
    @Bind(R.id.tv_remarks)
    TextView tvRemarks;//备注

//  支付信息

    @Bind(R.id.tv_total_billmoney)
    TextView tvTotalBillmoney;//总金额
    @Bind(R.id.tv_reduce_amount)
    TextView tvReduceMoney;//优惠金额
    @Bind(R.id.tv_pay_type)
    TextView tvPayType;//支付类型-百度支付
    @Bind(R.id.tv_refund_type)
    TextView tvRefundType;//退款类型-百度支付
    @Bind(R.id.tv_integral)
    TextView tvIntegral;//积分
    @Bind(R.id.tv_invoice)
    TextView tvInvoice;//查看发票二维码

    //   提示
    @Bind(R.id.ll_total_billmoney)
    LinearLayout llTotalBillmoney;//总计
    @Bind(R.id.ll_reduce_amount)
    LinearLayout llReduceAmount;//优惠
    @Bind(ll_pay_type)
    LinearLayout llPayType;//百度支付
    @Bind(R.id.ll_refund_type)
    LinearLayout llRefundype;//积分
    @Bind(R.id.ll_integral)
    LinearLayout llIntegral;//积分
    @Bind(R.id.ll_invoidce)
    LinearLayout llInvoidce;//发票

    //点菜和退款按钮
    @Bind(R.id.btn_left)
    TextView btnLeft;
    @Bind(R.id.btn_right)
    TextView btnRight;

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
        mPresenter = new OrderDetailPresenterImpl(mContext, FRA_TakeOutDetail.this);
        mPresenter.requestWMOrderDetail(tableBillId);

        //有数据页面
        mAdapter = new ADA_TakeOutOrderDetail(mContext);

        //添加头布局
        View headerView = View.inflate(mContext, R.layout.item_take_out_header, null);
        //绑定左边详情部分头部的详情信息
        bindHeadView(headerView);
        //列表中需要悬浮的部分
        View suspension_head_title = View.inflate(mContext, R.layout.suspension_header_view, null);
        wmListView.addHeaderView(headerView);
        wmListView.addHeaderView(suspension_head_title);
        wmListView.setAdapter(mAdapter);

        wmListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    suspension_title.setVisibility(View.VISIBLE);
                } else {
                    suspension_title.setVisibility(View.GONE);
                }
            }
        });
        // 无数据页面
//        showEmpty(R.drawable.default_no_order_detail, mContext.getResources().getString(R.string.empty_msg), ContextCompat.getColor(mContext, R.color.color_e9e9e9), ContextCompat.getColor(mContext, R.color.color_222222),mContext.getResources().getString(R.string.empty_msg_other));
        refundReasonPresenter = new WMRefundReasonPresenterImpl(mContext, this);
        refundReasonPresenter.getWMRefundReason(extendsTypeId, pageNum, pageSize);
    }

    @OnClick({R.id.btn_right, R.id.btn_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_right:
                readyGo(ACT_OrderDishes.class);
                break;
            case R.id.btn_left:
                //退款弹框
                dia_takeOutRefund.getDialog().show();
                dia_takeOutRefund.setWmReasonsCallback(FRA_TakeOutDetail.this);
                break;
            default:
                break;
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

    /**
     * 绑定左边详情部分头部的详情信息
     *
     * @param headerView
     */
    private void bindHeadView(View headerView) {
        tvTakeoutCompanyName = (TextView) headerView.findViewById(R.id.tv_takeout_company_name);
        tvOrderStatus = (TextView) headerView.findViewById(R.id.tv_order_status);
        tvCreateTime = (TextView) headerView.findViewById(R.id.tv_create_time);
        tvBillMoney = (TextView) headerView.findViewById(R.id.tv_bill_money);
        tvRealReceiveMoney = (TextView) headerView.findViewById(R.id.tv_real_receive_money);
        tvReceiveTime = (TextView) headerView.findViewById(R.id.tv_receive_time);
        tvRefundTime = (TextView) headerView.findViewById(R.id.tv_refund_time);
        tvRefundMoney = (TextView) headerView.findViewById(R.id.tv_refund_money);
        tvRefundReason = (TextView) headerView.findViewById(R.id.tv_refund_reason);
    }

    public void requestTakeOutDetail(String tableBillId) {
        mPresenter.requestWMOrderDetail(tableBillId);
    }

    /**
     * 获取外卖订单详情信息
     *
     * @param orderDetailEntity
     */
    @Override
    public void requestWMOrderDetail(WMOrderDetailEntity orderDetailEntity) {
        if (orderDetailEntity != null) {
            List<WMDishDetailEntity> dishList = orderDetailEntity.dishList;
            if (dishList != null && dishList.size() > 0) {
                this.dataList = dishList;
                mAdapter.update(dataList,true);
            }
            //左侧信息
            //设置订单详情信息
            //订单来源-百度/饿了么
            tvTakeoutCompanyName.setText(orderDetailEntity.takeOutCompanyName);
            //创建时间-要求后台返回Long值 -07-06 11:00
            tvCreateTime.setText(orderDetailEntity.orderTime);
            //总计
            tvBillMoney.setText("￥" + orderDetailEntity.getBillOriginMoney());

            //缺的字段
            //实收金额和收款时间
            tvRealReceiveMoney.setText("￥" + orderDetailEntity.getBillMoney());
            //2017-06-08 11:30
            tvReceiveTime.setText(orderDetailEntity.billTime);
            //退款金额和退款时间
//            tvRefundMoney.setText();
            //2017-06-08 11:30
//            tvRefundTime.setText();
            //退款原因
//            tvRefundReason.setText();

            //右侧信息-要求返回Long型
            tvArriveTime.setText(orderDetailEntity.arriveTime);
            tvSendNow.setText("(" + mContext.getResources().getString(R.string.string_send_now) + ")");
            tvSendNow.setVisibility(orderDetailEntity.sendNow.equals("Y") ? View.VISIBLE : View.GONE);
            tvPhone.setText(orderDetailEntity.phone);
            tvCustomerName.setText(orderDetailEntity.name);
            tvArriveAddress.setText(orderDetailEntity.address);
            tvReduceMoney.setText("￥" + orderDetailEntity.getReduceMoney());
            //缺少的字段“
            //就餐人数和时段
            tvPersonAndDinnerType.setText(orderDetailEntity.personNum + "人" + "(午餐)");
            //备注
            tvRemarks.setText(orderDetailEntity.remark);
            tvRemarks.setVisibility(!TextUtils.isEmpty(orderDetailEntity.remark) ? View.VISIBLE : View.GONE);
            tvTotalBillmoney.setText("￥" + orderDetailEntity.getBillMoney());
            //支付类型-百度支付
            //退款类型-百度支付
            //积分
            tvIntegral.setText(orderDetailEntity.nowPoints);


            //根据订单状态设置显示信息
            handOrderStatus(orderDetailEntity);
        }

    }

    /**
     * 根据订单状态设置显示信息
     *
     * @param orderDetailEntity
     */
    private void handOrderStatus(WMOrderDetailEntity orderDetailEntity) {
        // 开单1，下单2，结账3，退款4
        int orderState = -1;
        if (!TextUtils.isEmpty(orderDetailEntity.orderState)) {
            orderState = Integer.parseInt(orderDetailEntity.orderState);
        }
        switch (orderState) {
            case 1:
                //显示：总计
                llTotalBillmoney.setVisibility(View.VISIBLE);
                llReduceAmount.setVisibility(View.GONE);
                llPayType.setVisibility(View.GONE);
                llIntegral.setVisibility(View.GONE);
                llRefundype.setVisibility(View.GONE);
                llInvoidce.setVisibility(View.GONE);
                tvOrderStatus.setText(mContext.getResources().getString(R.string.string_not_order));

                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText(R.string.string_order_dishes);
                break;
            case 2:
                //显示总计和优惠
                tvTotalBillmoney.setVisibility(View.VISIBLE);
                tvReduceMoney.setVisibility(View.VISIBLE);
                tvOrderStatus.setText(mContext.getResources().getString(R.string.string_ordered));

                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText(mContext.getResources().getString(R.string.string_go_settle_amount));
                break;
            case 3:
                //显示：总计、优惠、支付、积分，发票未开的状态
                tvOrderStatus.setText(mContext.getResources().getString(R.string.sting_payed));

                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                btnLeft.setText(mContext.getResources().getString(R.string.string_refund));
                btnRight.setText(mContext.getResources().getString(R.string.string_late_make_bill));
                break;
            case 4:
                //全显示：发票状态：显示已开
                tvOrderStatus.setText(mContext.getResources().getString(R.string.string_refunded));
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText(mContext.getResources().getString(R.string.string_late_make_bill));
                break;
        }
    }

    @Override
    public void getWMRefundReason(List<RefundReasonEntity> reasonList) {
        if (reasonList != null && reasonList.size() > 0) {
            FilterItemEntity refundReasons = new FilterItemEntity();
            refundReasons.setTitle(mContext.getString(R.string.refund_reason));
            ArrayList<FilterOptionsEntity> refundReasonOptions = new ArrayList<>();
            for (int i = 0; i < reasonList.size(); i++) {
                FilterOptionsEntity foe = new FilterOptionsEntity();
                foe.setOption(reasonList.get(i).getReasonName());
                foe.setType(FiltrationView.REFUND_REASON);
                refundReasonOptions.add(foe);
            }
            refundReasons.setOptions(refundReasonOptions);

            dia_takeOutRefund = new DIA_TakeOutRefund(mContext, refundReasons);
            dia_takeOutRefund.setWmReasonsCallback(FRA_TakeOutDetail.this);
        }
    }

    @Override
    public void sendItems(WMRefundFreeReasonCallbackEntity bean) {
        //Todo 获取退款原因后的处理
    }
}
