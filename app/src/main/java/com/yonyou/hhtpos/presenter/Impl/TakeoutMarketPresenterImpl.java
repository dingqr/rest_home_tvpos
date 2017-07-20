package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.TakeoutMarketEntity;
import com.yonyou.hhtpos.interactor.ITakeoutMarketInteractor;
import com.yonyou.hhtpos.interactor.Impl.TakeoutMarketInteractorImpl;
import com.yonyou.hhtpos.presenter.ITakeoutMarketPresenter;
import com.yonyou.hhtpos.view.ITakeoutMarketView;

import java.util.List;

/**
 * Created by ybing on 2017/7/17.
 * 查询所有市别接口
 */

public class TakeoutMarketPresenterImpl implements ITakeoutMarketPresenter{

    private Context mContext;
    private ITakeoutMarketView mView;
    private ITakeoutMarketInteractor mInteractor;

    public TakeoutMarketPresenterImpl(Context mContext, final ITakeoutMarketView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new TakeoutMarketInteractorImpl(new TakeoutMarketListener());
    }

    @Override
    public void getAllSchedule(String shopId) {
//        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.getAllTakeOutSchedule(shopId);
    }
    private class TakeoutMarketListener implements BaseLoadedListener<List<TakeoutMarketEntity>> {

        @Override
        public void onSuccess(int event_tag, List<TakeoutMarketEntity> data) {
            mView.dismissDialogLoading();
            mView.getAllTakeOutMarket(data);
        }

        @Override
        public void onError(String msg) {
            mView.dismissDialogLoading();
            mView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mView.hideLoading();
            mView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mView.hideLoading();
            mView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
