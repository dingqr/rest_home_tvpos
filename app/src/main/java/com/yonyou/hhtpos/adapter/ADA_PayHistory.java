package com.yonyou.hhtpos.adapter;

import android.content.Context;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.check.SettleAccountDataEntity;

/**
 * Created by zj on 2017/8/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：结账页面-支付记录
 */
public class ADA_PayHistory extends CommonAdapterListView<SettleAccountDataEntity.PaidHistory> {
    public ADA_PayHistory(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_settle_account_pay_type;
    }

    @Override
    protected void convert(ViewHolderListView holder, SettleAccountDataEntity.PaidHistory bean, int position) {
        if (bean != null) {
            holder.setText(R.id.tv_pay_name, bean.getPayType());
            holder.setText(R.id.tv_pay_money, mContext.getResources().getString(R.string.RMB_symbol) + bean.getPayAmount());
        }
    }
}
