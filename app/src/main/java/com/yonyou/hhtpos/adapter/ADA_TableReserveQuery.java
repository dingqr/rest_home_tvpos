package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.TableReserveEntity;

import java.util.List;


/**
 * Created by ybing on 2017/7/3.
 * 桌台预定总览 数据适配器
 */

public class ADA_TableReserveQuery extends RecyclerView.Adapter<ADA_TableReserveQuery.ViewHolder>{
    private LayoutInflater mInflater;
    private List<TableReserveEntity> mDatas;
    private TableReserveEntity currentBean;
    private OnTableClickListener mOnItemClickListener ;

    public ADA_TableReserveQuery(Context mContext, List<TableReserveEntity> mDatas) {
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
        View view = mInflater.inflate(R.layout.item_table_reserve_query, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.llTableContent = (LinearLayout) view.findViewById(R.id.ll_table_content);
        viewHolder.tvLunchOrderNumber = (TextView)view.findViewById(R.id.tv_lunch_order_number);
        viewHolder.tvSupperOrderNumber = (TextView) view.findViewById(R.id.tv_supper_order_number);
        viewHolder.tvRoomNumber = (TextView) view.findViewById(R.id.tv_room_number);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TableReserveEntity dataBean = mDatas.get(position);
        if (dataBean != null){
            holder.tvLunchOrderNumber.setText(dataBean.getLunchNum()+"");
            holder.tvSupperOrderNumber.setText(dataBean.getSupperNum()+"");
            holder.tvRoomNumber.setText(dataBean.getRoomName());
            if (dataBean.isCheck()){
                currentBean = dataBean;
            }
            holder.llTableContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onTableClick(holder.itemView,position);
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
                holder.llTableContent.setBackground(this.mInflater.getContext().getResources().getDrawable(R.drawable.bg_red_radius_6));
            } else {
                holder.llTableContent.setBackground(this.mInflater.getContext().getResources().getDrawable(R.drawable.bg_gray_radius_6));
            }
        }
    }



    /**
     * 点击事件的回调接口
     */
    public interface OnTableClickListener {
        void onTableClick(View view, int position);
    }

    public void setmOnItemClickListener(OnTableClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 更新数据
     * @param mDatas
     */
    public void update(List<TableReserveEntity> mDatas) {
        if (null != mDatas) {
            this.mDatas = mDatas;
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public LinearLayout llTableContent;
        public TextView tvLunchOrderNumber;
        public TextView tvSupperOrderNumber;
        public TextView tvRoomNumber;
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