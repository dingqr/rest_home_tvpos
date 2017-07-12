package com.yonyou.hhtpos.interactor;

/**
 * 作者：liushuofei on 2017/7/11 17:31
 * 邮箱：lsf@yonyou.com
 */
public interface INavigationInteractor {

    /**
     * 请求导航栏数据
     * @param functionCode POS端后台根编码
     */
    void requestNavigationList(String functionCode);
}
