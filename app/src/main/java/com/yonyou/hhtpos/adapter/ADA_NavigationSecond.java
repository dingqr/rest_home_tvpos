package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RadioButton;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.NavigationNewEntity;
import com.yonyou.hhtpos.util.IntentUtil;

/**
 * 左侧导航栏二级adapter
 * 作者：liushuofei on 2017/6/27 17:35
 */
public class ADA_NavigationSecond extends BaseAbsAdapter<NavigationNewEntity.SecondList> {

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
        final NavigationNewEntity.SecondList bean = mDataSource.get(position);
        handleDataSource(position, holder, bean);
        holder.mTxt.setChecked(false);

        return convertView;
    }

    private void handleDataSource(int position,final ViewHolder holder, final NavigationNewEntity.SecondList bean) {
        if (null == bean)
            return;

        // 二级菜单名称
        holder.mTxt.setText(StringUtil.getString(bean.getFunctionName()));

        holder.mTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转
                IntentUtil.handleNavigationIntent(mContext, bean.getFunctionName());

                // 更新已选状态
                if (null != mCurrentSelected && !mCurrentSelected.equals(holder.mTxt)){
                    mCurrentSelected.setChecked(false);
                }
                mCurrentSelected = holder.mTxt;
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
