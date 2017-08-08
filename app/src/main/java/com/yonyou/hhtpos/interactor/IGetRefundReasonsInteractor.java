package com.yonyou.hhtpos.interactor;

/**
 * Created by ybing on 2017/8/8.
 * 获取所有外卖退款原因
 */

public interface IGetRefundReasonsInteractor {
    void getRefundReasons(String extendsTypeId,String pageNum,String pageSize);
}
