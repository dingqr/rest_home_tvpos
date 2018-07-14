package com.smart.tvpos.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.smart.framework.library.common.utils.DP2PX;
import com.smart.tvpos.R;

/**
 * Created by JoJo on 2018/6/29.
 * wechat:18510829974
 * description: 自定义圆头部和尾部的渐变色的水平progressbar
 * https://www.2cto.com/kf/201401/270836.html
 */
public class GradientProgressBar extends View {

    private Context mContext;
    /**
     * 分段颜色
     */
    private int[] mGradientColorArray = new int[3];
    /**
     * 进度条最大值
     */
    private float maxProgress;
    /**
     * 进度条当前值
     */
    private float currentProgress;
    /**
     * 画笔
     */
    private Paint mPaint;
    public int mWidth, mHeight;
    private Paint mPaintText;
    private int progressBarWidth;
    //百分比文字的颜色
    private int mTextColor;
    private int lineColor;
    private float leftMargin = 30;
    private Paint mPaintLine;
    private float startY;


    public GradientProgressBar(Context context) {
        super(context);
        this.mContext = context;
        initView(context, null);
    }

    public GradientProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context, attrs);
    }

    public GradientProgressBar(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        //1.将布局中声明的属性attrs与R.styleable.RoundProgress关联，加载attrs.xml文件中的的RoundProgress下的自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientHorizantalProgress);
        //2.读取布局中声明的属性值，没有获取到有个默认值
        currentProgress = typedArray.getFloat(R.styleable.GradientHorizantalProgress_currentProgress, 0);
        maxProgress = typedArray.getFloat(R.styleable.GradientHorizantalProgress_maxProgress, 100);
        //3.回收typedArray
        typedArray.recycle();
        //默认的渐变颜色数组
        mGradientColorArray = new int[]{Color.parseColor("#41a0eb"), Color.parseColor("#6488e5"), Color.parseColor("#7084e4")};

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(23);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
        //左右两边30px的间距
        progressBarWidth = (int) (mWidth - (2 * leftMargin));
        startY = mHeight * 1f * 18 / 23; //控件高度为40px，上方留出10px的margin,26px的文字大小高度，10px进度条的高度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制进度条的画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        //绘制刻度值文字的画笔
        mPaintText = new Paint();
        //文字大小26px
        mPaintText.setTextSize(DP2PX.dip2px(mContext, 13));
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(2);

        //绘制背景线的画笔
        mPaintLine = new Paint();
        mPaintLine.setColor(lineColor == 0 ? Color.parseColor("#d8dae1") : lineColor);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStrokeWidth(2);
        //头和尾圆角半径
        int round = (int) ((mHeight * 1f / 3) / 2);
//        Elog.e("TAG", "max=" + maxProgress + "  current=" + currentProgress);
//        mPaint.setColor(Color.rgb(71, 76, 80));
//        RectF rectBg = new RectF(0, 0, mWidth, mHeight);
        //（1）绘制圆角进度条背景
//        canvas.drawRoundRect(rectBg, round, round, mPaint);

        //（2）绘制圆角边框
//        mPaint.setColor(Color.BLACK);
//        RectF rectBlackBg = new RectF(2, 2, mWidth - 2, mHeight - 2);
//        canvas.drawRoundRect(rectBlackBg, round, round, mPaint);

        //（3）绘制上层动态的渐变的进度条
        float percent = currentProgress / maxProgress;
//        RectF rectProgressBg = new RectF(0, 0, mWidth * percent, mHeight);
        //腾出20px绘制上方百分比的文字:mHeight * 1f * 2 / 3
        RectF rectProgressBg = new RectF(0 + leftMargin, startY, (progressBarWidth + leftMargin) * percent, mHeight);
        //绘制2px高的底线
        canvas.drawLine(leftMargin, startY + (mHeight - startY) / 2, leftMargin + progressBarWidth, startY + (mHeight - startY) / 2, mPaintLine);
        if (percent <= 1.0f / 3.0f) {
            if (percent != 0.0f) {
                mPaint.setColor(mGradientColorArray[0]);
            } else {
                mPaint.setColor(Color.TRANSPARENT);
            }
        } else {
            int count = (percent <= 1.0f / 3.0f * 2) ? 2 : 3;
            int[] colors = new int[count];
            System.arraycopy(mGradientColorArray, 0, colors, 0, count);
            float[] positions = new float[count];
            if (count == 2) {
                positions[0] = 0.0f;
                positions[1] = 1.0f - positions[0];
            } else {
                positions[0] = 0.0f;
                positions[1] = (maxProgress / 3) / currentProgress;
                positions[2] = 1.0f - positions[0] * 2;
            }
            positions[positions.length - 1] = 1.0f;
            LinearGradient shader = new LinearGradient(0 + leftMargin, startY, (progressBarWidth + leftMargin) * percent, mHeight, colors, null, Shader.TileMode.MIRROR);
            mPaint.setShader(shader);
        }
        canvas.drawRoundRect(rectProgressBg, round, round, mPaint);
        //测量的高度全部绘制进度条了，所以显示不出下面画的文字
        mPaintText.setColor(mTextColor == 0 ? Color.parseColor("#4791e1") : mTextColor);
        mPaintText.setTextAlign(Paint.Align.LEFT);
        //绘制百分比文字
        int showProgress = (int) this.currentProgress;
        if (showProgress <= 5) {
            canvas.drawText(showProgress + "%", (progressBarWidth * percent) + (leftMargin - 10), mHeight * 1f * 2 / 3 - 4, mPaintText);
        } else if (showProgress >= 90) {
            canvas.drawText(showProgress + "%", (progressBarWidth * percent) - (leftMargin - 10), startY - 4, mPaintText);
        } else {
            canvas.drawText(showProgress + "%", (progressBarWidth * percent), startY - 4, mPaintText);
        }

    }

    public void setGradientColor(int[] gradientColorArray) {
        this.mGradientColorArray = gradientColorArray;
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /***
     * 设置最大的进度值
     * @param maxProgress
     */
    public void setmaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
    }

    /***
     * 设置当前的进度值
     * @param currentProgress
     */
    public void setcurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress > maxProgress ? maxProgress : currentProgress;
        //测试百分比很小的时候文字绘制的位置（效果）
        //        if (currentProgress == 0) {
//            this.currentProgress = 10;
//        }
        invalidate();
    }

    //百分比文字
    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    //设置进度条绘制的x轴起点-距离左边的距离
    public void setLeftMargin(float leftMargin) {
        this.leftMargin = leftMargin;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public float getmaxProgress() {
        return maxProgress;
    }

    public float getcurrentProgress() {
        return currentProgress;
    }


}
