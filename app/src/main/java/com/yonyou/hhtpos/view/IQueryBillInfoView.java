package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.check.SettleAccountDataEntity;

/**
 * Created by zj on 2017/7/28.
 * 邮箱：zjuan@yonyou.com
 * 描述：获取结账信息页数据
 */
public interface IQueryBillInfoView extends BaseView {
    void queryBillInfo(SettleAccountDataEntity settleAccountDataEntity);
}
