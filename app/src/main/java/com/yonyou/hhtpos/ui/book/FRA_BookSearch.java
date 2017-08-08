package com.yonyou.hhtpos.ui.book;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.yonyou.hhtpos.global.SalesModeConstants;
import com.yonyou.hhtpos.presenter.IWDListPresenter;
import com.yonyou.hhtpos.presenter.Impl.WDListPresenterImpl;
import com.yonyou.hhtpos.ui.dinner.wd.ACT_Packing;
import com.yonyou.hhtpos.util.AdapterUtil;
import com.yonyou.hhtpos.util.Constants;
import com.yonyou.hhtpos.view.IWDListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 搜索页面
 * 作者：liushuofei on 2017/7/3 14:19
 */
public class FRA_BookSearch extends BaseFragment implements IWDListView, SwipeRefreshLayout.OnRefreshListener, PLALoadMoreListView.OnLoadMoreListener {

    @Bind(R.id.tv_filter)
    TextView tvSearch;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.srl_data)
    ESwipeRefreshLayout srlData;
    @Bind(R.id.pla_lv_data)
    PLALoadMoreListView plaLvData;
    @Bind(R.id.iv_delete)
    ImageView ivDelete;

    /**传入参数 */
    public static final String SEARCH_TYPE = "search.type";
    private String searchType;

    public static final String TYPE_YD = "type.yd";
    public static final String TYPE_WD = "type.wd";
    public static final String TYPE_WM = "type.wm";

    /**外带 */
    private ADA_PackingList mAdapter;
    private IWDListPresenter mPackingListPresenter;

    /**当前页数 */
    private int mCurrentPage = 1;
    /**默认页数 */
    private static final String DEFAULT_PAGE = "1";

    public static final FRA_BookSearch newInstance(String type) {
        FRA_BookSearch f = new FRA_BookSearch();
        Bundle bdl = new Bundle(1);
        bdl.putString(SEARCH_TYPE, type);
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
        return srlData;
    }

    @Override
    protected void initViewsAndEvents() {
        Bundle bundle = getArguments();
        if (null != bundle) {
            searchType = bundle.getString(SEARCH_TYPE, "");
        }

        if (null == searchType)
            return;

        // 加载中的4种颜色
        srlData.setColorSchemeColors(
                ContextCompat.getColor(mContext, R.color.gplus_color_1),
                ContextCompat.getColor(mContext, R.color.gplus_color_2),
                ContextCompat.getColor(mContext, R.color.gplus_color_3),
                ContextCompat.getColor(mContext, R.color.gplus_color_4));
        srlData.setOnRefreshListener(this);
        plaLvData.setOnLoadMoreListener(this);

        if (searchType.equals(TYPE_WD)) {
            mPackingListPresenter = new WDListPresenterImpl(mContext, this);
            mAdapter = new ADA_PackingList(mContext);
            plaLvData.setAdapter(mAdapter);
        }

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    search();
                }
                return false;
            }
        });
    }

    private void search() {
        if (NetUtils.isNetworkConnected(mContext)) {
            mPackingListPresenter.requestPackingList(etSearch.getText().toString(), SalesModeConstants.SALES_MODE_WD, Constants.SHOP_ID, DEFAULT_PAGE, String.valueOf(AdapterUtil.DEFAULT_PAGE_SIZE), "", true, true);
        } else {
            // reset refresh state
            if (null != srlData) {
                srlData.setRefreshing(false);
            }
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_book_search;
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
     * 外带列表回调
     *
     * @param dataList  外带列表数据
     * @param isRefresh 是否为刷新
     */
    @Override
    public void requestPackingList(List<OrderListEntity> dataList, boolean isRefresh) {
        // reset state
        if (isRefresh) {
            srlData.setRefreshing(false);
        } else {
            plaLvData.onLoadMoreComplete();
        }

        // no more data
        if (mCurrentPage != 1 && (null == dataList || dataList.size() == 0)) {
            plaLvData.setCanLoadMore(false);
        } else {
            if (null != dataList && dataList.size() > 0) {
                //默认请求第一条详情
                ((ACT_Packing) getActivity()).requestPackingDetail(dataList.get(0));

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
        plaLvData.setCanLoadMore(true);

        if (NetUtils.isNetworkConnected(mContext)) {
            mPackingListPresenter.requestPackingList(etSearch.getText().toString(), SalesModeConstants.SALES_MODE_WD, Constants.SHOP_ID, DEFAULT_PAGE, String.valueOf(AdapterUtil.DEFAULT_PAGE_SIZE), "", true, false);
        } else {
            // reset refresh state
            if (null != srlData) {
                srlData.setRefreshing(false);
            }
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
    }

    @Override
    public void onLoadMore() {
        // current page from adapter count
        mCurrentPage = AdapterUtil.getPage(mAdapter, AdapterUtil.DEFAULT_PAGE_SIZE);

        if (NetUtils.isNetworkConnected(mContext)) {
            mPackingListPresenter.requestPackingList(etSearch.getText().toString(), SalesModeConstants.SALES_MODE_WD, Constants.SHOP_ID, String.valueOf(mCurrentPage), String.valueOf(AdapterUtil.DEFAULT_PAGE_SIZE), "", false, false);
        } else {
            // reset load more state
            if (null != plaLvData) {
                plaLvData.onLoadMoreComplete();
            }
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
    }

    @OnClick({R.id.iv_delete, R.id.tv_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_delete:
                etSearch.setText("");
                break;
            case R.id.tv_filter:
                if (null != searchType){
                    if (searchType.equals(TYPE_WD)) {
                        ((ACT_Packing) getActivity()).switchToLeft();
                    }
                }
                break;
        }
    }
}
