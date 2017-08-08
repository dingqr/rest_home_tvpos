package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.RecommendDataEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IRecommendDishInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zj on 2017/8/7.
 * 邮箱：zjuan@yonyou.com
 * 描述：查询所有推荐套餐
 */
public class RecommendDishInteractorImpl implements IRecommendDishInteractor {
    private BaseLoadedListener<List<RecommendDataEntity>> mDishListener;

    public RecommendDishInteractorImpl(BaseLoadedListener<List<RecommendDataEntity>> dishListListener) {
        this.mDishListener = dishListListener;
    }


    @Override
    public void getRecommendDishes(String shopId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("shopId", shopId);
        RequestManager.getInstance().requestGetByAsyn(API.URL_RECOMMEND_DISH, params, new ReqCallBack<List<RecommendDataEntity>>() {
            @Override
            public void onReqSuccess(List<RecommendDataEntity> dataList) {
                mDishListener.onSuccess(1, dataList);
            }

            @Override
            public void onFailure(String result) {
                /**联网失败的回调*/
                mDishListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                mDishListener.onBusinessError(error);
            }
        });
    }
}
