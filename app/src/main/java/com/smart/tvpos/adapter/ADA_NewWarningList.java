package com.smart.tvpos.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.smart.framework.library.adapter.rv.CommonAdapter;
import com.smart.framework.library.adapter.rv.ViewHolder;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.NewWarningEntity;

/**
 * Created by JoJo on 2018/6/26.
 * wechat：18510829974
 * description：最新警报
 */
public class ADA_NewWarningList extends CommonAdapter<NewWarningEntity> {


    public ADA_NewWarningList(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_new_warning;
    }

    @Override
    protected void convert(ViewHolder holder, NewWarningEntity bean, int position) {
        ImageView ivLevelSign = holder.getView(R.id.iv_level_sign);
        if (bean != null) {
            holder.setText(R.id.tv_name, bean.getUserName() == null ? "" : bean.getUserName());
            holder.setText(R.id.tv_title, bean.getTitle() == null ? "" : bean.getTitle());
            holder.setText(R.id.tv_type_child, bean.getTypeChild() == null ? "" : bean.getTypeChild());
            //所属护工
            holder.setText(R.id.tv_staffs, "所属护工：" + bean.getAllStaff());
            //地点
            holder.setText(R.id.tv_address, bean.getBuildingName() + bean.getFloorName() + bean.getRoomName());
            holder.setText(R.id.tv_time, bean.getUpdated());
        }
    }
}
