package com.yonyou.hhtpos.bean.dish;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2017/7/21.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
public class DataBean implements Serializable {
    List<DishPriceEntity> practices = new ArrayList<>();
    List<DishRemarkEntity> remarks = new ArrayList<>();
    List<DishStandardEntity> standards = new ArrayList<>();
}
