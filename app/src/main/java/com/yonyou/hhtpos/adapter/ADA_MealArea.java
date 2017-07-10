package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.MealAreaEntity;

/**
 * 餐区adapter
 * 作者：liushuofei on 2017/7/8 15:56
 */
public class ADA_MealArea extends BaseAbsAdapter<MealAreaEntity> {

    private MealAreaEntity currentBean;

    public ADA_MealArea(Context context) {
        super(context);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_meal_area, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final MealAreaEntity bean = mDataSource.get(position);
        handleDataSource(position, holder, bean);

        if (bean.isCheck()){
            currentBean = bean;
            holder.mLine.setVisibility(View.VISIBLE);
            holder.mMealName.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
        }else {
            holder.mLine.setVisibility(View.INVISIBLE);
            holder.mMealName.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
        }
        return convertView;
    }

    private void handleDataSource(int position, final ViewHolder holder, final MealAreaEntity bean) {
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bean.equals(currentBean)){
                    bean.setCheck(true);
                    if (null != currentBean){
                        currentBean.setCheck(false);
                    }
                    currentBean = bean;
                    notifyDataSetChanged();
                }
            }
        });

        switch (position){
            case 0:
                holder.mMealName.setText("全部餐区");
                break;

            case 1:
                holder.mMealName.setText("一楼大厅");
                break;

            case 2:
                holder.mMealName.setText("二楼包厢");
                break;

            case 3:
                holder.mMealName.setText("二楼宴会厅");
                break;

            default:
                break;
        }
    }

    static class ViewHolder {
        FrameLayout mRoot;
        TextView mMealName;
        View mLine;

        ViewHolder(View v) {
            mRoot = (FrameLayout) v.findViewById(R.id.fl_root);
            mMealName = (TextView) v.findViewById(R.id.tv_area_name);
            mLine = (View) v.findViewById(R.id.v_right_line);
        }
    }
}
