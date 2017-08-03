package com.yonyou.hhtpos.interfaces;

import com.yonyou.hhtpos.bean.RaxInvoiceCallBackEntity;

/**
 * Created by ybing on 2017/8/2.
 *
 * 增值税发票数据回调接口
 */


public interface RaxCallback {
    void sendItems(RaxInvoiceCallBackEntity bean);
}
