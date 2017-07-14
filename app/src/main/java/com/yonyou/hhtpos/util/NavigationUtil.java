package com.yonyou.hhtpos.util;

import com.yonyou.hhtpos.bean.RightTitleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航栏工具类
 * 作者：liushuofei on 2017/6/23 10:16
 */
public class NavigationUtil {

    private static final String[] mGroup = {"堂食", "外带", "外卖", "预定", "会员管理"};
    //子视图显示文字
    private static String[][] mChild = new String[][]{
            {},
            {},
            {"百度外卖", "饿了么", "美团外卖"},
            {"预定申请", "预定总览", "桌台预定查询"},
            {}
    };
    private static final String[] mDishTypes = {"招牌必吃", "热饭菜", "凉菜", "热菜", "蒸煮", "甜品", "主食", "热饮", "冷饮","泡菜","辣菜","招牌必吃", "热饭菜", "凉菜", "热菜"};
    //一级菜单数据
    private static List<String> mGroupData = new ArrayList<>();
    //二级菜单数据
    private static List<List<String>> mChildData = new ArrayList<>();

    /**
     * 默认父数据
     */
    public static List<String> getDefaultGroupData() {
        for (int i = 0; i < mGroup.length; i++) {
            mGroupData.add(mGroup[i]);
        }
        return mGroupData;
    }

    /**
     * 默认子数据
     */
    public static List<List<String>> getDefaultChildData() {
        for (int i = 0; i < mGroup.length; i++) {
            List<String> child = new ArrayList<String>();
            for (int j = 0; j < mChild[i].length; j++) {
                child.add(mChild[i][j]);
            }
            mChildData.add(child);
        }
        return mChildData;
    }

    /**
     * 右侧导航栏测试数据
     *
     * @return
     */
    public static List<RightTitleEntity> getRightDefaultData() {
        List<RightTitleEntity> dataList = new ArrayList<>();
        //右边标题
        for (int i = 0; i < mDishTypes.length; i++) {
            RightTitleEntity rightTitleEntity = new RightTitleEntity();
            rightTitleEntity.id = i + "";
            rightTitleEntity.name = mDishTypes[i];
            dataList.add(rightTitleEntity);
        }
        return dataList;
    }


}
