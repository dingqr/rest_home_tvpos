package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.mine.CashTypeEntity;

import java.util.List;


/**
 * Created by ybing on 2017/8/8.
 * 设置收款类型
 */

public class ADA_ChooseCashType extends RecyclerView.Adapter<ADA_ChooseCashType.ViewHolder>{
    private LayoutInflater mInflater;
    private List<CashTypeEntity> mDatas;
    private CashTypeEntity currentBean;
    private OnCashTypeClickListener mOnItemClickListener ;

    public ADA_ChooseCashType(Context mContext, List<CashTypeEntity> mDatas) {
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
        View view = mInflater.inflate(R.layout.item_dia_cash_type, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.llCashType = (LinearLayout) view.findViewById(R.id.ll_cash_type);
        viewHolder.tvCashTypeName = (TextView) view.findViewById(R.id.tv_cash_type_name);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CashTypeEntity dataBean = mDatas.get(position);
        if (dataBean !=null){
            holder.tvCashTypeName.setText(dataBean.getCashTypeName());

            if (dataBean.isCheck()){
                currentBean = dataBean;
            }
            holder.llCashType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onCashTypeClick(holder.itemView,position);
                    }
                    //实现单选功能
                    if(null != dataBean && null != currentBean){
                        if (!dataBean.equals(currentBean)) {
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
//                holder.llSelectTableCapacity.setBackground(this.mInflater.getContext().getResources().getDrawable(R.drawable.bg_red_4));
                holder.llCashType.setBackground(this.mInflater.getContext().getResources().getDrawable(R.drawable.bg_dotted_b2b2b2_red_4));
                holder.tvCashTypeName.setTextColor(Color.parseColor("#eb6247"));
            } else {
//                holder.llSelectTableCapacity.setBackground(this.mInflater.getContext().getResources().getDrawable(R.drawable.bg_gray_4));
                holder.llCashType.setBackground(this.mInflater.getContext().getResources().getDrawable(R.drawable.bg_dotted_b2b2b2_4));
                holder.tvCashTypeName.setTextColor(Color.parseColor("#222222"));
            }
        }
    }



    /**
     * 点击事件的回调接口
     */
    public interface OnCashTypeClickListener {
        void onCashTypeClick(View view, int position);
    }

    public void setmOnItemClickListener(OnCashTypeClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 更新数据
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
        public LinearLayout llCashType;
        public TextView tvCashTypeName;
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