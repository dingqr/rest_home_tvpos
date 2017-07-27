package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.wm.WMDishDetailEntity;

import static com.yonyou.framework.library.common.utils.StringUtil.getString;


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
            String headerTime = "";
            String tvHearder = "";
            if (dishDetailEntity.orderTime != 0) {
                headerTime = String.valueOf(AppDateUtil.getTimeStamp(dishDetailEntity.orderTime, AppDateUtil.HH_MM)) + mContext.getResources().getString(R.string.string_ordered_dishes);
            } else {
                headerTime = mContext.getResources().getString(R.string.string_unordered_dishes);
            }
            if (dishDetailEntity.totalCount != null) {
                tvHearder = "(" + dishDetailEntity.totalCount + mContext.getResources().getString(R.string.string_ordered_dish_num) + ")";
            }
            holder.setText(R.id.tv_header_time, headerTime + tvHearder);

            holder.setText(R.id.tv_dish_name, dishDetailEntity.dishName);
            holder.setText(R.id.tv_dish_price, "￥" + dishDetailEntity.getDishPrice());
            if (!TextUtils.isEmpty(dishDetailEntity.quantity)) {
                int quantity = (int) Double.parseDouble(dishDetailEntity.quantity);
                holder.setText(R.id.tv_quanlity, "x" + quantity);
            }
            if (!TextUtils.isEmpty(dishDetailEntity.listShowRemark)) {
                //备注
                holder.setText(R.id.tv_remarks, "" + dishDetailEntity.listShowRemark);
            }
            //新点的菜后台没有记录时间
        }
        if (dishDetailEntity.orderTime != null) {
            //根据菜品的提交订单时间，分组显示列表
            if (position == getFirstVisiblePosition(getString(dishDetailEntity.orderTime))) {
                holder.setVisible(R.id.tv_header_time, true);
            } else {
                holder.setVisible(R.id.tv_header_time, false);
            }
        }
    }

    /**
     * 根据订单统一提交的时间，找到分组中每组应显示标题的第一个position
     *
     * @param orderTime
     */
    private int getFirstVisiblePosition(String orderTime) {
        for (int i = 0; i < mDatas.size(); i++) {
            WMDishDetailEntity wmDishDetailEntity = mDatas.get(i);
            String order_time = getString(wmDishDetailEntity.orderTime);
            if (order_time.equals(orderTime)) {
                return i;
            }
        }
        return -1;
    }
}
