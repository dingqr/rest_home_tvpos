package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.dish.DishListEntity;
import com.yonyou.hhtpos.global.DishConstants;

/**
 * 退增适配器
 * 作者：liushuofei on 2017/7/26 10:59
 */
public class ADA_SpecialHandleDish extends BaseAbsAdapter<DishListEntity.Dishes.Abnormal> {

    public ADA_SpecialHandleDish(Context context) {
        super(context);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_special_handle_dish, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final DishListEntity.Dishes.Abnormal bean = mDataSource.get(position);
        handleDataSource(bean, holder);

        return convertView;
    }

    private void handleDataSource(DishListEntity.Dishes.Abnormal bean, ViewHolder holder){
        if (null == bean)
            return;

        // 退增标识
        if (!TextUtils.isEmpty(bean.getDishAbnormalStatus())){
            if (bean.getDishAbnormalStatus().equals(DishConstants.RETURN_DISH)){
                holder.mSignIv.setImageResource(R.drawable.ic_dishes_retreat);
            }else {
                holder.mSignIv.setImageResource(R.drawable.ic_dishes_give);
            }
        }

        // 姓名
        if (!TextUtils.isEmpty(bean.getWaiterName())){
            holder.mWaiterName.setText(bean.getWaiterName());
        }

        // 时间
        holder.mCreateTime.setText(AppDateUtil.getTimeStamp(bean.getCreateTime(), AppDateUtil.HH_MM));

        // 数量
        if (!TextUtils.isEmpty(bean.getQuantity())){
            holder.mHandleCount.setText(mContext.getString(R.string.multiply) + (int)Double.parseDouble(bean.getQuantity()));
        }
    }

    static class ViewHolder {
        ImageView mSignIv;
        TextView mWaiterName;
        TextView mHandleCount;
        TextView mCreateTime;

        ViewHolder(View v) {
            mSignIv = (ImageView) v.findViewById(R.id.iv_sign);
            mWaiterName = (TextView) v.findViewById(R.id.tv_waiter_name);
            mHandleCount = (TextView) v.findViewById(R.id.tv_handle_count);
            mCreateTime = (TextView) v.findViewById(R.id.tv_create_time);
        }
    }
}
