package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.wm.RefundReasonEntity;

import java.util.List;

/**
 * Created by ybing on 2017/8/8.
 * 获取所有退款原因
 */

public interface IGetRefundReasonsView extends BaseView {
    void getRefundReasons(List<RefundReasonEntity> result);
}
