package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.interactor.ITSCancelSplitInteractor;
import com.yonyou.hhtpos.interactor.ITSSplitTableInteractor;
import com.yonyou.hhtpos.interactor.Impl.TSCancelSplitInteractorImpl;
import com.yonyou.hhtpos.interactor.Impl.TSSplitTableInteractorImpl;
import com.yonyou.hhtpos.presenter.ITSCancelSplitPresenter;
import com.yonyou.hhtpos.presenter.ITSSplitTablePresenter;
import com.yonyou.hhtpos.view.ITSCancelSplitOrderView;
import com.yonyou.hhtpos.view.ITSSplitTableView;


/**
 * Created by ybing on 2017/7/28.
 * 取消拼台接口
 */

public class TSCancelSplitPresenterImpl implements ITSCancelSplitPresenter{

    private Context mContext;
    private ITSCancelSplitOrderView mView;
    private ITSCancelSplitInteractor mInteractor;

    public TSCancelSplitPresenterImpl(Context mContext, final ITSCancelSplitOrderView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new TSCancelSplitInteractorImpl(new SplitTableListener());
    }



    @Override
    public void cancelSplit( String tableId) {
        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.cancelSplit(tableId);
    }

    private class SplitTableListener implements BaseLoadedListener<String>{

        @Override
        public void onSuccess(int event_tag, String data) {
            mView.dismissDialogLoading();
            mView.cancelSplitTable();
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
