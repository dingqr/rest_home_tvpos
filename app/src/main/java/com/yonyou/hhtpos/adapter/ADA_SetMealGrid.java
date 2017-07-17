package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.SetMealGridEntity;

import java.util.List;


/**
 * Created by ybing on 2017/7/13.
 * 全家福套餐表格选项数据适配器
 */

public class ADA_SetMealGrid extends RecyclerView.Adapter<ADA_SetMealGrid.ViewHolder>  {
    private LayoutInflater mInflater;
    private List<SetMealGridEntity> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public ADA_SetMealGrid(Context mContext, List<SetMealGridEntity> mDatas) {
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
        View view = mInflater.inflate(R.layout.item_set_meal_grid, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.llSetMealContent = (LinearLayout) view.findViewById(R.id.ll_set_meal_content);
        viewHolder.llAddPrice = (LinearLayout) view.findViewById(R.id.ll_add_price);
        viewHolder.tvDishPart = (TextView) view.findViewById(R.id.tv_dish_part);
        viewHolder.tvDishName = (TextView) view.findViewById(R.id.tv_dish_name);
        viewHolder.tvAddPrice = (TextView) view.findViewById(R.id.tv_add_price);
        viewHolder.tvDishCountLimit = (TextView) view.findViewById(R.id.tv_dish_count_limit);
        viewHolder.ibDishSelect = (ImageButton)view.findViewById(R.id.ib_dish_select);
        return viewHolder;
    }

    //  SetMealGridEntity smge1 = new SetMealGridEntity("大份","麻辣皮皮虾",1,0,true);
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SetMealGridEntity dataBean = mDatas.get(position);
        final int limit =  dataBean.getDishLimitCount();
        if (dataBean !=null) {
            if (dataBean.getDishPart()!=null){
                holder.tvDishPart.setText("["+ dataBean.getDishPart()+"]");
            }else{
                holder.tvDishPart.setVisibility(View.GONE);
            }
            holder.tvDishName.setText(StringUtil.getString(dataBean.getDishName()));
           if (dataBean.getDishAddPrice()==0){
               holder.llAddPrice.setVisibility(View.GONE);
           }else{
               holder.llAddPrice.setVisibility(View.VISIBLE);
               holder.tvAddPrice.setText(StringUtil.getString(dataBean.getDishAddPrice()+""));
           }
            holder.tvDishCountLimit.setText(mInflater.getContext().getString(R.string.limit_select)
                    + dataBean.getDishLimitCount()
                    + mInflater.getContext().getString(R.string.part));
        }
        holder.llSetMealContent.setOnClickListener(new View.OnClickListener() {
            int clickNum = 0;
            @Override
            public void onClick(View v) {
                //item点击回调
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
                holder.ibDishSelect.setVisibility(View.VISIBLE);
                clickNum ++;
                if (clickNum >= limit){
                    dataBean.setCheckAble(false);
                    holder.llSetMealContent.setBackground(mInflater.getContext().getResources().getDrawable(R.drawable.bg_dish_sell_out));
                }
            }
        });
        //设置可选和不可选效果
        if (dataBean.isCheckAble()) {
            holder.llSetMealContent.setBackground(mInflater.getContext().getResources().getDrawable(R.drawable.bg_dish_type));
        } else {
            holder.llSetMealContent.setBackground(mInflater.getContext().getResources().getDrawable(R.drawable.bg_dish_sell_out));
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
    public void update(List<SetMealGridEntity> mDatas) {
        if (null != mDatas) {
            this.mDatas = mDatas;
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public LinearLayout llSetMealContent;
        public LinearLayout llAddPrice;
        public TextView tvDishPart;
        public TextView tvDishName;
        public TextView tvAddPrice;
        public TextView tvDishCountLimit;
        public ImageButton ibDishSelect;
    }
}
