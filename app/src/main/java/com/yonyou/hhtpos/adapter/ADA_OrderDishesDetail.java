package com.yonyou.hhtpos.adapter;

import android.content.Context;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.wd.DishDetaiListlEntity;

/**
 * Created by zj on 2017/7/5.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带-分组列表-点菜明细的适配器
 */
public class ADA_OrderDishesDetail extends CommonAdapterListView<DishDetaiListlEntity> {
    public ADA_OrderDishesDetail(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_order_dishes;
    }

    @Override
    protected void convert(ViewHolderListView holder, DishDetaiListlEntity dishDetaiListlEntity, int position) {
        if (dishDetaiListlEntity != null) {
            //设置点菜时间
//            holder.setText(R.id.tv_header_time, );
            //设置份数
            holder.setText(R.id.tv_quanlity, dishDetaiListlEntity.quantity);

            holder.setText(R.id.tv_dish_name, dishDetaiListlEntity.dishName);
            //设置价格-原价
//                holder.setText(R.id.tv_price,);
            //设置优惠后的价格
//                holder.setText(R.id.tv_discount_price,);
            //设置规格
//            holder.setText(R.id.tv_standards, );
        }

//        //根据菜品的提交订单时间，分组显示列表
//        if (position == getFirstVisiblePosition(dishDetaiListlEntity.orderTime)) {
//            holder.setVisible(R.id.tv_header_time, true);
//        } else {
//            holder.setVisible(R.id.tv_header_time, false);
//        }
    }

    /**
     * 根据订单统一提交的时间，找到分组中每组应显示标题的第一个position
     *
     * @param order_time
     */
    private int getFirstVisiblePosition(String order_time) {
        for (int i = 0; i < mDatas.size(); i++) {
            DishDetaiListlEntity dishDetaiListlEntity = mDatas.get(i);
            if (order_time.equals(dishDetaiListlEntity.orderTime)) {
                return i;
            }
        }
        return -1;
    }
}
