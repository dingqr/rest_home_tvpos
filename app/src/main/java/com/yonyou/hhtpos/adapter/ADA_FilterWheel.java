package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;

import java.util.List;


/**
 * Created by ybing on 2017/6/23.
 * 单项筛选横向循环滚动组件adapter
 */

public class ADA_FilterWheel extends ADA_LoopRecycler<ADA_FilterWheel.ViewHolder> {
    private LayoutInflater mInflater;
    private List<FilterOptionsEntity> mDatas;
    /**
     * 当前选中位置，高亮显示
     */
    private int mHighlight = 0;

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
    public void onBindLoopViewHolder(final ViewHolder holder, final int position) {
        final FilterOptionsEntity dataBean = mDatas.get(position % getItemRawCount());
        if (dataBean != null) {
            holder.tvDishType.setText(StringUtil.getString(dataBean.getMultipleOption().getDishType()));
            holder.tvDishSelectedCount.setText(StringUtil.getString(dataBean.getMultipleOption().getSelectedCount() + ""));
            holder.tvDishTotalCount.setText(StringUtil.getString(dataBean.getMultipleOption().getTotalCount() + ""));
        }
        if (isSelect(position)) {
            holder.llDishOptions.setBackground(mInflater.getContext().getResources().getDrawable(R.drawable.bg_red_eb6247));
            holder.tvDishType.setTextColor(Color.parseColor("#eb6247"));
        } else {
            holder.llDishOptions.setBackground(mInflater.getContext().getResources().getDrawable(R.drawable.bg_gray_4));
            holder.tvDishType.setTextColor(Color.parseColor("#222222"));
        }
    }

    public void highlightItem(int position) {
        mHighlight = position;
        notifyDataSetChanged();
    }
    public void reset() {
        mHighlight = -1;
        notifyDataSetChanged();
    }

    /**
     * 判断某个条目是否是选中状态（居最左状态）
     *
     * @param position
     * @return
     */
    private boolean isSelect(int position) {
        return mHighlight == position;
    }

    private OnItemClickListener mOnItemClickListener;

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
     *
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

        public LinearLayout llDishOptions;
        public TextView tvDishType;
        public TextView tvDishSelectedCount;
        public TextView tvDishTotalCount;
    }
}
