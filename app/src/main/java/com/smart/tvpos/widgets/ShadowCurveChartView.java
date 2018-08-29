package com.smart.tvpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.DP2PX;
import com.smart.tvpos.R;
import com.smart.tvpos.util.DateUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShadowCurveChartView extends View {

    private Context mContext;
    //曲线图坐标原点(px) X/Y轴长
    private int originX;
    private int originY;
    private int mXAxisLength;
    private int mYAxisLength;
    //X/Y轴刻度间距(px)
    private int yAxisSpace = 48;
    private int xAxisSpace = 63;
    //X/Y轴刻度集合
    private List<String> mXAxisText = DateUtils.getInstance().getDatesBefore(14, ".");
    private int[] mYAxisText = new int[]{0, 50, 100, 150, 200, 250};
    // Y轴最大刻度值\每单位刻度所占的像素值
    private int maxScaleValue;
    private float YAxisUnitLength;

    //y轴单位
    private String yValueUnit;

    //曲线起点偏移原点宽度
    private int mCurveStartWidth = 36;
    private float scaleTextSize = 10;
    //刻度线与刻度值文字直接的间距
    private int scaleTextSpace = 26;
    //X轴的偏移量
    private int xOffset = 60;

    //各类画笔
    private Paint mAxisPaint;
    private Paint[] mPaint;
    private Paint[] shadowPaint;
    private Paint mPaintText;

    private int lineNum = 1;

    private boolean isClicked = false;
    private int clickedPoint = -1;

    //根据具体传入的数据，在坐标轴上绘制点
//    private Point[] mPoints;
    private List<PointF> mPointsF;
    //传入的数据，决定绘制的纵坐标值
//    private ArrayList<Integer> mYDatas = new ArrayList<>();
    private Map<Integer, Float> mYFloatDatas = new HashMap<>();
    private List<Float> mYRealDatas = new ArrayList<>();
    private List<Integer> mYRealDataPos = new ArrayList<>();
    private ArrayList<Map<Integer, Float>> mYDataGroup = new ArrayList<>();

    private int[] colors = new int[] {R.color.color_5fbee7, R.color.color_light_sleep, R.color.color_e8af44, R.color.color_5fc6bb};
    private int[] shadowColors = new int[] {R.color.color_4438b1e5, R.color.color_44f47564, R.color.color_44e8af44, R.color.color_445fc6bb};

    private boolean isDataEmpty;

    public ShadowCurveChartView(Context context) {
        this(context, null);
    }

    public ShadowCurveChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowCurveChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
//        initData();
        initView();
    }

    private void initView() {

        //初始化画笔
        mPaint= new Paint[lineNum];
        shadowPaint = new Paint[lineNum];
        for(int i = 0; i < lineNum; i++){
            mPaint[i] = new Paint();
            mPaint[i].setColor(ContextCompat.getColor(mContext, colors[i]));
            mPaint[i].setStrokeWidth(2);
            mPaint[i].setAntiAlias(true);
            mPaint[i].setStyle(Paint.Style.STROKE);

            //颜色填充
            shadowPaint[i] = new Paint();
            shadowPaint[i].setColor(ContextCompat.getColor(mContext, shadowColors[i]));
            shadowPaint[i].setStyle(Paint.Style.FILL);
            shadowPaint[i].setStrokeWidth(2);
            shadowPaint[i].setAntiAlias(true);
        }

        //绘制X,Y轴坐标的画笔
        mAxisPaint = new Paint();
        mAxisPaint.setColor(ContextCompat.getColor(mContext, R.color.color_808080));
        mAxisPaint.setStrokeWidth(2);
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setStyle(Paint.Style.STROKE);

        //绘制刻度值文字的画笔
        mPaintText = new Paint();
        //文字大小22px
        mPaintText.setTextSize(DP2PX.dip2px(mContext, scaleTextSize));
        mPaintText.setColor(ContextCompat.getColor(mContext, R.color.color_8a8a8a));
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(1);

        //指定绘制的起始位置
        originX = xOffset;
        //坐标原点Y的位置（+1的原因：X轴画笔的宽度为2 ; +DP2PX.dip2px(mContext, 5)原因：为刻度文字所占的超出的高度 ）——>解决曲线画到最大刻度值时，显示高度不够，曲线显示扁扁的问题
        originY = yAxisSpace * (mYAxisText.length - 1) + 1 + DP2PX.dip2px(mContext, scaleTextSize);

        //最大刻度值
        maxScaleValue = (mYAxisText[mYAxisText.length - 1]);
        //每单位的值所占的像素
        YAxisUnitLength = yAxisSpace * ((mYAxisText.length - 1) * 1f / maxScaleValue);
        Elog.e("TAG", "YAxisUnitLength=" + YAxisUnitLength);
        //X轴绘制距离
        mXAxisLength = 13 * xAxisSpace + mCurveStartWidth * 2;
        //Y轴绘制距离
        mYAxisLength = (mYAxisText.length - 1) * yAxisSpace;

        //坐标起始点Y轴高度=(originY+mScaleWidth)  下方文字所占高度= DP2PX.dip2px(mContext, keduTextSize)
        int viewHeight = originY + DP2PX.dip2px(mContext, scaleTextSize);
        //viewHeight=121
        Elog.e("TAG", "viewHeight=" + viewHeight);
    }

    private List<PointF> initPointF() {
        List<PointF> points = new ArrayList<>();
        Set<Integer> pos = mYFloatDatas.keySet();
        mYRealDatas.clear();
        mYRealDataPos.clear();
        for (int i = 0; i < mXAxisText.size(); i++) {
            if(!pos.contains(i) || mYFloatDatas.get(i).equals(-1f)){
                continue;
            }
            float ybean = mYFloatDatas.get(i);
            float drawHeight = (float) (originY * 1.0 - (ybean * YAxisUnitLength));
            float startx = originX + xAxisSpace * i + mCurveStartWidth;
            mYRealDatas.add(ybean);
            mYRealDataPos.add(i);
            points.add(new PointF(startx, drawHeight));
        }
        Elog.e("TAG", "originX=" + originX + "---originY=" + originY);
        return points;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mYAxisText.length; i++) {
            //绘制文字时,Y轴方向递增的高度
            int yTextHeight = originY - yAxisSpace * i;
            //绘制Y轴刻度旁边的刻度文字值,10为刻度线与文字的间距
            mPaintText.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(mYAxisText[i] + "", originX - scaleTextSpace, yTextHeight, mPaintText);
        }
        //绘制X轴
        canvas.drawLine(originX, originY, originX + mXAxisLength, originY, mAxisPaint);
        //绘制Y轴
        canvas.drawLine(originX, originY, originX, originY - mYAxisLength, mAxisPaint);

        //绘制X轴下面显示的文字
        for (int i = 0; i < mXAxisText.size(); i++) {
            int xTextWidth = originX + xAxisSpace * i + mCurveStartWidth / 2;
            //设置从起点位置的左边对齐绘制文字
            mPaintText.setTextAlign(Paint.Align.LEFT);
            if(isClicked && i == clickedPoint){
                mPaintText.setColor(ContextCompat.getColor(mContext, R.color.color_5fbee7));
                canvas.drawText(mXAxisText.get(i), xTextWidth, originY + DP2PX.dip2px(mContext, scaleTextSize), mPaintText);
                mPaintText.setColor(ContextCompat.getColor(mContext, R.color.color_8a8a8a));
            }
            canvas.drawText(mXAxisText.get(i), xTextWidth, originY + DP2PX.dip2px(mContext, scaleTextSize), mPaintText);
        }
        //连接所有的数据点,画曲线
        if(mYDataGroup.size() > 0){
            for(int i = 0; i < lineNum; i++){
                if(mYDataGroup.get(i).size() > 0){
                    mYFloatDatas = mYDataGroup.get(i);
                    mPointsF = initPointF();
                    if(mPointsF.size() == 0){
                        return;
                    }
                    else if(mPointsF.size() == 1){
                        drawPoint(canvas, i, mYRealDataPos.get(0));
                    }
                    else {
                        drawScrollLine(canvas, i);
                    }
                }
            }

            isClicked = false;
        }
    }

    /**
     * 绘制曲线-曲线图
     *
     * @param canvas
     */
    private void drawScrollLine(Canvas canvas, int line) {

        int pLength = mPointsF.size();

        PointF startp;
        PointF endp;

        float xStart = mPointsF.get(0).x;
        float yStart = mPointsF.get(0).y;
        float maxY = yStart;

        float xLast = mPointsF.get(pLength-1).x;

        Path path = new Path();
        Path shadowPath = new Path();
        shadowPath.moveTo(xStart, originY);
        shadowPath.lineTo(xStart, yStart-2);

        path.moveTo(xStart, originY);
        path.moveTo(xStart, yStart);

        for (int i = 0; i < pLength - 1; i++) {
            startp = mPointsF.get(i);
            endp = mPointsF.get(i + 1);

            maxY = (maxY < startp.y) ? startp.y : maxY;

//            float wt = (startp.x + endp.x) / 2;
//            PointF p3 = new PointF();
//            PointF p4 = new PointF();
//            p3.y = startp.y;
//            p3.x = wt;
//            p4.y = endp.y;
//            p4.x = wt;

            path.lineTo(endp.x, endp.y);
            shadowPath.lineTo(endp.x, endp.y-2);
        }

        shadowPath.lineTo(xLast, originY);
        canvas.drawPath(path, mPaint[line]);

        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.parseColor("#ffffff"));
        rectPaint.setStyle(Paint.Style.FILL);
        float left = originX + 2;
        float top = maxY;
        float right = xLast;
        float bottom = originY - 2;
        // 渐变的颜色
        LinearGradient lg = new LinearGradient(left, top, left, bottom, Color.parseColor("#00ffffff"),
                Color.parseColor("#efffffff"), Shader.TileMode.CLAMP);
        rectPaint.setShader(lg);

        canvas.drawPath(shadowPath, shadowPaint[line]);
        canvas.drawRect(left, top, right, bottom, rectPaint);
        if(!isClicked) {
           drawPoint(canvas, line, mYRealDataPos.get(pLength - 2));
        }
        else {
            drawPoint(canvas, line, clickedPoint);
        }
    }

    private void drawPoint(Canvas canvas, int line, int clickedPoint){

        int pos = mYRealDataPos.indexOf(clickedPoint);
        canvas.drawCircle(mPointsF.get(pos).x, mPointsF.get(pos).y, 7, mPaint[line]);

        Paint valuePaint = new Paint();
        valuePaint.setColor(Color.parseColor("#ffffff"));
        valuePaint.setStyle(Paint.Style.FILL);
        float left = mPointsF.get(pos).x + 15;
        float top = mPointsF.get(pos).y + 23;
        float right= left + 107;
        float bottom = mPointsF.get(pos).y - 23;

        canvas.drawRect(left, top, right, bottom, valuePaint);

        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        valuePaint.setStrokeWidth(1);
        valuePaint.setColor(ContextCompat.getColor(mContext, colors[line]));
        valuePaint.setTextAlign(Paint.Align.LEFT);
        valuePaint.setTextSize(24);
        DecimalFormat df = new DecimalFormat("###.####");
        canvas.drawText(df.format(mYRealDatas.get(pos)) + yValueUnit, left, mPointsF.get(pos).y + 10, valuePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                //获取屏幕上点击的坐标
                float x = event.getX();
                float y = event.getY();

                if(mYFloatDatas.size() > 0){
                    if(x - originX - mCurveStartWidth > 0 && x < originX + mXAxisLength
                            && y < originY && y > originY - mYAxisLength){
                        int i = (int) ((x - originX - mCurveStartWidth) / xAxisSpace);

                        if(i < mYRealDataPos.get(0)){
                            clickedPoint = mYRealDataPos.get(0);
                        }
                        else if(i > mYRealDataPos.get(mYRealDataPos.size() - 1)){
                            clickedPoint = mYRealDataPos.get(mYRealDataPos.size() - 1);
                        }
                        else if(mYRealDataPos.contains(i)){
                            clickedPoint = i;
                        }
                        else {
                            for(int j = 0; j < mYRealDataPos.size() - 1; j++){
                                if(i < mYRealDataPos.get(j + 1)){
                                    clickedPoint = (i - mYRealDataPos.get(j) > mYRealDataPos.get(j + 1) ? mYRealDataPos.get(j+1) : mYRealDataPos.get(j));
                                }
                            }
                        }

                        isClicked = true;

                        initView();
                        postInvalidate();//更新视图
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    public void updateXAxisText(List<String> xAxisText){
        this.mXAxisText = xAxisText;
    }

    /**
     * 传入数据，重新绘制图表
     */
    public void updateData(ArrayList<Map<Integer, Float>> yDataGroup, int[] YAxisText, int lineNum, String yValueUnit) {
        this.mYDataGroup = yDataGroup;
//        this.mXAxisText = xAxisText;
        this.mYAxisText = YAxisText;
        this.lineNum = lineNum;
        this.yValueUnit = yValueUnit;
        initView();
        postInvalidate();
    }
}
