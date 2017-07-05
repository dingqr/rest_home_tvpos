package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.ReserveOrderListEntity;
import com.yonyou.hhtpos.bean.TableReserveEntity;

import java.util.List;


/**
 * Created by ybing on 2017/7/3.
 * 桌台预定总览 数据适配器
 */

public class ADA_ReserveOrderList extends RecyclerView.Adapter<ADA_ReserveOrderList.ViewHolder>{
    private LayoutInflater mInflater;
    private List<ReserveOrderListEntity> mDatas;
    private TableReserveEntity currentBean;
    private OnTableClickListener mOnItemClickListener ;

    public ADA_ReserveOrderList(Context mContext, List<ReserveOrderListEntity> mDatas) {
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.item_reserve_order, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.llOrderContent = (LinearLayout) view.findViewById(R.id.ll_order_content);
        viewHolder.tvOrderReserveStatus = (TextView)view.findViewById(R.id.tv_order_reserve_status);
        viewHolder.tvDishType = (TextView) view.findViewById(R.id.tv_dish_type);
        viewHolder.tvOrderDate = (TextView) view.findViewById(R.id.tv_order_date);
        viewHolder.tvOrderTime = (TextView) view.findViewById(R.id.tv_order_time);
        viewHolder.tvOrderPhone = (TextView) view.findViewById(R.id.tv_order_phone);
        viewHolder.tvCustomerHonorific = (TextView) view.findViewById(R.id.tv_customer_honorific);
        viewHolder.tvCustomerNumber = (TextView) view.findViewById(R.id.tv_customer_number);
        viewHolder.tvReserveRemark = (TextView) view.findViewById(R.id.tv_reserve_remark);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ReserveOrderListEntity dataBean = mDatas.get(position);
        if (dataBean !=null){
            holder.tvOrderReserveStatus.setText(dataBean.getOrderStatus());
            holder.tvDishType.setText(dataBean.getDishType());
            String dateTime = AppDateUtil.getTimeStamp(dataBean.getOrderTime(), AppDateUtil.YYYY_MM_DD_HH_MM);
            holder.tvOrderDate.setText(dateTime);
            holder.tvOrderTime.setText(dateTime);
            holder.tvOrderPhone.setText(dataBean.getCustomerPhone());
            holder.tvCustomerHonorific.setText(dataBean.getCustomerHonorific());
            holder.tvCustomerNumber.setText(dataBean.getCustomerPhone());
            holder.tvReserveRemark.setText(dataBean.getOrderRemark());

            if (dataBean.getOrderStatus() == "客人已到达" || dataBean.getOrderStatus().equals("客人已到达")){
                holder.llOrderContent.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.tvOrderReserveStatus.setTextColor(Color.parseColor("#222222"));
            }
            if (dataBean.getOrderStatus() == "等待客人中" || dataBean.getOrderStatus().equals("等待客人中")){
                holder.llOrderContent.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.tvOrderReserveStatus.setTextColor(Color.parseColor("#222222"));
            }
            if (dataBean.getOrderStatus() == "客人已取消" || dataBean.getOrderStatus().equals("客人已取消")){
                holder.llOrderContent.setBackgroundColor(Color.parseColor("#fef2f0"));
                holder.tvOrderReserveStatus.setTextColor(Color.parseColor("#999999"));
            }
            if (dataBean.getOrderStatus() == "预约过期关闭" || dataBean.getOrderStatus().equals("预约过期关闭")){
                holder.llOrderContent.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.tvOrderReserveStatus.setTextColor(Color.parseColor("#999999"));
            }
        }
    }



    /**
     * 点击事件的回调接口
     */
    public interface OnTableClickListener {
        void onTableClick(View view, int position);
    }

    public void setmOnItemClickListener(OnTableClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 更新数据
     * @param mDatas
     */
    public void update(List<ReserveOrderListEntity> mDatas) {
        if (null != mDatas) {
            this.mDatas = mDatas;
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public LinearLayout llOrderContent;
        public TextView tvOrderReserveStatus;
        public TextView tvDishType;
        public TextView tvOrderDate;
        public TextView tvOrderTime;
        public TextView tvOrderPhone;
        public TextView tvCustomerHonorific;
        public TextView tvCustomerNumber;
        public TextView tvReserveRemark;
    }
}
/** holder.llSelectTableCapacity.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
//item点击回调
if (mOnItemClickListener != null) {
mOnItemClickListener.onCapacityClick(holder.itemView,position);
}
//实现单选功能
if(null != dataBean && null != currentBean){
if (!dataBean.equals(currentBean)){
dataBean.setCheck(true);
currentBean.setCheck(false);
currentBean = dataBean;
notifyDataSetChanged();
}
}
}
});*/