package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wd.OrderDetailEntity;
import com.yonyou.hhtpos.interactor.IWDOrderDetailInteractor;
import com.yonyou.hhtpos.interactor.Impl.WDOrderDetailInteractorImpl;
import com.yonyou.hhtpos.presenter.IWDOrderDetailPresenter;
import com.yonyou.hhtpos.view.IOrderDetailView;


/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单详情
 */
public class WDOrderDetailPresenterImpl implements IWDOrderDetailPresenter {

    private Context mContext;
    private IOrderDetailView mOrderDetailView;
    private IWDOrderDetailInteractor mOrderDetailInteractor;

    public WDOrderDetailPresenterImpl(Context mContext, IOrderDetailView orderDetailView) {
        this.mContext = mContext;
        this.mOrderDetailView = orderDetailView;
        mOrderDetailInteractor = new WDOrderDetailInteractorImpl(new OrderDetailListener());
    }


    @Override
    public void requestOrderDetail(String tableBillId) {
        mOrderDetailView.showLoading(mContext.getString(R.string.network_loading));
        mOrderDetailInteractor.requestOrderDetail(tableBillId);
    }

    private class OrderDetailListener implements BaseLoadedListener<OrderDetailEntity> {


        @Override
        public void onSuccess(int event_tag, OrderDetailEntity data) {
            mOrderDetailView.hideLoading();
            mOrderDetailView.requestOrderDetail(data);
        }

        @Override
        public void onError(String msg) {
            mOrderDetailView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mOrderDetailView.hideLoading();
            mOrderDetailView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mOrderDetailView.hideLoading();
            mOrderDetailView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
