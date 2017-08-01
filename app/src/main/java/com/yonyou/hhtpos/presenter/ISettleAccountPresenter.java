package com.yonyou.hhtpos.presenter;

import com.yonyou.hhtpos.bean.check.RequestPayEntity;

/**
 * Created by zj on 2017/8/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：二次结账信息页数据
 */
public interface ISettleAccountPresenter {
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
