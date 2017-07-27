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
import com.yonyou.hhtpos.bean.SetMealListEntity;

import java.util.List;

/**
 * Created by ybing on 2017/7/13.
 * 套餐列表数据适配器
 */

public class ADA_SetMealList extends RecyclerView.Adapter<ADA_SetMealList.ViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    private List<SetMealListEntity> mDatas;

    public ADA_SetMealList(Context mContext, List<SetMealListEntity> mDatas) {
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        final SetMealListEntity dataBean = mDatas.get(position);
        if (dataBean != null) {
            if (dataBean.getDishCookery()!= null)
                return 1;
            else return 0;
        }
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == 0) {
            view = mInflater.inflate(R.layout.item_set_meal_list_no_add_cookery, viewGroup, false);
        } else {
            view = mInflater.inflate(R.layout.item_set_meal_list_add_cookery, viewGroup, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.llSetMealListContent = (LinearLayout) view.findViewById(R.id.ll_set_meal_list_content);
        viewHolder.tvDishName = (TextView) view.findViewById(R.id.tv_dish_name);
        viewHolder.tvDishCount = (TextView) view.findViewById(R.id.tv_dish_count);
        viewHolder.tvCookery = (TextView) view.findViewById(R.id.tv_dish_add_cookery);
        viewHolder.ibDishModify = (ImageButton) view.findViewById(R.id.ib_dish_modify);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SetMealListEntity dataBean = mDatas.get(position);
        if (dataBean != null) {
            holder.tvDishName.setText(StringUtil.getString(dataBean.getDishName()));
            if (dataBean.getDishCount() > 0) {
                holder.tvDishCount.setText("x" + dataBean.getDishCount());
            }
            if (dataBean.getDishCookery()!=null) {
                holder.tvCookery.setVisibility(View.VISIBLE);
                holder.tvCookery.setText(mInflater.getContext().getString(R.string.hint_dish_cookery)+dataBean.getDishCookery());
            }else{
                holder.tvCookery.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_dish_modify:
                break;
            default:
                break;
        }
    }

    /**
     * 更新数据
     *
     * @param mDatas
     */
    public void update(List<SetMealListEntity> mDatas) {
        if (null != mDatas) {
            this.mDatas = mDatas;
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public LinearLayout llSetMealListContent;
        public TextView tvDishCount;
        public TextView tvDishName;
        public TextView tvCookery;
        public ImageButton ibDishModify;
    }
}
