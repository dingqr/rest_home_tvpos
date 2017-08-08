package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.check.PayTypeEntity;

import java.util.List;

/**
 * 作者：liushuofei on 2017/8/8 14:51
 * 邮箱：lsf@yonyou.com
 */
public interface IPayTypeView extends BaseView {

    /**
     * 请求支付方式列表
     */
    void requestPayTypeList(List<PayTypeEntity> dataList);
}
