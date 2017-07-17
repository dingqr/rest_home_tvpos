package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.yonyou.hhtpos.adapter.ADA_LoopRecycler;
import com.yonyou.hhtpos.util.MySnapHelper;

/**
 * Created by ybing on 2017/7/14.
 * 循环的recyclerView
 */

public class LoopRecyclerView extends RecyclerView {
    public LoopRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoopRecyclerView(Context context) {
        super(context);
    }

    public LoopRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
    }
    private void initView() {
        MySnapHelper mMySnapHelper = new MySnapHelper();
        mMySnapHelper.attachToRecyclerView(this);
    }
    @Override
    public ADA_LoopRecycler getAdapter() {
        return (ADA_LoopRecycler) super.getAdapter();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (!(adapter instanceof ADA_LoopRecycler)) {
            throw new IllegalArgumentException("adapter must  instanceof LoopAdapter!");
        }
        super.setAdapter(adapter);
    }
}
