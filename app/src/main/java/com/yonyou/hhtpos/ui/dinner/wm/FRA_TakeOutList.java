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
import com.yonyou.hhtpos.adapter.ADA_TakeOutList;
import com.yonyou.hhtpos.bean.TakeOutListBean;
import com.yonyou.hhtpos.bean.TakeOutListEntity;
import com.yonyou.hhtpos.presenter.ITakeOutListPresenter;
import com.yonyou.hhtpos.presenter.Impl.TakeOutListPresenterImpl;
import com.yonyou.hhtpos.util.AdapterUtil;
import com.yonyou.hhtpos.view.ITakeOutListView;

import java.util.List;

import butterknife.Bind;

/**
 * 外卖列表fragment
 * 作者：liushuofei on 2017/7/6 10:47
 */
public class FRA_TakeOutList extends BaseFragment implements ITakeOutListView, SwipeRefreshLayout.OnRefreshListener, PLALoadMoreListView.OnLoadMoreListener{

    @Bind(R.id.srl_take_out)
    ESwipeRefreshLayout srlTakeOut;
    @Bind(R.id.pla_lv_take_out)
    PLALoadMoreListView plaLvTakeOut;

    /**传入数据 */
    public static final String TYPE = "type";
    private int type;

    private List<TakeOutListBean> dataList;
    private ADA_TakeOutList mAdapter;

    /**中间者 */
    private ITakeOutListPresenter mTakeOutListPresenter;
    /**当前页数 */
    private int mCurrentPage = 1;
    /**默认页数 */
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

    }

    @Override
    protected void onUserVisible() {

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
        // 加载中的4种颜色
        srlTakeOut.setColorSchemeColors(
                ContextCompat.getColor(mContext, R.color.gplus_color_1),
                ContextCompat.getColor(mContext, R.color.gplus_color_2),
                ContextCompat.getColor(mContext, R.color.gplus_color_3),
                ContextCompat.getColor(mContext, R.color.gplus_color_4));
        srlTakeOut.setOnRefreshListener(this);

        mAdapter = new ADA_TakeOutList(mContext);
        plaLvTakeOut.setAdapter(mAdapter);

        // 请求接口
        mTakeOutListPresenter = new TakeOutListPresenterImpl(mContext, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            mTakeOutListPresenter.requestTakeOutList("hht", "2", "C482CE78AC000000AA8000000003A000", false, true);
        }else {
            // reset refresh state
            if (null != srlTakeOut) {
                srlTakeOut.setRefreshing(false);
            }
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
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
        return false;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    public void requestTakeOutList(List<TakeOutListEntity> dataList, boolean isRefresh) {
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
                dataList.get(0).setCheck(true);
                mAdapter.update(dataList, isRefresh);
            } else {
                // empty data
                showEmpty(R.drawable.default_no_order, mContext.getString(R.string.take_out_order_no_data));
            }
        }
    }

    @Override
    public void onRefresh() {
        // reset page and list view state
        mCurrentPage = 1;
        plaLvTakeOut.setCanLoadMore(true);

        if (NetUtils.isNetworkConnected(mContext)) {
            mTakeOutListPresenter.requestTakeOutList("hht", "2", "C482CE78AC000000AA8000000003A000", true, false);
        }else {
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
            mTakeOutListPresenter.requestTakeOutList("hht", "2", "C482CE78AC000000AA8000000003A000", false, false);
        }else {
            // reset load more state
            if (null != plaLvTakeOut) {
                plaLvTakeOut.onLoadMoreComplete();
            }
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
    }
}
