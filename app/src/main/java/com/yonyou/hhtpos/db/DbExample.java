package com.yonyou.hhtpos.db;

import android.content.Context;

import com.yonyou.buyer.MyApplication;
import com.yonyou.buyer.R;
import com.yonyou.buyer.db.entity.User;
import com.yonyou.buyer.db.gen.UserDao;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;

/**
 * Created by ljt at 21:15 on 2016/12/21.
 * E-Mail:ljt@yonyou.com
 *
 * 数据库操作示范类：
 * 所有的 Xxx.class 都表示要对Xxx对应的那张表进行操作
 */

public class DbExample {

    // DbManager的单例实例
    private static DbManager dbManager;

    // 示例类对象
    private User cyy;
    private ArrayList<User> users;

    public static void main(String[] args) {
        dbManager = MyApplication.initDataBase();
    }

    /*************************插入数据示例*************************/

    // 插入某条数据
    public void addItem() {
        // id字段位置传入null表示自增长
        cyy = new User(null, "cyy", 25, 1);
        dbManager.insert(User.class, cyy);
    }

    // 插入某组数据
    public void addMultItems() {
        users = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            User user = new User(null, "ljt_" + i, 25 + i, 1);
            users.add(user);
        }
        dbManager.insertMultObject(User.class, users);
    }


    /*************************删除数据示例*************************/

    // 删除某条数据
    public void deleteItem() {
        dbManager.delete(User.class, cyy);
    }

    // 根据id删除表数据
    public void deleteItemById() {
        dbManager.deleteByKey(User.class, 3L);
    }

    // 删除某组数据
    public void deleteMultItems() {
        dbManager.deleteMultObject(User.class, users);
    }

    // 清空某张表的全部内容
    public void deleteAll() {
        dbManager.deleteAll(User.class);
    }


    /*************************更新数据示例*************************/

    // 更新某条数据
    public void updateItem(Context mContext) {
        cyy.setName(mContext.getString(R.string.app_name));
        dbManager.update(User.class,cyy);
    }

    // 更新某组数据
    public void updateMultItems() {
        for(User user : users) {
           user.setAge(110);
        }
        dbManager.updateMultObject(User.class,users);
    }


    /*************************查询数据示例*************************/

    // 精确查询某条数据 --- 底层使用模糊查询的 eq 条件实现
    public void accurateQuery(Context mContext) {
        // 目标属性
        ArrayList<Property> properties = new ArrayList<>();
        properties.add(UserDao.Properties.Name);

        // 匹配条件
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(mContext.getString(R.string.app_name));

        dbManager.query(User.class, properties, objects);
    }

    // 模糊查询某条数据
    public void obscureQuery() {
        // 目标属性
        ArrayList<Property> properties = new ArrayList<>();
        properties.add(UserDao.Properties.Age);

        // 模糊查询条件，可选类型：eq, notEq,like, between
        ArrayList<String> conditions = new ArrayList<>();
        conditions.add("notEq");

        // 匹配条件
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("24");

        // select * from User where Age != 24
        dbManager.query(User.class, properties, conditions, objects);
    }

    // 精确查询某组数据
    public void accurateMultQuery(Context mContext) {
        // 目标属性
        ArrayList<Property> properties = new ArrayList<>();
        properties.add(UserDao.Properties.Name);

        // 匹配条件
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(mContext.getString(R.string.app_name));

        dbManager.queryMultObject(User.class, properties, objects);
    }

    // 模糊查询某组数据
    public void obscureMultQuery() {
        // 目标属性
        ArrayList<Property> properties = new ArrayList<>();
        properties.add(UserDao.Properties.Id);

        // 模糊查询条件，可选类型：eq, notEq,like, between
        ArrayList<String> conditions = new ArrayList<>();
        conditions.add("between");

        // 匹配条件
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("2");
        objects.add("4");

        dbManager.query(User.class, properties, conditions, objects);
    }

    // 记得在onDestory()方法里关闭数据库
    protected void onDestroy() {
        //super.onDestroy();
        dbManager.closeDataBase();
    }
}
