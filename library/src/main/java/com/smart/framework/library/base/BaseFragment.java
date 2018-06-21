package com.smart.framework.library.base;

/**
 * 作者：addison on 15/12/15 14:29
 * 邮箱：lsf@yonyou.com
 */
public abstract class BaseFragment extends BaseLazyFragment implements BaseView {

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, null);
    }

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
    }

    @Override
    public void showDialogLoading(String msg) {
        toggleShowDialogLoading(msg);
    }

    @Override
    public void dismissDialogLoading() {
        dismissDialogLoad();
    }

//    @Override
//    public void showBusinessError(ErrorBean error) {
//        if (null != error.getCode() && error.getCode().equals("10000"))
//            toggleshowLogin();
//    }
}

