package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wd.WDOrderDetailEntity;
import com.yonyou.hhtpos.bean.wm.WMOrderDetailEntity;
import com.yonyou.hhtpos.interactor.IOrderDetailInteractor;
import com.yonyou.hhtpos.interactor.Impl.OrderDetailInteractorImpl;
import com.yonyou.hhtpos.presenter.IOrderDetailPresenter;
import com.yonyou.hhtpos.view.IWDOrderDetailView;
import com.yonyou.hhtpos.view.IWMOrderDetailView;


/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带和外卖的订单详情
 */
public class OrderDetailPresenterImpl implements IOrderDetailPresenter {

    private Context mContext;
    private IWDOrderDetailView wdOrderDetailView;
    private IWMOrderDetailView wmOrderDetailView;
    private IOrderDetailInteractor mdOrderDetailInteractor;
    private IOrderDetailInteractor wmOrderDetailInteractor;

    public OrderDetailPresenterImpl(Context mContext, IWDOrderDetailView orderDetailView) {
        this.mContext = mContext;
        this.wdOrderDetailView = orderDetailView;
        mdOrderDetailInteractor = new OrderDetailInteractorImpl(new WDOrderDetailListener());
    }

    public OrderDetailPresenterImpl(Context mContext, IWMOrderDetailView orderDetailView) {
        this.mContext = mContext;
        this.wmOrderDetailView = orderDetailView;
        wmOrderDetailInteractor = new OrderDetailInteractorImpl(new WMOrderDetailListener(), true);
    }


    /**
     * 获取外带订单详情
     *
     * @param tableBillId
     */
    @Override
    public void requestWDOrderDetail(String tableBillId) {
        wdOrderDetailView.showLoading(mContext.getString(R.string.network_loading));
        mdOrderDetailInteractor.requestWDOrderDetail(tableBillId);
    }

    /**
     * 获取外卖订单详情
     *
     * @param tableBillId
     */
    @Override
    public void requestWMOrderDetail(String tableBillId) {
        wmOrderDetailView.showLoading(mContext.getString(R.string.network_loading));
        wmOrderDetailInteractor.requestWMOrderDetail(tableBillId);
    }

    /**
     * 外带
     */
    private class WDOrderDetailListener implements BaseLoadedListener<WDOrderDetailEntity> {


        @Override
        public void onSuccess(int event_tag, WDOrderDetailEntity data) {
            wdOrderDetailView.hideLoading();
            wdOrderDetailView.requestWDOrderDetail(data);
        }

        @Override
        public void onError(String msg) {
            wdOrderDetailView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            wdOrderDetailView.hideLoading();
            wdOrderDetailView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            wdOrderDetailView.hideLoading();
//            wmOrderDetailView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

    /**
     * 外卖
     */
    private class WMOrderDetailListener implements BaseLoadedListener<WMOrderDetailEntity> {


        @Override
        public void onSuccess(int event_tag, WMOrderDetailEntity data) {
            wmOrderDetailView.hideLoading();
            wmOrderDetailView.requestWMOrderDetail(data);
        }

        @Override
        public void onError(String msg) {
            wmOrderDetailView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            wmOrderDetailView.hideLoading();
            wmOrderDetailView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            wmOrderDetailView.hideLoading();
            wmOrderDetailView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

}
