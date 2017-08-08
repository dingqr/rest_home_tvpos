package com.yonyou.hhtpos.presenter;

/**
 * Created by ybing on 2017/8/8.
 * 外卖--获取所有退款原因列表
 */

public interface IGetRefundReasonsPresenter {
    void getRefundReasons(String extendsTypeId,String pageNum,String pageSize);
}
