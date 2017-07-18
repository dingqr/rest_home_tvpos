package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.PackingListBean;
import com.yonyou.hhtpos.interactor.IPackingListInteractor;
import com.yonyou.hhtpos.interactor.Impl.PackingListInteractorImpl;
import com.yonyou.hhtpos.presenter.IPackingListPresenter;
import com.yonyou.hhtpos.view.IPackingListView;

import java.util.List;

/**
 * 作者：liushuofei on 2017/7/17 16:36
 * 邮箱：lsf@yonyou.com
 */
public class PackingListPresenterImpl implements IPackingListPresenter {

    private Context mContext;
    private IPackingListView mPackingListView;
    private IPackingListInteractor mPackingListInteractor;
    private boolean isRefresh;

    public PackingListPresenterImpl(Context mContext, IPackingListView mPackingListView) {
        this.mContext = mContext;
        this.mPackingListView = mPackingListView;
        mPackingListInteractor = new PackingListInteractorImpl(new PackingListListener());
    }

    @Override
    public void requestPackingList(String billNo, String salesMode, String shopId, String pageNum, String pageSize, boolean isRefresh, boolean isEmpty) {
        this.isRefresh = isRefresh;
        if (isEmpty){
            mPackingListView.showLoading(mContext.getString(R.string.network_loading));
        }
        mPackingListInteractor.requestPackingList(billNo, salesMode, shopId, pageNum, pageSize);
    }

    private class PackingListListener implements BaseLoadedListener<List<PackingListBean>> {

        @Override
        public void onSuccess(int event_tag, List<PackingListBean> dataList) {
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
