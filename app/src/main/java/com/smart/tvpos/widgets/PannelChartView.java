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

/**
 * Created by JoJo on 2018/6/22.
 * wechat：18510829974
 * description：柱状统计图表 https://blog.csdn.net/xcl168/article/details/23134055
 */
public class PannelChartView extends View {
    private Context mContext;
    private int ScrHeight;
    private int ScrWidth;

    private Paint[] arrPaintArc;
    private Paint mPaintText = null;
    private Paint mPaintWhite = null;
    //柱状条对应的颜色数组
    private int[] colors;
    private int scaleTextSpace = 20;//刻度与文字之间的间距
    private int lnkeduWidth = 10; //标识线宽度
    private int lnkeduSpace = 36; //刻度间距
    private int scaleSpace = 40;//柱状条之间的间距
    private int spanelViewWidth = 56;//柱状条的宽度
    //数据值
    private int[] valueDatas = new int[]{};
    private int[] keduList = {0, 40, 80, 120, 160, 200};
    //每个刻度对应的高度值
    private int valueSpace = 40;
    //绘制柱形图的坐标起点
    private int startx;
    private int starty;

    public PannelChartView(Context context) {
        this(context, null);
    }

    public PannelChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public PannelChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        colors = new int[]{ContextCompat.getColor(context, R.color.color_07f2ab), ContextCompat.getColor(context, R.color.color_79d4d8), ContextCompat.getColor(context, R.color.color_4388bc), ContextCompat.getColor(context, R.color.color_4388bc), ContextCompat.getColor(context, R.color.color_4388bc)};
        initView(context);
    }

    private void initView(Context context) {
        //解决4.1版本 以下canvas.drawTextOnPath()不显示问题
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        if (keduList.length >= 2) {
            valueSpace = keduList[1] - keduList[0];
        }
        //屏幕信息
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ScrHeight = dm.heightPixels;
        ScrWidth = dm.widthPixels;
        //文字+刻度宽度+文字与刻度之间间距
        startx = 10 + lnkeduWidth + scaleTextSpace + 10;
        //为该曲线图Y轴所占的高度
        starty = lnkeduSpace * (keduList.length - 1) + lnkeduWidth;
        //设置边缘特殊效果
        BlurMaskFilter PaintBGBlur = new BlurMaskFilter(
                1, BlurMaskFilter.Blur.INNER);

        arrPaintArc = new Paint[5];
        Resources res = this.getResources();
        for (int i = 0; i < 5; i++) {
            arrPaintArc[i] = new Paint();
            arrPaintArc[i].setColor(colors[i]);
            arrPaintArc[i].setStyle(Paint.Style.FILL);
            arrPaintArc[i].setStrokeWidth(4);
            arrPaintArc[i].setMaskFilter(PaintBGBlur);
        }

        mPaintText = new Paint();
        mPaintText.setColor(ContextCompat.getColor(context, R.color.color_274782));
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(2);

        mPaintWhite = new Paint();
        //文字大小10px
        mPaintWhite.setTextSize(DP2PX.dip2px(mContext, 5));
        mPaintWhite.setColor(ContextCompat.getColor(context, R.color.color_a9c6d6));
        mPaintWhite.setAntiAlias(true);
        mPaintWhite.setStrokeWidth(1);
    }

    public void setValueData(int[] valueData, int[] yAxisDataArray) {
        this.valueDatas = valueData;
        this.keduList = yAxisDataArray;
        //调用postInvalidate不起作用,应该是因为画笔的原因，暂时采用调用initView(mContext)中方法
        initView(mContext);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Elog.e("TAG", "onDraw()");
        //画布背景
//        canvas.drawColor(Color.WHITE);
        arrPaintArc[0].setTextSize(25);
        arrPaintArc[3].setTextSize(25);

        int i = 0;

        int initX = startx;
        int initY = starty;

        int endx = startx + 20;
        int endy = ScrHeight / 3;
        /////////////////////////
        //竖向柱形图  :Y 轴  标识线和刻度值
        ///////////////////////////
        // 绘制Y 轴
        canvas.drawLine(startx, starty + lnkeduWidth, startx, starty - (keduList.length - 1) * lnkeduSpace, mPaintText);

        for (i = 0; i < keduList.length; i++) {
            starty = initY - (i + 1) * lnkeduSpace;
            endy = starty;

//            //坐标原点不绘制刻度线
//            if (i == 0) {
//                //在某个(x,y)的位置画文字
//                canvas.drawText(Integer.toString(keduList[i]), startx - scaleTextSpace, endy + lnkeduSpace, mPaintText);
//                continue;
//            }
            //画刻度 lnWidth:刻度线宽度  lnSpace:刻度之间的间距
            canvas.drawLine(startx - lnkeduWidth, starty + lnkeduSpace, initX, starty + lnkeduSpace, mPaintText);
            //在某个(x,y)的位置画刻度文字  lnkeduSpace：刻度间距
            if (i == 5) {
                canvas.drawText(Integer.toString(keduList[i]), (startx - lnkeduWidth) - scaleTextSpace - 4, endy + lnkeduSpace, mPaintWhite);
            } else {
                canvas.drawText(Integer.toString(keduList[i]), (startx - lnkeduWidth) - scaleTextSpace, endy + lnkeduSpace, mPaintWhite);
            }
        }
        //画X轴
        canvas.drawLine(initX - lnkeduWidth, initY, initX + 340, initY, mPaintText);
        //画X轴上方横向的5根直线
        mPaintText.setStrokeWidth(1);
        mPaintText.setColor(ContextCompat.getColor(mContext, R.color.color_132450));
        for (int j = 1; j < keduList.length; j++) {
            canvas.drawLine(initX, initY - lnkeduSpace * j, initX + 340, initY - lnkeduSpace * j, mPaintText);
        }

        //画柱状条
        for (i = 0; i < valueDatas.length; i++) {
            //记录前一根柱子离起始点的距离
            if (i == 0) {
                //设置第一根柱子距离原点的距离长10
                startx = initX + (scaleSpace + 8) * (i + 1) + i * spanelViewWidth;
            } else {
                //由于设置第一根柱子距离原点的距离长10,所以右边的柱子的距离整体往后偏移10
                startx = initX + scaleSpace * (i + 1) + i * spanelViewWidth + 8;
            }

            arrPaintArc[0].setColor(colors[i]);

            //(float) (initY * 1.0 - (values[i] * (lnSpace * 1.0 / valueSpace))) 的值控制柱状条显示的高度，根据具体值显示
            canvas.drawRect(startx, initY, startx + spanelViewWidth, (float) (initY * 1.0 - (valueDatas[i] * (lnkeduSpace * 1.0 / valueSpace))), arrPaintArc[0]);
        }
    }
}