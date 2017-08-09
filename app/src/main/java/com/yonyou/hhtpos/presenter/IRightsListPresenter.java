package com.yonyou.hhtpos.presenter;

/**
 * Created by zj on 2017/8/9.
 * 邮箱：zjuan@yonyou.com
 * 描述：根据当前登录用户，获取权限列表
 */
public interface IRightsListPresenter {
    void requestRightsList(String functionCode);
}
