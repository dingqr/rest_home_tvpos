package com.yonyou.hhtpos.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.widgets.RightListView;

/**
 * 渐显渐隐动画
 * 作者：liushuofei on 2017/1/11 14:39
 */
public class AnimationUtil {
    //贝塞尔曲线中间过程的点的坐标
    private static float[] mCurrentPosition = new float[2];

    // 渐隐
    private static AlphaAnimation mHideAnimation = null;
    // 渐显
    private static AlphaAnimation mShowAnimation = null;

    /**
     * View渐隐动画效果
     */
    public static void setHideAnimation(View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }

        if (null != mHideAnimation) {
            mHideAnimation.cancel();
        }

        mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
        mHideAnimation.setDuration(duration);
        mHideAnimation.setFillAfter(true);
        view.startAnimation(mHideAnimation);
    }

    /**
     * View渐现动画效果
     */
    public static void setShowAnimation(View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }

        if (null != mShowAnimation) {
            mShowAnimation.cancel();
        }

        mShowAnimation = new AlphaAnimation(0.0f, 1.0f);
        mShowAnimation.setDuration(duration);
        mShowAnimation.setFillAfter(true);
        view.startAnimation(mShowAnimation);
    }


    /**
     * 点菜页面的飞入动画效果
     *
     * @param startView
     * @param refreshPos
     */
    public static void doFlyAnimation(Context mContext, final RelativeLayout rootView, RightListView rightListView, View startView, int refreshPos, int firstVisiblePosition) {
//      一、创造出执行动画的主题---imageview
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        final ImageView goods = new ImageView(mContext);
        goods.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_fly_anim));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(30, 30);
        rootView.addView(goods, params);

//        二、计算动画开始/结束点的坐标的准备工作
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        rootView.getLocationInWindow(parentLocation);

        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        startView.getLocationInWindow(startLoc);

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];


        /**
         * 动画结束位置
         */
        //根据右侧角标刷新的位置，拿到对应的ListView的itemView，作为动画的结束view
        View endView = rightListView.getChildAt(refreshPos - firstVisiblePosition);
        LinearLayout view = (LinearLayout) endView;
        //获取结束位置的角标View对象rl_badge
        final RelativeLayout rl_badge = (RelativeLayout) view.findViewById(R.id.rl_badge);

        if (endView == null) {
//            Log.e("TAG", "endView==null");
            rootView.removeView(goods);
            return;
        }
        endView.getLocationInWindow(endLoc);

//        三、正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + startView.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + startView.getHeight() / 2;

        //商品掉落后的终点坐标：起始点-父布局起始点+终点图片的1/5
        float toX = endLoc[0] - parentLocation[0] + endView.getWidth() / 5 + 80;
        float toY = endLoc[1] - parentLocation[1] + 10;

//        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        final PathMeasure mPathMeasure = new PathMeasure(path, false);

        //属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(500);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                //获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
//      五、 开始执行动画
        valueAnimator.start();

//      六、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                // 把移动的图片imageview从父布局里移除
                rootView.removeView(goods);
                ObjectAnimator.ofFloat(rl_badge, "translationY", 0, 8, -2, 0).setDuration(400).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
