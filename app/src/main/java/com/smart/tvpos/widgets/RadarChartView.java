package com.smart.tvpos.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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

    private List<Integer> dataList = new ArrayList<>();
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

    private void initView() {
        this.mRes = mContext.getResources();
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setStrokeWidth(DP2PX.dip2px(mContext, mPaintWidth));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        lineColors = mRes.getIntArray(R.array.color_group);
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
        if(lineNum == 0){
            return;
        }
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
    }

    private void drawRadarNet(Canvas canvas) {

        mPaint.setColor(mRes.getColor(R.color.color_12284c));
        mPaint.setStyle(Paint.Style.STROKE);
        if(mShowTextList.size() == 0){
            return;
        }
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


    public void setShowTextList(List<String> showTextList) {
        this.mShowTextList = showTextList;
//        initView();
        postInvalidate();
    }


    public void setDataList(List<Integer> dataList) {
        this.dataList = dataList;
        initView();
        postInvalidate();
    }
}
