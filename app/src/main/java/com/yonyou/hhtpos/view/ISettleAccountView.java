package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.check.SettleAccountDataEntity;

/**
 * Created by zj on 2017/8/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：二次结账
 */
public interface ISettleAccountView extends BaseView {
    void settleAccount(SettleAccountDataEntity settleAccountDataEntity);
}
