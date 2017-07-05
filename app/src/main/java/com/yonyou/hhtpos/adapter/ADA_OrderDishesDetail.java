package com.yonyou.hhtpos.adapter;

import android.content.Context;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.OrderDishesEntity;

/**
 * Created by zj on 2017/7/5.
 * 邮箱：zjuan@yonyou.com
 * 描述：分组列表-点菜明细的适配器
 */
public class ADA_OrderDishesDetail extends CommonAdapterListView<OrderDishesEntity> {
    public ADA_OrderDishesDetail(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_order_dishes;
    }

    @Override
    protected void convert(ViewHolderListView holder, OrderDishesEntity orderDishesEntity, int position) {




        //根据菜品的提交订单时间，分组显示列表
//        if (position == getFirstVisiblePosition(orderDishesEntity.order_time)) {
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
            OrderDishesEntity orderDishesEntity = mDatas.get(i);
            if (order_time.equals(orderDishesEntity.order_time)) {
                return i;
            }
        }
        return -1;
    }
}
