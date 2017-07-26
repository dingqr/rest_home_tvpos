package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.interactor.IDishEditInteractor;
import com.yonyou.hhtpos.interactor.Impl.DishEditInteractorImpl;
import com.yonyou.hhtpos.presenter.IDishEditPresenter;
import com.yonyou.hhtpos.view.IDishEditView;

/**
 * 作者：liushuofei on 2017/7/22 11:58
 * 邮箱：lsf@yonyou.com
 */
public class DishEditPresenterImpl implements IDishEditPresenter {

    private Context mContext;
    private IDishEditView mDishEditView;
    private IDishEditInteractor mDishEditInteractor;

    public DishEditPresenterImpl(Context mContext, IDishEditView mDishEditView) {
        this.mContext = mContext;
        this.mDishEditView = mDishEditView;
        mDishEditInteractor = new DishEditInteractorImpl(new UpdateQuantityListener(), new DeleteDishListener(), new UpdateDishStatusListener(), new SpecialHandleDishListener(), new ConfirmWeightListener());
    }

    @Override
    public void updateQuantity(String companyId, String id, String quantity, String shopId) {
        mDishEditInteractor.updateQuantity(companyId, id, quantity, shopId);
    }

    @Override
    public void deleteDish(String companyId, String id, String shopId) {
        mDishEditInteractor.deleteDish(companyId, id, shopId);
    }

    @Override
    public void specialHandleDish(String dishAbnormalStatus, String id, String shopId, String count) {
        mDishEditInteractor.specialHandleDish(dishAbnormalStatus, id, shopId, count);
    }

    @Override
    public void confirmWeightDish(String id, String quantity, String shopId) {
        mDishEditInteractor.confirmWeightDish(id, quantity, shopId);
    }

    @Override
    public void updateDishStatus(String companyId, String dishStatus, String id, String shopId) {
        mDishEditInteractor.updateDishStatus(companyId, dishStatus, id, shopId);
    }

    private class UpdateQuantityListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String result) {
            mDishEditView.hideLoading();
            mDishEditView.updateQuantitySuccess();
        }

        @Override
        public void onError(String msg) {
            mDishEditView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mDishEditView.hideLoading();
            mDishEditView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mDishEditView.hideLoading();
            mDishEditView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

    private class DeleteDishListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String result) {
            mDishEditView.hideLoading();
            mDishEditView.deleteDishSuccess();
        }

        @Override
        public void onError(String msg) {
            mDishEditView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mDishEditView.hideLoading();
            mDishEditView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mDishEditView.hideLoading();
            mDishEditView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

    private class UpdateDishStatusListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String result) {
            mDishEditView.hideLoading();
            mDishEditView.updateDishStatusSuccess();
        }

        @Override
        public void onError(String msg) {
            mDishEditView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mDishEditView.hideLoading();
            mDishEditView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mDishEditView.hideLoading();
            mDishEditView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

    private class SpecialHandleDishListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String result) {
            mDishEditView.hideLoading();
            mDishEditView.handleDishSuccess();
        }

        @Override
        public void onError(String msg) {
            mDishEditView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mDishEditView.hideLoading();
            mDishEditView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mDishEditView.hideLoading();
            mDishEditView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }

    private class ConfirmWeightListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String result) {
            mDishEditView.hideLoading();
            mDishEditView.confirmWeightSuccess();
        }

        @Override
        public void onError(String msg) {
            mDishEditView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mDishEditView.hideLoading();
            mDishEditView.showException(msg);
            //CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mDishEditView.hideLoading();
            mDishEditView.showBusinessError(error);
            //CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
