package com.yonyou.hhtpos.adapter;

import android.content.Context;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.DishDetailEntity;

/**
 * Created by zj on 2017/7/6.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
public class ADA_TakeOutOrderDetail extends CommonAdapterListView<DishDetailEntity> {
    public ADA_TakeOutOrderDetail(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_takeout_order_detail;
    }

    @Override
    protected void convert(ViewHolderListView viewHolder, DishDetailEntity item, int position) {

    }
}
