package com.smart.tvpos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.smart.framework.library.adapter.lv.CommonAdapterListView;
import com.smart.framework.library.adapter.lv.ViewHolderListView;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.BuildingEntity;

/**
 * Created by JoJo on 2018/7/3.
 * wechat:18510829974
 * description:楼宇
 */

public class ADA_BuildingList extends CommonAdapterListView<BuildingEntity> {
    private int mCurPosition;

    public ADA_BuildingList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_building;
    }

    @Override
    protected void convert(ViewHolderListView holder, BuildingEntity bean, int position) {
        holder.setText(R.id.tv_buildingName, bean.getBuildingName());
        if (mCurPosition == position) {
            holder.setTextColor(R.id.tv_buildingName, ContextCompat.getColor(mContext, R.color.color_529af0));
            holder.setBackgroundRes(R.id.ll_item,R.drawable.bg_grey_building_item);
        } else {
            holder.setTextColor(R.id.tv_buildingName, ContextCompat.getColor(mContext, R.color.color_adadad));
            holder.setBackgroundColor(R.id.ll_item,ContextCompat.getColor(mContext, R.color.color_FFFFFF));
        }
    }

    public void setCheckCurrentItem(int position) {
        this.mCurPosition = position;
        notifyDataSetChanged();
    }
}
