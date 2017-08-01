package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.check.RequestPayEntity;
import com.yonyou.hhtpos.bean.check.SettleAccountDataEntity;
import com.yonyou.hhtpos.interactor.ISettleAccountInteractor;
import com.yonyou.hhtpos.interactor.Impl.SettleAccountInteractorImpl;
import com.yonyou.hhtpos.presenter.ISettleAccountPresenter;
import com.yonyou.hhtpos.view.ISettleAccountView;

/**
 * Created by zj on 2017/8/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：二次结账
 */
public class SettleAccountPresenterImpl implements ISettleAccountPresenter {
    private Context mContext;
    private ISettleAccountView mSettleAccountView;
    private ISettleAccountInteractor mSettleAccountInteractor;

    public SettleAccountPresenterImpl(Context context, ISettleAccountView settleAccountView) {
        this.mContext = context;
        this.mSettleAccountView = settleAccountView;
        mSettleAccountInteractor = new SettleAccountInteractorImpl(new SettleAccountListener());
    }

    /**
     * 二次结账
     * @param companyId
     * @param memberId
     * @param shopId
     * @param tableBillId
     * @param requestPayEntity
     */
    @Override
    public void settleAccount(String companyId, String memberId, String shopId, String tableBillId, RequestPayEntity requestPayEntity) {
        mSettleAccountView.showLoading(mContext.getResources().getString(R.string.common_loading_message));
        mSettleAccountInteractor.settleAccount(companyId, memberId, shopId, tableBillId, requestPayEntity);
    }

    private class SettleAccountListener implements BaseLoadedListener<SettleAccountDataEntity> {

        @Override
        public void onSuccess(int event_tag, SettleAccountDataEntity settleAccountDataEntity) {
            mSettleAccountView.hideLoading();
            mSettleAccountView.settleAccount(settleAccountDataEntity);
        }

        @Override
        public void onError(String msg) {
            mSettleAccountView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mSettleAccountView.hideLoading();
            mSettleAccountView.showException(msg);
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mSettleAccountView.showBusinessError(error);
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
