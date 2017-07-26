package com.yonyou.hhtpos.interfaces;

import com.yonyou.hhtpos.bean.ts.OpenOrderEntity;

/**
 * Created by ybing on 2017/7/25.
 * 堂食模块 预订单开单回调、空胎开单回调
 */

public interface OpenOrderCallback {
    void sendTsEntity(OpenOrderEntity tsOpenOrderEntity);
}
