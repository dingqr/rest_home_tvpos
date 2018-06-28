package com.smart.tvpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by JoJo on 2018/6/29.
 * wechat：18510829974
 * description：在Android TV上一般选中某个View, 都会有焦点突出放大的效果, 但是当在RecyclerView中(ListView或GridView)实现当item View执行放大动画后会被其他的item View遮挡.
 原因是: RecyclerView的机制是越靠后的View z-order越高, 所以bringToFront方法是不管用的.
 在实现针对TV端的自定义控件 TvRecyclerView 时遇到此问题, 最后的解决方案是:
 自定义RecyclerView, 重写getChildDrawingOrder方法, 让选中的item最后绘制, 这样就不会让其他view遮挡.
 */
public class ScaleRecyclerView extends RecyclerView {

    private int mSelectedPosition = 0;

    public ScaleRecyclerView(Context context) {
        super(context);
        init();
    }

    public ScaleRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScaleRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //启用子视图排序功能
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    public void onDraw(Canvas c) {
        mSelectedPosition = getChildAdapterPosition(getFocusedChild());
        super.onDraw(c);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int position = mSelectedPosition;
        if (position < 0) {
            return i;
        } else {
            if (i == childCount - 1) {
                if (position > i) {
                    position = i;
                }
                return position;
            }
            if (i == position) {
                return childCount - 1;
            }
        }
        return i;
    }
}
