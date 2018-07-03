package com.smart.tvpos.adapter;

import android.content.Context;

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
    }
}
