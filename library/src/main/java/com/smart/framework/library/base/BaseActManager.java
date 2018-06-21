package com.smart.framework.library.base;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者：addison on 14/12/15 15:03
 * 邮箱：lsf@yonyou.com
 * activity 栈管理
 */
public class BaseActManager {
    private static final String TAG = BaseActManager.class.getSimpleName();

    private static BaseActManager instance = null;
    private static List<Activity> mActivities = new LinkedList<Activity>();

    private BaseActManager() {

    }

    public synchronized static BaseActManager getInstance() {
        if (instance == null) {
            instance = new BaseActManager();
        }
        return instance;
    }

    public int size() {
        return mActivities.size();
    }

    public synchronized Activity getForwardActivity() {
        return size() > 0 ? mActivities.get(size() - 1) : null;
    }

    public synchronized void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public synchronized void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    public synchronized void clear() {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size();
        }
    }

    public synchronized void clearToTop() {
        for (int i = mActivities.size() - 2; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size() - 1;
        }
    }
}
