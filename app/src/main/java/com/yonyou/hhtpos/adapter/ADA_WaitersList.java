package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.yonyou.framework.library.adapter.lv.CommonAdapterListView;
import com.yonyou.framework.library.adapter.lv.ViewHolderListView;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.WaiterEntity;

/**
 * Created by zj on 2017/7/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：服务员列表适配器
 */
public class ADA_WaitersList extends CommonAdapterListView<WaiterEntity> {

    private double mSelectedPos;

    public ADA_WaitersList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_waiter;
    }

    @Override
    protected void convert(ViewHolderListView holder, WaiterEntity waiterEntity, int position) {
        TextView tv_waiter_name = holder.getView(R.id.tv_waiter_name);
        View divider_view = holder.getView(R.id.divider_view);
        holder.setText(R.id.tv_waiter_name, waiterEntity.waiterName);
        if (mSelectedPos == position) {
            tv_waiter_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            tv_waiter_name.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_fef2f0));
        } else {
            tv_waiter_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
            tv_waiter_name.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_FFFFFF));
        }
        //设置第一排顶部距离显示
        if (position <= 4) {
            divider_view.setVisibility(View.VISIBLE);
        } else {
            divider_view.setVisibility(View.GONE);
        }
    }

    /**
     * 设置当前选中的item的位置
     *
     * @param position
     */
    public void setSelectItem(int position) {
        this.mSelectedPos = position;
    }
}
