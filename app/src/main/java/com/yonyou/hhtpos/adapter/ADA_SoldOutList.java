package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.hhtpos.R;

/**
 * 作者：liushuofei on 2017/8/7 11:38
 * 邮箱：lsf@yonyou.com
 */
public class ADA_SoldOutList extends BaseAbsAdapter<String> {

    public ADA_SoldOutList(Context context) {
        super(context);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sold_out_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {

        ViewHolder(View v) {

        }
    }
}
