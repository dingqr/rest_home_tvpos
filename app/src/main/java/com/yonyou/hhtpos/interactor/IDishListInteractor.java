package com.yonyou.hhtpos.interactor;

/**
 * 作者：liushuofei on 2017/7/17 19:30
 * 邮箱：lsf@yonyou.com
 */
public interface IDishListInteractor {
    /**
     * 请求已点菜品列表
     * @param billId 桌台账单id
     */
    void requestDishList(String billId);

    /**
     * 请求下单接口
     * @param dishIds 未下单菜品id
     */
    void requestPlaceOrder(String dishIds,String tableBillId,String saleManner);

    /**
     * 删除未下单菜品接口
     * @param shopId 店铺id
     * @param tableBillId 账单id
     */
    void deleteNoOrderDish(String shopId, String tableBillId);
}
