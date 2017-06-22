package com.yonyou.framework.library.http.exception;

/**
 * 作者：addison on 15/12/15 18:30
 * 邮箱：lsf@yonyou.com
 */
public enum NetException {
    NetworkNotAvilable("Network Is Not Avilable", "未连接网络"),
    NetworkUnstable("Service Unreachable, or Network Is Unstable", "服务无法访问"),
    NetworkDisabled("Current Network Is Disabled By Your Setting", "已禁用该网络类型");

    private static final String TAG = NetException.class.getName();
    public String reason;
    public String chiReason;

    NetException(String name, String chiName) {
        this.reason = name;
        this.chiReason = chiName;
    }

    @Override
    public String toString() {
        return TAG + ": " + this.reason + " (" + this.chiReason + ")";
    }
}
