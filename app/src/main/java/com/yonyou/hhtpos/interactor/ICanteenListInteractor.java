package com.yonyou.hhtpos.interactor;

/**
 * 作者：liushuofei on 2017/7/14 17:26
 * 邮箱：lsf@yonyou.com
 */
public interface ICanteenListInteractor {

    /**
     * 开单
     * @param billRemark 整单备注
     * @param companyId 公司id
     * @param memberId 	会员手机号
     * @param personNum 就餐人数
     * @param shopId 门店id
     * @param tableNo 桌台号
     * @param waiterId 	服务员id
     */
    void openOrder(String billRemark, String companyId, String memberId, String personNum, String shopId, String tableNo, String waiterId);
}
