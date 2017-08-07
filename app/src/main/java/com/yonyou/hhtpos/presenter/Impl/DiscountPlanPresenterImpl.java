package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.check.DiscountEntity;
import com.yonyou.hhtpos.interactor.IDiscountPlanInteractor;
import com.yonyou.hhtpos.interactor.Impl.DiscountPlanInteractorImpl;
import com.yonyou.hhtpos.presenter.IDiscountPlanPresenter;
import com.yonyou.hhtpos.view.IDiscountPlanView;

import java.util.List;

/**
 * Created by zj on 2017/8/7.
 * 邮箱：zjuan@yonyou.com
 * 描述：获取所有折扣方案
 */
public class DiscountPlanPresenterImpl implements IDiscountPlanPresenter {
    private Context mContext;
    private IDiscountPlanView mDiscountPlanView;
    private IDiscountPlanInteractor mDiscountPlanInteractor;

    public DiscountPlanPresenterImpl(Context context, IDiscountPlanView mView) {
        this.mContext = context;
        this.mDiscountPlanView = mView;
        mDiscountPlanInteractor = new DiscountPlanInteractorImpl(new DiscountPlanListener());
    }

    @Override
    public void getAllDiscountPlan(String shopId) {
        mDiscountPlanView.showLoading(mContext.getResources().getString(R.string.common_loading_message));
        mDiscountPlanInteractor.getAllDiscountPlan(shopId);
    }

    private class DiscountPlanListener implements BaseLoadedListener<List<DiscountEntity>> {

        @Override
        public void onSuccess(int event_tag, List<DiscountEntity> dataList) {
            mDiscountPlanView.hideLoading();
            mDiscountPlanView.getAllDiscountPlan(dataList);
        }

        @Override
        public void onError(String msg) {
            mDiscountPlanView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mDiscountPlanView.hideLoading();
            mDiscountPlanView.showException(msg);
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mDiscountPlanView.showBusinessError(error);
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
