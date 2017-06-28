package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.hhtpos.R;

/**
 * 左侧导航栏二级adapter
 * 作者：liushuofei on 2017/6/27 17:35
 */
public class ADA_NavigationSecond extends BaseAbsAdapter<String> {

    private Context context;
    private RadioButton mCurrentSelected;

    public ADA_NavigationSecond(Context context) {
        super(context);

        this.context = context;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_navigation_second, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        handleDataSource(position, holder);

        return convertView;
    }

    private void handleDataSource(int position,final ViewHolder holder) {
        String category = "";
        switch (position) {
            case 0:
                category = "待确认订单";
                break;

            case 1:
                category = "预订总览";
                break;

            case 2:
                category = "桌台预订查询";
                break;

            default:
                break;
        }

        holder.mTxt.setText(category);

        holder.mTxt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (null != mCurrentSelected){
                        mCurrentSelected.setChecked(false);
                    }

                    mCurrentSelected = holder.mTxt;
                }
            }
        });
    }

    static class ViewHolder {
        RadioButton mTxt;

        ViewHolder(View v) {
            mTxt = (RadioButton) v.findViewById(R.id.rb_category_second);
        }
    }
}
