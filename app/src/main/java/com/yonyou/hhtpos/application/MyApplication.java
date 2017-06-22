package com.yonyou.hhtpos.application;

import android.content.Context;

import com.yonyou.framework.library.BaseApplication;
import com.yonyou.hhtpos.db.DbManager;
import com.yonyou.hhtpos.manager.CrashHandler;

/**
 * 作者：liushuofei on 2017/6/22 10:00
 * 邮箱：lsf@yonyou.com
 */
public class MyApplication extends BaseApplication{

    private static Context mContext;

    public static MyApplication mInstance = null;

    public static MyApplication getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();

        //初始化全局捕获异常类
        CrashHandler.getInstance().init(this);
    }

    /**
     * 初始化数据库
     */
    public static DbManager initDataBase() {
        return DbManager.getInstance();
    }
}
