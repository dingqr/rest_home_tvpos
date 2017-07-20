package com.yonyou.hhtpos.adapter;

import android.content.Context;

import com.yonyou.framework.library.adapter.rv.CommonAdapter;
import com.yonyou.framework.library.adapter.rv.ViewHolder;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.CanteenTableEntity;

/**
 * Created by zj on 2017/7/8.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食桌台列表适配器
 */
public class ADA_CanteenList extends CommonAdapter<CanteenTableEntity> {
    public ADA_CanteenList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_table_list;
    }

    @Override
    protected void convert(ViewHolder holder, CanteenTableEntity canteenTableEntity, int position) {
            holder.setText(R.id.tv_table_name,canteenTableEntity.tableName);

//        // item点击
//        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}
