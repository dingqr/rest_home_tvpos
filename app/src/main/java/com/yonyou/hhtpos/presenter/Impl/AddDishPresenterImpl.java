package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.dish.RequestAddDishEntity;
import com.yonyou.hhtpos.interactor.IAddDishInteractor;
import com.yonyou.hhtpos.interactor.Impl.AddDishInteractorImpl;
import com.yonyou.hhtpos.presenter.IAddDishPresenter;
import com.yonyou.hhtpos.view.IAddDishView;

/**
 * Created by zj on 2017/7/19.
 * 邮箱：zjuan@yonyou.com
 * 描述：新加菜品请求参数实体类-（（套餐、临时菜、菜品））
 */
public class AddDishPresenterImpl implements IAddDishPresenter {
    private Context mContext;
    private IAddDishView mAddDishView;
    private IAddDishInteractor mAddDishInteractor;

    public AddDishPresenterImpl(Context context, IAddDishView addDishView) {
        this.mContext = context;
        this.mAddDishView = addDishView;
        mAddDishInteractor = new AddDishInteractorImpl(new AddDishListener());
    }

    /**
     * 添加菜品
     *
     * @param requestAddDishEntity
     */
    @Override
    public void requestAddDish(RequestAddDishEntity requestAddDishEntity) {
        mAddDishView.showLoading(mContext.getResources().getString(R.string.common_loading_message));
        mAddDishInteractor.requestAddDish(requestAddDishEntity);
    }

    private class AddDishListener implements BaseLoadedListener<String> {

        @Override
        public void onSuccess(int event_tag, String result) {
            mAddDishView.hideLoading();
            mAddDishView.requestAddDish(result);
        }

        @Override
        public void onError(String msg) {
            mAddDishView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mAddDishView.hideLoading();
            mAddDishView.showException(msg);
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mAddDishView.showBusinessError(error);
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
