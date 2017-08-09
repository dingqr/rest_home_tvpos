package com.yonyou.hhtpos.presenter;

import com.yonyou.hhtpos.bean.dish.RequestEditDishEntity;

/**
 * 作者：liushuofei on 2017/7/22 11:53
 * 邮箱：lsf@yonyou.com
 */
public interface IDishEditPresenter {
    /**
     * 修改数量接口
     * @param companyId
     * @param id 点菜菜品id
     * @param quantity 	菜品数量
     * @param shopId
     */
    void updateQuantity(String companyId, String id, String quantity, String shopId, String unit);

    /**
     * 修改菜品状态接口
     * @param companyId
     * @param dishStatus 状态（催菜：6，等叫：7，叫起：8）
     * @param id 点菜菜单id
     * @param shopId
     * @param saleManner 堂食：1  外带：2  外卖：3
     */
    void updateDishStatus(String companyId, String dishStatus, String id, String shopId, String saleManner);

    /**
     * 删除菜品接口
     * @param companyId
     * @param id 菜品id
     * @param shopId
     */
    void deleteDish(String companyId, String id, String shopId);

    /**
     * 退菜和赠菜的接口
     * @param dishStatus 菜品状态
     * @param dishAbnormalStatus 异常状态
     * @param id 点菜菜单id
     * @param shopId
     * @param count 数量
     * @param saleManner 堂食：1  外带：2  外卖：3
     */
    void specialHandleDish(String dishStatus, String dishAbnormalStatus, String id, String shopId, String count, String saleManner);

    /**
     * 称重确认接口
     * @param id 点菜菜品id
     * @param quantity 重量
     * @param shopId
     */
    void confirmWeightDish(String id, String quantity, String shopId);

    /**
     * 转台接口
     * @param id 菜品明细表id
     * @param count 转台的数量
     * @param shopId 商户Id
     * @param tableBillId 桌台账单Id
     */
    void switchTable(String id, String 	count, String shopId, String tableBillId);

    /**
     * 取消赠菜接口
     * @param id 菜品明细id
     * @param shopId 门店id
     * @param waiterId 服务员id
     */
    void cancelGiftDish(String id, String shopId, String waiterId);

    /**
     * 修改菜品接口
     * @param bean 修改菜品实体类
     */
    void updateDish(RequestEditDishEntity bean);
}
