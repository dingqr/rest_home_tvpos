package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.dish.DishDataEntity;
import com.yonyou.hhtpos.interactor.IGetAllDishesInteractor;
import com.yonyou.hhtpos.interactor.Impl.GetAllDishesInteractorImpl;
import com.yonyou.hhtpos.presenter.IGetAllDishesPresenter;
import com.yonyou.hhtpos.view.IGetAllDishesView;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：获取所有菜品/菜类-presenter实现类
 */
public class GetAllDishesPresenterImpl implements IGetAllDishesPresenter {
    private Context mContext;
    private IGetAllDishesView mDishesView;
    private IGetAllDishesInteractor mDishesInteractor;

    public GetAllDishesPresenterImpl(Context context, IGetAllDishesView dishesView) {
        this.mContext = context;
        this.mDishesView = dishesView;
        mDishesInteractor = new GetAllDishesInteractorImpl(new DishTypeListListener());
    }

    /**
     * 获取菜品/菜类
     *
     * @param compId
     * @param shopId
     */
    @Override
    public void getAllDishes(String compId, String shopId) {
        mDishesView.showLoading(mContext.getResources().getString(R.string.common_loading_message));
        mDishesInteractor.getAllDishes(compId, shopId);
    }

    private class DishTypeListListener implements BaseLoadedListener<DishDataEntity> {

        @Override
        public void onSuccess(int event_tag, DishDataEntity dishDataEntity) {
            mDishesView.hideLoading();
            mDishesView.getAllDishes(dishDataEntity);
        }

        @Override
        public void onError(String msg) {
            mDishesView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mDishesView.hideLoading();
            mDishesView.showException(msg);
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mDishesView.showBusinessError(error);
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
