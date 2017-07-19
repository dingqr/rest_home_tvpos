package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.wm.OrderListEntity;

/**
 * 作者：liushuofei on 2017/7/6 15:13
 * 邮箱：lsf@yonyou.com
 */
public class ADA_TakeOutList extends BaseAbsAdapter<OrderListEntity> {

    private OrderListEntity currentBean;

    public ADA_TakeOutList(Context context) {
        super(context);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_take_out_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final OrderListEntity bean = mDataSource.get(position);
        handleDataSource(position, holder, bean);

        if (bean.isCheck()){
            currentBean = bean;
            holder.mRoot.setBackgroundResource(R.color.color_eaeaea);
            holder.mLeftLine.setVisibility(View.VISIBLE);
            holder.mOrderNumber.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mChannelName.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mCustomerName.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mOrderTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mOrderPrice.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mCustomerPhone.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
        }else {
            holder.mRoot.setBackgroundResource(R.color.color_FFFFFF);
            holder.mLeftLine.setVisibility(View.INVISIBLE);
            holder.mOrderNumber.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
            holder.mChannelName.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
            holder.mCustomerName.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
            holder.mOrderTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_666666));
            holder.mOrderPrice.setTextColor(ContextCompat.getColor(mContext, R.color.color_666666));
            holder.mCustomerPhone.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
            holder.mOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
        }

        return convertView;
    }

    private void handleDataSource(int position, final ViewHolder holder, final OrderListEntity bean) {
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bean.equals(currentBean)){
                    bean.setCheck(true);
                    if (null != currentBean){
                        currentBean.setCheck(false);
                    }
                    currentBean = bean;
                    notifyDataSetChanged();
                }
            }
        });

        position++;
        //TODO: 单号都显示为#00001的格式，从后台获取数据并显示后五位
        holder.mOrderNumber.setText("#0000" + position);

//        // 编号
//        String billNo = bean.getBillNo();
//        if (!TextUtils.isEmpty(billNo) && billNo.length() > 5){
//            holder.mNumber.setText(mContext.getString(R.string.well_no) + billNo.substring(billNo.length() - 5, billNo.length()));
//        }

        if (null == bean)
            return;

        // 外卖渠道
        holder.mChannelName.setText(StringUtil.getString(bean.getTakeOutCompanyId()));
        // 下单时间
        holder.mOrderTime.setText(StringUtil.getString(bean.getCreateTime()));
        // 金额
        holder.mOrderPrice.setText(mContext.getString(R.string.RMB_symbol) + StringUtil.getString(bean.getBillMoney()));
        // 用户姓名
        holder.mCustomerName.setText(StringUtil.getString(bean.getName()));
        // 用户手机号
        holder.mCustomerPhone.setText(StringUtil.getString(bean.getPhone()));
        // 订单状态
        holder.mOrderStatus.setText(StringUtil.getString(bean.getDinnerState()));
    }

    static class ViewHolder {
        LinearLayout mRoot;
        View mLeftLine;
        TextView mOrderNumber;
        ImageView mChannelImg;
        TextView mChannelName;
        TextView mCustomerName;
        TextView mOrderTime;
        TextView mOrderPrice;
        TextView mCustomerPhone;
        TextView mOrderStatus;

        ViewHolder(View v) {
            mRoot = (LinearLayout) v.findViewById(R.id.ll_root);
            mLeftLine = (View) v.findViewById(R.id.v_left_line);
            mOrderNumber = (TextView) v.findViewById(R.id.tv_order_number);
            mChannelImg = (ImageView) v.findViewById(R.id.iv_channel);
            mChannelName = (TextView) v.findViewById(R.id.tv_channel);
            mCustomerName = (TextView) v.findViewById(R.id.tv_customer_name);
            mOrderTime = (TextView) v.findViewById(R.id.tv_order_time);
            mOrderPrice = (TextView) v.findViewById(R.id.tv_order_price);
            mCustomerPhone = (TextView) v.findViewById(R.id.tv_customer_phone);
            mOrderStatus = (TextView) v.findViewById(R.id.tv_order_status);
        }
    }
}
