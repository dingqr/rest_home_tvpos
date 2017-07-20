package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;

import java.util.List;


/**
 * Created by ybing on 2017/6/23.
 */

public class ADA_Filtration extends RecyclerView.Adapter<ADA_Filtration.ViewHolder>  {
    private LayoutInflater mInflater;
    private List<FilterOptionsEntity> mDatas;
    private FilterOptionsEntity currentBean;
    private OnItemClickListener mOnItemClickListener;

    private static final int DISH_TYPE = 0;
    private static final int DISH_AREA = 1;
    private static final int RESERVE_STATUS = 2;
    private static final int VIEW_TAKEOUT_TYPE = 3;
    private static final int REFUND_REASON = 4;
    private static final int ORDER_RESOURCE = 5;
    private static final int COOKERY = 6;
    private static final int DISH_NORMS = 7;

    public ADA_Filtration(Context mContext, List<FilterOptionsEntity> mDatas) {
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        currentBean = mDatas.get(0);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position){
        final FilterOptionsEntity dataBean = mDatas.get(position);
        return dataBean.getType();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case DISH_TYPE:{
                View view = mInflater.inflate(R.layout.item_filtration_ada_type1, viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.mBtn = (RadioButton) view.findViewById(R.id.btn_option);
                return viewHolder;
            }
            case DISH_AREA:{
                View view = mInflater.inflate(R.layout.item_filtration_ada_type1, viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.mBtn = (RadioButton) view.findViewById(R.id.btn_option);
                return viewHolder;
            }

            case RESERVE_STATUS: {
                View view = mInflater.inflate(R.layout.item_filtration_ada_type1, viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.mBtn = (RadioButton) view.findViewById(R.id.btn_option);
                viewHolder.mBtn.setPadding(3,10,3,10);
                return viewHolder;
            }
            case ORDER_RESOURCE: {
                View view = mInflater.inflate(R.layout.item_filtration_ada_type1, viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.mBtn = (RadioButton) view.findViewById(R.id.btn_option);
                return viewHolder;
            }
            case VIEW_TAKEOUT_TYPE: {
                View view = mInflater.inflate(R.layout.item_filtration_ada_type2, viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.mBtn = (RadioButton) view.findViewById(R.id.btn_option);
                return viewHolder;
            }
            case REFUND_REASON: {
                View view = mInflater.inflate(R.layout.item_filtration_refund_reason, viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.mBtn = (RadioButton) view.findViewById(R.id.btn_option);
                return viewHolder;
            }
            case COOKERY: {
                View view = mInflater.inflate(R.layout.item_filtration_cookery, viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.mBtn = (RadioButton) view.findViewById(R.id.btn_option);
                return viewHolder;
            }
            case DISH_NORMS: {
                View view = mInflater.inflate(R.layout.item_dish_norms, viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.mBtn = (RadioButton) view.findViewById(R.id.btn_option);
                return viewHolder;
            }
            default: break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final FilterOptionsEntity dataBean = mDatas.get(position);
        holder.mBtn.setText(dataBean.getOption());
        if (dataBean.isCheck()){
            holder.mBtn.setChecked(true);
        }else {
            holder.mBtn.setChecked(false);
        }

        holder.mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item点击回调
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                    dataBean.setCheck(true);
                }
                //实现单选功能
                if(null != dataBean && null != currentBean){
                    if (!dataBean.getOption().equals(currentBean.getOption())){
                        dataBean.setCheck(true);
                        currentBean.setCheck(false);
                        currentBean = dataBean;
                        notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * 点击事件的回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 更新数据
     * @param mDatas
     */
    public void update(List<FilterOptionsEntity> mDatas) {
        if (null != mDatas) {
            this.mDatas = mDatas;
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public RadioButton mBtn;
    }
}
