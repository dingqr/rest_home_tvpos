package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.dish.RequestAddDishEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IAddDishInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * Created by zj on 2017/7/19.
 * 邮箱：zjuan@yonyou.com
 * 描述：新加菜品请求参数实体类-（套餐、临时菜、菜品）
 */
public class AddDishInteractorImpl implements IAddDishInteractor {
    private BaseLoadedListener<String> mAddDishListener;

    public AddDishInteractorImpl(BaseLoadedListener<String> listener) {
        this.mAddDishListener = listener;
    }

    /**
     * 新加菜品
     *
     * @param requestAddDishEntity
     */
    @Override
    public void requestAddDish(RequestAddDishEntity requestAddDishEntity) {
        //dishType  菜品：1，固定套餐：2，N选N套餐：3，临时菜：4
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("companyId", requestAddDishEntity.companyId);
        params.put("dishClassid", requestAddDishEntity.dishClassid);
        params.put("dishId", requestAddDishEntity.dishId);
        params.put("dishName", requestAddDishEntity.dishName);
        params.put("dishPrice", requestAddDishEntity.getDishPrice());
        params.put("dishRelateId", requestAddDishEntity.dishRelateId);
        params.put("dishStatus", requestAddDishEntity.dishStatus);
        params.put("dishType", requestAddDishEntity.dishType);
        params.put("listShowPractice", requestAddDishEntity.listShowRemark);
        params.put("listShowRemark", requestAddDishEntity.listShowRemark);
        params.put("spractices", requestAddDishEntity.practices);
        params.put("sremarks", requestAddDishEntity.remarks);
        params.put("quantity", requestAddDishEntity.quantity);
        params.put("remark", requestAddDishEntity.remark);
        params.put("shopId", requestAddDishEntity.shopId);
        params.put("standardId", requestAddDishEntity.standardId);
        params.put("tableBillId", requestAddDishEntity.tableBillId);
        params.put("unit", requestAddDishEntity.unit + "");
        RequestManager.getInstance().requestPostByAsyn(API.URL_ADD_DISH, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                mAddDishListener.onSuccess(1, result);
            }

            @Override
            public void onFailure(String result) {
                /**联网失败的回调*/
                mAddDishListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                mAddDishListener.onBusinessError(error);
            }
        });
    }
}
