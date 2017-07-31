package com.yonyou.hhtpos.interactor;

import com.yonyou.hhtpos.bean.check.RequestPayEntity;

/**
 * Created by zj on 2017/7/28.
 * 邮箱：zjuan@yonyou.com
 * 描述：获取结账信息页数据
 */
public interface IQueryBillInfoInteractor {
    void queryBillInfo(String compId, String shopId,String tableBillId, boolean isPay, RequestPayEntity requestPayEntity);
}
