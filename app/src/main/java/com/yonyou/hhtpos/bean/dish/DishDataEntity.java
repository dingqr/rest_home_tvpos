package com.yonyou.hhtpos.bean.dish;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖-菜品实体
 */
public class DishDataEntity implements Serializable {
    List<DishTypesEntity> dishTypesList;

    public DishDataEntity() {
    }

    @Override
    public String toString() {
        return "DishDataEntity{" +
                "dishTypesList=" + dishTypesList +
                '}';
    }
}

