package com.yonyou.hhtpos.ui.dinner.wm;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.framework.library.widgets.ESwipeRefreshLayout;
import com.yonyou.framework.library.widgets.pla.PLALoadMoreListView;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_PackingList;
import com.yonyou.hhtpos.adapter.ADA_TakeOutList;
import com.yonyou.hhtpos.bean.wm.FilterEntity;
import com.yonyou.hhtpos.bean.wm.OrderListEntity;
import com.yonyou.hhtpos.bean.wm.OrderListRequestEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.global.ReceiveConstants;
import com.yonyou.hhtpos.global.SalesModeConstants;
import com.yonyou.hhtpos.presenter.IWMListPresenter;
import com.yonyou.hhtpos.presenter.Impl.WMListPresenterImpl;
import com.yonyou.hhtpos.util.AdapterUtil;
import com.yonyou.hhtpos.view.IWMListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 外卖列表fragment
 * 作者：liushuofei on 2017/7/6 10:47
 */
public class FRA_TakeOutList extends BaseFragment implements IWMListView, SwipeRefreshLayout.OnRefreshListener, PLALoadMoreListView.OnLoadMoreListener {

    @Bind(R.id.srl_take_out)
    ESwipeRefreshLayout srlTakeOut;
    @Bind(R.id.pla_lv_take_out)
    PLALoadMoreListView plaLvTakeOut;

    /**
     * 传入数据
     */
    public static final String TYPE = "type";
    private int type;

    private List<OrderListEntity> dataList;
    private ADA_TakeOutList mAdapter;

    /**
     * 中间者
     */
    private IWMListPresenter mTakeOutListPresenter;
    /**
     * 请求参数
     */
    private OrderListRequestEntity bean;
    /**
     * 外卖公司id
     */
    public String takeOutCompanyId = "";
    /**
     * 市别id
     */
    public String takeOutScheduleId = "";
    /**
     * 当前页数
     */
    private int mCurrentPage = 1;
    /**
     * 默认页数
     */
    private static final String DEFAULT_PAGE = "1";

    public static final FRA_TakeOutList newInstance(int type) {
        FRA_TakeOutList f = new FRA_TakeOutList();
        Bundle bdl = new Bundle(1);
        bdl.putInt(TYPE, type);
        f.setArguments(bdl);
        return f;
    }

    @Override
    protected void onFirstUserVisible() {
        if (NetUtils.isNetworkConnected(mContext)) {
            mTakeOutListPresenter.requestTakeOutList(bean, false, true);
        } else {
            // reset refresh state
            if (null != srlTakeOut) {
                srlTakeOut.setRefreshing(false);
            }
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
    }

    @Override
    protected void onUserVisible() {
        if (NetUtils.isNetworkConnected(mContext)) {
            mTakeOutListPresenter.requestTakeOutList(bean, false, true);
        } else {
            // reset refresh state
            if (null != srlTakeOut) {
                srlTakeOut.setRefreshing(false);
            }
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return srlTakeOut;
    }

    @Override
    protected void initViewsAndEvents() {
        type = getArguments().getInt(TYPE);

        // 加载中的4种颜色
        srlTakeOut.setColorSchemeColors(
                ContextCompat.getColor(mContext, R.color.gplus_color_1),
                ContextCompat.getColor(mContext, R.color.gplus_color_2),
                ContextCompat.getColor(mContext, R.color.gplus_color_3),
                ContextCompat.getColor(mContext, R.color.gplus_color_4));
        srlTakeOut.setOnRefreshListener(this);

        mAdapter = new ADA_TakeOutList(mContext);
        plaLvTakeOut.setAdapter(mAdapter);
        plaLvTakeOut.setOnLoadMoreListener(this);

        // 固定参数
        bean = new OrderListRequestEntity();
        bean.setSalesMode(SalesModeConstants.SALES_MODE_WM);
        bean.setShopId(API.shopId);
        bean.setDinnerStatus(String.valueOf(type));
        bean.setPageSize(String.valueOf(AdapterUtil.DEFAULT_PAGE_SIZE));
        // 非固定参数
        bean.setScheduleNameId("");
        bean.setTakeOutCompanyId("");
        bean.setPageNum(DEFAULT_PAGE);

        // 请求接口
        mTakeOutListPresenter = new WMListPresenterImpl(mContext, this);

        mAdapter.setOnItemClickListener(new ADA_PackingList.OnItemClickLister() {
            @Override
            public void onItemClick(int position) {
                List<OrderListEntity> dataList = mAdapter.getDataSource();
                if (null != dataList && dataList.size() > 0) {
                    requestTakeOutDetail(dataList.get(position).getTableBillId());
                }
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_take_out_list;
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
     * 触发右侧请求外卖详情接口
     */
    private void requestTakeOutDetail(String tableBillId) {
        ACT_TakeOut actTakeOutc = (ACT_TakeOut) getActivity();
        actTakeOutc.requestTakeOutDetail(tableBillId);
    }

    @Override
    public void requestTakeOutList(List<OrderListEntity> dataList, boolean isRefresh) {
        if (null == plaLvTakeOut)
            return;

        // reset state
        if (isRefresh) {
            srlTakeOut.setRefreshing(false);
        } else {
            plaLvTakeOut.onLoadMoreComplete();
        }

        // no more data
        if (mCurrentPage != 1 && (null == dataList || dataList.size() == 0)) {
            plaLvTakeOut.setCanLoadMore(false);
        } else {
            if (null != dataList && dataList.size() > 0) {
                //触发详情接口
                ACT_TakeOut actTakeOut = (ACT_TakeOut) getActivity();
                actTakeOut.requestTakeOutDetail(dataList.get(0).getTableBillId());
                this.dataList = dataList;
                dataList.get(0).setCheck(true);
                mAdapter.update(dataList, isRefresh);
            } else {
                // empty data
                showEmpty(R.drawable.default_no_order, mContext.getString(R.string.take_out_order_no_data));
                // empty event
                EventBus.getDefault().post(new ArrayList<OrderListEntity>());
            }
        }
    }

    @Override
    public void onRefresh() {
        // reset page and list view state
        mCurrentPage = 1;
        if (null != plaLvTakeOut) {
            plaLvTakeOut.setCanLoadMore(true);
        }

        if (NetUtils.isNetworkConnected(mContext)) {
            // 非固定参数
            bean.setScheduleNameId(takeOutScheduleId);
            bean.setTakeOutCompanyId(takeOutCompanyId);
            bean.setPageNum(String.valueOf(mCurrentPage));

            mTakeOutListPresenter.requestTakeOutList(bean, true, false);
        } else {
            // reset refresh state
            if (null != srlTakeOut) {
                srlTakeOut.setRefreshing(false);
            }
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
    }

    @Override
    public void onLoadMore() {
        // current page from adapter count
        mCurrentPage = AdapterUtil.getPage(mAdapter, AdapterUtil.DEFAULT_PAGE_SIZE);

        if (NetUtils.isNetworkConnected(mContext)) {
            // 非固定参数
            bean.setScheduleNameId(takeOutScheduleId);
            bean.setTakeOutCompanyId(takeOutCompanyId);
            bean.setPageNum(String.valueOf(mCurrentPage));

            mTakeOutListPresenter.requestTakeOutList(bean, false, false);
        } else {
            // reset load more state
            if (null != plaLvTakeOut) {
                plaLvTakeOut.onLoadMoreComplete();
            }
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
    }

    @Subscribe
    public void onEvent(FilterEntity bean) {
        takeOutCompanyId = bean.takeOutCompanyId;
        takeOutScheduleId = bean.takeOutScheduleId;
        onRefresh();
    }

    @Override
    protected void onReceiveBroadcast(int intent, Bundle bundle) {
        if (intent == ReceiveConstants.WM_OPEN_ORDER_SUCCESS) {
            onRefresh();
        }
    }
}
