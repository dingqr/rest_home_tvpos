package com.smart.tvpos.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.smart.tvpos.R;
import com.smart.tvpos.util.CommonUtil;

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
    private int topMargin = 45;         // 上边距
    private int leftMargin = 95;        // 左边距
    private Resources mRes;
    private DisplayMetrics dm;
    private int showRateSize = 14; // 展示文字的大小

    private int circleCenterX = 70;     // 圆心点X  要与外圆半径相等
    private int circleCenterY = 70;     // 圆心点Y  要与外圆半径相等

    private int ringOuterRidus = 70;     // 外圆的半径
    private int ringInnerRidus = 50;     // 内圆的半径
    private int ringPointRidus = 70;    // 点所在圆的半径

//    private float rate = 0.4f;     //点的外延距离  与  点所在圆半径的长度比率
//    private float extendLineWidth = 10;     //点外延后  折的横线的长度
    private RectF rectF;                // 外圆所在的矩形
    private RectF rectShadow;                // 外圆所在的矩形
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
        Log.d("aqua", "dm.density:" + dm.density);
        int screenWidth = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        leftMargin = (px2dip(screenWidth) - (2 * circleCenterX)) / 2;

        mPaint.setColor(getResources().getColor(R.color.color_red));
        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        rectF = new RectF(mPaintWidth + leftMargin, mPaintWidth + topMargin,
                circleCenterX + ringOuterRidus + mPaintWidth * 2 + leftMargin,
                circleCenterY + ringOuterRidus + mPaintWidth * 2 + topMargin);

        int shadowLen = (ringOuterRidus - ringInnerRidus) * 2 / 3;
        rectShadow = new RectF(mPaintWidth + leftMargin + shadowLen, mPaintWidth + topMargin + shadowLen,
                circleCenterX + ringOuterRidus + mPaintWidth * 2 + leftMargin - shadowLen,
                circleCenterY + ringOuterRidus + mPaintWidth * 2 + topMargin - shadowLen);

        rectFPoint = new RectF(mPaintWidth + leftMargin + (ringOuterRidus - ringPointRidus),
                mPaintWidth + topMargin + (ringOuterRidus - ringPointRidus),
                circleCenterX + ringPointRidus + mPaintWidth * 2 + leftMargin,
                circleCenterY + ringPointRidus + mPaintWidth * 2 + topMargin);

        Log.e("矩形点:", circleCenterX + ringOuterRidus + mPaintWidth * 2 + " --- " + circleCenterY + ringOuterRidus + mPaintWidth * 2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pointList.clear();
        if (colorList != null && rateList != null) {
            for (int i = 0; i < colorList.size(); i++) {
                mPaint.setColor(mRes.getColor(colorList.get(i)));
                mPaint.setStyle(Paint.Style.FILL);
                drawOuter(canvas, i);
            }
        }
        if(isRing){
            mPaint.setStyle(Paint.Style.FILL);
            drawInner(canvas);
        }
        if (isShowCenterPoint) {
            drawCenterPoint(canvas);
        }

    }

    private void drawCenterPoint(Canvas canvas) {
        mPaint.setColor(mRes.getColor(R.color.color_red));
        canvas.drawCircle(circleCenterX + mPaintWidth * 2 + leftMargin, circleCenterY + mPaintWidth * 2 + topMargin, 1, mPaint);
    }

    private void drawInner(Canvas canvas) {
        mPaint.setColor(mRes.getColor(R.color.color_081638));
        canvas.drawCircle(circleCenterX + mPaintWidth * 2 + leftMargin, circleCenterY + mPaintWidth * 2 + topMargin, ringInnerRidus, mPaint);
    }

    private float preRate;
    private Point lastPoint;

    private void drawArcCenterPoint(Canvas canvas, int position) {
        if (pointArcCenterList.size() > 0) {
            if (position >= 1) {
                lastPoint = pointArcCenterList.get(position - 1);
            } else {
                lastPoint = pointArcCenterList.get(0);
            }
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mRes.getColor(R.color.color_transparent));
        mPaint.setStrokeWidth(1);
        //画圆
        canvas.drawArc(rectFPoint, preAngle, (endAngle) / 2, true, mPaint);
        //处理每块圆饼弧的中心点，绘制折线，显示对应的文字
        dealPoint(rectFPoint, preAngle, (endAngle) / 2, pointArcCenterList);
        Point point = pointArcCenterList.get(position);
        mPaint.setColor(mRes.getColor(R.color.color_FFFFFF));

        float[] floats = new float[8];
        float textX, textY;
        floats[0] = point.x;
        floats[1] = point.y;
        //右半圆
        if (point.x >= leftMargin + ringOuterRidus) {
            mPaint.setTextAlign(Paint.Align.LEFT);
            //防止绘制的文字重叠显示
            if (lastPoint != null) {
                int absX = Math.abs(point.x - lastPoint.x);
                int absY = Math.abs(point.y - lastPoint.y);
                if (absX > 0 && absX < 10 && absY > 0 && absY < 10) {
                    floats[6] = point.x + 5;
                } else {
                    floats[6] = point.x + 25;
                }
            } else {
                floats[6] = point.x + 25;
            }

            textX = floats[6];
            if (point.y <= topMargin + ringOuterRidus) {
                //右上角
                floats[2] = point.x + 10;
                floats[3] = point.y - 10;
                floats[4] = point.x + 10;
                floats[5] = point.y - 10;
                floats[7] = point.y - 10;
                textY = point.y - 20;
            } else {
                //右下角
                floats[2] = point.x + 10;
                floats[3] = point.y + 10;
                floats[4] = point.x + 10;
                floats[5] = point.y + 10;
                floats[7] = point.y + 10;
                textY = point.y;
            }
            //左半圆
        } else {
            mPaint.setTextAlign(Paint.Align.RIGHT);
//            floats[6] = point.x - 36;
            //防止绘制的文字重叠显示
            if (lastPoint != null) {
                int absX = Math.abs(point.x - lastPoint.x);
                int absY = Math.abs(point.y - lastPoint.y);
                if (absX > 0 && absX < 10 && absY > 0 && absY < 10) {
                    floats[6] = point.x - 5;
                } else {
                    floats[6] = point.x - 25;
                }
            } else {
                floats[6] = point.x - 25;
            }
            textX = floats[6] - showRateSize * 4;
            if (point.y <= topMargin + ringOuterRidus) {
                //左上角
                floats[2] = point.x - 10;
                floats[3] = point.y - 10;
                floats[4] = point.x - 10;
                floats[5] = point.y - 10;
                floats[7] = point.y - 10;
                textY = point.y - 20;
            } else {
                //左下角
                floats[2] = point.x - 10;
                floats[3] = point.y + 10;
                floats[4] = point.x - 10;
                floats[5] = point.y + 10;
                floats[7] = point.y + 10;
                textY = point.y;
            }
        }
        //根据每块的颜色，绘制对应颜色的折线
//        mPaint.setColor(mRes.getColor(colorList.get(position)));
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.color_b69b4f));
        //画圆饼图每块边上的折线
        canvas.drawLines(floats, mPaint);
        mPaint.setTextSize(showRateSize);
        mPaint.setStyle(Paint.Style.STROKE);
        //绘制显示的文字,需要根据类型显示不同的文字
        if (mShowTextList.size() > 0) {
//            canvas.drawText(mShowTextList.get(position), floats[6], floats[7] + dip2px(showRateSize) / 3, mPaint);

            TextPaint textPaint = new TextPaint();
            textPaint.setColor(ContextCompat.getColor(mContext, R.color.color_FFFFFF));
            textPaint.setTextSize(showRateSize);
            textPaint.setAntiAlias(true);
            StaticLayout layout = new StaticLayout(mShowTextList.get(position), textPaint, 300,
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            canvas.save();
            canvas.translate(textX,  textY);
            layout.draw(canvas);
            canvas.restore();
            textPaint.setColor(ContextCompat.getColor(mContext, R.color.color_b69b4f));
            StaticLayout layoutRate = new StaticLayout(rateList.get(position) + mContext.getString(R.string.symbol_percent),
                    textPaint, 300, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            canvas.save();
            canvas.translate(textX,  textY + 13);
            layoutRate.draw(canvas);
            canvas.restore();
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
        float x = coords[0];
        float y = coords[1];
        Point point = new Point(Math.round(x), Math.round(y));
        pointList.add(point);
    }

    private void drawOuter(Canvas canvas, int position) {
//        canvas.drawCircle(circleCenterX, circleCenterY, ringInnerRidus, mPaint);
        if(position > rateList.size() - 1){
            return;
        }
        if (rateList != null) {
            endAngle = getAngle(rateList.get(position));
        }
//        Log.e("preAngle:", "" + preAngle + "   endAngle:" + endAngle);
        canvas.drawArc(rectF, preAngle, endAngle, true, mPaint);
//        dealPoint(rectF, preAngle, endAngle, pointList);

        if(isRing){
            mPaint.setColor(CommonUtil.getColorWithAlpha(0.3f, mRes.getColor(R.color.color_transparent)));
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawArc(rectShadow, preAngle, endAngle, true, mPaint);
        }

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
//    public int dip2px(float dpValue) {
//        return (int) (dpValue * dm.density + 0.5f);
//    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int px2dip(float pxValue) {
        return (int) (pxValue / dm.density + 0.5f);
    }

    public void setShowTextList(List<String> showTextList) {
        this.mShowTextList = showTextList;
        initView();
        postInvalidate();
    }

    public void setCommonSize(int topMargin, int leftMargin, int ringOuterRidus, int ringInnerRidus){
        this.topMargin = topMargin;         // 上边距
        this.leftMargin = leftMargin;        // 左边距

        this.circleCenterX = ringOuterRidus;     // 圆心点X  要与外圆半径相等
        this.circleCenterY = ringOuterRidus;     // 圆心点Y  要与外圆半径相等
        this.ringPointRidus = ringOuterRidus;

        this.ringOuterRidus = ringOuterRidus;     // 外圆的半径
        this.ringInnerRidus = ringInnerRidus;     // 内圆的半径
    }
}
