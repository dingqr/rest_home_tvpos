package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.hhtpos.R;

/**
 * 作者：liushuofei on 2017/7/15 14:46
 * 邮箱：lsf@yonyou.com
 */
public class ADA_CheckOutList extends BaseAbsAdapter<String> {

    public ADA_CheckOutList(Context context) {
        super(context);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        textview.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_check_out_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == 0){
            holder.mHeaderView.setVisibility(View.VISIBLE);
        }else {
            holder.mHeaderView.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder {

        TextView mHeaderView;

        ViewHolder(View v) {
            mHeaderView = (TextView) v.findViewById(R.id.tv_dishes_ordered);
        }
    }
}
