package com.smart.framework.library.base;


import com.smart.framework.library.bean.ErrorBean;

/**
 * 作者：addison on 15/12/15 14:17
 * 邮箱：lsf@yonyou.com
 */
public interface BaseView {
    /**
     * show loading message
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * hide loading
     */
    void hideLoading();

    /**
     * show error message
     */
    void showError(String msg);

    /**
     * show exception message
     */
    void showException(String msg);

    /**
     * show net error
     */
    void showNetError();

    /**
     * show business error
     */
    void showBusinessError(ErrorBean error);

    /**
     * dialog loading
     */
    void showDialogLoading(String msg);

    /**
     * dismiss  dialog loading
     */
    void dismissDialogLoading();
}
