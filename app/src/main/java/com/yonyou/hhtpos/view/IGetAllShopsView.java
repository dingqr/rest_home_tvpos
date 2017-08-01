package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.StoreEntity;

import java.util.List;

/**
 * Created by ybing on 2017/7/31.
 * 获取所有门店
 */

public interface IGetAllShopsView extends BaseView {
    void getShops(List<StoreEntity> result);
}
