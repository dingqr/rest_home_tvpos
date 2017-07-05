package com.yonyou.hhtpos.util;

import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.ReserveOrderListEntity;
import com.yonyou.hhtpos.bean.TableCapacityEntity;
import com.yonyou.hhtpos.bean.TableReserveEntity;

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

        FilterOptionsEntity fde10 = new FilterOptionsEntity("待门店接单",VIEW_RESERVE_STATUS,true);
        FilterOptionsEntity fde11= new FilterOptionsEntity("等待客人中",VIEW_RESERVE_STATUS,false);
        FilterOptionsEntity fde12 = new FilterOptionsEntity("客人已到达",VIEW_RESERVE_STATUS,false);
        FilterOptionsEntity fde13 = new FilterOptionsEntity("客人已取消",VIEW_RESERVE_STATUS,false);
        FilterOptionsEntity fde14 = new FilterOptionsEntity("预约过期关闭",VIEW_RESERVE_STATUS,false);
        FilterOptionsEntity fde15 = new FilterOptionsEntity("门店已拒绝",VIEW_RESERVE_STATUS,false);

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
        reserveStatus.add(fde14);
        reserveStatus.add(fde15);


        FilterItemEntity fie1 = new FilterItemEntity(dishType,title1);
        FilterItemEntity fie2 = new FilterItemEntity(dishArea,title2);
        FilterItemEntity fie3 = new FilterItemEntity(reserveStatus,title3);

        filterItemList = new ArrayList<>();

        filterItemList.add(fie1);
        filterItemList.add(fie2);
        filterItemList.add(fie3);

        return filterItemList;
    }
    public static ArrayList<FilterOptionsEntity> getOptions(){
        ArrayList<FilterOptionsEntity> filterOptionsEntities = new ArrayList<>();
        FilterOptionsEntity fde1 = new FilterOptionsEntity("全部餐厅",VIEW_DISH_TYPE,false);
        FilterOptionsEntity fde2 = new FilterOptionsEntity("一楼大厅",VIEW_DISH_TYPE,true);
        FilterOptionsEntity fde3 = new FilterOptionsEntity("一楼宴会厅",VIEW_DISH_TYPE,false);
        FilterOptionsEntity fde4 = new FilterOptionsEntity("一楼包厢",VIEW_DISH_TYPE,false);
        filterOptionsEntities.add(fde1);
        filterOptionsEntities.add(fde2);
        filterOptionsEntities.add(fde3);
        filterOptionsEntities.add(fde4);

        return  filterOptionsEntities;
    }

    /*
    *  public TableCapacityEntity(int capacity, int type, String area, boolean isCheck) {
        this.capacity = capacity;
        this.type = type;
        this.area = area;
        this.isCheck = isCheck;
    }*/

    public static ArrayList<TableCapacityEntity> getCapacities(){
         ArrayList<TableCapacityEntity> capacityEntities = new ArrayList<>();
        TableCapacityEntity tce1 = new TableCapacityEntity(10,0,"北京1",true);
        TableCapacityEntity tce2 = new TableCapacityEntity(16,0,"北京2",false);
        TableCapacityEntity tce3 = new TableCapacityEntity(10,0,"北京3",false);
        TableCapacityEntity tce4 = new TableCapacityEntity(16,0,"北京4",false);
        TableCapacityEntity tce5 = new TableCapacityEntity(10,0,"北京5",false);
        TableCapacityEntity tce6 = new TableCapacityEntity(16,0,"北京6",false);
        TableCapacityEntity tce7 = new TableCapacityEntity(10,0,"北京7",false);
        TableCapacityEntity tce8 = new TableCapacityEntity(16,0,"北京8",false);
        TableCapacityEntity tce9 = new TableCapacityEntity(10,0,"北京9",false);
        TableCapacityEntity tce10 = new TableCapacityEntity(16,0,"北京10",false);
        TableCapacityEntity tce11 = new TableCapacityEntity(10,0,"北京11",false);
        TableCapacityEntity tce12 = new TableCapacityEntity(16,0,"北京12",false);
        capacityEntities.add(tce1);
        capacityEntities.add(tce2);
        capacityEntities.add(tce3);
        capacityEntities.add(tce4);
        capacityEntities.add(tce5);
        capacityEntities.add(tce6);
        capacityEntities.add(tce7);
        capacityEntities.add(tce8);
        capacityEntities.add(tce9);
        capacityEntities.add(tce10);
        capacityEntities.add(tce11);
        capacityEntities.add(tce12);
        return capacityEntities;
    }

//    getTableQueryFakeData

    public static ArrayList<TableReserveEntity> getTableQueryFakeData(){
        ArrayList<TableReserveEntity> tableReserveEntities = new ArrayList<>();
        TableReserveEntity tre1 = new TableReserveEntity(true,"包房001",1,1);
        TableReserveEntity tre2 = new TableReserveEntity(false,"包房002",2,1);
        TableReserveEntity tre3 = new TableReserveEntity(false,"包房003",3,1);
        TableReserveEntity tre4 = new TableReserveEntity(false,"包房004",1,11);
        TableReserveEntity tre5 = new TableReserveEntity(false,"包房005",10,1);
        TableReserveEntity tre6 = new TableReserveEntity(false,"包房006",1,1);
        TableReserveEntity tre7 = new TableReserveEntity(false,"包房007",1,1);
        TableReserveEntity tre8 = new TableReserveEntity(false,"包房008",1,1);
        TableReserveEntity tre9 = new TableReserveEntity(false,"包房009",1,1);
        TableReserveEntity tre10 = new TableReserveEntity(false,"包房010",1,1);
        TableReserveEntity tre11 = new TableReserveEntity(false,"包房011",1,1);
        TableReserveEntity tre12 = new TableReserveEntity(false,"包房012",1,1);
        TableReserveEntity tre13 = new TableReserveEntity(false,"包房013",1,1);
        TableReserveEntity tre14 = new TableReserveEntity(false,"包房014",1,1);
        TableReserveEntity tre15 = new TableReserveEntity(false,"包房015",1,1);
        TableReserveEntity tre16 = new TableReserveEntity(false,"包房016",1,1);
        TableReserveEntity tre17 = new TableReserveEntity(false,"包房017",1,1);
        TableReserveEntity tre18 = new TableReserveEntity(false,"包房018",1,1);
        TableReserveEntity tre19 = new TableReserveEntity(false,"包房019",1,1);
        TableReserveEntity tre20 = new TableReserveEntity(false,"包房020",1,1);
        TableReserveEntity tre21 = new TableReserveEntity(false,"包房021",1,1);
        TableReserveEntity tre22 = new TableReserveEntity(false,"包房022",1,1);
        TableReserveEntity tre23 = new TableReserveEntity(false,"包房023",1,1);
        TableReserveEntity tre24 = new TableReserveEntity(false,"包房024",1,1);
        TableReserveEntity tre25 = new TableReserveEntity(false,"包房025",1,1);
        TableReserveEntity tre26 = new TableReserveEntity(false,"包房026",1,1);
        TableReserveEntity tre27 = new TableReserveEntity(false,"包房027",1,1);
        TableReserveEntity tre28 = new TableReserveEntity(false,"包房028",1,1);
        TableReserveEntity tre29 = new TableReserveEntity(false,"包房029",1,1);
        TableReserveEntity tre30 = new TableReserveEntity(false,"包房030",1,1);

        tableReserveEntities.add(tre1);
        tableReserveEntities.add(tre2);
        tableReserveEntities.add(tre3);
        tableReserveEntities.add(tre4);
        tableReserveEntities.add(tre5);
        tableReserveEntities.add(tre6);
        tableReserveEntities.add(tre7);
        tableReserveEntities.add(tre8);
        tableReserveEntities.add(tre9);
        tableReserveEntities.add(tre10);

        tableReserveEntities.add(tre11);
        tableReserveEntities.add(tre12);
        tableReserveEntities.add(tre13);
        tableReserveEntities.add(tre14);
        tableReserveEntities.add(tre15);
        tableReserveEntities.add(tre16);
        tableReserveEntities.add(tre17);
        tableReserveEntities.add(tre18);
        tableReserveEntities.add(tre19);
        tableReserveEntities.add(tre20);

        tableReserveEntities.add(tre21);
        tableReserveEntities.add(tre22);
        tableReserveEntities.add(tre23);
        tableReserveEntities.add(tre24);
        tableReserveEntities.add(tre25);
        tableReserveEntities.add(tre26);
        tableReserveEntities.add(tre27);
        tableReserveEntities.add(tre28);
        tableReserveEntities.add(tre29);
        tableReserveEntities.add(tre30);

        return tableReserveEntities;
    }
    /*
    *  this.orderStatus = orderStatus;
        this.dishType = dishType;
        this.orderTime = orderTime;
        this.customerPhone = customerPhone;
        this.customerHonorific = customerHonorific;
        this.diningNumber = diningNumber;
        this.orderRemark = orderRemark;
        */
    public static ArrayList<ReserveOrderListEntity> getReserveOrderList(){
        ArrayList<ReserveOrderListEntity> reserveOrderListEntity = new ArrayList<>();
        long time = System.currentTimeMillis();
        ReserveOrderListEntity rol1 = new ReserveOrderListEntity("1","客人已到达","午餐",time,"15900008888","张总","6","需要两个儿童座椅");
        ReserveOrderListEntity rol2 = new ReserveOrderListEntity("2","等待客人中","晚餐",time,"15900008888","王总","6","需要两个儿童座椅");
        ReserveOrderListEntity rol3 = new ReserveOrderListEntity("3","客人已取消","午餐",time,"15900008888","李总","6","需要两个儿童座椅");
        ReserveOrderListEntity rol4 = new ReserveOrderListEntity("4","预约过期关闭","晚餐",time,"15900008888","赵总","10","需要两个儿童座椅");
        ReserveOrderListEntity rol5 = new ReserveOrderListEntity("5","客人已到达","午餐",time,"15900008888","魏总","16","需要两个儿童座椅需要两个儿童座椅需要两个");
        ReserveOrderListEntity rol6 = new ReserveOrderListEntity("6","预约过期关闭","午餐",time,"15900008888","孙总","3","需要两个儿童座椅需要两个儿童座椅需要两个");
        reserveOrderListEntity.add(rol1);
        reserveOrderListEntity.add(rol2);
        reserveOrderListEntity.add(rol3);
        reserveOrderListEntity.add(rol4);
        reserveOrderListEntity.add(rol5);
        reserveOrderListEntity.add(rol6);

        return reserveOrderListEntity;
    }

    public static ArrayList<ReserveOrderListEntity> getLeftReserveOrderList(){
        ArrayList<ReserveOrderListEntity> reserveOrderListEntity = new ArrayList<>();
        long time = System.currentTimeMillis();
        ReserveOrderListEntity rol1 = new ReserveOrderListEntity("1","客人已到达","午餐",time,"15900008888","张总","6","需要两个儿童座椅",false);
        ReserveOrderListEntity rol2 = new ReserveOrderListEntity("2","等待客人中","晚餐",time,"15900008888","王总","6","需要两个儿童座椅",false);
        ReserveOrderListEntity rol3 = new ReserveOrderListEntity("3","客人已取消","午餐",time,"15900008888","李总","6","需要两个儿童座椅",true);
        ReserveOrderListEntity rol4 = new ReserveOrderListEntity("4","预约过期关闭","晚餐",time,"15900008888","赵总","10","需要两个儿童座椅",false);
        ReserveOrderListEntity rol5 = new ReserveOrderListEntity("5","客人已到达","午餐",time,"15900008888","魏总","16","需要两个儿童座椅需要两个儿童座椅需要两个",false);
        ReserveOrderListEntity rol6 = new ReserveOrderListEntity("6","预约过期关闭","午餐",time,"15900008888","孙总","3","需要两个儿童座椅需要两个儿童座椅需要两个",false);
        ReserveOrderListEntity rol7 = new ReserveOrderListEntity("7","客人已到达","午餐",time,"15900008888","张总","6","需要两个儿童座椅",false);
        ReserveOrderListEntity rol8 = new ReserveOrderListEntity("8","等待客人中","晚餐",time,"15900008888","王总","6","需要两个儿童座椅",false);
        ReserveOrderListEntity rol9 = new ReserveOrderListEntity("9","客人已取消","午餐",time,"15900008888","李总","6","需要两个儿童座椅",false);
        ReserveOrderListEntity rol10 = new ReserveOrderListEntity("10","预约过期关闭","晚餐",time,"15900008888","赵总","10","需要两个儿童座椅",false);
        ReserveOrderListEntity rol11 = new ReserveOrderListEntity("11","客人已到达","午餐",time,"15900008888","魏总","16","需要两个儿童座椅需要两个儿童座椅需要两个",false);
        ReserveOrderListEntity rol12 = new ReserveOrderListEntity("12","预约过期关闭","午餐",time,"15900008888","孙总","3","需要两个儿童座椅需要两个儿童座椅需要两个",false);
        reserveOrderListEntity.add(rol1);
        reserveOrderListEntity.add(rol2);
        reserveOrderListEntity.add(rol3);
        reserveOrderListEntity.add(rol4);
        reserveOrderListEntity.add(rol5);
        reserveOrderListEntity.add(rol6);
        reserveOrderListEntity.add(rol7);
        reserveOrderListEntity.add(rol8);
        reserveOrderListEntity.add(rol9);
        reserveOrderListEntity.add(rol10);
        reserveOrderListEntity.add(rol11);
        reserveOrderListEntity.add(rol12);
        return reserveOrderListEntity;
    }
}

//        lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 9; // 设置宽度
//        lp.height = ScreenUtil.getScreenHeight((Activity)mContext)/ 10 * 8; // 设置高度