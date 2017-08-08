package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wm.RefundReasonEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IGetRefundReasonsInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ybing on 2017/8/8.
 * 获取所有外卖退款原因
 */

public class GetRefundReasonsInteractorImpl implements IGetRefundReasonsInteractor {

    private BaseLoadedListener mBaseLoadedListener;

    public GetRefundReasonsInteractorImpl(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }

    @Override
    public void getRefundReasons(String extendsTypeId,String pageNum,String pageSize) {
        HashMap<String,String> hashMap = new HashMap<String,String>();

        hashMap.put("extendsTypeId",StringUtil.getString(extendsTypeId));
        hashMap.put("pageNum",StringUtil.getString(pageNum));
        hashMap.put("pageSize",StringUtil.getString(pageSize));
        RequestManager.getInstance().requestGetByAsyn(API.URL_GET_REFUND_REASONS, hashMap, new ReqCallBack<List<RefundReasonEntity>>() {
            @Override
            public void onReqSuccess(List<RefundReasonEntity> result) {
                mBaseLoadedListener.onSuccess(1,result);
            }

            @Override
            public void onFailure(String result) {
                mBaseLoadedListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                mBaseLoadedListener.onBusinessError(errorBean);
            }
        });
    }
}
