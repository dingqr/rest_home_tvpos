package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.WaiterEntity;
import com.yonyou.hhtpos.interactor.IChooseWaiterInteractor;
import com.yonyou.hhtpos.interactor.Impl.ChooseWaiterInteractorImpl;
import com.yonyou.hhtpos.presenter.IChooseWaiterPresenter;
import com.yonyou.hhtpos.view.IChooseWaiterView;

import java.util.List;

/**
 * Created by zj on 2017/7/20.
 * 邮箱：zjuan@yonyou.com
 * 描述：新加菜品请求参数实体类-（（套餐、临时菜、菜品））
 */
public class ChooseWaiterPresenterImpl implements IChooseWaiterPresenter {
    private Context mContext;
    private IChooseWaiterView mChooseWaiterView;
    private IChooseWaiterInteractor mChooseWaiterInteractor;

    public ChooseWaiterPresenterImpl(Context context, IChooseWaiterView chooseWaiterView) {
        this.mContext = context;
        this.mChooseWaiterView = chooseWaiterView;
        mChooseWaiterInteractor = new ChooseWaiterInteractorImpl(new ChooseWaiterListener());
    }

    /**
     * 查询所有服务员
     * @param shopId
     */
    @Override
    public void requestWaiterList(String shopId) {
        mChooseWaiterView.showLoading(mContext.getResources().getString(R.string.common_loading_message));
        mChooseWaiterInteractor.requestWaiterList(shopId);
    }

    private class ChooseWaiterListener implements BaseLoadedListener<List<WaiterEntity>> {

        @Override
        public void onSuccess(int event_tag, List<WaiterEntity> data) {
            mChooseWaiterView.hideLoading();
            mChooseWaiterView.requestWaiterList(data);
        }

        @Override
        public void onError(String msg) {
            mChooseWaiterView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mChooseWaiterView.hideLoading();
            mChooseWaiterView.showException(msg);
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mChooseWaiterView.showBusinessError(error);
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
