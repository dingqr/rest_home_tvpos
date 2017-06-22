package com.yonyou.hhtpos.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zj on 2017/2/4.
 * email:zjuan@yonyou.com
 * 封装adapter（注意：仅供参考，根据需要选择使用demo中提供的封装adapter）
 *
 * @param <T>
 */
public abstract class ADA_BaseRcyclerView<T> extends RecyclerView.Adapter<SuperViewHolder> {
    protected Context mContext;
    private LayoutInflater mInflater;

    protected List<T> mDataList = new ArrayList<>();

    public ADA_BaseRcyclerView(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(getLayoutId(), parent, false);
        return new SuperViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        onBindItemHolder(holder, position);
    }

    //局部刷新关键：带payload的这个onBindViewHolder方法必须实现
    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            onBindItemHolder(holder, position, payloads);
        }

    }

    public abstract int getLayoutId();

    public abstract void onBindItemHolder(SuperViewHolder holder, int position);

    public void onBindItemHolder(SuperViewHolder holder, int position, List<Object> payloads) {

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(Collection<T> list) {
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(Collection<T> list) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void remove(int position) {
        if (mDataList != null && mDataList.size() > 0) {
            // 数据移除
            this.mDataList.remove(position);
            // 界面移除
            notifyItemRemoved(position - 1);
            // 刷新位置
            if (position != (mDataList.size())) {
                // 如果移除的是最后一个，忽略
                notifyItemRangeChanged(position - 1, getItemCount() - position);
            }
        }
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 设置适配器数据
     * @param dataList data
     * @param isClear  是否需要清空list然后在加载数据
     */
    public void update(List<T> dataList, Boolean isClear) {
        if (isClear) {
            clear();
        }
        if (dataList != null) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

}
