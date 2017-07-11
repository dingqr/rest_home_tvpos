package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.hhtpos.R;

/**
 * 点菜列表adapter
 * 作者：liushuofei on 2017/7/11 15:11
 */
public class ADA_DishesList extends BaseAbsAdapter<String> {

    public ADA_DishesList(Context context) {
        super(context);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dishes_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {

        TextView mDishesName;
        TextView mDishesRemark;
        TextView mDishesPrice;
        TextView mDishesCount;
        TextView mDishesStatus;
        ImageView mVipLogo;

        ViewHolder(View v) {
            mDishesName = (TextView) v.findViewById(R.id.tv_dishes_name);
            mDishesRemark = (TextView) v.findViewById(R.id.tv_dishes_remark);
            mDishesPrice = (TextView) v.findViewById(R.id.tv_dishes_price);
            mDishesCount = (TextView) v.findViewById(R.id.tv_dishes_count);
            mDishesStatus = (TextView) v.findViewById(R.id.tv_dishes_status);
            mVipLogo = (ImageView) v.findViewById(R.id.iv_vip_logo);
        }
    }
}
