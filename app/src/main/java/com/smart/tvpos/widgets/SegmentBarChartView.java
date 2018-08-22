package com.smart.tvpos.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.DP2PX;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.SleepDataEntity;
import com.smart.tvpos.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class SegmentBarChartView extends View {

    private Context mContext;

    //坐标原点
    private int originX;
    private int originY;

    //坐标轴长
    private int XAxisLenth;
    private int YAxisLenth;
    private float XAxisUnitLenth;
    private float YAxisUnitLenth;
    private int XAxisTextSize;
    private int YAxisTextSize;

    private int[] yAxisTextList = {0, 4, 8, 12, 16, 20, 24};
    private String[] xAxisTextList = DateUtils.WEEK;
    private int xAxisTextSpace = 22;//轴与文字之间的间距
    private int yAxisTextSpace = 22;//轴与文字之间的间距
    private  String yUnit = "h";

    private Paint mAxisPaint;
    private Paint[] arrPaintArc;
    private Paint mPaintText;
    private Paint mPaintWhite = null;

    //分段柱状条颜色数组
    private int[] segmentColors;

    private int lnkeduSpace = 36; //刻度间距
    private int barGap = 50;//柱状条之间的间距
    private int barWidth = 18;//柱状条的宽度
    private float barHight;
//    //数据值
//    private int[] valueDatas = new int[]{};
    //每个刻度对应的高度值
    private int valueSpace = 40;

    private List<SleepDataEntity> weekData = new ArrayList<SleepDataEntity>();

    public SegmentBarChartView(Context context) {
        this(context, null);
    }

    public SegmentBarChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SegmentBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();

        initData();
    }

    private void initData(){
//test
        for(int i = 0; i < 7; i++){
            SleepDataEntity dayData = new SleepDataEntity(3.5f, 5.1f, i+1);
            weekData.add(dayData);
        }
    }

    public void updateData(List<SleepDataEntity> sleepDatas){
        weekData = sleepDatas;
        init();
        postInvalidate();
    }

    private void init(){

        segmentColors = new int[]{ContextCompat.getColor(mContext, R.color.color_deep_sleep),
                ContextCompat.getColor(mContext, R.color.color_light_sleep), ContextCompat.getColor(mContext, R.color.color_sober)};

//        XAxisLenth = DP2PX.dip2px(mContext, 524);
//        YAxisLenth = DP2PX.dip2px(mContext, 228);
//        XAxisTextSize = DP2PX.dip2px(mContext, 18);
//        YAxisTextSize = DP2PX.dip2px(mContext, 16);
        XAxisLenth = (barGap + barWidth) * 7 + 20;
        YAxisLenth = 228 + xAxisTextSpace + XAxisTextSize;
        XAxisTextSize = 9;
        YAxisTextSize = 8;
        barHight = YAxisLenth - 20;

        XAxisUnitLenth = (XAxisLenth - 20) / xAxisTextList.length;
        YAxisUnitLenth = YAxisLenth / yAxisTextList.length;
        //坐标原点位置
        originX = YAxisTextSize + yAxisTextSpace + 36;
        originY = YAxisLenth - xAxisTextSpace - XAxisTextSize;

        //绘制X,Y轴坐标的画笔
        mAxisPaint = new Paint();
        mAxisPaint.setColor(ContextCompat.getColor(mContext, R.color.color_808080));
        mAxisPaint.setStrokeWidth(2);
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setStyle(Paint.Style.STROKE);
        //绘制刻度值文字的画笔
        mPaintText = new Paint();
        mPaintText.setColor(ContextCompat.getColor(mContext, R.color.color_8a8a8a));
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(1);

        mPaintWhite = new Paint();
        //文字大小10px
//        mPaintWhite.setTextSize(DP2PX.dip2px(mContext, 5));
        mPaintWhite.setColor(ContextCompat.getColor(mContext, R.color.color_a9c6d6));
        mPaintWhite.setAntiAlias(true);
        mPaintWhite.setStrokeWidth(1);

        arrPaintArc = new Paint[segmentColors.length];
        for (int i = 0; i < segmentColors.length; i++) {
            arrPaintArc[i] = new Paint();
            arrPaintArc[i].setColor(segmentColors[i]);
            arrPaintArc[i].setStyle(Paint.Style.FILL);
            arrPaintArc[i].setStrokeWidth(1);
//            arrPaintArc[i].setMaskFilter(PaintBGBlur);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Elog.e("TAG", "onDraw()");

        drawCoordinate(canvas);

        drawBarView(canvas);
    }

    private void drawCoordinate(Canvas canvas){

        float offset = barWidth/2;
        // 绘制X,Y 轴
        canvas.drawLine(originX, originY, originX + XAxisLenth + offset, originY, mAxisPaint);
        canvas.drawLine(originX, originY, originX, originY - YAxisLenth, mAxisPaint);

        //绘制Y轴坐标刻度
        mPaintText.setTextSize(DP2PX.dip2px(mContext, YAxisTextSize));
        for (int i = 0; i < yAxisTextList.length; i++) {
            float endY = originY - YAxisUnitLenth * i;
            canvas.drawText(yAxisTextList[i] + yUnit, originX - YAxisTextSize - yAxisTextSpace, endY, mPaintText);
        }
        //绘制X轴坐标刻度
        mPaintText.setTextSize(DP2PX.dip2px(mContext, YAxisTextSize));

        for (int i = 0; i < xAxisTextList.length; i++){
            float endX = originX + XAxisUnitLenth * i + barGap - offset;
            canvas.drawText(xAxisTextList[i], endX, originY + xAxisTextSpace, mPaintText);
        }
    }

    private void drawBarView(Canvas canvas){

        //画柱状条
        for(int i = 0; i < weekData.size(); i++){

            int day = weekData.get(i).getDay();
            float left = originX + day * barGap + (day - 1) * barWidth;
            float right = left + barWidth;
            float bottom = originY;
            float topDeep = bottom - (weekData.get(i).getDeepSleepTime() * barHight / 24);
            float topLight = topDeep - (weekData.get(i).getLightSleepTime() * barHight / 24);
            float top = originX - barHight;

            canvas.drawRect(left, topDeep, right,bottom, arrPaintArc[0]);
            canvas.drawRect(left, topLight, right,topDeep, arrPaintArc[1]);
            canvas.drawRect(left, top, right,topLight, arrPaintArc[2]);
        }
    }
}
