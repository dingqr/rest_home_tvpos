package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.dish.DishListEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IDishListInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：liushuofei on 2017/7/17 19:30
 * 邮箱：lsf@yonyou.com
 */
public class DishListInteractorImpl implements IDishListInteractor {

    private BaseLoadedListener dishListListener;

    public DishListInteractorImpl(BaseLoadedListener dishListListener) {
        this.dishListListener = dishListListener;
    }

    @Override
    public void requestDishList(String billId) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("billId", billId);
        RequestManager.getInstance().requestGetByAsyn(API.URL_DISH_LIST, hashMap, new ReqCallBack<DishListEntity>() {

            @Override
            public void onReqSuccess(DishListEntity bean) {
                dishListListener.onSuccess(0, bean);
            }

            @Override
            public void onFailure(String result) {
                dishListListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                dishListListener.onBusinessError(errorBean);
            }
        });
    }
}
