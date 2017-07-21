package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.wm.WMDishDetailEntity;

/**
 * Created by zj on 2017/7/6.
 * 邮箱：zjuan@yonyou.com
 * 描述：菜品明细列表-展示的实体类
 */
public class ADA_TakeOutOrderDetail extends CommonAdapterListView<WMDishDetailEntity> {
    public ADA_TakeOutOrderDetail(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_takeout_order_detail;
    }

    @Override
    protected void convert(ViewHolderListView holder, WMDishDetailEntity dishDetailEntity, int position) {
        //设置接口返回数据
        if (dishDetailEntity != null) {
            holder.setText(R.id.tv_header_time, String.valueOf(AppDateUtil.getTimeStamp(dishDetailEntity.orderTime, AppDateUtil.HH_MM)));
            holder.setText(R.id.tv_dish_name, dishDetailEntity.dishName);
            holder.setText(R.id.tv_dish_price, dishDetailEntity.getActualPrice());
            holder.setText(R.id.tv_quanlity, "x" + dishDetailEntity.quantity);
            if(!TextUtils.isEmpty(dishDetailEntity.remark)) {
                //备注
                holder.setText(R.id.tv_remarks,""+dishDetailEntity.remark);
            }
        }
    }
}
