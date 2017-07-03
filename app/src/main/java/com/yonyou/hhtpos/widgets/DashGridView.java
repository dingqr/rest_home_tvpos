package com.yonyou.hhtpos.widgets;

/**
 * Created by zj on 2017/6/26.
 * 邮箱：zjuan@yonyou.com
 * 描述：自定义带虚线网格的GridView
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridView;

import com.yonyou.hhtpos.R;

public class DashGridView extends GridView {
    //实线的画笔
    private Paint mSolidLinePaint;
    //虚线的画笔
    private Paint mLinePaint;
    private Path mPath;
    //虚线的颜色
    private int mDashColor = Color.parseColor("#cccccc");

    //虚线之间的间隔
    private int mDashSpace = 2;
    //虚线的宽度
    private int mDashWidth = 4;
    //虚线粗细
    private float mDashStroke = 0.3f;
    //实线颜色
    private int mSolidLineColor;
    //实线高度
    private int mSolidLineHeight;
    //是否需要边缘线
    private boolean isDrawEdge;

    //画实线还是画虚线 -true:虚线
    private boolean isDash;

    public DashGridView(Context context) {
        this(context, null);
    }

    public DashGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.dashgridview, 0, 0);
            isDash = a.getBoolean(R.styleable.dashgridview_is_dash, true);
            mSolidLineColor = a.getColor(R.styleable.dashgridview_solid_line_color,Color.RED);
            a.recycle();//回收内存
        }
        init(context);
    }

    private void init(Context context) {
        //实线的画笔
        mSolidLinePaint = new Paint();
        mSolidLinePaint.setStyle(Paint.Style.STROKE);
        mSolidLinePaint.setColor(mSolidLineColor);
        //虚线的画笔
        mLinePaint = new Paint();
        mLinePaint.reset();
        mLinePaint.setStyle(Paint.Style.STROKE);
        //控制虚线的粗细
        mLinePaint.setStrokeWidth(mDashStroke);
        //控制虚线的颜色
        mLinePaint.setColor(mDashColor);
        mLinePaint.setAntiAlias(true);
        DashPathEffect pathEffect = new DashPathEffect(new float[]{dp2px(mDashWidth), dp2px(mDashSpace)}, mDashStroke);
        mLinePaint.setPathEffect(pathEffect);

        mPath = new Path();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isDash) {
            //画虚线
            drawLine(canvas);
        } else {
            //画实线
            draw9line(canvas);
        }
    }

    /**
     * @param canvas
     * @des 绘制实线田字格
     */
    private void draw9line(Canvas canvas) {
        //不设置左内边距，会导致最右边的实线画不出来
        setPadding(0,0,1,1);

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int left = view.getLeft();
            int top = view.getTop();
            int right = view.getRight();
            int bottom = view.getBottom();
            //画竖直实线
            canvas.drawLine(left, top, left, bottom, mSolidLinePaint);
            //画最后一竖行和最后一竖格
            if ((i + 1) % (getNumColumns()) == 0 || i == childCount - 1) {
                //画竖直
                canvas.drawLine(right, top, right, bottom, mSolidLinePaint);
            }
            //画横向实线
            canvas.drawLine(left, top, right, top, mSolidLinePaint);
            //画横向底部的实线
            if (i >= childCount - getNumColumns() * (getNumColumns() - 1) - 1) {
                canvas.drawLine(left, bottom, right, bottom, mSolidLinePaint);
            }
        }
    }


    /**
     * @param canvas
     * @des 在item周围绘制虚线
     */
    private void drawLine(Canvas canvas) {
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int left = view.getLeft();
            int top = view.getTop();
            int right = view.getRight();
            int bottom = view.getBottom();

            //画竖直虚线
            mPath.moveTo(left, top);
            mPath.lineTo(left, bottom);
            canvas.drawPath(mPath, mLinePaint);
            mPath.reset();

            //画横向虚线
            mPath.moveTo(left, top);
            mPath.lineTo(right, top);
            canvas.drawPath(mPath, mLinePaint);
            mPath.reset();

            //画最后一行虚线
            if (i >= childCount - getNumColumns() * (getNumColumns() - 1) - 1) {
                //画横向底部的虚线
                mPath.moveTo(left, bottom);
                mPath.lineTo(right, bottom);
                canvas.drawPath(mPath, mLinePaint);
                mPath.reset();
            }
            //画最后一根竖直虚线,加上画最后一个view的竖直虚线
            if ((i + 1) % (getNumColumns()) == 0 || i == childCount - 1) {

                mPath.moveTo(right, top);
                mPath.lineTo(right, bottom);
                canvas.drawPath(mPath, mLinePaint);
                mPath.reset();
            }

        }
    }

//    /**
//     * 禁止GridView滑动
//     * @param widthMeasureSpec
//     * @param heightMeasureSpec
//     */
//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int expandSpec = MeasureSpec.makeMeasureSpec(
//                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources()
                .getDisplayMetrics());
    }

    public void setmDashColor(int mDashColor) {
        this.mDashColor = mDashColor;
    }

    public void setmDashSpace(int mDashSpace) {
        this.mDashSpace = mDashSpace;
    }

    public void setmDashWidth(int mDashWidth) {
        this.mDashWidth = mDashWidth;
    }

    public void setmDashStroke(int mDashStroke) {
        this.mDashStroke = mDashStroke;
    }

    public void setDash(boolean dash) {
        isDash = dash;
    }
}
