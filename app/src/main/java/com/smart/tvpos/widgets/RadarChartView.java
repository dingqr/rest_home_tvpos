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
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.smart.framework.library.common.utils.DP2PX;
import com.smart.tvpos.R;

import java.util.ArrayList;
import java.util.List;

public class RadarChartView extends View {

    private Context mContext;
    private Paint mPaint;
    private int mPaintWidth = 1;        // 画笔的宽
    private int topMargin = 25;         // 上边距
    private int leftMargin = 55;        // 左边距
    private Resources mRes;
    private int showRateSize = 5; // 展示文字的大小

    private int circleCenterX = 40;     // 圆心点X  要与外圆半径相等
    private int circleCenterY = 40;     // 圆心点Y  要与外圆半径相等

    private int ringOuterRidus = 40;     // 外圆的半径

    private List<Float> dataList = new ArrayList<>();
    private boolean isShowCenterPoint = true;
    //刻度文字
    private List<String> mShowTextList = new ArrayList<>();

    private int[] lineColors;

    public RadarChartView(Context context) {
        super(context, null);
    }

    public RadarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

//    public void setShow(List<Integer> colorList, List<Float> rateList) {
//        setShow(colorList, rateList, false);
//    }
//
//    public void setShow(List<Integer> colorList, List<Float> rateList, boolean isRing) {
//        setShow(colorList, rateList, isRing, false);
//    }
//
//    public void setShow(List<Integer> colorList, List<Float> rateList, boolean isRing, boolean isShowRate) {
//        setShow(colorList, rateList, isRing, isShowRate, false);
//    }
//
//    public void setShow(List<Integer> colorList, List<Float> rateList, boolean isRing, boolean isShowRate, boolean isShowCenterPoint) {
//        this.rateList = rateList;
//        this.isShowCenterPoint = isShowCenterPoint;
//    }

    private void initView() {
        this.mRes = mContext.getResources();
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setStrokeWidth(DP2PX.dip2px(mContext, mPaintWidth));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        lineColors = mRes.getIntArray(R.array.color_group);
//
//        rectFPoint = new RectF(DP2PX.dip2px(mContext, mPaintWidth + leftMargin + (ringOuterRidus - ringPointRidus)),
//                DP2PX.dip2px(mContext, mPaintWidth + topMargin + (ringOuterRidus - ringPointRidus)),
//                DP2PX.dip2px(mContext, circleCenterX + ringPointRidus + mPaintWidth * 2 + leftMargin),
//                DP2PX.dip2px(mContext, circleCenterY + ringPointRidus + mPaintWidth * 2 + topMargin));

        for(int i = 0; i<5; i++){
            mShowTextList.add(i * 30 + "");
        }

        for(int i = 0; i<20; i++){
            dataList.add(10f + 5 * i);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawRadarNet(canvas);

        drawDataLine(canvas);

        if (isShowCenterPoint) {
            drawCenterPoint(canvas);
        }
    }

    private void drawDataLine(Canvas canvas) {

        int lineNum = dataList.size();
        int maxValue = Integer.parseInt(mShowTextList.get(mShowTextList.size() - 1));
        int alfX = 270 / lineNum;
        for(int i = 1; i < lineNum ; i++){
            int dlta = alfX * i;
            float lineLen = dataList.get(i) * circleCenterX / maxValue;

            int px, py;
            if(dlta < 90) {
                px = (int) (leftMargin + ringOuterRidus + Math.abs(lineLen * Math.cos(Math.PI*dlta/180)));
                py = (int) (topMargin + ringOuterRidus + Math.abs(lineLen * Math.sin(Math.PI*dlta/180)));
            }
            else if(dlta < 180){
                px = (int) (leftMargin + ringOuterRidus - Math.abs(lineLen * Math.cos(Math.PI*dlta/180)));
                py = (int) (topMargin + ringOuterRidus + Math.abs(lineLen * Math.sin(Math.PI*dlta/180)));
            }
            else {
                px = (int) (leftMargin + ringOuterRidus - Math.abs(lineLen * Math.cos(Math.PI*dlta/180)));
                py = (int) (topMargin + ringOuterRidus - Math.abs(lineLen * Math.sin(Math.PI*dlta/180)));
            }

            mPaint.setColor(lineColors[i % lineColors.length]);
            mPaint.setStrokeWidth(3);
            canvas.drawLine(DP2PX.dip2px(mContext, circleCenterX + leftMargin), DP2PX.dip2px(mContext, circleCenterY + topMargin),
                    DP2PX.dip2px(mContext, px), DP2PX.dip2px(mContext, py), mPaint);
        }
//        int maxValue = Integer.parseInt(mShowTextList.get(mShowTextList.size() - 1));
//        int dltaX = ringOuterRidus * 2 / lineNum;
//        for(int i = -lineNum / 2, j = 0; j < lineNum; i++, j++){
//            float lineLen = dataList.get(j) * circleCenterX / maxValue;
//
//            int px, py;
//            if(lineLen < Math.abs(dltaX * i)) {
//                px = leftMargin + ringOuterRidus - (int) (dltaX * i - lineLen);
//                int yLen = (int) Math.sqrt(Math.pow(dltaX * i, 2) - Math.pow(lineLen, 2));
//                py = topMargin + ringOuterRidus + yLen;
//            }
//            else {
//                px = leftMargin + ringOuterRidus + dltaX * i;
//                int yLen = (int) Math.sqrt(Math.pow(lineLen, 2) - Math.pow(dltaX * i, 2));
//                if(i > 0){
//                    py = topMargin + ringOuterRidus + yLen;
//                }
//                else {
//                    py = topMargin + ringOuterRidus - yLen;
//                }
//            }
//
//            mPaint.setColor(lineColors[j % lineColors.length]);
//            mPaint.setStrokeWidth(3);
//            canvas.drawLine(DP2PX.dip2px(mContext, circleCenterX + leftMargin), DP2PX.dip2px(mContext, circleCenterY + topMargin),
//                    DP2PX.dip2px(mContext, px), DP2PX.dip2px(mContext, py), mPaint);
//        }
    }

    private void drawRadarNet(Canvas canvas) {

        mPaint.setColor(mRes.getColor(R.color.color_12284c));
        mPaint.setStyle(Paint.Style.STROKE);
        float unitLen = ringOuterRidus / mShowTextList.size();
        for(int i = 0 ; i < mShowTextList.size(); i++){

            RectF rectIn = new RectF(DP2PX.dip2px(mContext,mPaintWidth + leftMargin + unitLen * i),
                    DP2PX.dip2px(mContext, mPaintWidth + topMargin + unitLen * i),
                    DP2PX.dip2px(mContext, circleCenterX + ringOuterRidus + mPaintWidth * 2 + leftMargin - unitLen * i),
                    DP2PX.dip2px(mContext, circleCenterY + ringOuterRidus + mPaintWidth * 2 + topMargin - unitLen *i));
            for(int j = 0; j < 6; j++){
                canvas.drawArc(rectIn, j * 45, 45, true, mPaint);
            }
        }
    }

    private void drawCenterPoint(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mRes.getColor(R.color.color_bfbfbf));
        canvas.drawCircle(DP2PX.dip2px(mContext, circleCenterX + mPaintWidth * 2 + leftMargin),
                DP2PX.dip2px(mContext, circleCenterY + mPaintWidth * 2 + topMargin),
                DP2PX.dip2px(mContext, 3), mPaint);
    }

//    private float preRate;
//    private Point lastPoint;

//    private void drawArcCenterPoint(Canvas canvas, int position) {
//        if (pointArcCenterList.size() > 0) {
//            if (position >= 1) {
//                lastPoint = pointArcCenterList.get(position - 1);
//            } else {
//                lastPoint = pointArcCenterList.get(0);
//            }
//        }
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(mRes.getColor(R.color.color_transparent));
//        mPaint.setStrokeWidth(1);
//        //画圆
//        canvas.drawArc(rectFPoint, preAngle, (endAngle) / 2, true, mPaint);
//        //处理每块圆饼弧的中心点，绘制折线，显示对应的文字
//        dealPoint(rectFPoint, preAngle, (endAngle) / 2, pointArcCenterList);
//        Point point = pointArcCenterList.get(position);
//        mPaint.setColor(mRes.getColor(R.color.color_FFFFFF));
////        canvas.drawCircle(point.x, point.y, DP2PX.dip2px(mContext, 2), mPaint);
//
//        if (preRate / 2 + rateList.get(position) / 2 < 5) {
//            //将20改成5,外延线的横向距离会缩短
////            extendLineWidth += 20;
//            rate -= 0.05f;
//        } else {
////            extendLineWidth = 20;
//            rate = 0.4f;
//        }
//
//        // 外延画折线
////        float lineXPoint1 = (point.x - DP2PX.dip2px(mContext, leftMargin + ringOuterRidus)) * (1 + rate);
////        float lineYPoint1 = (point.y - DP2PX.dip2px(mContext, topMargin + ringOuterRidus)) * (1 + rate);
//
//        float[] floats = new float[8];
//        floats[0] = point.x;
//        floats[1] = point.y;
//        //右半圆
//        if (point.x >= DP2PX.dip2px(mContext, leftMargin + ringOuterRidus)) {
//            mPaint.setTextAlign(Paint.Align.LEFT);
//            //防止绘制的文字重叠显示
//            if (lastPoint != null) {
//                int absX = Math.abs(point.x - lastPoint.x);
//                int absY = Math.abs(point.y - lastPoint.y);
//                if (absX > 0 && absX < 10 && absY > 0 && absY < 10) {
//                    floats[6] = point.x + 16;
//                } else {
//                    floats[6] = point.x + 36;
//                }
//            } else {
//                floats[6] = point.x + 36;
//            }
//            if (point.y <= DP2PX.dip2px(mContext, topMargin + ringOuterRidus)) {
//                //右上角
//                floats[2] = point.x + 10;
//                floats[3] = point.y - 10;
//                floats[4] = point.x + 10;
//                floats[5] = point.y - 10;
//                floats[7] = point.y - 10;
//            } else {
//                //右下角
//                floats[2] = point.x + 10;
//                floats[3] = point.y + 10;
//                floats[4] = point.x + 10;
//                floats[5] = point.y + 10;
//                floats[7] = point.y + 10;
//            }
//            //左半圆
//        } else {
//            mPaint.setTextAlign(Paint.Align.RIGHT);
////            floats[6] = point.x - 36;
//            //防止绘制的文字重叠显示
//            if (lastPoint != null) {
//                int absX = Math.abs(point.x - lastPoint.x);
//                int absY = Math.abs(point.y - lastPoint.y);
//                if (absX > 0 && absX < 10 && absY > 0 && absY < 10) {
//                    floats[6] = point.x - 16;
//                } else {
//                    floats[6] = point.x - 36;
//                }
//            } else {
//                floats[6] = point.x - 36;
//            }
//            if (point.y <= DP2PX.dip2px(mContext, topMargin + ringOuterRidus)) {
//                //左上角
//                floats[2] = point.x - 10;
//                floats[3] = point.y - 10;
//                floats[4] = point.x - 10;
//                floats[5] = point.y - 10;
//                floats[7] = point.y - 10;
//            } else {
//                //左下角
//                floats[2] = point.x - 10;
//                floats[3] = point.y + 10;
//                floats[4] = point.x - 10;
//                floats[5] = point.y + 10;
//                floats[7] = point.y + 10;
//            }
//        }
//        //根据每块的颜色，绘制对应颜色的折线
////        mPaint.setColor(mRes.getColor(colorList.get(position)));
//        mPaint.setColor(ContextCompat.getColor(mContext, R.color.color_b69b4f));
//        //画圆饼图每块边上的折线
//        canvas.drawLines(floats, mPaint);
//        mPaint.setTextSize(DP2PX.dip2px(mContext, showRateSize));
//        mPaint.setStyle(Paint.Style.STROKE);
//        //绘制显示的文字,需要根据类型显示不同的文字
//        if (mShowTextList.size() > 0) {
//            canvas.drawText(mShowTextList.get(position), floats[6], floats[7] + DP2PX.dip2px(mContext, showRateSize) / 3, mPaint);
//        }
//        preRate = rateList.get(position);
//    }

//    List<Point> pointList = new ArrayList<>();
//    List<Point> pointArcCenterList = new ArrayList<>();
//
//    private void dealPoint(RectF rectF, float startAngle, float endAngle, List<Point> pointList) {
//        Path orbit = new Path();
//        //通过Path类画一个90度（180—270）的内切圆弧路径
//        orbit.addArc(rectF, startAngle, endAngle);
//
//        PathMeasure measure = new PathMeasure(orbit, false);
////        Log.e("路径的测量长度:", "" + measure.getLength());
//
//        float[] coords = new float[]{0f, 0f};
//        //利用PathMeasure分别测量出各个点的坐标值coords
//        int divisor = 1;
//        measure.getPosTan(measure.getLength() / divisor, coords, null);
//        float x = coords[0];
//        float y = coords[1];
//        Point point = new Point(Math.round(x), Math.round(y));
//        pointList.add(point);
//    }
//
//    private float preAngle = -90;
//    private float endAngle = -90;

//    /**
//     * @param percent 百分比
//     * @return
//     */
//    private float getAngle(float percent) {
//        float a = 360f / 100f * percent;
//        return a;
//    }


    public void setShowTextList(List<String> showTextList) {
        this.mShowTextList = showTextList;
//        initView();
        postInvalidate();
    }
}
