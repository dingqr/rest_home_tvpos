package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.check.PayTypeEntity;
import com.yonyou.hhtpos.interactor.IPayTypeListInteractor;
import com.yonyou.hhtpos.interactor.Impl.PayTypeListInteractorImpl;
import com.yonyou.hhtpos.presenter.IPayTypePresenter;
import com.yonyou.hhtpos.view.IPayTypeView;

import java.util.List;

/**
 * 作者：liushuofei on 2017/8/8 14:55
 * 邮箱：lsf@yonyou.com
 */
public class PayTypeListPresenterImpl implements IPayTypePresenter {

    private Context mContext;
    private IPayTypeView mPayTypeView;
    private IPayTypeListInteractor mPayTypeListInteractor;

    public PayTypeListPresenterImpl(Context context, IPayTypeView mPayTypeView) {
        this.mContext = context;
        this.mPayTypeView = mPayTypeView;
        mPayTypeListInteractor = new PayTypeListInteractorImpl(new PayTypeListener());
    }
    
    
    @Override
    public void requestPayTypeList(String shopId) {
        mPayTypeListInteractor.requestPayTypeList(shopId);
    }

    private class PayTypeListener implements BaseLoadedListener<List<PayTypeEntity>> {

        @Override
        public void onSuccess(int event_tag, List<PayTypeEntity> dataList) {
            mPayTypeView.hideLoading();
            mPayTypeView.requestPayTypeList(dataList);
        }

        @Override
        public void onError(String msg) {
            mPayTypeView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mPayTypeView.hideLoading();
            mPayTypeView.showException(msg);
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mPayTypeView.showBusinessError(error);
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
