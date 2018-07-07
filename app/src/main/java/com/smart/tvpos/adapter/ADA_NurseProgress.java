package com.smart.tvpos.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
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
import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.DP2PX;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.UserNurseListEntity;
import com.smart.tvpos.global.API;
import com.smart.tvpos.widgets.GradientProgressBar;

/**
 * Created by JoJo on 2018/6/27.
 * wechat:18510829974
 * description:
 */
//http://ycljf86.iteye.com/blog/2345413  droidtv 开发系列 二 recycleview item放大效果
//https://www.jb51.net/article/138747.htm Android RecyclerView item选中放大被遮挡问题详解
//https://blog.csdn.net/qq_35697312/article/details/53018819?locationNum=8&fps=1    androidTV中使用recyclerview并使其item在获取焦点后获取边框，并伴随放大，凸显效果
public class ADA_NurseProgress extends CommonAdapter<UserNurseListEntity>  {
    private Activity context;
    private int[] normalColors;
    private int[] zoomColors;
    private ViewGroup viewGroup;
    private int mSelectedPosition;
    private final int[] textcolor;

    public ADA_NurseProgress(Activity context) {
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
        ImageView ivWarningLevelZoom = holder.getView(R.id.iv_warning_level_zoom);
        TextView tvWarningNoteNormal = holder.getView(R.id.tv_warning_note_normal);
        TextView tvWarningNoteZoom = holder.getView(R.id.tv_warning_note_zoom);

        //progressBar
        final GradientProgressBar progressBarNormal = holder.getView(R.id.progressBarNormal);
        GradientProgressBar progressBarZoom = holder.getView(R.id.progressBarZoom);
        //设置itemview的高度固定,以防两种类型的Itemview高度不一致，导致列表的item显示间距和item的高度不一致导致的问题
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DP2PX.dip2px(mContext, 140));
        rlRoot.setLayoutParams(params);
        View itemNormal = holder.getView(R.id.item_normal);
        View itemZoom = holder.getView(R.id.item_zoom);

        //根据警报级别设置item的背景
        if (bean.getWarningLevel() == 1) {
            progressBarZoom.setTextColor(textcolor[0]);

            itemZoom.setBackgroundResource(R.drawable.bg_warning_one);
//            ivWarningLevelNormal.setImageResource(R.drawable.ic_warning_red);
            ivWarningLevelZoom.setImageResource(R.drawable.ic_warning_red_zoom);
//            tvWarningNoteNormal.setText(MyApplication.getContext().getString(R.string.string_one_fall_down));
            tvWarningNoteZoom.setText(MyApplication.getContext().getString(R.string.string_one_fall_down));

        } else if (bean.getWarningLevel() == 2) {
            progressBarZoom.setTextColor(textcolor[1]);

            itemZoom.setBackgroundResource(R.drawable.bg_warning_two);
//            ivWarningLevelNormal.setImageResource(R.drawable.ic_warning_yellow);
            ivWarningLevelZoom.setImageResource(R.drawable.ic_warning_yellow_zoom);
//            tvWarningNoteNormal.setText(MyApplication.getContext().getString(R.string.string_two_fall_down));
            tvWarningNoteZoom.setText(MyApplication.getContext().getString(R.string.string_two_fall_down));
        } else if (bean.getWarningLevel() == 3) {
            progressBarZoom.setTextColor(textcolor[2]);

            itemZoom.setBackgroundResource(R.drawable.bg_warning_three);
//            ivWarningLevelNormal.setImageResource(R.drawable.ic_warning_blue);
            ivWarningLevelZoom.setImageResource(R.drawable.ic_warning_blue_zoom);
//            tvWarningNoteNormal.setText(MyApplication.getContext().getString(R.string.string_three_fall_down));
            tvWarningNoteZoom.setText(MyApplication.getContext().getString(R.string.string_three_fall_down));
        }
        Elog.e("TTT", "percent=" + (bean.getNumF() * 1.f / bean.getNumA()) * 100);
        //如果是警报的前三级，突出放大显示——（设置某些Item放大-把动画时间置为0 缩放比例：（18*2+264）/264=1.136）
        if (bean.getWarningLevel() != 0) {
            progressBarZoom.setLeftMargin(20 + 30);
            progressBarZoom.setLineColor(ContextCompat.getColor(mContext, R.color.color_feeed2));
            if (bean != null) {
                holder.setText(R.id.tv_name_zoom, bean.getUserName() == null ? "" : bean.getUserName());
                holder.setText(R.id.tv_type_child_zoom, bean.getTypeChild() == null ? "" : bean.getTypeChild());
                holder.setVisible(R.id.tv_type_child_zoom, (!TextUtils.isEmpty(bean.getTypeChild())) ? true : false);
                //地点
                holder.setText(R.id.tv_address_zoom, TextUtils.isEmpty((bean.getBuildingName() + bean.getFloorName() + bean.getRoomName())) ? "未知" : (bean.getBuildingName() + bean.getFloorName() + bean.getRoomName()));
                holder.setText(R.id.tv_sex_old_zoom, MyApplication.getContext().getString(R.string.string_age) + bean.getAge() + "    " + MyApplication.getContext().getString(R.string.string_sex) + (TextUtils.isEmpty(bean.getSex()) ? "" : bean.getSex()));
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
            //设置放大显示的progressBar
            progressBarZoom.setGradientColor(zoomColors);
            progressBarZoom.setcurrentProgress((bean.getNumF() * 1f / bean.getNumA()) * 100);

            //设置放大
            ViewCompat.animate(holder.itemView).scaleX(1.18f).scaleY(1.18f).translationY(DP2PX.dip2px(mContext, 5)).setDuration(0).start();
//            ViewCompat.animate(holder.itemView).scaleX(1.18f).scaleY(1.26f).translationY(DP2PX.dip2px(mContext, 5)).setDuration(0).start();
//            ViewCompat.animate(holder.itemView).scaleX(1.18f).scaleY(1.26f).translationY(1).setDuration(0).start();
            //测试item遮挡问题，还未解决，需在实践：https://www.jb51.net/article/138747.htm Android
//            ViewCompat.animate(holder.itemView).scaleX(1.8f).scaleY(1.8f).translationY(10).setDuration(0).start();
            itemNormal.setVisibility(View.GONE);
            itemZoom.setVisibility(View.VISIBLE);
        } else {
            //普通的百分比文字的颜色
            progressBarNormal.setTextColor(textcolor[3]);
            progressBarNormal.setLeftMargin(30);
            progressBarNormal.setLineColor(ContextCompat.getColor(mContext, R.color.color_d8dae1));
            if (bean != null) {
                holder.setText(R.id.tv_name, bean.getUserName() == null ? "" : bean.getUserName());
                holder.setText(R.id.tv_type_child, bean.getTypeChild() == null ? "" : bean.getTypeChild());
                holder.setVisible(R.id.tv_type_child, (!TextUtils.isEmpty(bean.getTypeChild())) ? true : false);
                //地点
                holder.setText(R.id.tv_address, TextUtils.isEmpty((bean.getBuildingName() + bean.getFloorName() + bean.getRoomName())) ? "未知" : (bean.getBuildingName() + bean.getFloorName() + bean.getRoomName()));
                holder.setText(R.id.tv_sex_old, MyApplication.getContext().getString(R.string.string_age) + bean.getAge() + "    " + MyApplication.getContext().getString(R.string.string_sex) + (TextUtils.isEmpty(bean.getSex()) ? "" : bean.getSex()));
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
            }
            //设置正常大小显示的progressBar
            progressBarNormal.setGradientColor(normalColors);
            progressBarNormal.setcurrentProgress((bean.getNumF() * 1f / bean.getNumA()) * 100);

            //缩放动画重置
            ViewCompat.animate(holder.itemView).scaleX(1).scaleY(1).translationY(0).setDuration(0).start();
            itemNormal.setVisibility(View.VISIBLE);
            itemZoom.setVisibility(View.GONE);
        }

//        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//
//                if(b){
//                    //zoom in
//                    if(Build.VERSION.SDK_INT>=21) {
//                        ViewCompat.animate(view).scaleX(1.17f).scaleY(1.17f).translationZ(1).start();
//                    }
//
//                    //view.bringToFront();
//                    //zoomin.setFillAfter(true);//这两句基本没用
//
////                    view.startAnimation(zoomin);
//
//                    //recyclerView.requestLayout();会全部抖动下
//
//                    /**下面的 3种 代码是同等效果*/
//
//                    /**1*****/
//                    //recyclerView.invalidate();//recyclerView 是传过来的
//
//                    /**2***因为目前例子 placeholder 上面还有个Layout, layout上面才是 **/
//                    //ViewGroup viewGroup=(ViewGroup) view.getParent().getParent();
//                    //viewGroup.invalidate();
//
//                    /**3*****/
//                    viewGroup.invalidate();
//
//
//                }else{
//                    //restore
//                    if(Build.VERSION.SDK_INT>=21) {
//                        ViewCompat.animate(view).scaleX(1f).scaleY(1f).translationZ(0).start();
//                    }
////                    zoomout.setFillAfter(true);
////                    view.startAnimation(zoomout);
//                }
//
//            }
//        });
    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        this.viewGroup = parent;
//        return super.onCreateViewHolder(parent, viewType);
//    }
//
//    /**
//     * 改变绘制顺序,让获得焦点的最后绘制,从而浮在其他item上面 http://www.aichengxu.com/view/11147419
//     */
//    @Override
//    public int onGetChildDrawingOrder(int childCount, int i) {
//        View focusedChild = viewGroup.getFocusedChild();
//        int focusViewIndex = viewGroup.indexOfChild(focusedChild);
//        if (focusViewIndex == -1) {
//            return i;
//        }
//
//        int focusid = focusViewIndex;
//
//        if (focusViewIndex == i) {
//            focusid = i;
//            return childCount - 1;
//        } else if (i == childCount - 1) {
//            return focusid;
//        } else {
//            return i;
//        }
//    }
}
