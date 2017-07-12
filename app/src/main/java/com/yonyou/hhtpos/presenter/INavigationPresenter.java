package com.yonyou.hhtpos.presenter;

/**
 * 作者：liushuofei on 2017/7/11 17:28
 * 邮箱：lsf@yonyou.com
 */
public interface INavigationPresenter {

    /**
     * 请求导航栏数据
     * @param functionCode POS端后台根编码
     */
    void requestNavigationList(String functionCode);
}
