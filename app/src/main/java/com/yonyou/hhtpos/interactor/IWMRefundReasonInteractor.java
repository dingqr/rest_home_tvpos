package com.yonyou.hhtpos.interactor;

/**
 * Created by ybing on 2017/7/22.
 * 获取外卖退款原因筛选框中的退款原因选项
 */

public interface IWMRefundReasonInteractor {
    void getWMRefundReason(String extendsTypeId,String pageNum,String pageSize);
}
