package com.smart.tvpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.smart.framework.library.common.utils.DP2PX;
import com.smart.tvpos.R;

/**
 * Created by JoJo on 2018/7/1.
 * wechat:18510829974
 * description: *
 * 1. 缺口的圆环；
 * 2. 圆环颜色渐进(颜色配比在下面代码中还未给出，不影响代码分析)；
 * 3. 圆环的刻度(即虚线)稳定；
 * https://bbs.csdn.net/topics/390746097
 */

public class PieMarkChatView extends View {
    private Context mContext;
    private Paint mPaint;
    private float mRotate;
    private Matrix mMatrix = new Matrix();
    private Shader mShader;
    private boolean mDoTiming = true;
    private Rect textBoundRect;
    private Paint centerTextPaint;
    private int mWidth;
    private int mHeight;
    //圆心
    private int startX;
    private int startY;
    //圆环刻度的长度
    private int mCircleKeduWidth = 10;
    //内圆的半径
    private int mInnerRadius = 36;
    private int mCurrentProgress;

    public PieMarkChatView(Context context) {
        this(context, null);
    }

    public PieMarkChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setFocusable(true);
        setFocusableInTouchMode(true);

//        float x = 160;
//        float y = 100;
//        mShader = new SweepGradient(x, y, new int[]{Color.GREEN,
//                Color.RED,
//                Color.BLUE,
//                Color.GREEN}, null);
        //设置渐变
        //        mPaint.setShader(mShader);
    }

    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = mInnerRadius * 2 + 2 * mCircleKeduWidth;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = mInnerRadius * 2 + 2 * mCircleKeduWidth + 20;
        } else {
            mHeight = heightSpecSize + 20;
        }
        setMeasuredDimension(mWidth, mHeight);
        mInnerRadius = (mWidth - 2 * mCircleKeduWidth) / 2;
        startX = mInnerRadius + mCircleKeduWidth;
        startY = mInnerRadius + mCircleKeduWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制圆盘刻度的画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleKeduWidth);
        mPaint.setAntiAlias(true);
        //表盘每个刻度的宽度时6px,间隙为1px
        PathEffect effect = new DashPathEffect(new float[]{6, 1, 6, 1}, 1);
        //设置画笔path,达到刻度的效果
        mPaint.setPathEffect(effect);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.color_3662a0));

        //绘制圆盘中间的文字
        centerTextPaint = new Paint();
        centerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerTextPaint.setStyle(Paint.Style.STROKE);
        centerTextPaint.setColor(ContextCompat.getColor(mContext, R.color.color_68fdfe));
        centerTextPaint.setTextSize(DP2PX.dip2px(mContext, 20));


        //80是圆的半径，20为画笔的宽度
//        float x = 80 + 20;
//        float y = 80 + 20;

//        canvas.drawColor(Color.WHITE);

//        mMatrix.setRotate(mRotate, x, y);
//        mShader.setLocalMatrix(mMatrix);
//        mRotate += 3;
//        if (mRotate >= 360) {
//            mRotate = 0;
//        }
        //不断绘制，达到动画的效果
//        invalidate();
        //绘制第一个圆盘
        canvas.drawCircle(startX, startY, mInnerRadius, mPaint);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.color_2af0ae));
        //20为画笔所占的宽度
        RectF rectF = new RectF(0 + mCircleKeduWidth, 0 + mCircleKeduWidth, 2 * mInnerRadius + mCircleKeduWidth, 2 * mInnerRadius + mCircleKeduWidth); //正方形背景
        //绘制第二个圆，圆弧进度——userCenter true:显示圆中的水平横线
        canvas.drawArc(rectF, 0, mCurrentProgress * 1f / 100 * 360, false, mPaint); //画弧

        //文字的边界矩形
        textBoundRect = new Rect();
        //绘制中心的百分比文字
        String data = mCurrentProgress + "";
        centerTextPaint.getTextBounds(data, 0, data.length(), textBoundRect);
        canvas.drawText(data, startX - textBoundRect.width() / 2, startY + textBoundRect.height() / 2, centerTextPaint);
    }

    /**
     * 刷新当前进度:1-100
     *
     * @param progress
     */
    public void setCurrentProgress(int progress) {
        this.mCurrentProgress = progress;
        invalidate();
    }
}
