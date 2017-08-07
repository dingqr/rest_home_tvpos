package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.mine.CashTypeEntity;

import java.util.List;

/**
 * 现金票据类型adapter
 * 作者：ybing on 2017/8/5 19:21
 */
public class ADA_CashType extends RecyclerView.Adapter<ADA_CashType.ViewHolder>  {
    private LayoutInflater mInflater;
    private List<CashTypeEntity> mDatas;

    public ADA_CashType(Context mContext, List< CashTypeEntity> mDatas) {
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.item_cash_type, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvcCashyType = (TextView) view.findViewById(R.id.tv_cash_type);
        viewHolder.tvAmount = (TextView) view.findViewById(R.id.tv_amount);
        viewHolder.ivSplitLine = view.findViewById(R.id.iv_split_line);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CashTypeEntity dataBean = mDatas.get(position);
        if (dataBean != null) {
            holder.tvcCashyType.setText(StringUtil.getString(dataBean.getCashTypeName()));
            holder.tvAmount.setText(mInflater.getContext().getString(R.string.RMB_symbol)+
            StringUtil.getString(dataBean.getCashAmount()));
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
    public void update(List<CashTypeEntity> mDatas) {
        if (null != mDatas) {
            this.mDatas = mDatas;
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public TextView tvcCashyType;
        public TextView tvAmount;
        public View ivSplitLine;

    }
}
