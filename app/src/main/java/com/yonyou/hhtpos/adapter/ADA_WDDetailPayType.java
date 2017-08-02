package com.yonyou.hhtpos.adapter;

import android.content.Context;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.application.MyApplication;
import com.yonyou.hhtpos.bean.PayTypeEntity;

/**
 * Created by zj on 2017/7/20.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带详情-支付方式Adapter
 */
public class ADA_WDDetailPayType extends CommonAdapterListView<PayTypeEntity> {
    public ADA_WDDetailPayType(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_wd_pay_type;
    }

    @Override
    protected void convert(ViewHolderListView holder, PayTypeEntity payTypeEntity, int position) {
        holder.setText(R.id.tv_pay_type_name, getPayTypeRemark(payTypeEntity.payType));
        holder.setText(R.id.tv_pay_money, mContext.getResources().getString(R.string.RMB_symbol) + StringUtil.getFormattedMoney(payTypeEntity.getPayAmount()));
    }

    /**
     * 转换支付方式名称
     *
     * @param payType
     * @return
     */
    public String getPayTypeRemark(String payType) {
        if (payType.equals("cash")) {
            return MyApplication.getInstance().getResources().getString(R.string.string_pay_by_cash);
        }
        return "";
    }
}
