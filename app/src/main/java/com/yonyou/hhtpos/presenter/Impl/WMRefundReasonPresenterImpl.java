package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wm.RefundReasonEntity;
import com.yonyou.hhtpos.interactor.IWMRefundReasonInteractor;
import com.yonyou.hhtpos.interactor.Impl.WMRefunReasonInteractorImpl;
import com.yonyou.hhtpos.presenter.IWMRefundReasonPresenter;
import com.yonyou.hhtpos.view.IWMRefundReasonView;

import java.util.List;

/**
 * Created by ybing on 2017/7/22.
 * 获取外卖退款原因筛选框中的退款原因选项
 */

public class WMRefundReasonPresenterImpl implements IWMRefundReasonPresenter{

    private Context mContext;
    private IWMRefundReasonView mView;
    private IWMRefundReasonInteractor mInteractor;

    public WMRefundReasonPresenterImpl(Context mContext, final IWMRefundReasonView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new WMRefunReasonInteractorImpl(new WMRefundReasonListener());
    }

    @Override
    public void getWMRefundReason(String extendsTypeId, String pageNum, String pageSize) {
        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.getWMRefundReason(extendsTypeId,pageNum,pageSize);
    }

    private class WMRefundReasonListener implements BaseLoadedListener<List<RefundReasonEntity>>{

        @Override
        public void onSuccess(int event_tag, List<RefundReasonEntity> data) {
            mView.dismissDialogLoading();
            mView.getWMRefundReason(data);
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
