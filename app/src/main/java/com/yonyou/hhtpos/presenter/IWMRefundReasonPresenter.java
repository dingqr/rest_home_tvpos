package com.yonyou.hhtpos.presenter;


/**
 * Created by ybing on 2016/7/22.
 * 获取外卖退款原因筛选框中的退款原因选项
 */

public interface IWMRefundReasonPresenter {
    void getWMRefundReason(String extendsTypeId,String pageNum,String pageSize);
}
