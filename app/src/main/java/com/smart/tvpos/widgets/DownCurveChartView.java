package com.smart.tvpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.DP2PX;
import com.smart.tvpos.R;

import java.util.ArrayList;

/**
 * Created by JoJo on 2018/6/24.
 * wechat：18510829974
 * description：带坐标的双曲线图（上下两个坐标）
 */
public class DownCurveChartView extends View {
    private Context mContext;
    //向上的曲线图的绘制起点(px)
//    private int downStartX;
//    private int downStartY;
    //向下的曲线图的绘制起点(px)
    private int downStartX;
    private int downStartY;
    //上方Y轴每单位刻度所占的像素值
    private float YAxisUpUnitValue;
    //下方Y轴每单位刻度所占的像素值
    private float YAxisDownUnitValue;
    //根据具体传入的数据，在坐标轴上绘制点
    private Point[] mPoints;
    //传入的数据，决定绘制的纵坐标值
    private ArrayList<Integer> mRealDatas = new ArrayList<>();
    //Y轴刻度间距(px)
    private int yAxisSpace = 20;
    //X轴刻度间距(px)
    private int xAxisSpace = 90;
    //Y轴刻度集合
    private int[] mYAxisData = new int[]{0, 2, 4, 6, 8};
    //X轴刻度集合
    private ArrayList<String> mXAxisData = new ArrayList<>();
    //最大刻度值
    private int maxKeduValue;
    //X轴的绘制距离
    private int mXAxisMaxValue;
    //Y轴的绘制距离
    private int mYAxisMaxValue;
    //Y轴刻度线宽度
    private int mKeduWidth = 10;
    //绘制坐标轴的画笔
    private Paint mAxisPaint;
    //绘制曲线的画笔
    private Paint mPaint;
    //绘制X轴上方的画笔
    private Paint mXAxisLinePaint;
    private Paint mPaintText;
    private float keduTextSize = 5;
    //刻度线与刻度值文字直接的间距
    private int keduTextSpace = 4;
    //X轴的偏移量
    private int xOffset = 46;
    public DownCurveChartView(Context context) {
        this(context, null);
    }

    public DownCurveChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownCurveChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initData();
        initView();

    }

    private void initView() {
        //初始化画笔
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.color_4487bc));
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        //绘制X,Y轴坐标的画笔
        mAxisPaint = new Paint();
        mAxisPaint.setColor(ContextCompat.getColor(mContext, R.color.color_274782));
        mAxisPaint.setStrokeWidth(2);
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setStyle(Paint.Style.STROKE);
        //绘制坐标轴上方的横线的画笔
        mXAxisLinePaint = new Paint();
        mXAxisLinePaint.setColor(ContextCompat.getColor(mContext, R.color.color_132450));
        mXAxisLinePaint.setStrokeWidth(1);
        mXAxisLinePaint.setAntiAlias(true);
        mXAxisLinePaint.setStyle(Paint.Style.STROKE);

        //绘制刻度值文字的画笔
        mPaintText = new Paint();
        //文字大小10px
        mPaintText.setTextSize(DP2PX.dip2px(mContext, keduTextSize));
        mPaintText.setColor(ContextCompat.getColor(mContext, R.color.color_a9c6d6));
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(1);

        //指定绘制的起始位置
        downStartX = xOffset;
        //坐标原点Y的位置(距离左上角Y方向的mKeduWidth位置)
        downStartY = mKeduWidth;
        //最大刻度值
        maxKeduValue = (mYAxisData[mYAxisData.length - 1]);
        //每单位的值所占的像素
        YAxisUpUnitValue = yAxisSpace * ((mYAxisData.length - 1) * 1f / maxKeduValue);
        Elog.e("TAG", "YAxisUpUnitValue=" + YAxisUpUnitValue);
        //X轴绘制距离
        mXAxisMaxValue = (mRealDatas.size() - 1) * xAxisSpace;
        //Y轴绘制距离
        mYAxisMaxValue = (mYAxisData.length - 1) * yAxisSpace;

        //坐标起始点Y轴高度=(downStartY+mKeduWidth)  下方文字所占高度= DP2PX.dip2px(mContext, keduTextSize)
        int viewHeight = downStartY + 2 * mKeduWidth + DP2PX.dip2px(mContext, keduTextSize);
        //viewHeight=121
        Elog.e("TAG", "viewHeight=" + viewHeight);
    }

    private void initData() {
        //外界传入的数据，即为绘制曲线的每个点
        mRealDatas.add(0);
        mRealDatas.add(0);
        mRealDatas.add(0);
        mRealDatas.add(0);
        mRealDatas.add(0);

        //X轴数据
//        mXAxisData.add("01月");
//        mXAxisData.add("02月");
//        mXAxisData.add("03月");
//        mXAxisData.add("04月");
//        mXAxisData.add("05月");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPoints = initPoint();
        //绘制X轴
        canvas.drawLine(downStartX - mKeduWidth, downStartY, downStartX + mXAxisMaxValue, downStartY, mAxisPaint);
        //绘制Y轴
        canvas.drawLine(downStartX, downStartY, downStartX, downStartY + mYAxisMaxValue, mAxisPaint);

        for (int i = 0; i < mYAxisData.length; i++) {
            //Y轴方向递增的高度
            int yAxisHeight = downStartY + yAxisSpace * (i + 1);
            if (i < mYAxisData.length - 1) {
                //绘制X轴下方横线
                canvas.drawLine(downStartX, yAxisHeight, downStartX + mXAxisMaxValue, yAxisHeight, mXAxisLinePaint);
                //绘制左边Y轴刻度线
                canvas.drawLine(downStartX, yAxisHeight, downStartX - mKeduWidth, yAxisHeight, mAxisPaint);
            }
            //绘制文字时,Y轴方向递增的高度
            int yTextHeight = downStartY + yAxisSpace * i;
            //绘制Y轴刻度旁边的刻度文字值,10为刻度线与文字的间距
            mPaintText.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(mYAxisData[i] + "", (downStartX - mKeduWidth) - keduTextSpace, yTextHeight, mPaintText);
        }
        //绘制X轴下面显示的文字
//        for (int i = 0; i < mXAxisData.size(); i++) {
//            int xTextWidth = downStartX + xAxisSpace * i - mKeduWidth;
//            mPaintText.setTextAlign(Paint.Align.LEFT);
//            //canvas.drawText(mXAxisData.get(i), xTextWidth, downStartY + 1 * mKeduWidth +, mPaintText); //紧挨着X轴画文字
//            canvas.drawText(mXAxisData.get(i), xTextWidth, downStartY + 2 * mKeduWidth, mPaintText);
//        }
        //连接所有的数据点,画曲线
        drawScrollLine(canvas);
        //画折线
        // drawLine(canvas);
    }

    /**
     * 根据传入的数据，确定绘制的点
     *
     * @return
     */
    private Point[] initPoint() {
        Point[] points = new Point[mRealDatas.size()];
        for (int i = 0; i < mRealDatas.size(); i++) {
            Integer ybean = mRealDatas.get(i);
            int drawHeight = downStartY + (int) (ybean * YAxisUpUnitValue);
            int startx = downStartX + xAxisSpace * i;
            points[i] = new Point(startx, drawHeight);
        }
        Elog.e("TAG", "downStartX=" + downStartX + "---downStartY=" + downStartY);
        return points;
    }

    /**
     * 绘制曲线-曲线图
     *
     * @param canvas
     */
    private void drawScrollLine(Canvas canvas) {
        Point startp;
        Point endp;
        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            int wt = (startp.x + endp.x) / 2;
            Point p3 = new Point();
            Point p4 = new Point();
            p3.y = startp.y;
            p3.x = wt;
            p4.y = endp.y;
            p4.x = wt;
            Path path = new Path();
            path.moveTo(startp.x, startp.y);
            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
            canvas.drawPath(path, mPaint);
        }
    }

    /**
     * 绘制直线-折线图
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        Point startp;
        Point endp;
        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            canvas.drawLine(startp.x, startp.y, endp.x, endp.y, mPaint);
        }
    }

    /**
     * 传入数据，重新绘制图表
     *
     * @param mIncressUserList
     * @param downYAxisData
     */
    public void setData(ArrayList<Integer> mIncressUserList, int[] downYAxisData) {
        this.mRealDatas = mIncressUserList;
        this.mYAxisData = downYAxisData;
        initView();
        postInvalidate();
    }
}
