package com.yonyou.hhtpos.adapter;

import android.support.v7.widget.RecyclerView;

import com.yonyou.hhtpos.bean.FilterOptionsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ybing on 2017/7/14.
 */

public abstract class ADA_LoopRecycler <T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    List<FilterOptionsEntity> datas = new ArrayList<>();

    public void setDatas(List<FilterOptionsEntity> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    /**
     * 真实数据的大小
     */
    public int getItemRawCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    final public int getItemViewType(int position) {
        return getLoopItemViewType(position % getItemRawCount());
    }

    protected int getLoopItemViewType(int position) {
        return 0;
    }

    @Override
    final public void onBindViewHolder(T holder, int position) {
        onBindLoopViewHolder(holder, position % getItemRawCount());
    }

    public abstract void onBindLoopViewHolder(T holder, int position);

    @Override
    final public int getItemCount() {
        int rawCount = getItemRawCount();
        if (rawCount > 0) {
            return Integer.MAX_VALUE;
        }
        return 0;
    }
}
