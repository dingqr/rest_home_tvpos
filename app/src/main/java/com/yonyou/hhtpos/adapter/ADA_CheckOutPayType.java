package com.yonyou.hhtpos.adapter;

import android.content.Context;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;

/**
 * 服务员结账付款方式adapter
 * 作者：liushuofei on 2017/7/20 09:23
 */
public class ADA_CheckOutPayType extends CommonAdapterListView<String> {

    public ADA_CheckOutPayType(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_check_out_pay_type;
    }

    @Override
    protected void convert(ViewHolderListView holder, String item, int position) {
        holder.setText(R.id.tv_pay_type_name,item);
    }
}
