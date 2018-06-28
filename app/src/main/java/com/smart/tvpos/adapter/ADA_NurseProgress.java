package com.smart.tvpos.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.smart.framework.library.adapter.rv.CommonAdapter;
import com.smart.framework.library.adapter.rv.ViewHolder;
import com.smart.framework.library.common.utils.DP2PX;
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
public class ADA_NurseProgress extends CommonAdapter<UserNurseListEntity> implements RecyclerView.ChildDrawingOrderCallback {
    private ViewGroup viewGroup;
    private int mSelectedPosition;

    public ADA_NurseProgress(Context context) {
        super(context);
    }

    @Override
    protected int itemLayoutId() {
        return R.layout.item_nurse_progress;
    }

    @Override
    protected void convert(final ViewHolder holder, UserNurseListEntity bean, final int position) {
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
        }
        //设置itemview的高度固定,以防两种类型的Itemview高度不一致，导致列表的item显示间距和item的高度不一致导致的问题
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DP2PX.dip2px(mContext, 132));
        rlRoot.setLayoutParams(params);
        View itemNormal = holder.getView(R.id.item_normal);
        View itemZoom = holder.getView(R.id.item_zoom);
        if (bean.getWarningLevel() == 1) {
            itemZoom.setBackgroundResource(R.drawable.bg_warning_one);
        } else if (bean.getWarningLevel() == 2) {
            itemZoom.setBackgroundResource(R.drawable.bg_warning_two);
        } else {
            itemZoom.setBackgroundResource(R.drawable.bg_warning_three);
        }
        //设置某些Item放大-把动画时间置为0 缩放比例：（18*2+264）/264=1.136
        if (bean.getWarningLevel() != 0) {
                        ViewCompat.animate(holder.itemView).scaleX(1.18f).scaleY(1.26f).translationY(1).setDuration(0).start();
//            ViewCompat.animate(holder.itemView).scaleX(1.18f).scaleY(1.26f).translationY(1).setDuration(0).start();
            //测试item遮挡问题，还未解决，需在实践：https://www.jb51.net/article/138747.htm Android
//            ViewCompat.animate(holder.itemView).scaleX(1.8f).scaleY(1.8f).translationY(10).setDuration(0).start();
            itemNormal.setVisibility(View.GONE);
            itemZoom.setVisibility(View.VISIBLE);
        } else {
            itemZoom.setVisibility(View.GONE);
            itemNormal.setVisibility(View.VISIBLE);
            ViewCompat.animate(holder.itemView).scaleX(1).scaleY(1).setDuration(0).translationZ(1).start();
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
