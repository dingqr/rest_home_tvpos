package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.RightTitleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2017/6/22.
 * 邮箱：zjuan@yonyou.com
 * 描述：右侧导航栏-Adapter
 */
public class RightTitleAdapter extends BaseAdapter {
    private Context mContext;
    private List<RightTitleEntity> mDishTypes = new ArrayList<>();
    private int mSelectedPos;
    private boolean isRefreshCount;
    private int itemCheckColor = R.color.color_FF4d4d;
    private int itemUnCheckColor = R.color.color_e5e5e5;

    public RightTitleAdapter(Context context, List<RightTitleEntity> datas) {
        this.mContext = context;
        this.mDishTypes = datas;
    }

    @Override
    public int getCount() {
        return mDishTypes == null ? 0 : mDishTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return mDishTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_title, null);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置初始值
        holder.tv_title.setText(mDishTypes.get(position).name);
        holder.tv_count.setText(StringUtil.getString(mDishTypes.get(position).count));
        //控制角标是否显示
        if (holder.tv_count.getText().equals("0")) {
            holder.tv_count.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_count.setVisibility(View.VISIBLE);
        }
        //更新右上角标的数量
        if (isRefreshCount) {
            holder.tv_title.setText(mDishTypes.get(position).name);
            String s = holder.tv_count.getText().toString();
            if (!TextUtils.isEmpty(s)) {
                holder.tv_count.setText(Integer.parseInt(s) + StringUtil.getString(1));
                if (refreshCurrentItemListener != null) {
                    refreshCurrentItemListener.onRefreshCount(position, Integer.parseInt(s) + 1);
                }
            }
            isRefreshCount = false;
        } else {
            //不刷新有角标才设置title的选中效果
            if (mSelectedPos == position) {
                convertView.setBackgroundColor(ContextCompat.getColor(mContext, itemCheckColor));
            } else {
                convertView.setBackgroundColor(ContextCompat.getColor(mContext, itemUnCheckColor));
            }
        }
        return convertView;
    }

    /**
     * 设置当前选中的item的位置
     *
     * @param position
     */
    public void setSelectItem(int position) {
        this.mSelectedPos = position;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_count;
    }

    /**
     * listView单条刷新
     *
     * @param listView
     * @param id
     */
    public void updateSingleRow(ListView listView, String id) {
        if (listView != null) {
            int start = listView.getFirstVisiblePosition();
            for (int i = start, j = listView.getLastVisiblePosition(); i <= j; i++) {
                RightTitleEntity dishType = (RightTitleEntity) listView.getItemAtPosition(i);
                if (id.equals(dishType.id)) {
                    View view = listView.getChildAt(i - start);
                    getView(i, view, listView);
                    break;
                }
            }

        }
    }

    /**
     * 刷新右上角标的数量
     */
    public void refreshCount(ListView listView, String id, boolean refreshCount) {
        this.isRefreshCount = refreshCount;
        updateSingleRow(listView, id);
    }

    /**
     * 刷新数据
     *
     * @param mData
     */
    public void refreshData(List<RightTitleEntity> mData) {
        this.mDishTypes = mData;
        notifyDataSetChanged();
    }

    private OnRefreshCurrentItemListener refreshCurrentItemListener;

    public interface OnRefreshCurrentItemListener {
        void onRefreshCount(int refreshPos, int curCount);
    }

    public void setRefreshCurrentItemListener(OnRefreshCurrentItemListener refreshCurrentItemListener) {
        this.refreshCurrentItemListener = refreshCurrentItemListener;
    }
}
