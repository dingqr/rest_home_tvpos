package com.smart.tvpos.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.smart.framework.library.common.utils.DP2PX;
import com.smart.tvpos.R;

import java.util.ArrayList;
import java.util.List;

public class RadarChartView extends View {

    private Context mContext;
    private Paint mPaint;
    private Paint mPaintText;
    private int mPaintWidth = 1;        // 画笔的宽
    private int topMargin = 30;         // 上边距
    private int leftMargin = 70;        // 左边距
    private Resources mRes;
    private int showRateSize = 5; // 展示文字的大小

    private int circleCenterX = 90;     // 圆心点X  要与外圆半径相等
    private int circleCenterY = 90;     // 圆心点Y  要与外圆半径相等

    private int ringOuterRidus = 90;     // 外圆的半径

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

        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        //绘制刻度值文字的画笔
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(1);

        lineColors = mRes.getIntArray(R.array.color_group);
/****** test ******/
        dataList.clear();
        dataList.add(130);
        dataList.add(80);
        dataList.add(110);
        dataList.add(140);
        dataList.add(60);
        dataList.add(100);
        dataList.add(100);
        dataList.add(120);
        dataList.add(90);
        dataList.add(110);
        dataList.add(85);
        dataList.add(50);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; ++i) {
            int data = 0 + i *30;
            list.add(String.valueOf(data));
        }
        mShowTextList = list;
/****** test ******/
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

            int px, py, tx, ty;
            if(dlta < 90) {
                px = (int) (leftMargin + ringOuterRidus + Math.abs(lineLen * Math.cos(Math.PI*dlta/180)));
                py = (int) (topMargin + ringOuterRidus + Math.abs(lineLen * Math.sin(Math.PI*dlta/180)));
                tx = px;
                ty = py + 10;
            }
            else if(dlta < 180){
                px = (int) (leftMargin + ringOuterRidus - Math.abs(lineLen * Math.cos(Math.PI*dlta/180)));
                py = (int) (topMargin + ringOuterRidus + Math.abs(lineLen * Math.sin(Math.PI*dlta/180)));
                tx = px - 20;
                ty = py + 10;
            }
            else {
                px = (int) (leftMargin + ringOuterRidus - Math.abs(lineLen * Math.cos(Math.PI*dlta/180)));
                py = (int) (topMargin + ringOuterRidus - Math.abs(lineLen * Math.sin(Math.PI*dlta/180)));
                tx = px - 20;
                ty = py ;
            }

            mPaint.setColor(lineColors[i]);
            mPaint.setStrokeWidth(2);
            canvas.drawLine(circleCenterX + leftMargin, circleCenterY + topMargin,
                    px, py, mPaint);
            if(dataList.get(i) !=0) {
                mPaintText.setColor(ContextCompat.getColor(mContext, R.color.color_b69b4f));
                canvas.drawText(dataList.get(i) + "", tx, ty, mPaintText);
            }
        }
    }

    private void drawRadarNet(Canvas canvas) {

        mPaint.setColor(mRes.getColor(R.color.color_12284c));
        mPaint.setStyle(Paint.Style.STROKE);
        if(mShowTextList.size() == 0){
            return;
        }
        float unitLen = ringOuterRidus / (mShowTextList.size() - 1);
        for(int i = 0 ; i < mShowTextList.size() - 1; i++){

            RectF rectIn = new RectF(mPaintWidth + leftMargin + unitLen * i,
                    mPaintWidth + topMargin + unitLen * i,
                    circleCenterX + ringOuterRidus + mPaintWidth * 2 + leftMargin - unitLen * i,
                    circleCenterY + ringOuterRidus + mPaintWidth * 2 + topMargin - unitLen *i);
            for(int j = 0; j < 6; j++){
                canvas.drawArc(rectIn, j * 45, 45, true, mPaint);
            }
        }

        mPaintText.setColor(ContextCompat.getColor(mContext, R.color.color_8a8a8a));
        for (int i = 0; i < mShowTextList.size(); ++i){
            int endX = leftMargin + circleCenterX + 5;
            float endY = topMargin + circleCenterY - i * unitLen;
            canvas.drawText(mShowTextList.get(i), endX, endY, mPaintText);
        }
    }

    private void drawCenterPoint(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mRes.getColor(R.color.color_bfbfbf));
        canvas.drawCircle(circleCenterX + mPaintWidth * 2 + leftMargin,
                circleCenterY + mPaintWidth * 2 + topMargin,
                3, mPaint);
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
