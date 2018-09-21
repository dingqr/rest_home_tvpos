package com.smart.tvpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.smart.framework.library.common.utils.DP2PX;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.interfaces.BarChartEntity;

import java.util.ArrayList;
import java.util.List;

public class DashBarChartView extends View {

    private Context mContext;

    //坐标原点
    private int originX;
    private int originY;

    //坐标轴长
    private int XAxisLenth;
    private int YAxisLenth;
    private float XAxisUnitLenth;
    private float YAxisUnitLenth;
    private float yAxisUnitValue;
    private int XAxisTextSize;
    private int YAxisTextSize;

    private int[] yAxisTextList;
    private String[] xAxisTextList;
    private int xAxisTextSpace = 10;//轴与文字之间的间距
    private int yAxisTextSpace = 30;//轴与文字之间的间距

    private Paint mAxisPaint;
//    private Paint[] arrPaintArc;
    private Paint mPaintText;
    private Paint mPaint = null;
    private Paint mPaintWhite = null;

    //柱状条颜色数组
    private int[] barColors;

    private int xStartOffset = 40;//x起始偏移
    private int xEndOffset = 40;//x终点偏移
    private int yEndOffset = 0;//y终点偏移
//    private int barGap = 10;//柱状条之间的间距
    private int barWidth = 14;//柱状条的宽度
    private float barHight;

    private List<BarChartEntity> listData = new ArrayList<BarChartEntity>();

    public DashBarChartView(Context context) {
        this(context, null);
    }

    public DashBarChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
//        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void updateData(List<BarChartEntity> sleepDatas){
        listData = sleepDatas;
        init();
        postInvalidate();
    }

    private void init(){

        if(null == mContext){
            mContext = MyApplication.getContext();
        }

        xAxisTextList = mContext.getResources().getStringArray(R.array.arr_age_indicator);
        barColors = mContext.getResources().getIntArray(R.array.color_group);
        YAxisTextSize = 10;

        //坐标原点位置
        originX = YAxisTextSize + yAxisTextSpace;
        originY = getHeight() - xAxisTextSpace;

        XAxisLenth = this.getWidth() - 20 - originX;
        YAxisLenth = originY -30;
        barHight = YAxisLenth - yEndOffset;

//        if(null != listData && listData.size() > 0){
//            yAxisTextList = listData.get(0).getYAxisTextList();
//        }

        if(null != yAxisTextList){
            XAxisUnitLenth = (XAxisLenth - xStartOffset - xEndOffset) / (xAxisTextList.length - 1);
            YAxisUnitLenth = (YAxisLenth - yEndOffset) / (yAxisTextList.length - 1);
            yAxisUnitValue = YAxisUnitLenth / (yAxisTextList[1] - yAxisTextList[0]);
        }

        //绘制X,Y轴坐标的画笔
        mAxisPaint = new Paint();
        mAxisPaint.setColor(ContextCompat.getColor(mContext, R.color.color_173053));
        mAxisPaint.setStrokeWidth(2);
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setStyle(Paint.Style.STROKE);
        //绘制刻度值文字的画笔
        mPaintText = new Paint();
        mPaintText.setColor(ContextCompat.getColor(mContext, R.color.color_b2cddb));
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(1);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(barWidth);
        PathEffect effect = new DashPathEffect(new float[]{6, 1, 6, 1}, 1);
        mPaint.setPathEffect(effect);

        mPaintWhite = new Paint();
        mPaintWhite.setColor(ContextCompat.getColor(mContext, R.color.color_0d1b40));
        mPaintWhite.setAntiAlias(true);
        mPaintWhite.setStyle(Paint.Style.STROKE);
        mPaintWhite.setStrokeWidth(barWidth);
        mPaintWhite.setPathEffect(effect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();

        drawCoordinate(canvas);

        drawBarView(canvas);
    }


    private void drawCoordinate(Canvas canvas){

//        float offset = barWidth/2;
        // 绘制X,Y 轴
        canvas.drawLine(originX, originY, originX + XAxisLenth, originY, mAxisPaint);
        canvas.drawLine(originX, originY, originX, originY - YAxisLenth, mAxisPaint);

        //绘制Y轴坐标刻度
        if(null == yAxisTextList || yAxisTextList.length < 0){
            return;
        }
        mPaintText.setTextSize(DP2PX.dip2px(mContext, YAxisTextSize));
        for (int i = 0; i < yAxisTextList.length; i++) {
            float endY = originY - YAxisUnitLenth * i + DP2PX.dip2px(mContext, YAxisTextSize) / 2;
            canvas.drawText(yAxisTextList[i] + "", originX - YAxisTextSize - yAxisTextSpace, endY, mPaintText);
        }
        //绘制X轴坐标刻度
//        mPaintText.setTextSize(DP2PX.dip2px(mContext, XAxisTextSize));

//        for (int i = 0; i < xAxisTextList.length; i++){
//            float endX = originX + XAxisUnitLenth * i + xStartOffset;
//            canvas.drawText(xAxisTextList[i], endX, originY + xAxisTextSpace, mPaintText);
//        }
    }

    private void drawBarView(Canvas canvas){

        //按天画柱状条
        for(int i = 0; i < listData.size(); i++){

            int day = i + 1;
            float left = originX + xStartOffset + (day - 1) * XAxisUnitLenth;
            float bottom = originY - 1;
            float topNum = bottom - (listData.get(i).getDataNum() * yAxisUnitValue);
            float top = originY - barHight;

            mPaint.setColor(barColors[i]);
            Path path = new Path();
            path.moveTo(left, bottom);
            path.lineTo(left, topNum);
            canvas.drawPath(path, mPaint);
            Path pathWhite = new Path();
            pathWhite.moveTo(left, topNum);
            pathWhite.lineTo(left, top);
            canvas.drawPath(pathWhite, mPaintWhite);

            mPaintText.setTextSize(DP2PX.dip2px(mContext, YAxisTextSize * 2));
            canvas.drawText(listData.get(i).getDataNum() + "", left + barWidth + 1, topNum, mPaintText);
        }
    }

    public void setYAxisTextList(int[] yAxisTextList) {
        this.yAxisTextList = yAxisTextList;
    }
}
