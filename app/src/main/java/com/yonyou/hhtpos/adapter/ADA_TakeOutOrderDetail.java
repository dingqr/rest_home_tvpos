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

    private int start;
    private Long mCurrentTime;

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
            holder.setText(R.id.tv_dish_price, "￥" + dishDetailEntity.getDishPrice());
            holder.setText(R.id.tv_quanlity, "x" + dishDetailEntity.quantity);
            if (!TextUtils.isEmpty(dishDetailEntity.remark)) {
                //备注
                holder.setText(R.id.tv_remarks, "" + dishDetailEntity.remark);
            }
            //新点的菜后台没有记录时间
        }
        //根据菜品的提交订单时间，分组显示列表
        if (position == getFirstVisiblePosition(String.valueOf(AppDateUtil.getTimeStamp(dishDetailEntity.orderTime, AppDateUtil.HH_MM)))) {
            holder.setVisible(R.id.tv_header_time, true);
        } else {
            holder.setVisible(R.id.tv_header_time, false);
        }
        //根据是否是同一个时间，算出同一时间下的订单数量
        countGroupOrderNum(holder, dishDetailEntity, position);
    }

    /**
     * 计算每个分组订单的数量
     *
     * @param holder
     * @param dishDetailEntity
     * @param position
     */
    private void countGroupOrderNum(ViewHolderListView holder, WMDishDetailEntity dishDetailEntity, int position) {
        mCurrentTime = dishDetailEntity.orderTime;
        int k = 0;
        if (k == 0) {
            for (int i = 0 + start; i < mDatas.size(); i++) {
                Long orderTime = mDatas.get(i).orderTime;
                if (mCurrentTime != orderTime) {
                    holder.setText(R.id.tv_header_time, "(" + (i - position) + ")");
                    start = i;
                    k = i - position;
                    break;
                }
            }
        }
        k--;
    }

    /**
     * 根据订单统一提交的时间，找到分组中每组应显示标题的第一个position
     *
     * @param orderTime
     */
    private int getFirstVisiblePosition(String orderTime) {
        for (int i = 0; i < mDatas.size(); i++) {
            WMDishDetailEntity wmDishDetailEntity = mDatas.get(i);
            String order_time = String.valueOf(AppDateUtil.getTimeStamp(wmDishDetailEntity.orderTime, AppDateUtil.HH_MM));
            if (order_time.equals(orderTime)) {
                return i;
            }
        }
        return -1;
    }
}
