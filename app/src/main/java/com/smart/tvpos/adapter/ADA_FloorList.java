package com.smart.tvpos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.smart.framework.library.adapter.lv.CommonAdapterListView;
import com.smart.framework.library.adapter.lv.ViewHolderListView;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.FloorEntity;

/**
 * Created by JoJo on 2018/7/3.
 * wechat:18510829974
 * description:楼层
 */

public class ADA_FloorList extends CommonAdapterListView<FloorEntity> {

    private double mCurPosition;

    public ADA_FloorList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_floor;
    }

    @Override
    protected void convert(ViewHolderListView holder, FloorEntity bean, int position) {
        holder.setText(R.id.tv_floorName, bean.getFloorName());
        if (mCurPosition == position) {
            holder.setTextColor(R.id.tv_floorName, ContextCompat.getColor(mContext, R.color.color_FFFFFF));
            holder.setBackgroundRes(R.id.ll_item,R.drawable.bg_grey_building_item);
        } else {
            holder.setTextColor(R.id.tv_floorName, ContextCompat.getColor(mContext, R.color.color_adadad));
            holder.setBackgroundColor(R.id.ll_item,ContextCompat.getColor(mContext, R.color.color_FFFFFF));
        }
    }
    public void setCheckCurrentItem(int position) {
        this.mCurPosition = position;
        notifyDataSetChanged();
    }
}
