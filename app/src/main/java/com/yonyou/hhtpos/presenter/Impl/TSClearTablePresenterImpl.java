package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.interactor.ITSClearTableInteractor;
import com.yonyou.hhtpos.interactor.ITSSplitTableInteractor;
import com.yonyou.hhtpos.interactor.Impl.TSClearTableInteractorImpl;
import com.yonyou.hhtpos.interactor.Impl.TSSplitTableInteractorImpl;
import com.yonyou.hhtpos.presenter.ITSClearTablePresenter;
import com.yonyou.hhtpos.presenter.ITSSplitTablePresenter;
import com.yonyou.hhtpos.view.ITSClearTableView;
import com.yonyou.hhtpos.view.ITSSplitTableView;


/**
 * Created by ybing on 2017/7/25.
 * 清台接口
 */

public class TSClearTablePresenterImpl implements ITSClearTablePresenter {

    private Context mContext;
    private ITSClearTableView mView;
    private ITSClearTableInteractor mInteractor;

    public TSClearTablePresenterImpl(Context mContext, final ITSClearTableView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new TSClearTableInteractorImpl(new ClearTableListener());
    }



    @Override
    public void clearTable( String tableId) {
        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.clearTable(tableId);
    }

    private class ClearTableListener implements BaseLoadedListener<String>{

        @Override
        public void onSuccess(int event_tag, String data) {
            mView.dismissDialogLoading();
            mView.clearTable(data);
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
