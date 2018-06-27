package com.smart.tvpos.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.smart.framework.library.adapter.rv.CommonAdapter;
import com.smart.framework.library.adapter.rv.ViewHolder;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.NewWarningEntity;
import com.smart.tvpos.global.API;

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
        ImageView ivUserAvatar = holder.getView(R.id.iv_user_avatar);
        if (bean != null) {
            holder.setText(R.id.tv_name, bean.getUserName() == null ? "" : bean.getUserName());
            holder.setText(R.id.tv_title, bean.getTitle() == null ? "" : bean.getTitle());
            holder.setText(R.id.tv_type_child, bean.getTypeChild() == null ? "" : bean.getTypeChild());
            //所属护工
            holder.setText(R.id.tv_staffs, "所属护工：" + bean.getAllStaff());
            //地点
            holder.setText(R.id.tv_address, bean.getBuildingName() + bean.getFloorName() + bean.getRoomName());
            holder.setText(R.id.tv_time, bean.getUpdated());
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_user_avatar)
                    .error(R.drawable.ic_user_avatar)
                    .priority(Priority.HIGH)
                    .transform(new CircleCrop());
            Glide.with(MyApplication.getContext())
                    .load(API.IMG_SERVER_IP + bean.getHeadImg())
                    .apply(requestOptions)
                    .into(ivUserAvatar);
        }
    }
}
