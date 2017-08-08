package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.framework.library.common.utils.AppUtil;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.mine.CashTypeEntity;
import com.yonyou.hhtpos.bean.mine.DailyAccountEntity;

import java.util.List;

/**
 * 每日结算adapter
 * 作者：ybing on 2017/8/5 19:21
 */
public class ADA_DailySettleAccount extends RecyclerView.Adapter<ADA_DailySettleAccount.ViewHolder>  {
    private LayoutInflater mInflater;
    private List<DailyAccountEntity> mDatas;

    public ADA_DailySettleAccount(Context mContext, List< DailyAccountEntity> mDatas) {
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.item_daily_account, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvSettleTime = (TextView) view.findViewById(R.id.tv_settle_account_time);
        viewHolder.tvSettleOption = (TextView) view.findViewById(R.id.tv_settle_account_option);
        viewHolder.ivSplitLine = view.findViewById(R.id.iv_split_line);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DailyAccountEntity dataBean = mDatas.get(position);
        if (dataBean != null) {
            holder.tvSettleTime.setText(StringUtil.getString(dataBean.getSettleTime()));
            holder.tvSettleOption.setText(StringUtil.getString(dataBean.getSettleOption()));
        }
        if (position == mDatas.size()-1){
            holder.ivSplitLine.setVisibility(View.GONE);
        }else{
            holder.ivSplitLine.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 更新数据
     *
     * @param mDatas
     */
    public void update(List<DailyAccountEntity> mDatas) {
        if (null != mDatas) {
            this.mDatas = mDatas;
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public TextView tvSettleTime;
        public TextView tvSettleOption;
        public View ivSplitLine;

    }
}
