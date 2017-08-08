package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.dish.DishListEntity;
import com.yonyou.hhtpos.interactor.IDishListInteractor;
import com.yonyou.hhtpos.interactor.Impl.DishListInteractorImpl;
import com.yonyou.hhtpos.presenter.IDishListPresenter;
import com.yonyou.hhtpos.view.IDishListView;

/**
 * 作者：liushuofei on 2017/7/17 19:29
 * 邮箱：lsf@yonyou.com
 */
public class DishListPresenterImpl implements IDishListPresenter {

    private Context mContext;
    private IDishListView mDishListView;
    private IDishListInteractor mDishListInteractor;

    public DishListPresenterImpl(Context mContext, IDishListView mDishListView) {
        this.mContext = mContext;
        this.mDishListView = mDishListView;
        mDishListInteractor = new DishListInteractorImpl(new DishListListener(), new PlaceOrderListener(), new DeleteDishListener());
    }

    @Override
    public void requestDishList(String billId, boolean showLoading) {
        if (showLoading){
            mDishListView.showLoading(mContext.getString(R.string.network_loading));
        }

        mDishListInteractor.requestDishList(billId);
    }

    @Override
    public void requestPlaceOrder(String dishIds,String tableBillId, String saleManner) {
        mDishListView.showDialogLoading(mContext.getString(R.string.network_loading));
        mDishListInteractor.requestPlaceOrder(dishIds,tableBillId,saleManner);
    }

    @Override
    public void deleteNoOrderDish(String shopId, String tableBillId) {
        mDishListInteractor.deleteNoOrderDish(shopId, tableBillId);
    }

    private class DishListListener implements BaseLoadedListener<DishListEntity> {

        @Override
        public void onSuccess(int event_tag, DishListEntity bean) {
            mDishListView.hideLoading();
            mDishListView.requestDishList(bean);
        }

        @Override
        public void onError(String msg) {
            mDishListView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mDishListView.hideLoading();
            mDishListView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mDishListView.hideLoading();
            mDishListView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

    private class PlaceOrderListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String result) {
            mDishListView.dismissDialogLoading();
            mDishListView.requestPlaceOrder();
        }

        @Override
        public void onError(String msg) {
            mDishListView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mDishListView.dismissDialogLoading();
            mDishListView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mDishListView.dismissDialogLoading();
            mDishListView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

    private class DeleteDishListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String result) {
            mDishListView.dismissDialogLoading();
            mDishListView.deleteNoOrderDishes();
        }

        @Override
        public void onError(String msg) {
            mDishListView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mDishListView.dismissDialogLoading();
            mDishListView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mDishListView.dismissDialogLoading();
            mDishListView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
