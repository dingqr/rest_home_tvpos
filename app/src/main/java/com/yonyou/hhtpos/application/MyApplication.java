package com.yonyou.hhtpos.application;

import android.content.Context;
import android.text.TextUtils;

import com.yonyou.framework.library.BaseApplication;
import com.yonyou.framework.library.common.utils.AppSharedPreferences;
import com.yonyou.hhtpos.bean.NavigationNewEntity;
import com.yonyou.hhtpos.db.DbManager;
import com.yonyou.hhtpos.db.entity.UserEntity;
import com.yonyou.hhtpos.util.Constants;
import com.yonyou.hhtpos.util.SpUtil;

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

    private UserEntity userEntity;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
        AppSharedPreferences sharePre = new AppSharedPreferences(this);

        String shopId = sharePre.getString(SpUtil.SHOP_ID);
        if (!TextUtils.isEmpty(shopId)){
            Constants.SHOP_ID = shopId;
        }

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

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
    /**
     * 初始化数据库
     */
    public static DbManager initDataBase() {
        return DbManager.getInstance();
    }
}
