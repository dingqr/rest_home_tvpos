package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.TableCapacityEntity;

import java.util.List;


/**
 * Created by ybing on 2017/6/29.
 * 设置餐桌台容纳人数的数据适配器
 */

public class ADA_SelectTableCapacity extends RecyclerView.Adapter<ADA_SelectTableCapacity.ViewHolder>{
    private LayoutInflater mInflater;
    private List<TableCapacityEntity> mDatas;
    private TableCapacityEntity currentBean;
    private OnCapacityClickListener mOnItemClickListener ;

    public ADA_SelectTableCapacity(Context mContext, List<TableCapacityEntity> mDatas) {
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position){
        final TableCapacityEntity dataBean = mDatas.get(position);
        return dataBean.getType();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.item_table_capacity, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.llSelectTableCapacity = (LinearLayout) view.findViewById(R.id.ll_select_table_capacity);
        viewHolder.llTableCapacity = (RelativeLayout)view.findViewById(R.id.ll_table_capacity);
        viewHolder.tvTableCapacity = (TextView) view.findViewById(R.id.tv_table_capacity);
        viewHolder.tvTableArea = (TextView) view.findViewById(R.id.tv_table_area);
        viewHolder.rbSelectTable = (RadioButton)view.findViewById(R.id.rb_select_table);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TableCapacityEntity dataBean = mDatas.get(position);
        if (dataBean !=null){
            holder.tvTableCapacity.setText(dataBean.getCapacity()+"人");
            holder.tvTableArea.setText(dataBean.getArea());
            if (dataBean.isCheck()){
                currentBean = dataBean;
            }
            holder.rbSelectTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onCapacityClick(holder.itemView,position);
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
                holder.rbSelectTable.setBackground(this.mInflater.getContext().getResources().getDrawable(R.drawable.ic_table_selected));
            } else {
//                holder.llSelectTableCapacity.setBackground(this.mInflater.getContext().getResources().getDrawable(R.drawable.bg_gray_4));
                holder.rbSelectTable.setBackground(this.mInflater.getContext().getResources().getDrawable(R.drawable.ic_table_un_selected));
            }
        }
    }



    /**
     * 点击事件的回调接口
     */
    public interface OnCapacityClickListener {
        void onCapacityClick(View view, int position);
    }

    public void setmOnItemClickListener(OnCapacityClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 更新数据
     * @param mDatas
     */
    public void update(List<TableCapacityEntity> mDatas) {
        if (null != mDatas) {
            this.mDatas = mDatas;
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public LinearLayout llSelectTableCapacity;
        public RelativeLayout llTableCapacity;
        public TextView tvTableCapacity;
        public TextView tvTableArea;
        public RadioButton rbSelectTable;
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