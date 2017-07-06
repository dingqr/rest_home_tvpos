package com.yonyou.hhtpos.interactor;

/**
 * Created by ybing on 2017/7/5.
 */

public interface ILoginInteractor {
    void login(String companyId, String deviceType,String mobileNo,String password,String shopId);
}
