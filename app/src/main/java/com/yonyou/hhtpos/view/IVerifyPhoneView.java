package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;

/**
 * 作者：liushuofei on 2017/7/5 16:29
 * 邮箱：lsf@yonyou.com
 */
public interface IVerifyPhoneView extends BaseView{

    void sendSmsCode();

    void verifyPhone();
}
