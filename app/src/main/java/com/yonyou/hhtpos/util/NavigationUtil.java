package com.yonyou.hhtpos.util;

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

    //一级菜单数据
    private static List<String> mGroupData = new ArrayList<>();
    //二级菜单数据
    private static List<List<String>> mChildData = new ArrayList<>();

    /**
     * 默认父数据
     */
    public static List<String> getDefaultGroupData(){
        for (int i = 0; i < mGroup.length; i++) {
            mGroupData.add(mGroup[i]);
        }
        return mGroupData;
    }

    /**
     * 默认子数据
     */
    public static List<List<String>> getDefaultChildData(){
        for (int i = 0; i < mGroup.length; i++) {
            List<String> child = new ArrayList<String>();
            for (int j = 0; j < mChild[i].length; j++) {
                child.add(mChild[i][j]);
            }
            mChildData.add(child);
        }
        return mChildData;
    }


}
