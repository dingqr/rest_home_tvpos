package com.yonyou.hhtpos.adapter;

import android.content.Context;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.check.DiscountEntity;

/**
 * 服务员结账优惠方式adapter
 * 作者：liushuofei on 2017/7/20 09:21
 */
public class ADA_DiscountType extends CommonAdapterListView<DiscountEntity> {
    public ADA_DiscountType(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_discount_type;
    }

    @Override
    protected void convert(ViewHolderListView holder, DiscountEntity discountEntity, int position) {
        holder.setText(R.id.tv_discount_name,discountEntity.discountName);
        holder.setImageResource(R.id.iv_icon,discountEntity.icon);
    }
}
