package com.yonyou.hhtpos.widgets;

/**
 * Created by zj on 2017/6/26.
 * 邮箱：zjuan@yonyou.com
 * 描述：自定义带虚线网格的GridView
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridView;

public class DashGridView extends GridView {

    private Paint mPaint;
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

    public DashGridView(Context context) {
        super(context);
        init(context);
    }

    public DashGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DashGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
//        mPaint = new Paint();
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(Color.parseColor("#ff0000"));

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
        //画虚线
        drawLine(canvas);
        //画实线
//        draw9line(canvas);
    }

    /**
     * @param canvas
     * @des 绘制田字格
     */
    private void draw9line(Canvas canvas) {
        View localView1 = getChildAt(0);
        int column = getWidth() / localView1.getWidth();
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int left = view.getLeft();
            int right = view.getRight();
            int top = view.getTop();
            int bottom = view.getBottom();
            if ((i + 1) % column == 0) {
                canvas.drawLine(left, bottom, right, bottom, mPaint);
            } else if ((i + 1) > (childCount - (childCount % column))) {
                canvas.drawLine(right, top, right, bottom, mPaint);
            } else {
                canvas.drawLine(right, top, right, bottom, mPaint);
                canvas.drawLine(left, bottom, right, bottom, mPaint);
            }
        }
        if (childCount % column != 0) {
            for (int j = 0; j < (column - childCount % column); j++) {
                View lastView = getChildAt(childCount - 1);
                canvas.drawLine(lastView.getRight() + lastView.getWidth() * j, lastView.getTop(),
                        lastView.getRight() + lastView.getWidth() * j, lastView.getBottom(), mPaint);
            }
        }
    }


    /**
     * @param canvas
     * @des 在item底部绘制虚线
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
}
