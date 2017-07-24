package com.yonyou.hhtpos.interfaces;

import com.yonyou.hhtpos.bean.dish.WMRefundFreeReasonCallbackEntity;

/**
 * Created by ybing on 2017/7/21.
 *
 * 退款&免单对话框 获取退款原因、免单原因数据后传递数据用的接口
 */


public interface WMReasonsCallback {
    void sendItems(WMRefundFreeReasonCallbackEntity bean);
}
