package com.yonyou.hhtpos.application;

import android.content.Context;

import com.yonyou.framework.library.BaseApplication;
import com.yonyou.hhtpos.bean.NavigationNewEntity;
import com.yonyou.hhtpos.db.DbManager;

import java.util.List;

/**
 * 作者：liushuofei on 2017/6/22 10:00
 * 邮箱：lsf@yonyou.com
 */
public class MyApplication extends BaseApplication{

    public static List<NavigationNewEntity> dataList;

    public static final String DATABASE_NAME = "hhtdb.db";

    private static Context mContext;

    public static MyApplication mInstance = null;

    public static MyApplication getInstance(){
        return mInstance;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();

        //初始化全局捕获异常类
        //CrashHandler.getInstance().init(this);

//        try
//        {
//            RequestManager.getInstance()
//                    .setCertificates(getAssets().open("srca.cer"));
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
    }

    /**
     * 初始化数据库
     */
    public static DbManager initDataBase() {
        return DbManager.getInstance();
    }
}
