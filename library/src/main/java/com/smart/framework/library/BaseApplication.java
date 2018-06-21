package com.smart.framework.library;

import android.app.Application;

/**
 * 作者：addison on 11/12/15 13:59
 * 邮箱：lsf@yonyou.com
 */
public class BaseApplication extends Application {
    public static BaseApplication mInstance = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public void showLoginDialog(){

    }

    public static BaseApplication getInstance(){
        return mInstance;
    }
}
