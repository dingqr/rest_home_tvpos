package com.yonyou.hhtpos.presenter;

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
    void updateQuantity(String companyId, String id, String quantity, String shopId);

    /**
     * 修改菜品状态接口
     * @param companyId
     * @param dishStatus 状态（催菜：6，等叫：7，叫起：8）
     * @param id 点菜菜单id
     * @param shopId
     */
    void updateDishStatus(String companyId, String dishStatus, String id, String shopId);

    /**
     * 删除菜品接口
     * @param companyId
     * @param id 菜品id
     * @param shopId
     */
    void deleteDish(String companyId, String id, String shopId);

    /**
     * 退菜和赠菜的接口
     * @param dishAbnormalStatus 异常状态
     * @param id 点菜菜单id
     * @param shopId
     * @param count 数量
     */
    void specialHandleDish(String dishAbnormalStatus, String id, String shopId, String count);
}
