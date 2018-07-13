package com.smart.tvpos.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.smart.tvpos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoJo on 2018/6/21.
 * wechat：18510829974
 * description：圆环统计图 :https://blog.csdn.net/XiFangzheng/article/details/78122127
 */
public class RingChartView extends View {

    private Context mContext;
    private Paint mPaint;
    private int mPaintWidth = 0;        // 画笔的宽
    private int topMargin = 25;         // 上边距
    private int leftMargin = 55;        // 左边距
    private Resources mRes;
    private DisplayMetrics dm;
    private int showRateSize = 5; // 展示文字的大小

    private int circleCenterX = 40;     // 圆心点X  要与外圆半径相等
    private int circleCenterY = 40;     // 圆心点Y  要与外圆半径相等

    private int ringOuterRidus = 40;     // 外圆的半径
    private int ringInnerRidus = 25;     // 内圆的半径
    private int ringPointRidus = 40;    // 点所在圆的半径

    private float rate = 0.4f;     //点的外延距离  与  点所在圆半径的长度比率
    private float extendLineWidth = 10;     //点外延后  折的横线的长度
    //    private Context mContext;
//    private Paint mPaint;
//    private int mPaintWidth = 0;        // 画笔的宽
//    private int topMargin = 30;         // 上边距
//    private int leftMargin = 80;        // 左边距
//    private Resources mRes;
//    private DisplayMetrics dm;
//    private int showRateSize = 10; // 展示文字的大小
//
//    private int circleCenterX = 96;     // 圆心点X  要与外圆半径相等
//    private int circleCenterY = 96;     // 圆心点Y  要与外圆半径相等
//
//    private int ringOuterRidus = 96;     // 外圆的半径
//    private int ringInnerRidus = 33;     // 内圆的半径
//    private int ringPointRidus = 80;    // 点所在圆的半径
//
//    private float rate = 0.4f;     //点的外延距离  与  点所在圆半径的长度比率
//    private float extendLineWidth = 20;     //点外延后  折的横线的长度
    private RectF rectF;                // 外圆所在的矩形
    private RectF rectFPoint;           // 点所在的矩形

    private List<Integer> colorList;
    private List<Float> rateList;
    private boolean isRing;
    private boolean isShowCenterPoint;
    private boolean isShowRate;
    //柱状图每块显示的文字
    private List<String> mShowTextList = new ArrayList<>();

    public RingChartView(Context context) {
        super(context, null);
    }

    public RingChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public void setShow(List<Integer> colorList, List<Float> rateList) {
        setShow(colorList, rateList, false);
    }

    public void setShow(List<Integer> colorList, List<Float> rateList, boolean isRing) {
        setShow(colorList, rateList, isRing, false);
    }

    public void setShow(List<Integer> colorList, List<Float> rateList, boolean isRing, boolean isShowRate) {
        setShow(colorList, rateList, isRing, isShowRate, false);
    }

    public void setShow(List<Integer> colorList, List<Float> rateList, boolean isRing, boolean isShowRate, boolean isShowCenterPoint) {
        this.colorList = colorList;
        this.rateList = rateList;
        this.isRing = isRing;
        this.isShowRate = isShowRate;
        this.isShowCenterPoint = isShowCenterPoint;
    }

    private void initView() {
        this.mRes = mContext.getResources();
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        leftMargin = (px2dip(screenWidth) - (2 * circleCenterX)) / 2;

        mPaint.setColor(getResources().getColor(R.color.color_red));
        mPaint.setStrokeWidth(dip2px(mPaintWidth));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        rectF = new RectF(dip2px(mPaintWidth + leftMargin),
                dip2px(mPaintWidth + topMargin),
                dip2px(circleCenterX + ringOuterRidus + mPaintWidth * 2 + leftMargin),
                dip2px(circleCenterY + ringOuterRidus + mPaintWidth * 2 + topMargin));

        rectFPoint = new RectF(dip2px(mPaintWidth + leftMargin + (ringOuterRidus - ringPointRidus)),
                dip2px(mPaintWidth + topMargin + (ringOuterRidus - ringPointRidus)),
                dip2px(circleCenterX + ringPointRidus + mPaintWidth * 2 + leftMargin),
                dip2px(circleCenterY + ringPointRidus + mPaintWidth * 2 + topMargin));

        Log.e("矩形点:", dip2px(circleCenterX + ringOuterRidus + mPaintWidth * 2) + " --- " + dip2px(circleCenterY + ringOuterRidus + mPaintWidth * 2));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pointList.clear();
        if (colorList != null) {
            for (int i = 0; i < colorList.size(); i++) {
                mPaint.setColor(mRes.getColor(colorList.get(i)));
                mPaint.setStyle(Paint.Style.FILL);
                drawOuter(canvas, i);
            }
        }
        mPaint.setStyle(Paint.Style.FILL);
        if (isRing) {
            drawInner(canvas);
        }
        if (isShowCenterPoint) {
            drawCenterPoint(canvas);
        }

    }

    private void drawCenterPoint(Canvas canvas) {
        mPaint.setColor(mRes.getColor(R.color.color_red));
//        Log.e("中心点:", dip2px(circleCenterX + mPaintWidth * 2 + leftMargin) + " --- " + dip2px(circleCenterY + mPaintWidth * 2 + topMargin));
        canvas.drawCircle(dip2px(circleCenterX + mPaintWidth * 2 + leftMargin), dip2px(circleCenterY + mPaintWidth * 2 + topMargin), dip2px(1), mPaint);
    }

    private void drawInner(Canvas canvas) {
        mPaint.setColor(mRes.getColor(R.color.color_081638));
//        Log.e("内部圆点:", dip2px(circleCenterX + mPaintWidth * 2 + leftMargin) + " --- " + dip2px(circleCenterY + mPaintWidth * 2 + topMargin));
        canvas.drawCircle(dip2px(circleCenterX + mPaintWidth * 2 + leftMargin), dip2px(circleCenterY + mPaintWidth * 2 + topMargin), dip2px(ringInnerRidus), mPaint);
    }

    private float preRate;

    private void drawArcCenterPoint(Canvas canvas, int position) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mRes.getColor(R.color.color_transparent));
        mPaint.setStrokeWidth(1);
        //画圆
        canvas.drawArc(rectFPoint, preAngle, (endAngle) / 2, true, mPaint);
        //处理每块圆饼弧的中心点，绘制折线，显示对应的文字
        dealPoint(rectFPoint, preAngle, (endAngle) / 2, pointArcCenterList);
        Point point = pointArcCenterList.get(position);
        mPaint.setColor(mRes.getColor(R.color.color_FFFFFF));
//        canvas.drawCircle(point.x, point.y, dip2px(2), mPaint);

        if (preRate / 2 + rateList.get(position) / 2 < 5) {
            //将20改成5,外延线的横向距离会缩短
            extendLineWidth += 20;
            rate -= 0.05f;
        } else {
            extendLineWidth = 20;
            rate = 0.4f;
        }

        // 外延画折线
        float lineXPoint1 = (point.x - dip2px(leftMargin + ringOuterRidus)) * (1 + rate);
        float lineYPoint1 = (point.y - dip2px(topMargin + ringOuterRidus)) * (1 + rate);

        float[] floats = new float[8];
        floats[0] = point.x;
        floats[1] = point.y;

//        floats[2] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1;
//        floats[3] = dip2px(topMargin + ringOuterRidus) + lineYPoint1;
//        floats[4] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1;
//        floats[5] = dip2px(topMargin + ringOuterRidus) + lineYPoint1;
        //右半圆
        if (point.x >= dip2px(leftMargin + ringOuterRidus)) {
            mPaint.setTextAlign(Paint.Align.LEFT);
            floats[6] = point.x + 36;
            if (point.y <= dip2px(topMargin + ringOuterRidus)) {
                //右上角
                floats[2] = point.x + 10;
                floats[3] = point.y - 10;
                floats[4] = point.x + 10;
                floats[5] = point.y - 10;
                floats[7] = point.y - 10;
            } else {
                //右下角
                floats[2] = point.x + 10;
                floats[3] = point.y + 10;
                floats[4] = point.x + 10;
                floats[5] = point.y + 10;
                floats[7] = point.y + 10;
            }
            //左半圆
        } else {
            mPaint.setTextAlign(Paint.Align.RIGHT);
            floats[6] = point.x - 36;

            if (point.y <= dip2px(topMargin + ringOuterRidus)) {
                //左上角
                floats[2] = point.x - 10;
                floats[3] = point.y - 10;
                floats[4] = point.x - 10;
                floats[5] = point.y - 10;
                floats[7] = point.y - 10;
            } else {
                //左下角
                floats[2] = point.x - 10;
                floats[3] = point.y + 10;
                floats[4] = point.x - 10;
                floats[5] = point.y + 10;
                floats[7] = point.y + 10;
            }
        }
//        if (point.x >= dip2px(leftMargin + ringOuterRidus)) {
//            mPaint.setTextAlign(Paint.Align.LEFT);
//            //floats[6] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1 + dip2px(extendLineWidth);
//            Log.e("TAG", ">=pointx=" + point.x);
//            floats[6] = point.x + 36;
//        } else {
//            mPaint.setTextAlign(Paint.Align.RIGHT);
//            floats[6] = point.x - 36;
//            Log.e("TAG", "pointx=" + point.x);
//            //floats[6] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1 - dip2px(extendLineWidth);
//        }
        //floats[7] = dip2px(topMargin + ringOuterRidus) + lineYPoint1;
        //根据每块的颜色，绘制对应颜色的折线
//        mPaint.setColor(mRes.getColor(colorList.get(position)));
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.color_b69b4f));
        //画圆饼图每块边上的折线
        canvas.drawLines(floats, mPaint);
        mPaint.setTextSize(dip2px(showRateSize));
        mPaint.setStyle(Paint.Style.STROKE);
        //绘制显示的文字,需要根据类型显示不同的文字
        if (mShowTextList.size() > 0) {
            canvas.drawText(mShowTextList.get(position), floats[6], floats[7] + dip2px(showRateSize) / 3, mPaint);
        }
        preRate = rateList.get(position);
    }

    List<Point> pointList = new ArrayList<>();
    List<Point> pointArcCenterList = new ArrayList<>();

    private void dealPoint(RectF rectF, float startAngle, float endAngle, List<Point> pointList) {
        Path orbit = new Path();
        //通过Path类画一个90度（180—270）的内切圆弧路径
        orbit.addArc(rectF, startAngle, endAngle);

        PathMeasure measure = new PathMeasure(orbit, false);
//        Log.e("路径的测量长度:", "" + measure.getLength());

        float[] coords = new float[]{0f, 0f};
        //利用PathMeasure分别测量出各个点的坐标值coords
        int divisor = 1;
        measure.getPosTan(measure.getLength() / divisor, coords, null);
//        Log.e("coords:", "x轴:" + coords[0] + " -- y轴:" + coords[1]);
        float x = coords[0];
        float y = coords[1];
        Point point = new Point(Math.round(x), Math.round(y));
        pointList.add(point);
    }

    private void drawOuter(Canvas canvas, int position) {
//        canvas.drawCircle(circleCenterX, circleCenterY, ringInnerRidus, mPaint);
        if (rateList != null) {
            endAngle = getAngle(rateList.get(position));
        }
//        Log.e("preAngle:", "" + preAngle + "   endAngle:" + endAngle);
        canvas.drawArc(rectF, preAngle, endAngle, true, mPaint);
//        dealPoint(rectF, preAngle, endAngle, pointList);

        if (isShowRate) {
            drawArcCenterPoint(canvas, position);
        }

        preAngle = preAngle + endAngle;
    }

    private float preAngle = -90;
    private float endAngle = -90;

    /**
     * @param percent 百分比
     * @return
     */
    private float getAngle(float percent) {
        float a = 360f / 100f * percent;
        return a;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        return (int) (dpValue * dm.density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int px2dip(float pxValue) {
        return (int) (pxValue / dm.density + 0.5f);
    }

    public void setShowTextList(List<String> showTextList) {
        this.mShowTextList = showTextList;
//        initView();
        postInvalidate();
    }
}
