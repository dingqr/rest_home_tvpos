package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wm.OrderListEntity;
import com.yonyou.hhtpos.interactor.IWMListInteractor;
import com.yonyou.hhtpos.interactor.Impl.WMListInteractorImpl;
import com.yonyou.hhtpos.presenter.IWMListPresenter;
import com.yonyou.hhtpos.view.IWMListView;

import java.util.List;

/**
 * 作者：liushuofei on 2017/7/14 18:35
 * 邮箱：lsf@yonyou.com
 */
public class WMListPresenterImpl implements IWMListPresenter {

    private Context mContext;
    private IWMListView mTakeOutListView;
    private IWMListInteractor mTakeOutListInteractor;
    private boolean isRefresh;

    public WMListPresenterImpl(Context mContext, IWMListView mTakeOutListView) {
        this.mContext = mContext;
        this.mTakeOutListView = mTakeOutListView;
        mTakeOutListInteractor = new WMListInteractorImpl(new TakeOutListListener());
    }

    @Override
    public void requestTakeOutList(String companyId, String salesMode, String shopId, String pageNum, String pageSize, String dinnerStatus, boolean isRefresh, boolean isEmpty) {
        this.isRefresh = isRefresh;
        if (isEmpty){
            mTakeOutListView.showLoading(mContext.getString(R.string.network_loading));
        }
        mTakeOutListInteractor.requestTakeOutList(companyId, salesMode, shopId, pageNum, pageSize, dinnerStatus);
    }


    private class TakeOutListListener implements BaseLoadedListener<List<OrderListEntity>> {

        @Override
        public void onSuccess(int event_tag, List<OrderListEntity> dataList) {
            mTakeOutListView.hideLoading();
            mTakeOutListView.requestTakeOutList(dataList, isRefresh);
        }

        @Override
        public void onError(String msg) {
            mTakeOutListView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mTakeOutListView.hideLoading();
            mTakeOutListView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mTakeOutListView.hideLoading();
            mTakeOutListView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
