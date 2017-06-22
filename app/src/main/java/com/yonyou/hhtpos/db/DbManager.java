package com.yonyou.hhtpos.db;

import android.content.Context;

import com.yonyou.buyer.MyApplication;
import com.yonyou.buyer.db.gen.DaoMaster;
import com.yonyou.buyer.db.gen.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by ljt at 17:14 on 2016/12/20.
 * E-Mail:ljt@yonyou.com
 * GreenDao的通用操作类
 */

public class DbManager {
    private final static String DB_NAME = MyApplication.DATABASE_NAME;
    private static DbManager instance;

    private Context context = MyApplication.getContext();
    private static DaoMaster.DevOpenHelper openHelper;
    private static DaoMaster daoMaster_write;
    private static DaoMaster daoMaster_read;
    private static DaoSession daoSession;

    private DbManager() {
        openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        daoMaster_write = new DaoMaster(openHelper.getWritableDb());
        daoMaster_read = new DaoMaster(openHelper.getReadableDb());
    }

    /***
     * 获取 DbManager 单例
     *
     * @return
     */
    public static DbManager getInstance() {
        DbManager dbManager = instance;
        if (dbManager == null) {
            synchronized (DbManager.class) {
                if (dbManager == null) {
                    instance = new DbManager();
                    dbManager = instance;
                }
            }
        }
        return dbManager;
    }

    /**
     * 获取 writeable Dao
     *
     * @param entityClass
     * @return
     */
    private AbstractDao getWriteDao(Class<? extends Object> entityClass) {
        daoSession = daoMaster_write.newSession();
        return daoSession.getDao(entityClass);
    }

    /**
     * 获取 readable Dao
     *
     * @param entityClass
     * @return
     */
    private AbstractDao getReadDao(Class<? extends Object> entityClass) {
        daoSession = daoMaster_read.newSession();
        return daoSession.getDao(entityClass);
    }

    /**
     * 关闭数据库
     */
    public void closeDataBase() {
        closeHelper();
        closeDaoSession();
    }

    private void closeDaoSession() {
        if (null != daoSession) {
            daoSession.clear();
            daoSession = null;
        }
    }

    /**
     * helper底层会关闭数据库
     */
    private void closeHelper() {
        if (openHelper != null) {
            openHelper.close();
            openHelper = null;
        }
    }

    /**
     * 插入单个的实体
     *
     * @param entityClass
     * @return
     */
    public long insert(Class<? extends Object> entityClass, Object object) {
        if (null == object) {
            return -1L;
        }
        return getWriteDao(entityClass).insert(object);
    }

    /**
     * 插入或更新
     * @param entityClass
     * @param object
     * @return
     */
    public long insertOrReplace(Class<? extends Object> entityClass, Object object){
        if (null == object) {
            return -1L;
        }
        return getWriteDao(entityClass).insertOrReplace(object);
    }

    /**
     * 插入列表
     *
     * @param entityClass
     * @param objects
     */
    public void insertMultObject(Class<? extends Object> entityClass, List<? extends Object> objects) {
        if (objects == null && objects.size() == 0) {
            return;
        }
        getWriteDao(entityClass).insertInTx(objects);
    }

    /**
     * 精确查找某个实体
     *
     * @param entityClass 目标表名
     * @param properties 目标属性
     * @param objects 匹配条件
     * @return
     */
    public Object query(Class<? extends Object> entityClass, List<Property> properties, List<Object> objects) {
        if (null == properties || properties.isEmpty()) {
            return null;
        }
        int size = properties.size();
        QueryBuilder queryBuilder = getWriteDao(entityClass).queryBuilder();
        for (int i = 0; i < size; i++) {
            queryBuilder.where(properties.get(i).eq(objects.get(i)));
        }

        return queryBuilder.build().unique();
    }

    /**
     * 模糊查找某个实体
     *
     * @param entityClass 目标表名
     * @param properties 目标属性
     * @param whereConditions 模糊查询条件
     * @param objects 匹配条件
     * @return
     */
    public Object query(Class<? extends Object> entityClass, List<Property> properties, List<String> whereConditions, List<Object> objects) {
        if (null == properties || properties.isEmpty()) {
            return null;
        }
        int size = properties.size();
        QueryBuilder queryBuilder = getWriteDao(entityClass).queryBuilder();
        for (int i = 0; i < size; i++) {
            switch (whereConditions.get(i)) {
                case "eq":
                    queryBuilder.where(properties.get(i).eq(objects.get(i)));
                    break;

                case "notEq":
                    queryBuilder.where(properties.get(i).notEq(objects.get(i)));
                    break;

                case "like":
                    queryBuilder.where(properties.get(i).like((String) objects.get(i)));
                    break;

                case "between":
                    queryBuilder.where(properties.get(i).between(objects.get(i), objects.get(i + 1)));
                    break;
            }
        }

        return queryBuilder.build().unique();
    }

    /**
     * 精确查找某些实体
     *
     * @param entityClass
     * @param properties
     * @param objects
     * @return
     */
    public List<? extends Object> queryMultObject(Class<? extends Object> entityClass, List<Property> properties, List<Object> objects) {
        if (null == properties || properties.isEmpty()) {
            return null;
        }
        int size = properties.size();
        QueryBuilder queryBuilder = getWriteDao(entityClass).queryBuilder();
        for (int i = 0; i < size; i++) {
            queryBuilder.where(properties.get(i).eq(objects.get(i)));
        }

        return queryBuilder.build().list();
    }


    /**
     * 模糊查找某些实体
     *
     * @param entityClass
     * @param properties
     * @param objects
     * @return
     */
    public List<? extends Object> queryMultObject(Class<? extends Object> entityClass, List<Property> properties, List<String> whereConditions, List<Object> objects) {
        if (null == properties || properties.isEmpty()) {
            return null;
        }
        int size = properties.size();
        QueryBuilder queryBuilder = getWriteDao(entityClass).queryBuilder();
        for (int i = 0; i < size; i++) {
            switch (whereConditions.get(i)) {
                case "eq":
                    queryBuilder.where(properties.get(i).eq(objects.get(i)));
                    break;

                case "notEq":
                    queryBuilder.where(properties.get(i).notEq(objects.get(i)));
                    break;

                case "like":
                    queryBuilder.where(properties.get(i).like((String) objects.get(i)));
                    break;

                case "between":
                    queryBuilder.where(properties.get(i).between(objects.get(i), objects.get(i + 1)));
                    break;
            }
        }

        return queryBuilder.build().list();
    }

    /**
     * 查询数据库中所有的实体
     *
     * @param entityClass
     * @return
     */
    public List<? extends Object> queryAll(Class<? extends Object> entityClass) {
        List<? extends Object> entitys = getWriteDao(entityClass).queryBuilder().list();
        return entitys;
    }


    /**
     * 删除单个实体
     *
     * @param entityClass
     * @param object
     */
    public void delete(Class<? extends Object> entityClass, Object object) {
        if (null == object) {
            return;
        }
        getWriteDao(entityClass).delete(object);
    }

    /**
     * 根据唯一的key值删除单个实体
     * @param entityClass
     * @param key
     */
    public void deleteByKey(Class<? extends Object> entityClass, Long key) {
        if(null == key) {
            return;
        }
        getWriteDao(entityClass).deleteByKey(key);
    }

    /**
     * 删除多个实体
     *
     * @param entityClass
     * @param objects
     */
    public void deleteMultObject(Class<? extends Object> entityClass, List<? extends Object> objects) {
        if (null == objects || objects.isEmpty()) {
            return;
        }

        getWriteDao(entityClass).deleteInTx(objects);
    }

    /**
     * 删除某个类的所有实体
     *
     * @param entityClass
     */
    public void deleteAll(Class<? extends Object> entityClass) {
        getWriteDao(entityClass).deleteAll();
    }

    /**
     * 更新某一条数据
     *
     * @param entityClass
     * @param object
     */
    public void update(Class<? extends Object> entityClass, Object object) {
        if (null == object) {
            return;
        }
        getWriteDao(entityClass).update(object);
    }

    /**
     * 更新多条数据
     *
     * @param entityClass
     * @param objects
     */
    public void updateMultObject(Class<? extends Object> entityClass, List<? extends Object> objects) {

        if (null == objects || objects.isEmpty()) {
            return;
        }
        getWriteDao(entityClass).updateInTx(objects);

    }
}
