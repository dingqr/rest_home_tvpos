package com.smart.tvpos.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.smart.framework.library.adapter.rv.CommonAdapter;
import com.smart.framework.library.adapter.rv.ViewHolder;
import com.smart.framework.library.common.utils.DP2PX;
import com.smart.framework.library.common.utils.ScreenUtil;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.UserNurseListEntity;
import com.smart.tvpos.global.API;

/**
 * Created by JoJo on 2018/6/27.
 * wechat:18510829974
 * description:
 */
//http://ycljf86.iteye.com/blog/2345413  droidtv 开发系列 二 recycleview item放大效果
//https://www.jb51.net/article/138747.htm Android RecyclerView item选中放大被遮挡问题详解
//https://blog.csdn.net/qq_35697312/article/details/53018819?locationNum=8&fps=1    androidTV中使用recyclerview并使其item在获取焦点后获取边框，并伴随放大，凸显效果
public class ADA_NurseProgressModify extends CommonAdapter<UserNurseListEntity> implements RecyclerView.ChildDrawingOrderCallback {
    private Activity context;
    private int[] normalColors;
    private int[] zoomColors;
    private ViewGroup viewGroup;
    private final int[] textcolor;

    public ADA_NurseProgressModify(Activity context) {
        super(context);
        this.context = context;
        //设置渐变进度条的颜色数组值
        normalColors = new int[]{Color.parseColor("#41a0eb"), Color.parseColor("#6488e5"), Color.parseColor("#7084e4")};
        zoomColors = new int[]{Color.parseColor("#ffffff"), Color.parseColor("#af8f5b"), Color.parseColor("#936622")};
        //设置报警级别对应的百分比文字颜色textcolor  #6c100e  956a25  3372ba
        textcolor = new int[]{Color.parseColor("#6c100e"), Color.parseColor("#956a25"), Color.parseColor("#3372ba"), Color.parseColor("#4791e1")};
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_nurse_progress;
    }

    @Override
    protected void convert(final ViewHolder holder, UserNurseListEntity bean, final int position) {
        RelativeLayout rlRoot = holder.getView(R.id.rl_root);
        ImageView ivUserAvatar = holder.getView(R.id.iv_user_avatar);
        ImageView ivUserAvatarZoom = holder.getView(R.id.iv_user_avatar_zoom);

        ImageView ivWarningLevelNormal = holder.getView(R.id.iv_warning_level_normal);
        TextView tvWarningNoteNormal = holder.getView(R.id.tv_warning_note_normal);
        ImageView ivWarningLevelZoom = holder.getView(R.id.iv_warning_level_zoom);
        //事件对应的级别
        TextView tvWarningLevelZoom = holder.getView(R.id.tv_warning_level_zoom);
        //事件类型
        TextView tvThingZoom = holder.getView(R.id.tv_thing_zoom);
        //床垫状态
        holder.setImageResource(R.id.iv_status, getBedStatusImage(bean.getStatus()));
        holder.setImageResource(R.id.iv_status_zoom, getBedStatusImage(bean.getStatus()));
        holder.setText(R.id.tv_status, bean.getStatus());
        holder.setText(R.id.tv_status_zoom, bean.getStatus());
        holder.setTextColor(R.id.tv_status, getBedStatusColor(bean.getStatus()));
        holder.setTextColor(R.id.tv_status_zoom, getBedStatusColor(bean.getStatus()));

        //设置itemview的高度固定,以防两种类型的Itemview高度不一致，导致列表的item显示间距和item的高度不一致导致的问题
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DP2PX.dip2px(mContext, 170));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (ScreenUtil.getScreenHeight(context) * 0.25f));
//        Elog.e("item", "height=" + (int) (ScreenUtil.getScreenHeight(context) * 0.25f));
        rlRoot.setLayoutParams(params);
        View itemNormal = holder.getView(R.id.item_normal);
        View itemZoom = holder.getView(R.id.item_zoom);

        //有警报即typeChild字段不为空，则红色放大显示。(其他颜色暂时不处理，统一显示红色)
        if (!TextUtils.isEmpty(bean.getTypeChild())) {

            itemZoom.setBackgroundResource(R.drawable.bg_warning_one);
//            ivWarningLevelNormal.setImageResource(R.drawable.ic_warning_red);
            ivWarningLevelZoom.setImageResource(R.drawable.ic_warning_red_zoom);
//            tvWarningNoteNormal.setText(MyApplication.getContext().getString(R.string.string_one_fall_down));
            tvThingZoom.setText(bean.getTypeChild());

        }
//         else if (bean.getWarningLevel() == 2) {
//
//            itemZoom.setBackgroundResource(R.drawable.bg_warning_two);
////            ivWarningLevelNormal.setImageResource(R.drawable.ic_warning_yellow);
//            ivWarningLevelZoom.setImageResource(R.drawable.ic_warning_yellow_zoom);
////            tvWarningNoteNormal.setText(MyApplication.getContext().getString(R.string.string_two_fall_down));
//            tvWarningNoteZoom.setText(MyApplication.getContext().getString(R.string.string_two_fall_down));
//        } else if (bean.getWarningLevel() == 3) {
//
//            itemZoom.setBackgroundResource(R.drawable.bg_warning_three);
////            ivWarningLevelNormal.setImageResource(R.drawable.ic_warning_blue);
//            ivWarningLevelZoom.setImageResource(R.drawable.ic_warning_blue_zoom);
////            tvWarningNoteNormal.setText(MyApplication.getContext().getString(R.string.string_three_fall_down));
//            tvWarningNoteZoom.setText(MyApplication.getContext().getString(R.string.string_three_fall_down));
//        }

//        Elog.e("TTT", "percent=" + (bean.getNumF() * 1.f / bean.getNumA()) * 100);
        //如果是警报的前三级，突出放大显示——（设置某些Item放大-把动画时间置为0 缩放比例：（18*2+264）/264=1.136）
        if (!TextUtils.isEmpty(bean.getTypeChild())) {
            if (bean != null) {
                holder.setText(R.id.tv_name_zoom, bean.getUserName() == null ? "" : bean.getUserName());
                holder.setText(R.id.tv_type_nurse_name_zoom, bean.getTypeNurseName());
//                holder.setVisibleHasMesure(R.id.tv_type_child_zoom, (!TextUtils.isEmpty(bean.getTypeChild())) ? true : false);
                //地点
                holder.setText(R.id.tv_address_zoom, TextUtils.isEmpty((bean.getBuildingName() + bean.getFloorName() + bean.getRoomName())) ? "未知" : (bean.getBuildingName() + bean.getFloorName() + bean.getRoomName()));
                holder.setText(R.id.tv_sex_old_zoom, MyApplication.getContext().getString(R.string.string_age) + bean.getAge() + "    " + MyApplication.getContext().getString(R.string.string_sex) + (TextUtils.isEmpty(bean.getSex()) ? "" : bean.getSex()));
                holder.setText(R.id.tv_time_zoom, (bean.getEndTimeMax() == null ? "" : bean.getEndTimeMax()) + "");
                //心跳呼吸
                holder.setText(R.id.tv_heartbeat_zoom, bean.getHeartbeat());
                holder.setText(R.id.tv_breath_zoom, bean.getBreath());
                holder.setText(R.id.tv_status_zoom, bean.getStatus());
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.ic_user_avatar)
                        .error(R.drawable.ic_user_avatar)
                        .priority(Priority.HIGH)
                        .transform(new CircleCrop());
                Glide.with(MyApplication.getContext())
                        .load(API.IMG_SERVER_IP + bean.getHeadImg())
                        .apply(requestOptions)
                        .into(ivUserAvatarZoom);
            }

            //设置放大
            ViewCompat.animate(holder.itemView).scaleX(1.15f).scaleY(1.15f).translationY(DP2PX.dip2px(mContext, 5)).setDuration(0).start();
//            ViewCompat.animate(holder.itemView).scaleX(1.18f).scaleY(1.26f).translationY(DP2PX.dip2px(mContext, 5)).setDuration(0).start();
//            ViewCompat.animate(holder.itemView).scaleX(1.18f).scaleY(1.26f).translationY(1).setDuration(0).start();
            //测试item遮挡问题，还未解决，需在实践：https://www.jb51.net/article/138747.htm Android
//            ViewCompat.animate(holder.itemView).scaleX(1.8f).scaleY(1.8f).translationY(10).setDuration(0).start();
            itemNormal.setVisibility(View.GONE);
            itemZoom.setVisibility(View.VISIBLE);
        } else {
            if (bean != null) {
                holder.setText(R.id.tv_name, bean.getUserName());
                holder.setText(R.id.tv_type_nurse_name, bean.getTypeNurseName());
//                holder.setVisibleHasMesure(R.id.tv_type_child, (!TextUtils.isEmpty(bean.getTypeChild())) ? true : false);
                //地点
                holder.setText(R.id.tv_address, TextUtils.isEmpty((bean.getBuildingName() + bean.getFloorName() + bean.getRoomName())) ? "未知" : (bean.getBuildingName() + bean.getFloorName() + bean.getRoomName()));
                holder.setText(R.id.tv_sex_old, MyApplication.getContext().getString(R.string.string_age) + bean.getAge() + "    " + MyApplication.getContext().getString(R.string.string_sex) + (TextUtils.isEmpty(bean.getSex()) ? "" : bean.getSex()));
                holder.setText(R.id.tv_time, (bean.getEndTimeMax() == null ? "" : bean.getEndTimeMax()) + "");
                //心跳呼吸
                holder.setText(R.id.tv_heartbeat, bean.getHeartbeat());
                holder.setText(R.id.tv_breath, bean.getBreath());
                holder.setText(R.id.tv_status, bean.getStatus());
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

            //缩放动画重置
            ViewCompat.animate(holder.itemView).scaleX(1).scaleY(1).translationY(0).setDuration(0).start();
            itemNormal.setVisibility(View.VISIBLE);
            itemZoom.setVisibility(View.GONE);
        }

    }

    private int[] bedStatus = {R.drawable.ic_jingwo, R.drawable.ic_tidong, R.drawable.ic_lichuang, R.drawable.ic_lixian, R.drawable.ic_weiguanlian};
    private int[] bedStatusColors = {R.color.color_4990e3, R.color.color_facf88, R.color.color_c53e3d, R.color.color_bbbbbc};

    private int getBedStatusImage(String status) {
        if (status.equals("静卧")) {
            return R.drawable.ic_jingwo;
        } else if (status.equals("离床")) {
            return R.drawable.ic_lichuang;
        } else if (status.equals("体动")) {
            return R.drawable.ic_tidong;
        } else if (status.equals("离线")) {
            return R.drawable.ic_lixian;
        } else {
            return R.drawable.ic_weiguanlian;
        }
    }

    private int getBedStatusColor(String status) {
        if (status.equals("静卧")) {
            return ContextCompat.getColor(mContext, R.color.color_4990e3);
        } else if (status.equals("体动")) {
            return ContextCompat.getColor(mContext, R.color.color_facf88);
        } else if (status.equals("离床")) {
            return ContextCompat.getColor(mContext, R.color.color_c53e3d);
        } else {
            return ContextCompat.getColor(mContext, R.color.color_bbbbbc);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.viewGroup = parent;
        return super.onCreateViewHolder(parent, viewType);
    }

    /**
     * 改变绘制顺序,让获得焦点的最后绘制,从而浮在其他item上面 http://www.aichengxu.com/view/11147419
     */
    @Override
    public int onGetChildDrawingOrder(int childCount, int i) {
        View focusedChild = viewGroup.getFocusedChild();
        int focusViewIndex = viewGroup.indexOfChild(focusedChild);
        if (focusViewIndex == -1) {
            return i;
        }

        int focusid = focusViewIndex;

        if (focusViewIndex == i) {
            focusid = i;
            return childCount - 1;
        } else if (i == childCount - 1) {
            return focusid;
        } else {
            return i;
        }
    }
}
