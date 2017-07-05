package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.hhtpos.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by ybing on 2017/7/2.
 */

public class DateSlideView extends LinearLayout implements GestureDetector.OnGestureListener, View.OnClickListener{
    /**上下文*/
    private Context mContext;
    private LinearLayout turnAhead;
    private LinearLayout turnAfter;
    private TextView today;
    /**手势管理*/
    private GestureDetector gestureDetector = new GestureDetector(this);

    /**当前日期*/
    private String currentDate = "";
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DateSlideView(Context context) {
        this(context,null);
    }

    public DateSlideView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DateSlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.date_select_view, this);
        turnAhead = (LinearLayout)convertView.findViewById(R.id.ib_turn_ahead);
        turnAfter = (LinearLayout)convertView.findViewById(R.id.ib_turn_after);
        turnAhead.setOnClickListener(this);
        turnAfter.setOnClickListener(this);
        today = (TextView)convertView.findViewById(R.id.tv_date_today);
        /**当前日期*/
        currentDate = sdf.format(calendar.getTime());
        today.setText(currentDate);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_turn_ahead:
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                currentDate = sdf.format(calendar.getTime());
                today.setText(currentDate);
                //上翻一天
                break;
            case R.id.ib_turn_after:
                //下翻一天
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                currentDate = sdf.format(calendar.getTime());
                today.setText(currentDate);
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
