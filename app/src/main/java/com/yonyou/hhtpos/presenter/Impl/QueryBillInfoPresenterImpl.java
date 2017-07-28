package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.check.SettleAccountDataEntity;
import com.yonyou.hhtpos.interactor.IQueryBillInfoInteractor;
import com.yonyou.hhtpos.interactor.Impl.QueryBillInfoInteractorImpl;
import com.yonyou.hhtpos.presenter.IQueryBillInfoPresenter;
import com.yonyou.hhtpos.view.IQueryBillInfoView;

/**
 * Created by zj on 2017/7/28.
 * 邮箱：zjuan@yonyou.com
 * 描述：获取结账信息页数据
 */
public class QueryBillInfoPresenterImpl implements IQueryBillInfoPresenter {
    private Context mContext;
    private IQueryBillInfoView mBillInfoView;
    private IQueryBillInfoInteractor mBillInfoInteractor;

    public QueryBillInfoPresenterImpl(Context context, IQueryBillInfoView billInfoView) {
        this.mContext = context;
        this.mBillInfoView = billInfoView;
        mBillInfoInteractor = new QueryBillInfoInteractorImpl(new BillInfoListener());
    }

    /**
     * 获取结账信息页数据
     * @param compId
     * @param shopId
     * @param tableBillId
     */
    @Override
    public void queryBillInfo(String compId, String shopId, String tableBillId) {
        mBillInfoView.showLoading(mContext.getResources().getString(R.string.common_loading_message));
        mBillInfoInteractor.queryBillInfo(compId, shopId,tableBillId);
    }

    private class BillInfoListener implements BaseLoadedListener<SettleAccountDataEntity> {

        @Override
        public void onSuccess(int event_tag, SettleAccountDataEntity settleAccountDataEntity) {
            mBillInfoView.hideLoading();
            mBillInfoView.queryBillInfo(settleAccountDataEntity);
        }

        @Override
        public void onError(String msg) {
            mBillInfoView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mBillInfoView.hideLoading();
            mBillInfoView.showException(msg);
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mBillInfoView.showBusinessError(error);
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
