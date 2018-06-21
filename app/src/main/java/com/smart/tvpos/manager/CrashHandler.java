package com.smart.tvpos.manager;

/**
 * Created by zj on 2017/2/28.
 * 邮箱：zjuan@yonyou.com
 * 描述：全局的捕获程序未处理的异常的类
 */

import android.content.Context;
import android.os.Looper;

import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.framework.library.manager.ActivityTaskManager;
import com.smart.tvpos.R;


public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler crashHandler = null;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
    private Context mContext;
    public CrashHandler() {
    }

    //懒汉式实现单例---此处不存在线程安全，因为处理异常的类，系统单独提供了一个线程来完成
    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }
    //初始化
    public void init(Context context) {
        this.mContext = context;
        this.defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //让当前的类，作为程序出现未捕获异常的处理类
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    //一旦出现未捕获的异常，即调用如下方法。异常信息发生时的处理：可以考虑发送给服务器
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e == null) {
            defaultUncaughtExceptionHandler.uncaughtException(t, e);
        } else {
            handleException(t, e);
        }
    }

    private void handleException(Thread t, Throwable e) {
//        //必须在主线程执行Toast
        new Thread() {
            public void run() {
                //Android系统当中，默认情况下，线程是没有开启looper消息处理的，但是主线程除外
                Looper.prepare();
                //更新界面需要放到主线程中
                CommonUtils.makeEventToast(mContext,mContext.getResources().getString(R.string.arise_exception),true);
                Looper.loop();
            }
        }.start();

        try {
            Thread.sleep(500);
            ActivityTaskManager.getInstance().closeAllActivity();  //1.报异常后，移除所有Activity
            android.os.Process.killProcess(android.os.Process.myPid());//2.结束当前应用的进程
            //3.结束当前虚拟机的执行，释放所有内存:参数0代表正常退出
            System.exit(0);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

}
