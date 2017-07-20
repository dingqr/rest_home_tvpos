package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ybing on 2017/7/7.
 *
 * 多项选择数据适配器
 */

public class ADA_MultipleSelector extends RecyclerView.Adapter<ADA_MultipleSelector.ViewHolder>  {

    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();


    private LayoutInflater mInflater;
    private List<FilterOptionsEntity> mDatas;
    private OnItemClickListener mOnItemClickListener;

    private static final int TAKE_OUT_TYPE = 0;
    private static final int MARKET_TYPE = 1;
    private static final int DISH_REMARK = 2;
    public ADA_MultipleSelector(Context mContext, List<FilterOptionsEntity> mDatas) {
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
        switch (viewType){
            case TAKE_OUT_TYPE:{
                View view = mInflater.inflate(R.layout.item_multiple_select, viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.mBtn = (CheckBox) view.findViewById(R.id.btn_option);
                return viewHolder;
            }
            case MARKET_TYPE:{
                View view = mInflater.inflate(R.layout.item_multiple_select, viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.mBtn = (CheckBox) view.findViewById(R.id.btn_option);
                return viewHolder;
            }
            case DISH_REMARK:{
                View view = mInflater.inflate(R.layout.item_dish_remark, viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.mBtn = (CheckBox) view.findViewById(R.id.btn_option);
                return viewHolder;
            }
            default: break;
        }
        return null;
    }

    /**
     * 设置给定位置条目的选择状态
     */
    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    /**
     * 根据位置判断条目是否选中
     */
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final FilterOptionsEntity dataBean = mDatas.get(position);
        holder.mBtn.setText(dataBean.getOption());
        if (isItemChecked(position)){
            holder.mBtn.setBackground(mInflater.getContext().getResources().getDrawable(R.drawable.bg_red_eb6247));
            holder.mBtn.setTextColor(mInflater.getContext().getResources().getColor(R.color.color_eb6247));
        }else{
            holder.mBtn.setBackground(mInflater.getContext().getResources().getDrawable(R.drawable.bg_gray_4));
            holder.mBtn.setTextColor(mInflater.getContext().getResources().getColor(R.color.color_222222));
        }
        holder.mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item点击回调
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
                if (isItemChecked(position)){
                    setItemChecked(position,false);
                    holder.mBtn.setBackground(mInflater.getContext().getResources().getDrawable(R.drawable.bg_gray_4));
                    holder.mBtn.setTextColor(mInflater.getContext().getResources().getColor(R.color.color_222222));
                }else{
                    setItemChecked(position,true);
                    holder.mBtn.setBackground(mInflater.getContext().getResources().getDrawable(R.drawable.bg_red_eb6247));
                    holder.mBtn.setTextColor(mInflater.getContext().getResources().getColor(R.color.color_eb6247));
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

    /***更新adpter的数据和选择状态 */
    public void updateDataSet(ArrayList<FilterOptionsEntity> list) {
        this.mDatas = list;
        mSelectedPositions = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    /** 获得选中条目的结果 */
    public ArrayList<FilterOptionsEntity> getSelectedItem() {
        ArrayList<FilterOptionsEntity> selectList = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            if (mSelectedPositions.get(i)){
                selectList.add(mDatas.get(i));
            }
        }
        return selectList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public CheckBox mBtn;
    }
}
