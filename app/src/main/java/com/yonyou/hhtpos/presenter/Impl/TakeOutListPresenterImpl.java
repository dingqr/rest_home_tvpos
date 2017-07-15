package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.interactor.ITakeOutListInteractor;
import com.yonyou.hhtpos.interactor.Impl.TakeOutListInteractorImpl;
import com.yonyou.hhtpos.presenter.ITakeOutListPresenter;
import com.yonyou.hhtpos.view.ITakeOutListView;

/**
 * 作者：liushuofei on 2017/7/14 18:35
 * 邮箱：lsf@yonyou.com
 */
public class TakeOutListPresenterImpl implements ITakeOutListPresenter {

    private Context mContext;
    private ITakeOutListView mTakeOutListView;
    private ITakeOutListInteractor mTakeOutListInteractor;

    public TakeOutListPresenterImpl(Context mContext, ITakeOutListView mTakeOutListView) {
        this.mContext = mContext;
        this.mTakeOutListView = mTakeOutListView;
        mTakeOutListInteractor = new TakeOutListInteractorImpl(new TakeOutListListener());
    }

    @Override
    public void requestTakeOutList(String companyId, String salesMode, String shopId) {
        mTakeOutListView.showLoading(mContext.getString(R.string.network_loading));
        mTakeOutListInteractor.requestTakeOutList(companyId, salesMode, shopId);
    }

    private class TakeOutListListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String bean) {
            mTakeOutListView.hideLoading();
            mTakeOutListView.requestTakeOutList();
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
