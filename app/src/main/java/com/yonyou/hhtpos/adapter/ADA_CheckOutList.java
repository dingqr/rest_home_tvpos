package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.check.CheckOrderListEntity;

import static com.yonyou.hhtpos.R.id.tv_dishes_single_price;

/**
 * 作者：liushuofei on 2017/7/15 14:46
 * 邮箱：lsf@yonyou.com
 */
public class ADA_CheckOutList extends BaseAbsAdapter<CheckOrderListEntity> {

    public ADA_CheckOutList(Context context) {
        super(context);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckOrderListEntity bean = mDataSource.get(position);
//        textview.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_check_out_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mHeaderView.setText(mContext.getResources().getString(R.string.string_ordered_dish) + mDataSource.size());
        //数量
        if (!TextUtils.isEmpty(bean.quantity)) {
            int quantity = (int) Double.parseDouble(bean.quantity);
            holder.tvQuantity.setText("x" + quantity);
        }
        //价格
        if (!TextUtils.isEmpty(bean.getDishPrice())) {
            holder.tvDishPrice.setText(mContext.getResources().getString(R.string.RMB_symbol) + bean.getDishPrice());
        }
        //名称
        if (!TextUtils.isEmpty(bean.dishName)) {
            holder.tvDishName.setText(bean.dishName);
        }
        //时间
        if (bean.orderTime != null) {
            holder.tvOrderTime.setText(String.valueOf(AppDateUtil.getTimeStamp(bean.orderTime, AppDateUtil.HH_MM)) + mContext.getResources().getString(R.string.string_note_order_dish));
        }

        if (position == 0) {
            holder.mHeaderView.setVisibility(View.VISIBLE);
        } else {
            holder.mHeaderView.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {

        TextView mHeaderView;
        TextView tvOrderTime;
        TextView tvDishName;
        TextView tvQuantity;
        TextView tvDishPrice;

        ViewHolder(View v) {
            mHeaderView = (TextView) v.findViewById(R.id.tv_dishes_ordered);
            tvOrderTime = (TextView) v.findViewById(R.id.tv_dishes_time);
            tvDishName = (TextView) v.findViewById(R.id.tv_dishes_name);
            tvQuantity = (TextView) v.findViewById(R.id.tv_quantity);
            tvDishPrice = (TextView) v.findViewById(tv_dishes_single_price);
        }
    }
}
