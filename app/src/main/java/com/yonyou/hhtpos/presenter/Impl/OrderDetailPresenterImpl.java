package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wd.WDOrderDetailEntity;
import com.yonyou.hhtpos.interactor.IOrderDetailInteractor;
import com.yonyou.hhtpos.interactor.Impl.OrderDetailInteractorImpl;
import com.yonyou.hhtpos.presenter.IOrderDetailPresenter;
import com.yonyou.hhtpos.view.IWDOrderDetailView;


/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带和外卖的订单详情
 */
public class OrderDetailPresenterImpl implements IOrderDetailPresenter {

    private Context mContext;
    private IWDOrderDetailView mOrderDetailView;
    private IOrderDetailInteractor mOrderDetailInteractor;

    public OrderDetailPresenterImpl(Context mContext, IWDOrderDetailView orderDetailView) {
        this.mContext = mContext;
        this.mOrderDetailView = orderDetailView;
        mOrderDetailInteractor = new OrderDetailInteractorImpl(new OrderDetailListener());
        mOrderDetailInteractor = new OrderDetailInteractorImpl(new OrderDetailListener());
    }

    /**
     * 获取外带订单详情
     * @param tableBillId
     */
    @Override
    public void requestWDOrderDetail(String tableBillId) {
        mOrderDetailView.showLoading(mContext.getString(R.string.network_loading));
        mOrderDetailInteractor.requestWDOrderDetail(tableBillId);
    }

    /**
     * 获取外卖订单详情
     * @param tableBillId
     */
    @Override
    public void requestWMOrderDetail(String tableBillId) {

    }

    private class OrderDetailListener implements BaseLoadedListener<WDOrderDetailEntity> {


        @Override
        public void onSuccess(int event_tag, WDOrderDetailEntity data) {
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
