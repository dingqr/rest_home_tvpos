package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.TakeoutCompanyEntity;

import java.util.List;

/**
 * Created by ybing on 2017/7/5.
 * 获取外卖筛选框中的外卖公司
 */

public interface ITakeoutCompanyView extends BaseView{
    void getAllTakeOutCompany(List<TakeoutCompanyEntity> list);
}
