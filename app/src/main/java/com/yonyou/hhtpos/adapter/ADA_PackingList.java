package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.PackingListBean;

/**
 * 外带列表adapter
 * 作者：liushuofei on 2017/7/5 09:43
 */
public class ADA_PackingList extends BaseAbsAdapter<PackingListBean> {

    private PackingListBean currentBean;

    public ADA_PackingList(Context context) {
        super(context);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_packing_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final PackingListBean bean = mDataSource.get(position);
        handleDataSource(position, holder, bean);

        if (bean.isCheck()){
            currentBean = bean;
            holder.mRoot.setBackgroundResource(R.color.color_eaeaea);
            holder.mLeftLine.setVisibility(View.VISIBLE);
            holder.mNumber.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mPrice.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
        }else {
            holder.mRoot.setBackgroundResource(R.color.color_FFFFFF);
            holder.mLeftLine.setVisibility(View.INVISIBLE);
            holder.mNumber.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
            holder.mPrice.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
            holder.mStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_666666));
            holder.mTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
        }
        return convertView;
    }

    private void handleDataSource(int position, final ViewHolder holder, final PackingListBean bean) {
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

        if (null == bean)
            return;

        // 编号
        String billNo = bean.getBillNo();
        if (!TextUtils.isEmpty(billNo) && billNo.length() > 5){
            holder.mNumber.setText(mContext.getString(R.string.well_no) + billNo.substring(billNo.length() - 5, billNo.length()));
        }

        // 价格
        holder.mPrice.setText(mContext.getString(R.string.RMB_symbol) + bean.getBillMoney());

        // 支付状态
        // 时间
        if (bean.getPayStatus().equals("Y")){
            holder.mStatus.setText(mContext.getString(R.string.packing_status_checked_out));
            holder.mTime.setText(AppDateUtil.getTimeStamp(bean.getBillTime(), AppDateUtil.HH_MM));
        }else {
            holder.mStatus.setText(mContext.getString(R.string.packing_status_out_standing));
            holder.mTime.setText(AppDateUtil.getTimeStamp(bean.getOpentime(), AppDateUtil.HH_MM));
        }
    }

    static class ViewHolder {

        RelativeLayout mRoot;
        View mLeftLine;
        TextView mNumber;
        TextView mPrice;
        TextView mStatus;
        TextView mTime;

        ViewHolder(View v) {
            mRoot = (RelativeLayout) v.findViewById(R.id.rl_root);
            mLeftLine = (View) v.findViewById(R.id.v_left_line);
            mNumber = (TextView) v.findViewById(R.id.tv_number);
            mPrice = (TextView) v.findViewById(R.id.tv_price);
            mStatus = (TextView) v.findViewById(R.id.tv_status);
            mTime = (TextView) v.findViewById(R.id.tv_time);
        }
    }
}
