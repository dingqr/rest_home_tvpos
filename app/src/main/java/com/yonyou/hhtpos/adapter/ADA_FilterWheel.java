package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;

import java.util.List;


/**
 * Created by ybing on 2017/6/23.
 */

public class ADA_FilterWheel extends ADA_LoopRecycler<ADA_FilterWheel.ViewHolder> {
    private LayoutInflater mInflater;
    private List<FilterOptionsEntity> mDatas;
    /**
     * 当前选中位置，高亮显示
     */
    private int mHighlight = 0;
    private int offset = 0;

    public void setOffset(int offset) {
        mHighlight = offset;
        this.offset = offset;
    }

    public ADA_FilterWheel(Context mContext, List<FilterOptionsEntity> mDatas) {
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        super.setDatas(mDatas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.item_set_detail, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.llDishOptions = (LinearLayout) view.findViewById(R.id.ll_dish_options);
        viewHolder.tvDishType = (TextView) view.findViewById(R.id.tv_dish_type);
        viewHolder.tvDishSelectedCount = (TextView) view.findViewById(R.id.tv_dish_selected_count);
        viewHolder.tvDishTotalCount = (TextView) view.findViewById(R.id.tv_dish_total_count);
        return viewHolder;
    }

    @Override
    public void onBindLoopViewHolder( ViewHolder holder,  int position) {
        final FilterOptionsEntity dataBean = mDatas.get(position);
        holder.tvDishType.setText(dataBean.getMultipleOption().getDishType());
        holder.tvDishSelectedCount.setText(dataBean.getMultipleOption().getSelectedCount() + "");
        holder.tvDishTotalCount.setText(dataBean.getMultipleOption().getTotalCount() + "");

    }

//    private OnItemClickListener mOnItemClickListener;
//    /**
//     * 点击事件的回调接口
//     */
//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }
//
//    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
//        this.mOnItemClickListener = mOnItemClickListener;
//    }

    /**
     * 更新数据
     *
     * @param mDatas
     */
    public void update(List<FilterOptionsEntity> mDatas) {
        if (null != mDatas) {
            this.mDatas = mDatas;
        }
        notifyDataSetChanged();
    }

    public int getItemWidth() {
        return 200;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public LinearLayout llDishOptions;
        public TextView tvDishType;
        public TextView tvDishSelectedCount;
        public TextView tvDishTotalCount;
    }
}

//        holder.llDishOptions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //item点击回调
//                if (mOnItemClickListener != null) {
//                    mOnItemClickListener.onItemClick(holder.itemView, position);
//                }
//                //实现单选功能
//                if (null != dataBean && null != currentBean) {
//                    if (!dataBean.equals(currentBean)) {
//                        dataBean.setCheck(true);
//                        currentBean.setCheck(false);
//                        currentBean = dataBean;
//                        notifyDataSetChanged();
//                    }
//                }
//            }
//        });


//if (position==0){
//        int x = (int)holder.llDishOptions.getX();
//        int y = (int) holder.llDishOptions.getY();
//        holder.llDishOptions.setBackground(mInflater.getContext().getResources().getDrawable(R.drawable.bg_red_eb6247));
//        holder.tvDishType.setTextColor(Color.parseColor("#eb6247"));
//        }else{
//        int x = (int)holder.llDishOptions.getX();
//        int y = (int) holder.llDishOptions.getY();
//        holder.llDishOptions.setBackground(mInflater.getContext().getResources().getDrawable(R.drawable.bg_gray_4));
//        holder.tvDishType.setTextColor(Color.parseColor("#222222"));
//        }