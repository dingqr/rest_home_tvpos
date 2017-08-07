package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.RecommendDataEntity;
import com.yonyou.hhtpos.interactor.IRecommendDishInteractor;
import com.yonyou.hhtpos.interactor.Impl.RecommendDishInteractorImpl;
import com.yonyou.hhtpos.presenter.IRecommendDishPresenter;
import com.yonyou.hhtpos.view.IRecommendDishesView;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：查询所有推荐套餐
 */
public class RecommendDishesPresenterImpl implements IRecommendDishPresenter {
    private Context mContext;
    private IRecommendDishesView mDishesView;
    private IRecommendDishInteractor mRecommendDishInteractor;

    public RecommendDishesPresenterImpl(Context context, IRecommendDishesView dishesView) {
        this.mContext = context;
        this.mDishesView = dishesView;
        mRecommendDishInteractor = new RecommendDishInteractorImpl(new RecommendDishListener());
    }

    /**
     * 获取菜品/菜类
     *
     * @param compId
     * @param shopId
     * @param saleManner 销售模式：1：堂食 2：外带 3：外卖
     */
    @Override
    public void getRecommendDishes(String compId, String shopId, int saleManner) {
        mDishesView.showLoading(mContext.getResources().getString(R.string.common_loading_message));
        mRecommendDishInteractor.getRecommendDishes(compId, shopId, saleManner);
    }

    private class RecommendDishListener implements BaseLoadedListener<RecommendDataEntity> {

        @Override
        public void onSuccess(int event_tag, RecommendDataEntity recommendDishEntity) {
            mDishesView.hideLoading();
            mDishesView.getRecommendDishes(recommendDishEntity);
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
