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

    /**
     * 右侧导航栏测试数据
     * @return
     */
    public static List<RightTitleEntity> getRightDefaultData(){
        List<RightTitleEntity> dataList = new ArrayList<>();
        RightTitleEntity bean = null;
        String title = null;
        for (int i = 0; i < 7; i++){
            switch (i){
                case 0:
                    title = "招牌必吃";
                    break;

                case 1:
                    title = "下饭菜";
                    break;

                case 2:
                    title = "热菜";
                    break;

                case 3:
                    title = "凉菜";
                    break;

                case 4:
                    title = "蒸煮";
                    break;

                case 5:
                    title = "甜品";
                    break;

                case 6:
                    title = "主食";
                    break;

                default:
                    break;
            }

            bean = new RightTitleEntity();
            bean.name = title;
            dataList.add(bean);
        }

        return dataList;
    }




}
