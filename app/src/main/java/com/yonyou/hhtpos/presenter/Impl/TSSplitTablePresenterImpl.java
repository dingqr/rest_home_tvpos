package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.interactor.ITSSplitTableInteractor;
import com.yonyou.hhtpos.interactor.Impl.TSSplitTableInteractorImpl;
import com.yonyou.hhtpos.presenter.ITSSplitTablePresenter;
import com.yonyou.hhtpos.view.ITSSplitTableView;


/**
 * Created by ybing on 2017/7/25.
 *
 */

public class TSSplitTablePresenterImpl implements ITSSplitTablePresenter{

    private Context mContext;
    private ITSSplitTableView mView;
    private ITSSplitTableInteractor mInteractor;

    public TSSplitTablePresenterImpl(Context mContext, final ITSSplitTableView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new TSSplitTableInteractorImpl(new SplitTableListener());
    }



    @Override
    public void splitTable(String shopId, String tableId) {
        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.splitTable(shopId,tableId);
    }

    private class SplitTableListener implements BaseLoadedListener<CanteenTableEntity>{

        @Override
        public void onSuccess(int event_tag, CanteenTableEntity data) {
            mView.dismissDialogLoading();
            mView.splitOrder(data);
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
