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

import com.smart.framework.library.common.utils.DP2PX;
import com.smart.tvpos.R;

/**
 * Created by JoJo on 2018/6/22.
 * wechat：18510829974
 * description：https://blog.csdn.net/xcl168/article/details/23134055
 */
public class PannelChartView extends View {
    private Context mContext;
    private int ScrHeight;
    private int ScrWidth;

    private Paint[] arrPaintArc;
    private Paint PaintText = null;
    //柱状条对应的颜色数组
    private int[] colors;
    private int scaleTextSpace = 20;//刻度与文字之间的间距
    private int lnWidth = 10; //标识线宽度
    private int lnSpace = 36; //刻度间距
    private int scaleSpace = 56;//柱状条之间的间距
    private int spanelViewWidth = 56;//柱状条的宽度
    //数据值
    private int[] valueDatas = {30, 60, 100};
    private int[] keduList = {0, 20, 40, 60, 80, 100};
    //每个刻度对应的高度值
    private int valueSpace = 20;

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

        //屏幕信息
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ScrHeight = dm.heightPixels;
        ScrWidth = dm.widthPixels;

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

        PaintText = new Paint();
        PaintText.setColor(ContextCompat.getColor(context, R.color.color_274782));
        PaintText.setTextSize(DP2PX.dip2px(mContext, 5));
        PaintText.setAntiAlias(true);
        PaintText.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画布背景
//        canvas.drawColor(Color.WHITE);

        //饼图标题
//        canvas.drawText("自绘柱形图", 100, 50, PaintText);

        arrPaintArc[0].setTextSize(25);
        arrPaintArc[3].setTextSize(25);

        int i = 0;

        int startx = 120;
        int endx = startx + 20;

        int starty = ScrHeight / 3;
        int endy = ScrHeight / 3;

        int initX = startx;
        int initY = starty;

        /////////////////////////
        //竖向柱形图  :Y 轴  标识线和刻度值
        ///////////////////////////
        // Y 轴
        canvas.drawLine(startx, starty, startx, starty - (keduList.length - 1) * lnSpace, PaintText);

        for (i = 0; i < keduList.length; i++) {
            starty = initY - (i + 1) * lnSpace;
            endy = starty;

            //坐标原点不绘制刻度线
            if (i == 0) {
                //在某个(x,y)的位置画文字
                canvas.drawText(Integer.toString(keduList[i]), startx - scaleTextSpace, endy + lnSpace, PaintText);
                continue;
            }
            //画刻度 lnWidth:刻度线宽度  lnSpace:刻度之间的间距
            canvas.drawLine(startx - lnWidth, starty + lnSpace, initX, starty + lnSpace, PaintText);
            //画刻度文字  lnSpace：标识间距
            if (i == 5) {
                canvas.drawText(Integer.toString(keduList[i]), (startx - lnWidth) - scaleTextSpace - 8, endy + lnSpace, PaintText);
            } else {
                canvas.drawText(Integer.toString(keduList[i]), (startx - lnWidth) - scaleTextSpace, endy + lnSpace, PaintText);
            }
        }

        //X 轴
        for (i = 0; i < 3; i++) {
            startx = initX + (i + 1) * lnSpace + scaleSpace * i;
//            endx = startx;
            //柱形
            arrPaintArc[0].setColor(colors[i]);
            //(float) (initY * 1.0 - (values[i] * (lnSpace * 1.0 / valueSpace))) 的值控制柱状条显示的高度，根据具体值显示
            canvas.drawRect(startx, initY,
                    startx + spanelViewWidth, (float) (initY * 1.0 - (valueDatas[i] * (lnSpace * 1.0 / valueSpace))), arrPaintArc[0]);
        }
        //画X轴
        canvas.drawLine(initX, initY, initX + 340, initY, PaintText);
        //画横向的5根直线
        PaintText.setStrokeWidth(1);
        for (int j = 0; j < keduList.length - 1; j++) {
            canvas.drawLine(initX, initY - lnSpace * j, initX + 340, initY - lnSpace * j, PaintText);
        }
    }
}
