package com.smart.tvpos.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.smart.framework.library.adapter.rv.CommonAdapter;
import com.smart.framework.library.adapter.rv.ViewHolder;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.UserNurseListEntity;
import com.smart.tvpos.global.API;

/**
 * Created by JoJo on 2018/6/27.
 * wechat:18510829974
 * description:
 */

public class ADA_NurseProgress extends CommonAdapter<UserNurseListEntity> {
    public ADA_NurseProgress(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_nurse_progress;
    }

    @Override
    protected void convert(ViewHolder holder, UserNurseListEntity bean, int position) {
        ImageView ivUserAvatar = holder.getView(R.id.iv_user_avatar);
        RelativeLayout rlRoot = holder.getView(R.id.rl_root);
        if (bean != null) {
            holder.setText(R.id.tv_name, bean.getUserName() == null ? "" : bean.getUserName());
            holder.setText(R.id.tv_type_child, bean.getTypeChild() == null ? "" : bean.getTypeChild());
            holder.setVisible(R.id.tv_type_child, (!TextUtils.isEmpty(bean.getTypeChild())) ? true : false);
            //地点
            holder.setText(R.id.tv_address, bean.getBuildingName() + bean.getFloorName() + bean.getRoomName());
            holder.setText(R.id.tv_sex_old, "年龄：" + bean.getAge() + "    性别:" + (TextUtils.isEmpty(bean.getSex()) ? "" : bean.getSex()));
//            holder.setText(R.id.tv_time, bean.getUpdated());
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_user_avatar)
                    .error(R.drawable.ic_user_avatar)
                    .priority(Priority.HIGH)
                    .transform(new CircleCrop());
            Glide.with(MyApplication.getContext())
                    .load(API.IMG_SERVER_IP + bean.getHeadImg())
                    .apply(requestOptions)
                    .into(ivUserAvatar);
            if (position == 6 || position == 9 || position == 13) {
                rlRoot.setBackgroundResource(R.drawable.bg_warning_one);
            }
            if (position == 17 || position == 20) {
                rlRoot.setBackgroundResource(R.drawable.bg_warning_two);
            }
        }
    }
}
