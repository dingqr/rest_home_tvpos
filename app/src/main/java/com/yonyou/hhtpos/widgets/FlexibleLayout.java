package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by ybing on 2017/7/7.
 * 点击radiobutton 显示或者隐藏Viw的控件
 * 原理是在onMeasure中得到隐藏内容的高度，点击这个view的时候
 * 对隐藏的view startAnimation,
 * 让它的高度从0增长到onMeasure得到的这个View的measureHeight
 */

public class FlexibleLayout extends LinearLayout{

    private Context mContext;
    private LinearLayout mHandleView;
    private RelativeLayout mContentView;
    private ImageView mIconExpand;
    int mContentHeight = 0;
    int mTitleHeight = 0;
    private boolean isExpand;
    private Animation animationDown;
    private Animation animationUp;


    public FlexibleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mContentHeight == 0) {
            this.mContentView.measure(widthMeasureSpec, 0);
             this.mContentHeight = this.mContentView.getMeasuredHeight();
          }
        if (this.mTitleHeight == 0) {
            this.mHandleView.measure(widthMeasureSpec, 0);
            this.mTitleHeight = this.mHandleView.getMeasuredHeight();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    class DropDownAnim extends Animation {
        /**
         * 目标的高度
         */
        private int targetHeight;
        /**
         * 目标view
         */
        private View view;
        /**
         * 是否向下展开
         */
        private boolean down;

        /**
         * 构造方法
         *
         * @param targetview 需要被展现的view
         * @param vieweight
         * @param isdown     true:向下展开，false:收起
         */
        public DropDownAnim(View targetview, int vieweight, boolean isdown) {
            this.view = targetview;
            this.targetHeight = vieweight;
            this.down = isdown;
        }

        //down的时候，interpolatedTime从0增长到1，这样newHeight也从0增长到targetHeight
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newHeight;
            if (down) {
                newHeight = (int) (targetHeight * interpolatedTime);
            } else {
                newHeight = (int) (targetHeight * (1 - interpolatedTime));
            }
            view.getLayoutParams().height = newHeight;
            view.requestLayout();
            if (view.getVisibility() == View.GONE) {
                view.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void initialize(int width, int height, int parentWidth,
                               int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

}
