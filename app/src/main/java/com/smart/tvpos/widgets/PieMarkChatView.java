package com.smart.tvpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.smart.framework.library.common.utils.DP2PX;

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
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mRotate;
    private Matrix mMatrix = new Matrix();
    private Shader mShader;
    private boolean mDoTiming = true;
    private Rect textBoundRect;
    private Paint centerTextPaint;
    private int mCircleRadius = 80;
    private int mWidth;
    private int mHeight;
    //圆心
    private int startX;
    private int startY;
    private int radius;

    public PieMarkChatView(Context context) {
        this(context, null);
    }

    public PieMarkChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setFocusable(true);
        setFocusableInTouchMode(true);

        float x = 160;
        float y = 100;
        mShader = new SweepGradient(x, y, new int[]{Color.GREEN,
                Color.RED,
                Color.BLUE,
                Color.GREEN}, null);
        //设置渐变
        //        mPaint.setShader(mShader);
        mPaint.setStyle(Paint.Style.STROKE);
        //刻度的宽度
        mPaint.setStrokeWidth(20);
        mPaint.setAntiAlias(true);
        PathEffect effect = new DashPathEffect(new float[]{5, 8, 5, 8}, 1);
        //设置画笔path,达到刻度的效果
        mPaint.setPathEffect(effect);
        mPaint.setColor(Color.parseColor("#3662a0"));
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
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = DP2PX.dip2px(mContext, 80);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
        startX = mHeight / 2;
        startY = mWidth / 2;
        radius = mHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        centerTextPaint = new Paint();
        centerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerTextPaint.setStyle(Paint.Style.STROKE);
        centerTextPaint.setColor(Color.parseColor("#68fdfe"));
        centerTextPaint.setTextSize(DP2PX.dip2px(mContext, 20));

//        canvas.drawColor(Color.WHITE);

        //80是圆的半径，20为画笔的宽度
        float x = 80 + 20;
        float y = 80 + 20;

//        canvas.drawColor(Color.WHITE);

//        mMatrix.setRotate(mRotate, x, y);
//        mShader.setLocalMatrix(mMatrix);
//        mRotate += 3;
//        if (mRotate >= 360) {
//            mRotate = 0;
//        }
        //不断绘制，达到动画的效果
//        invalidate();
        //绘制刻度
//            for (int i = 0; i < 20; i++) {
        canvas.drawCircle(x, y, 80, mPaint);
//            }
        mPaint.setColor(Color.parseColor("#2af0ae"));
//                canvas.drawCircle(x, y, 80, mPaint);
        //20为画笔所占的宽度
        RectF rectF = new RectF(0 + 20, 20, 160 + 20, 160 + 20); //正方形背景
        //userCenter true:显示圆中的水平横线
        canvas.drawArc(rectF, 0, 180, false, mPaint); //画弧
//        else {
//            RectF rect = new RectF(x - 80, y - 80, x + 80, y + 80);
//            Paint paintRect = new Paint();
//            paintRect.setColor(Color.RED);
//            paintRect.setStyle(Paint.Style.STROKE);
//            canvas.drawRect(rect, paintRect);
//            Path path = new Path();
//            path.addArc(rect, 60, 60);
//            canvas.clipPath(path);
//            //canvas.clipPath(path，Op.XOR);
//            canvas.drawCircle(x, y, 80, paint);
//        }

        //文字的边界矩形
        textBoundRect = new Rect();
        //绘制中心的百分比文字
        String data = 30 + "";
        centerTextPaint.getTextBounds(data, 0, data.length(), textBoundRect);
        canvas.drawText(data, x - textBoundRect.width() / 2, y + textBoundRect.height() / 2, centerTextPaint);
    }
}
