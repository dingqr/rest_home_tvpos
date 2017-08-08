package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.StoreEntity;
import com.yonyou.hhtpos.bean.wm.RefundReasonEntity;
import com.yonyou.hhtpos.interactor.IGetAllShopsInteractor;
import com.yonyou.hhtpos.interactor.IGetRefundReasonsInteractor;
import com.yonyou.hhtpos.interactor.Impl.GetAllShopsInteractorImpl;
import com.yonyou.hhtpos.interactor.Impl.GetRefundReasonsInteractorImpl;
import com.yonyou.hhtpos.presenter.IGetAllShopsPresenter;
import com.yonyou.hhtpos.presenter.IGetRefundReasonsPresenter;
import com.yonyou.hhtpos.view.IGetAllShopsView;
import com.yonyou.hhtpos.view.IGetRefundReasonsView;

import java.util.List;


/**
 * Created by ybing on 2017/8/8.
 * 获取所有外卖退款原因
 */


public class GetRefundReasonsPresenterImpl implements IGetRefundReasonsPresenter {

    private Context mContext;
    private IGetRefundReasonsView mView;
    private IGetRefundReasonsInteractor mInteractor;

    public GetRefundReasonsPresenterImpl(Context mContext, final IGetRefundReasonsView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new GetRefundReasonsInteractorImpl(new GetRefundReasonsListener());
    }


    @Override
    public void getRefundReasons(String extendsTypeId, String pageNum, String pageSize) {
        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.getRefundReasons(extendsTypeId,pageNum,pageSize);
    }

    private class GetRefundReasonsListener implements BaseLoadedListener<List<RefundReasonEntity>> {

        @Override
        public void onSuccess(int event_tag, List<RefundReasonEntity> data) {
            mView.dismissDialogLoading();
            mView.getRefundReasons(data);
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
