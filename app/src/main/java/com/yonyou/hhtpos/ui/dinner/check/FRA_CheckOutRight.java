package com.yonyou.hhtpos.ui.dinner.check;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.log.Elog;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_CheckOutPayType;
import com.yonyou.hhtpos.adapter.ADA_DiscountType;
import com.yonyou.hhtpos.adapter.ADA_PayHistory;
import com.yonyou.hhtpos.application.MyApplication;
import com.yonyou.hhtpos.bean.check.DiscountEntity;
import com.yonyou.hhtpos.bean.check.PayTypeEntity;
import com.yonyou.hhtpos.bean.check.RequestPayEntity;
import com.yonyou.hhtpos.bean.check.SettleAccountDataEntity;
import com.yonyou.hhtpos.dialog.DIA_AutoDismiss;
import com.yonyou.hhtpos.dialog.DIA_CheckOutByCash;
import com.yonyou.hhtpos.dialog.DIA_Discount;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.presenter.IPayTypePresenter;
import com.yonyou.hhtpos.presenter.IPrintPresenter;
import com.yonyou.hhtpos.presenter.IQueryBillInfoPresenter;
import com.yonyou.hhtpos.presenter.ISettleAccountPresenter;
import com.yonyou.hhtpos.presenter.Impl.DiscountPlanPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.PayTypeListPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.PrintPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.QueryBillInfoPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.SettleAccountPresenterImpl;
import com.yonyou.hhtpos.util.AidlUtil;
import com.yonyou.hhtpos.util.Constants;
import com.yonyou.hhtpos.view.IDiscountPlanView;
import com.yonyou.hhtpos.view.IPayTypeView;
import com.yonyou.hhtpos.view.IPrintView;
import com.yonyou.hhtpos.view.IQueryBillInfoView;
import com.yonyou.hhtpos.view.ISettleAccountView;
import com.yonyou.hhtpos.widgets.BanSlideListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 结账页面右侧fragment
 * 作者：liushuofei on 2017/7/19 14:49
 */
public class FRA_CheckOutRight extends BaseFragment implements IQueryBillInfoView, ISettleAccountView, IDiscountPlanView, IPrintView, IPayTypeView{

    @Bind(R.id.layout_root)
    LinearLayout layoutRoot;
    @Bind(R.id.gv_discount_type)
    GridView mDiscountTypeGv;
    @Bind(R.id.gv_pay_type)
    GridView mPayTypeGv;
    @Bind(R.id.tv_dish_status)
    TextView tvDishStatus;//菜品状态
    @Bind(R.id.tv_discount_total)
    TextView tvDiscountTotal;//合计优惠
    @Bind(R.id.tv_discounmt_money)
    TextView tvDiscounmtMoney;//折扣金额
    @Bind(R.id.tv_ignore_money)
    TextView tvIgnoreMoney;//抹零金额
    @Bind(R.id.tv_paid_money)
    TextView tvPaidMoney;//已支付
    @Bind(R.id.tv_unpaid_money)
    TextView tvUnpaidMoney;//未支付

    @Bind(R.id.lv_pay_type)
    BanSlideListView lvPaHistory;//支付方式
    @Bind(R.id.layout_couponuseds)
    LinearLayout layoutCouponuseds;//代金券
    @Bind(R.id.tv_coupon_count)
    TextView tvCouponCount;//使用的代金券数量
    @Bind(R.id.tv_coupon_amount)
    TextView tvCouponAmount;//使用的代金券总金额
    @Bind(R.id.layout_settled)
    LinearLayout layoutSettled;//结清

    private ADA_DiscountType mDiscountAdapter;
    private ADA_CheckOutPayType mPayTypeAdapter;
    private SettleAccountDataEntity dataBean;
    private String[] payTypeNames = {"现金", "免单", "零结", "会员余额", "聚合支付", "畅捷POS", "微信支付", "支付宝", "更多"};
    private String[] discountPrograms = {MyApplication.getInstance().getResources().getString(R.string.string_discount), MyApplication.getInstance().getResources().getString(R.string.string_coupon), MyApplication.getInstance().getResources().getString(R.string.string_discount_let)};
    private int[] discountIcons = {R.drawable.ic_check_out_discount, R.drawable.ic_discount_coupon, R.drawable.ic_discount_money};
    private DIA_CheckOutByCash mDiaCheckOutByCash;
    private IQueryBillInfoPresenter mPresenter;
    private ISettleAccountPresenter mSettleAccountPresenter;
    private String tableBillId;
    // 1-部分支付，2-支付完成，3-已退款，4-未支付
    private int payStatus;
    private ADA_PayHistory mPayhistoryAdapter;
    //选择折扣方案弹窗
    private DIA_Discount mDiaDiscount;
    private DiscountPlanPresenterImpl mDiscountPlanPresenter;
    //折扣方案集合
    private List<DiscountEntity> mDiscountPlanList = new ArrayList<>();

    /**打印中间者 */
    private IPrintPresenter mPrintPresenter;
    /**支付方式中间者 */
    private IPayTypePresenter mPayTypePresenter;

    private SettleAccountDataEntity settleAccountDataEntity;
    private PayTypeEntity currentPayTypeBean;

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onRefreshRight(SettleAccountDataEntity settleAccountDataEntity) {
        this.dataBean = settleAccountDataEntity;
        if (dataBean.payStatus != null) {
            payStatus = Integer.parseInt(dataBean.payStatus);
        }
        refreshData();
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
        return layoutRoot;
    }

    @Override
    protected void initViewsAndEvents() {
        tableBillId = ((ACT_CheckOut) getActivity()).getTableBillId();

        mDiaCheckOutByCash = new DIA_CheckOutByCash(getActivity());
        mPresenter = new QueryBillInfoPresenterImpl(mContext, this);
        mSettleAccountPresenter = new SettleAccountPresenterImpl(mContext, this);
        mDiscountPlanPresenter = new DiscountPlanPresenterImpl(mContext, this);
        mDiscountPlanPresenter.getAllDiscountPlan(Constants.SHOP_ID);

        //支付记录
        mPayhistoryAdapter = new ADA_PayHistory(mContext);
        lvPaHistory.setAdapter(mPayhistoryAdapter);

        //折扣方案-暂时产品设计写死在前端
        mDiscountAdapter = new ADA_DiscountType(mContext);
        mDiscountTypeGv.setAdapter(mDiscountAdapter);
        ArrayList<DiscountEntity> discountProgramList = new ArrayList<>();
        for (int i = 0; i < discountPrograms.length; i++) {
            DiscountEntity discountEntity = new DiscountEntity();
            discountEntity.discountName = discountPrograms[i];
            discountEntity.icon = discountIcons[i];
            discountProgramList.add(discountEntity);
        }
        mDiscountAdapter.update(discountProgramList, true);

        //支付方式
        mPayTypeAdapter = new ADA_CheckOutPayType(mContext);
        mPayTypeGv.setAdapter(mPayTypeAdapter);
//        ArrayList<String> payTypeList = new ArrayList<>();
//        for (int i = 0; i < payTypeNames.length; i++) {
//            payTypeList.add(payTypeNames[i]);
//        }
//        mPayTypeAdapter.update(payTypeList, true);


        mPayTypeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPayTypeBean = (PayTypeEntity)parent.getAdapter().getItem(position);

                if (null != currentPayTypeBean){
                    if (!TextUtils.isEmpty(currentPayTypeBean.getPayWayName()) && currentPayTypeBean.getPayWayName().equals("现金")){
                        mDiaCheckOutByCash.show();
                    }
                }
            }
        });

        //收到现金
        mDiaCheckOutByCash.setOnReceiveMoneyListener(new DIA_CheckOutByCash.OnReceiveMoneyListener() {
            @Override
            public void onReceiveMoney(String money) {
                RequestPayEntity requestPayEntity = new RequestPayEntity();
                requestPayEntity.payAmount = money;

                if (null != currentPayTypeBean){
                    // 支付方式编码
                    requestPayEntity.payType = currentPayTypeBean.getPayWayCode();
                    // 支付方式名称
                    requestPayEntity.payWayName = currentPayTypeBean.getPayWayName();
                }
                handlePayStatus(requestPayEntity);

            }
        });
        mDiaDiscount = new DIA_Discount(mContext);

        mDiscountTypeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //折扣
                        mDiaDiscount.show();
                        break;
                    case 1:
                        //优惠券
                        break;
                    case 2:
                        //折让
                        break;
                }
            }
        });

        mPrintPresenter = new PrintPresenterImpl(mContext, this);
        mPayTypePresenter = new PayTypeListPresenterImpl(mContext, this);

        // 请求支付方式列表
        mPayTypePresenter.requestPayTypeList(Constants.SHOP_ID);
    }

    /**
     * 根据账单状态做不同的处理
     *
     * @param requestPayEntity
     */
    private void handlePayStatus(RequestPayEntity requestPayEntity) {
        switch (payStatus) {
            //未支付
            case 1:
                mPresenter.queryBillInfo(API.compId, Constants.SHOP_ID, tableBillId, true, requestPayEntity);
                break;
            //支付完成
            case 2:
                break;
            //已退款
            case 3:
                break;
            //部分支付
            case 4:
                mSettleAccountPresenter.settleAccount(API.compId, "", Constants.SHOP_ID, tableBillId, requestPayEntity);
                break;
        }

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_check_out_right;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    /**
     * 刷新界面数据
     */
    private void refreshData() {
        if (payStatus == 2) {
            layoutSettled.setVisibility(View.VISIBLE);
            mPayTypeGv.setVisibility(View.GONE);
        } else {
            layoutSettled.setVisibility(View.GONE);
            mPayTypeGv.setVisibility(View.VISIBLE);
        }
        //待支付
        if (!TextUtils.isEmpty(dataBean.getUnpaidMoney())) {
            tvUnpaidMoney.setText(mContext.getResources().getString(R.string.RMB_symbol) + dataBean.getUnpaidMoney());
        }
        //优惠合计
        if (!TextUtils.isEmpty(dataBean.getDiscountTotal())) {
            tvDiscountTotal.setText(mContext.getResources().getString(R.string.RMB_symbol) + dataBean.getDiscountTotal());
        }
        //折扣
        if (!TextUtils.isEmpty(dataBean.getDiscountMoney())) {
            tvDiscounmtMoney.setText(mContext.getResources().getString(R.string.RMB_symbol) + dataBean.getDiscountMoney());
        }
        //自动抹零
        if (!TextUtils.isEmpty(dataBean.getIgnoreMoney())) {
            tvIgnoreMoney.setText(mContext.getResources().getString(R.string.RMB_symbol) + dataBean.getIgnoreMoney());
        }
        //代金券
        if (dataBean.couponUseds != null && dataBean.couponUseds.size() > 0) {
            layoutCouponuseds.setVisibility(View.VISIBLE);
            Double totalMoney = 0.0;
            for (int i = 0; i < dataBean.couponUseds.size(); i++) {
                totalMoney += Double.parseDouble(dataBean.couponUseds.get(i).getCouponMoney());
            }
            tvCouponAmount.setText("(" + dataBean.couponUseds.size() + mContext.getResources().getString(R.string.string_unit_coupon) + ")");
            tvCouponAmount.setText(StringUtil.getString(totalMoney));
        } else {
            layoutCouponuseds.setVisibility(View.GONE);
        }
        //支付方式
        if (dataBean.paidHistory != null && dataBean.paidHistory.size() > 0) {
            mPayhistoryAdapter.update(dataBean.paidHistory, true);
        }
        //发票

        //已支付
        if (!TextUtils.isEmpty(dataBean.getPaidMoney())) {
            tvPaidMoney.setText(mContext.getResources().getString(R.string.RMB_symbol) + dataBean.getPaidMoney());
        }
        //给弹窗设置待支付金额
        if (!TextUtils.isEmpty(dataBean.getUnpaidMoney())) {
            mDiaCheckOutByCash.setMaxInputMoneyHint(dataBean.getUnpaidMoney());
        }

    }

    /**
     * 查询账单信息及首次支付
     *
     * @param settleAccountDataEntity
     */
    @Override
    public void queryBillInfo(SettleAccountDataEntity settleAccountDataEntity) {
//        new DIA_AutoDismiss(mContext, getString(R.string.string_receive_money_successful)).show();

        if (null == settleAccountDataEntity)
            return;

        this.settleAccountDataEntity = settleAccountDataEntity;

        if (!TextUtils.isEmpty(settleAccountDataEntity.sourceId)){
            mPrintPresenter.requestPrintOrder("before1", Constants.SHOP_ID, "", settleAccountDataEntity.sourceId);
        }else {
            // 收款成功
            CommonUtils.makeEventToast(mContext, getString(R.string.string_receive_money_successful), false);
            // 结清
            if (settleAccountDataEntity.payStatus != null && settleAccountDataEntity.payStatus.equals("2")) {
                getActivity().finish();
            } else {
                EventBus.getDefault().post(settleAccountDataEntity);
            }
        }
    }

    /**
     * 二次及多次结账
     *
     * @param settleAccountDataEntity
     */
    @Override
    public void settleAccount(SettleAccountDataEntity settleAccountDataEntity) {
        new DIA_AutoDismiss(mContext, getString(R.string.string_receive_money_successful)).show();
        if (settleAccountDataEntity != null) {
            if (settleAccountDataEntity.payStatus != null && settleAccountDataEntity.payStatus.equals("2")) {
                getActivity().finish();
            } else {
                EventBus.getDefault().post(settleAccountDataEntity);
            }
        }
    }

    /**
     * 获取所有折扣方案
     *
     * @param dataList
     */
    @Override
    public void getAllDiscountPlan(List<DiscountEntity> dataList) {
        this.mDiscountPlanList = dataList;
        if (mDiscountPlanList != null && mDiscountPlanList.size() > 0) {
            mDiaDiscount.setData(mDiscountPlanList);
        }
    }

    @Override
    public void requestPrintOrder(String[] strings) {
        byte[] bytes = new byte[strings.length];
        for (int i = 0; i < strings.length; i++) {
            bytes[i] = Byte.parseByte(strings[i]);
        }

        // 打印文字
//        AidlUtil.getInstance().printText(AidlUtil.PRINT_TEXT, 20, false, false);
        try {
//            AidlUtil.getInstance().printText(new String(BytesUtil.getBaiduTestBytes(), "UTF-8"), 20, false, false);
//            AidlUtil.getInstance().printText(new String(BytesUtil.getBaiduTestBytes(), "GBK"), 20, false, false);
//            AidlUtil.getInstance().sendRawData(BytesUtil.getBaiduTestBytes());
            AidlUtil.getInstance().sendRawData(bytes);

            Elog.e("printText", String.valueOf(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 收款成功
        CommonUtils.makeEventToast(mContext, getString(R.string.string_receive_money_successful), false);
        // 结清
        if (null != settleAccountDataEntity){
            if (settleAccountDataEntity.payStatus != null && settleAccountDataEntity.payStatus.equals("2")) {
                getActivity().finish();
            } else {
                EventBus.getDefault().post(settleAccountDataEntity);
            }
        }
    }

    @Override
    public void requestPayTypeList(List<PayTypeEntity> dataList) {
        mPayTypeAdapter.update(dataList, true);
    }
}
