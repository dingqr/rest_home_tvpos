package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.ReserveOrderListEntity;

import java.util.List;

/**
 * Created by ybing on 2017/7/1.
 * 待确认订单左侧列表数据适配器
 */

public class ADA_TobeConfirmOrder extends RecyclerView.Adapter<ADA_TobeConfirmOrder.ViewHolder>{
    private LayoutInflater mInflater;
    private List<ReserveOrderListEntity> mDatas;
    private ReserveOrderListEntity currentBean;
    private ADA_SelectTableArea.OnItemClickListener mOnItemClickListener;

    public ADA_TobeConfirmOrder(Context mContext) {
        mInflater = LayoutInflater.from(mContext);
    }
    public ADA_TobeConfirmOrder(Context mContext, List<ReserveOrderListEntity> mDatas) {
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
        View view = mInflater.inflate(R.layout.item_tobe_confirm_order, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.rlWholeContent = (LinearLayout)view.findViewById(R.id.rl_whole_content);
        viewHolder.tvOrderId = (TextView) view.findViewById(R.id.tv_order_id);
        viewHolder.tvOrderTime = (TextView) view.findViewById(R.id.tv_order_time);
        viewHolder.icArrive = (ImageView) view.findViewById(R.id.ic_arrive);
        viewHolder.tvCustomerPhone = (TextView) view.findViewById(R.id.tv_user_phone);
        viewHolder.tvCustomerNumber = (TextView) view.findViewById(R.id.tv_customer_num);
        viewHolder.tvOrderRemark = (TextView) view.findViewById(R.id.tv_order_remark);
        viewHolder.viChecked = view.findViewById(R.id.vi_checked);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ReserveOrderListEntity dataBean = mDatas.get(position);
        if (dataBean !=null) {
            if (!TextUtils.isEmpty(dataBean.getOrderId())){
                holder.tvOrderId.setText(dataBean.getOrderId()+"#");
            }
            if (dataBean.getOrderTime()!= -1){
                holder.tvOrderTime.setText( StringUtil.getString(AppDateUtil.getTimeStamp(dataBean.getOrderTime(), AppDateUtil.YYYY_MM_DD_HH_MM)));
            }
            if (!TextUtils.isEmpty(dataBean.getCustomerPhone())){
                holder.tvCustomerPhone.setText(dataBean.getCustomerPhone().substring(0,3)+"****"+dataBean.getCustomerPhone().substring(7,11));
            }
            if (!TextUtils.isEmpty(dataBean.getOrderRemark())){
                holder.tvOrderRemark.setText(dataBean.getOrderRemark());
            }
            if (!TextUtils.isEmpty(dataBean.getDiningNumber())){
                holder.tvCustomerNumber.setText(dataBean.getDiningNumber()+ mInflater.getContext().getResources().getString(R.string.man));
            }
            if (dataBean.isCheck()) {
                currentBean = dataBean;
            }
        }
        holder.rlWholeContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item点击回调
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
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
        });
        //设置选中效果
        if (dataBean.isCheck()) {
            holder.rlWholeContent.setBackgroundColor(Color.parseColor("#eaeaea"));
            holder.tvCustomerNumber.setTextColor(Color.parseColor("#eb6427"));
            holder.tvCustomerPhone.setTextColor(Color.parseColor("#eb6427"));
            holder.tvOrderRemark.setTextColor(Color.parseColor("#eb6427"));
            holder.tvOrderId.setTextColor(Color.parseColor("#eb6427"));
            holder.tvOrderTime.setTextColor(Color.parseColor("#eb6427"));
            holder.viChecked.setBackgroundColor(Color.parseColor("#eb6427"));
        } else {
            holder.rlWholeContent.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.tvCustomerNumber.setTextColor(Color.parseColor("#666666"));
            holder.tvCustomerPhone.setTextColor(Color.parseColor("#999999"));
            holder.tvOrderRemark.setTextColor(Color.parseColor("#999999"));
            holder.tvOrderId.setTextColor(Color.parseColor("#222222"));
            holder.tvOrderTime.setTextColor(Color.parseColor("#222222"));
            holder.viChecked.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    /**
     * 点击事件的回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setmOnItemClickListener(ADA_SelectTableArea.OnItemClickListener mOnItemClickListener) {
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
        public LinearLayout rlWholeContent;
        public View viChecked;
        public TextView tvOrderId;
        public TextView tvOrderTime;
        public ImageView icArrive;
        public TextView tvCustomerPhone;
        public TextView tvCustomerNumber;
        public TextView tvOrderRemark;
    }
}
