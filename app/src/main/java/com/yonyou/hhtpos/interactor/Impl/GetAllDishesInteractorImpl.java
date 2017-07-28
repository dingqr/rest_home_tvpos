package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.dish.DishDataEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IGetAllDishesInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：查询所有菜品/菜类
 */
public class GetAllDishesInteractorImpl implements IGetAllDishesInteractor {
    private BaseLoadedListener<DishDataEntity> mDishListener;

    public GetAllDishesInteractorImpl(BaseLoadedListener<DishDataEntity> dishListListener) {
        this.mDishListener = dishListListener;
    }

    /**
     * 获取所有的菜品/菜类
     *
     * @param compId
     * @param shopId
     */
    @Override
    public void getAllDishes(String compId, String shopId,int saleManner) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("compId", compId);
        params.put("shopId", shopId);
        params.put("saleManner", StringUtil.getString(saleManner));
        RequestManager.getInstance().requestGetByAsyn(API.URL_GET_ALL_DISHES, params, new ReqCallBack<DishDataEntity>() {
            @Override
            public void onReqSuccess(DishDataEntity dishDataEntity) {
                mDishListener.onSuccess(1, dishDataEntity);
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
