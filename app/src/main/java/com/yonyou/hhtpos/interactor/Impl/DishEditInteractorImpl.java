package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IDishEditInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：liushuofei on 2017/7/22 11:59
 * 邮箱：lsf@yonyou.com
 */
public class DishEditInteractorImpl implements IDishEditInteractor {

    private BaseLoadedListener updateQuantityListener;
    private BaseLoadedListener deleteDishListener;
    private BaseLoadedListener updateDishStatusListener;
    private BaseLoadedListener handleDishListener;

    public DishEditInteractorImpl(BaseLoadedListener updateQuantityListener, BaseLoadedListener deleteDishListener, BaseLoadedListener updateDishStatusListener, BaseLoadedListener handleDishListener) {
        this.updateQuantityListener = updateQuantityListener;
        this.deleteDishListener = deleteDishListener;
        this.updateDishStatusListener = updateDishStatusListener;
        this.handleDishListener = handleDishListener;
    }
    
    @Override
    public void updateQuantity(String companyId, String id, String quantity, String shopId) {
        HashMap<String,String> hashMap = new HashMap<>();
        //hashMap.put("companyId", companyId);
        hashMap.put("id", id);
        hashMap.put("quantity", quantity);
        hashMap.put("shopId", shopId);
        RequestManager.getInstance().requestPostByAsyn(API.URL_UPDATE_DISH_QUANTITY, hashMap, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String result) {
                updateQuantityListener.onSuccess(0, result);
            }

            @Override
            public void onFailure(String result) {
                updateQuantityListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                updateQuantityListener.onBusinessError(errorBean);
            }
        });
    }

    @Override
    public void deleteDish(String companyId, String id, String shopId) {
        HashMap<String,String> hashMap = new HashMap<>();
        //hashMap.put("companyId", companyId);
        hashMap.put("id", id);
        hashMap.put("shopId", shopId);
        RequestManager.getInstance().requestPostByAsyn(API.URL_DELETE_DISH, hashMap, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String result) {
                deleteDishListener.onSuccess(0, result);
            }

            @Override
            public void onFailure(String result) {
                deleteDishListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                deleteDishListener.onBusinessError(errorBean);
            }
        });
    }

    @Override
    public void updateDishStatus(String companyId, String dishStatus, String id, String shopId) {
        HashMap<String,String> hashMap = new HashMap<>();
        //hashMap.put("companyId", companyId);
        hashMap.put("id", id);
        hashMap.put("shopId", shopId);
        hashMap.put("dishStatus", dishStatus);
        RequestManager.getInstance().requestPostByAsyn(API.URL_UPDATE_DISH_STATUS, hashMap, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String result) {
                updateDishStatusListener.onSuccess(0, result);
            }

            @Override
            public void onFailure(String result) {
                updateDishStatusListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                updateDishStatusListener.onBusinessError(errorBean);
            }
        });
    }

    @Override
    public void specialHandleDish(String dishAbnormalStatus, String id, String shopId, String count) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("dishAbnormalStatus", dishAbnormalStatus);
        hashMap.put("id", id);
        hashMap.put("shopId", shopId);
        hashMap.put("count", count);
        RequestManager.getInstance().requestPostByAsyn(API.URL_SPECIAL_HANDLE_DISH, hashMap, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String result) {
                handleDishListener.onSuccess(0, result);
            }

            @Override
            public void onFailure(String result) {
                handleDishListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                handleDishListener.onBusinessError(errorBean);
            }
        });
    }
}
