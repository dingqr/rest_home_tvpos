package com.yonyou.hhtpos.ui.dinner.wd;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.framework.library.widgets.ESwipeRefreshLayout;
import com.yonyou.framework.library.widgets.pla.PLALoadMoreListView;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_PackingList;
import com.yonyou.hhtpos.bean.wd.OrderListEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.global.ReceiveConstants;
import com.yonyou.hhtpos.global.SalesModeConstants;
import com.yonyou.hhtpos.presenter.IWDListPresenter;
import com.yonyou.hhtpos.presenter.Impl.WDListPresenterImpl;
import com.yonyou.hhtpos.util.AdapterUtil;
import com.yonyou.hhtpos.view.IWDListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * 外带列表
 * 作者：liushuofei on 2017/7/4 16:45
 */
public class FRA_PackingList extends BaseFragment implements IWDListView, SwipeRefreshLayout.OnRefreshListener, PLALoadMoreListView.OnLoadMoreListener {

    @Bind(R.id.ll_root)
    LinearLayout mRoot;
    @Bind(R.id.srl_packing)
    ESwipeRefreshLayout srlPacking;
    @Bind(R.id.pla_lv_packing)
    PLALoadMoreListView plaLvPacking;

    /**
     * 传入数据
     */
    public static final String TYPE = "type";
    private int type;
    private String payStatus;

    private ADA_PackingList mAdapter;

    /**中间者 */
    private IWDListPresenter mPackingListPresenter;
    /**当前页数 */
    private int mCurrentPage = 1;
    /**默认页数 */
    private static final String DEFAULT_PAGE = "1";

    public static final FRA_PackingList newInstance(int type) {
        FRA_PackingList f = new FRA_PackingList();
        Bundle bdl = new Bundle(1);
        bdl.putInt(TYPE, type);
        f.setArguments(bdl);
        return f;
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
        return srlPacking;
    }

    @Override
    protected void initViewsAndEvents() {
        type = getArguments().getInt(TYPE);
        getPayStatus();

        // 加载中的4种颜色
        srlPacking.setColorSchemeColors(
                ContextCompat.getColor(mContext, R.color.gplus_color_1),
                ContextCompat.getColor(mContext, R.color.gplus_color_2),
                ContextCompat.getColor(mContext, R.color.gplus_color_3),
                ContextCompat.getColor(mContext, R.color.gplus_color_4));
        srlPacking.setOnRefreshListener(this);

        mAdapter = new ADA_PackingList(mContext);
        plaLvPacking.setAdapter(mAdapter);
        plaLvPacking.setOnLoadMoreListener(this);

        mPackingListPresenter = new WDListPresenterImpl(mContext, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            mPackingListPresenter.requestPackingList("", SalesModeConstants.SALES_MODE_WD, API.shopId, DEFAULT_PAGE, String.valueOf(AdapterUtil.DEFAULT_PAGE_SIZE), payStatus, false, true);
        } else {
            // reset refresh state
            if (null != srlPacking) {
                srlPacking.setRefreshing(false);
            }
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
        mAdapter.setOnItemClickListener(new ADA_PackingList.OnItemClickLister() {
            @Override
            public void onItemClick(int position) {
                List<OrderListEntity> dataList = mAdapter.getDataSource();
                if (null != dataList && dataList.size() > 0){
                    requestPackingDetail(dataList.get(position).id);
                }
            }
        });
    }

    /**
     * 触发右侧请求外带详情接口
     */
    private void requestPackingDetail(String tableBillId) {
        ACT_Packing act_packing = (ACT_Packing) getActivity();
        act_packing.requestPackingDetail(tableBillId);
    }

    private void getPayStatus() {
        switch (type) {
            case 0:
                payStatus = "";
                break;

            case 1:
                payStatus = "N";
                break;

            case 2:
                payStatus = "Y";
                break;

            default:
                break;
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_packing_list;
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

    @Override
    public void requestPackingList(List<OrderListEntity> dataList, boolean isRefresh) {
        // reset state
        if (isRefresh) {
            srlPacking.setRefreshing(false);
        } else {
            plaLvPacking.onLoadMoreComplete();
        }

        // no more data
        if (mCurrentPage != 1 && (null == dataList || dataList.size() == 0)) {
            plaLvPacking.setCanLoadMore(false);
        } else {
            if (null != dataList && dataList.size() > 0) {
                //默认请求第一条详情
                requestPackingDetail(dataList.get(0).id);

                dataList.get(0).setCheck(true);
                mAdapter.update(dataList, isRefresh);

            } else {
                // empty data
                showEmpty(R.drawable.default_no_order, mContext.getString(R.string.packing_order_no_data));
                // empty eventBus
                EventBus.getDefault().post(new ArrayList<OrderListEntity>());
            }
        }
    }

    @Override
    public void onRefresh() {
        // reset page and list view state
        mCurrentPage = 1;
        plaLvPacking.setCanLoadMore(true);

        if (NetUtils.isNetworkConnected(mContext)) {
            mPackingListPresenter.requestPackingList("", SalesModeConstants.SALES_MODE_WD, API.shopId, DEFAULT_PAGE, String.valueOf(AdapterUtil.DEFAULT_PAGE_SIZE), payStatus, true, false);
        } else {
            // reset refresh state
            if (null != srlPacking) {
                srlPacking.setRefreshing(false);
            }
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
    }

    @Override
    public void onLoadMore() {
        // current page from adapter count
        mCurrentPage = AdapterUtil.getPage(mAdapter, AdapterUtil.DEFAULT_PAGE_SIZE);

        if (NetUtils.isNetworkConnected(mContext)) {
            mPackingListPresenter.requestPackingList("", SalesModeConstants.SALES_MODE_WD, API.shopId, String.valueOf(mCurrentPage), String.valueOf(AdapterUtil.DEFAULT_PAGE_SIZE), payStatus, false, false);
        } else {
            // reset load more state
            if (null != plaLvPacking) {
                plaLvPacking.onLoadMoreComplete();
            }
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
    }

    @Override
    protected void onReceiveBroadcast(int intent, Bundle bundle) {
        if (intent == ReceiveConstants.WD_OPEN_ORDER_SUCCESS) {
            onRefresh();
        }
    }
}
