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
import com.yonyou.hhtpos.popup.POP_DishesEdit;
import com.yonyou.hhtpos.widgets.BanSlideListView;

/**
 * 点菜列表adapter
 * 作者：liushuofei on 2017/7/11 15:11
 */
public class ADA_DishesList extends BaseAbsAdapter<String> {

    private ADA_DishesPackage mAdapter;

    public ADA_DishesList(Context context) {
        super(context);

        mAdapter = new ADA_DishesPackage(context);
        for (int i = 0; i < 10; i++){
            mAdapter.update("");
        }
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

        if (position == 0){
            holder.mDishesTime.setVisibility(View.VISIBLE);
        }else {
            holder.mDishesTime.setVisibility(View.GONE);
        }

        // 设置套餐数据
        holder.mListView.setAdapter(mAdapter);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POP_DishesEdit popup = new POP_DishesEdit(mContext);
                popup.showAsDropDown(v, v.getWidth() + 8, -(v.getHeight() + 4));
            }
        });

        return convertView;
    }

    static class ViewHolder {

        TextView mDishesTime;
        TextView mDishesName;
        TextView mDishesRemark;
        TextView mDishesPrice;
        TextView mDishesCount;
        TextView mDishesStatus;
        ImageView mVipLogo;
        BanSlideListView mListView;

        ViewHolder(View v) {
            mDishesTime = (TextView) v.findViewById(R.id.tv_dishes_time);
            mDishesName = (TextView) v.findViewById(R.id.tv_dishes_name);
            mDishesRemark = (TextView) v.findViewById(R.id.tv_dishes_remark);
            mDishesPrice = (TextView) v.findViewById(R.id.tv_dishes_price);
            mDishesCount = (TextView) v.findViewById(R.id.tv_dishes_count);
            mDishesStatus = (TextView) v.findViewById(R.id.tv_dishes_status);
            mVipLogo = (ImageView) v.findViewById(R.id.iv_vip_logo);
            mListView = (BanSlideListView) v.findViewById(R.id.lv_package);
        }
    }
}
