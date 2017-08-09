package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.interactor.IPayResultInteractor;
import com.yonyou.hhtpos.interactor.Impl.PayResultInteractorImpl;
import com.yonyou.hhtpos.presenter.IPayResultPresenter;
import com.yonyou.hhtpos.view.IPayResultView;

/**
 * 作者：liushuofei on 2017/8/9 14:18
 * 邮箱：lsf@yonyou.com
 */
public class PayResultPresenterImpl implements IPayResultPresenter{

    private Context mContext;
    private IPayResultView mView;
    private IPayResultInteractor mInteractor;

    public PayResultPresenterImpl(Context mContext, final IPayResultView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new PayResultInteractorImpl(new PayResultListener());
    }

    @Override
    public void requestPayResult(String shopId, String tableBillId) {
//        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.requestPayResult(shopId, tableBillId);
    }

    private class PayResultListener implements BaseLoadedListener<Boolean> {

        @Override
        public void onSuccess(int event_tag, Boolean isPaid) {
//            mView.dismissDialogLoading();
            mView.requestPayResult(isPaid);
        }

        @Override
        public void onError(String msg) {
//            mView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
//            mView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
//            mView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
