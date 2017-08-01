package com.yonyou.hhtpos.interactor;

import com.yonyou.hhtpos.bean.check.RequestPayEntity;

/**
 * Created by zj on 2017/7/28.
 * 邮箱：zjuan@yonyou.com
 * 描述：二次结账接口
 */
public interface ISettleAccountInteractor {
    /**
     * 二次结账
     * @param companyId
     * @param memberId
     * @param shopId
     * @param tableBillId
     * @param requestPayEntity
     */
    void settleAccount(String companyId,String memberId, String shopId, String tableBillId,RequestPayEntity requestPayEntity);
}
