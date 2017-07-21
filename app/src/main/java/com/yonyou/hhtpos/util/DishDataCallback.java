package com.yonyou.hhtpos.util;

import com.yonyou.hhtpos.bean.dish.DishCallBackEntity;

/**
 * Created by ybing on 2017/7/21.
 *
 * 点菜对话框 获取菜品数据后传递数据用的接口
 */


public interface DishDataCallback {
    void sendItems(DishCallBackEntity bean);
}
