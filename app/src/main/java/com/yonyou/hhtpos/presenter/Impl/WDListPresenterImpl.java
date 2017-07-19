package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wd.OrderListEntity;
import com.yonyou.hhtpos.interactor.IWDListInteractor;
import com.yonyou.hhtpos.interactor.Impl.WDListInteractorImpl;
import com.yonyou.hhtpos.presenter.IWDListPresenter;
import com.yonyou.hhtpos.view.IWDListView;

import java.util.List;

/**
 * 作者：liushuofei on 2017/7/17 16:36
 * 邮箱：lsf@yonyou.com
 */
public class WDListPresenterImpl implements IWDListPresenter {

    private Context mContext;
    private IWDListView mPackingListView;
    private IWDListInteractor mPackingListInteractor;
    private boolean isRefresh;

    public WDListPresenterImpl(Context mContext, IWDListView mPackingListView) {
        this.mContext = mContext;
        this.mPackingListView = mPackingListView;
        mPackingListInteractor = new WDListInteractorImpl(new PackingListListener());
    }

    @Override
    public void requestPackingList(String billNo, String salesMode, String shopId, String pageNum, String pageSize, String payStatus, boolean isRefresh, boolean isEmpty) {
        this.isRefresh = isRefresh;
        if (isEmpty){
            mPackingListView.showLoading(mContext.getString(R.string.network_loading));
        }
        mPackingListInteractor.requestPackingList(billNo, salesMode, shopId, pageNum, pageSize, payStatus);
    }

    private class PackingListListener implements BaseLoadedListener<List<OrderListEntity>> {

        @Override
        public void onSuccess(int event_tag, List<OrderListEntity> dataList) {
            mPackingListView.hideLoading();
            mPackingListView.requestPackingList(dataList, isRefresh);
        }

        @Override
        public void onError(String msg) {
            mPackingListView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mPackingListView.hideLoading();
            mPackingListView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mPackingListView.hideLoading();
            mPackingListView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

}
