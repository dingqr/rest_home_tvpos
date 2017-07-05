package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;

import java.util.List;


/**
 * Created by ybing on 2017/6/29.
 * 设置桌台区域的数据适配器
 */

public class ADA_SelectTableArea extends RecyclerView.Adapter<ADA_SelectTableArea.ViewHolder>  {
    private LayoutInflater mInflater;
    private List<FilterOptionsEntity> mDatas;
    private FilterOptionsEntity currentBean;
    private OnItemClickListener mOnItemClickListener;

    public ADA_SelectTableArea(Context mContext, List<FilterOptionsEntity> mDatas) {
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
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
        View view = mInflater.inflate(R.layout.item_table_area, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mBtn = (RadioButton) view.findViewById(R.id.btn_option);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final FilterOptionsEntity dataBean = mDatas.get(position);
        if (dataBean !=null) {
            holder.mBtn.setText(dataBean.getOption());
            if (dataBean.isCheck()) {
                holder.mBtn.setChecked(true);
                currentBean = dataBean;
            } else {
                holder.mBtn.setChecked(false);
            }
        }
        holder.mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item点击回调
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
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
        //设置选中效果
        if (dataBean.isCheck()) {
            holder.mBtn.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.mBtn.setTextColor(Color.parseColor("#222222"));
        }
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
