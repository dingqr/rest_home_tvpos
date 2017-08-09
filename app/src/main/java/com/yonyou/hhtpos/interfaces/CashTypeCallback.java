package com.yonyou.hhtpos.interfaces;

import com.yonyou.hhtpos.bean.mine.CashTypeEntity;

/**
 * Created by ybing on 2017/8/9.
 * 添加现金类型对话框 获取现金后传递数据用的接口
 */

public interface CashTypeCallback {
    void sendCashTypeItem (CashTypeEntity bean);
}
