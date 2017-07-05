package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yonyou.hhtpos.R;

/**
 * Created by ybing on 2017/7/2.
 */

public class LeftDateSelectView extends LinearLayout{
    /**上下文*/
    private Context mContext;



    public LeftDateSelectView(Context context) {
        this(context,null);
    }

    public LeftDateSelectView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LeftDateSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }
    /**
     * 初始化view
     */
    private void initView() {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.left_date_select_view, this);
    }
}
