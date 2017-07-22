package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.TakeoutCompanyEntity;
import com.yonyou.hhtpos.bean.wm.RefundReasonEntity;

import java.util.List;

/**
 * Created by ybing on 2017/7/22.
 * 获取外卖退款原因筛选框中的退款原因选项
 */

public interface IWMRefundReasonView extends BaseView{
    void getWMRefundReason(List<RefundReasonEntity> reasonList);
}
