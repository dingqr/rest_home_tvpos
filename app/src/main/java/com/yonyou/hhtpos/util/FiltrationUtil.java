package com.yonyou.hhtpos.util;

import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;

import java.util.ArrayList;

/**
 * 筛选框假数据工具类
 * 作者：ybing on 2017/6/28
 */

public class FiltrationUtil {

    private static final int VIEW_DISH_TYPE = 0;
    private static final int VIEW_DISH_AREA = 1;
    private static final int VIEW_RESERVE_STATUS = 2;

    private static String title1 = "餐别";
    private static String title2 = "餐区";
    private static String title3 = "预定状态";

    private static ArrayList<FilterOptionsEntity> dishType = new ArrayList<>();
    private static ArrayList<FilterOptionsEntity> dishArea = new ArrayList<>();
    private static ArrayList<FilterOptionsEntity> reserveStatus = new ArrayList<>();
    private static ArrayList<FilterItemEntity> filterItemList;

    public static ArrayList<FilterItemEntity> getFakeData(){
        FilterOptionsEntity fde1 = new FilterOptionsEntity("早餐",VIEW_DISH_TYPE,true);
        FilterOptionsEntity fde2 = new FilterOptionsEntity("午餐",VIEW_DISH_TYPE,false);
        FilterOptionsEntity fde3 = new FilterOptionsEntity("晚餐",VIEW_DISH_TYPE,false);
        FilterOptionsEntity fde4 = new FilterOptionsEntity("夜宵",VIEW_DISH_TYPE,false);

        FilterOptionsEntity fde5 = new FilterOptionsEntity("全部餐区",VIEW_DISH_AREA,true);
        FilterOptionsEntity fde6 = new FilterOptionsEntity("一楼",VIEW_DISH_AREA,false);
        FilterOptionsEntity fde7 = new FilterOptionsEntity("二楼",VIEW_DISH_AREA,false);
        FilterOptionsEntity fde8 = new FilterOptionsEntity("三楼",VIEW_DISH_AREA,false);
        FilterOptionsEntity fde9 = new FilterOptionsEntity("包房",VIEW_DISH_AREA,false);

        FilterOptionsEntity fde10 = new FilterOptionsEntity("预约过期关闭",VIEW_RESERVE_STATUS,true);
        FilterOptionsEntity fde11= new FilterOptionsEntity("客人已取消",VIEW_RESERVE_STATUS,false);
        FilterOptionsEntity fde12 = new FilterOptionsEntity("等待客人中",VIEW_RESERVE_STATUS,false);
        FilterOptionsEntity fde13 = new FilterOptionsEntity("客人已到达",VIEW_RESERVE_STATUS,false);

        dishType.add(fde1);
        dishType.add(fde2);
        dishType.add(fde3);
        dishType.add(fde4);

        dishArea.add(fde5);
        dishArea.add(fde6);
        dishArea.add(fde7);
        dishArea.add(fde8);
        dishArea.add(fde9);

        reserveStatus.add(fde10);
        reserveStatus.add(fde11);
        reserveStatus.add(fde12);
        reserveStatus.add(fde13);


        FilterItemEntity fie1 = new FilterItemEntity(dishType,title1);
        FilterItemEntity fie2 = new FilterItemEntity(dishArea,title2);
        FilterItemEntity fie3 = new FilterItemEntity(reserveStatus,title3);

        filterItemList = new ArrayList<>();

        filterItemList.add(fie1);
        filterItemList.add(fie2);
        filterItemList.add(fie3);

        return filterItemList;
    }
}
