package com.yonyou.hhtpos.interactor;

/**
 * 作者：liushuofei on 2017/7/5 16:40
 * 邮箱：lsf@yonyou.com
 */
public interface IVerifyPhoneInteractor {

    /**
     * 获取验证码
     * @param companyId 商户id
     * @param shopId 门店id
     * @param mobileNo 手机号
     */
    void sendSmsCode(String companyId, String shopId, String mobileNo);

    /**
     * 验证手机号
     * @param companyId 商户id
     * @param shopId 门店id
     * @param mobileNo 手机号
     * @param msgCode 验证码
     */
    void verifyPhone(String companyId, String shopId, String mobileNo, String msgCode);
}
