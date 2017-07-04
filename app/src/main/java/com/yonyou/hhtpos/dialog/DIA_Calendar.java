package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.DIA_Base;
import com.yonyou.hhtpos.widgets.calendar.CalendarView;
import com.yonyou.hhtpos.widgets.calendar.DayAndPrice;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：liushuofei on 2017/7/3 13:50
 * 邮箱：lsf@yonyou.com
 */
public class DIA_Calendar extends DIA_Base{

    public DIA_Calendar(Context context) {
        super(context);
    }

    public Dialog getDialog() {
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        // lp.dimAmount = 0.0f; 背景灰度
        lp.width = 1453; // 设置宽度
        lp.height = 950; // 设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        initData();
        return mDialog;
    }

    private void initData(){
        List<DayAndPrice> list = new ArrayList<DayAndPrice>();
        DayAndPrice dAndPrice = new DayAndPrice("64笔预约", 2017,7,20);
        DayAndPrice dAndPrice1 = new DayAndPrice("68笔预约", 2017,7,10);
        DayAndPrice dAndPrice2 = new DayAndPrice("50笔预约", 2017,7,18);
        DayAndPrice dAndPrice3 = new DayAndPrice("68笔预约", 2017,6,25);

        list.add(dAndPrice);
        list.add(dAndPrice1);
        list.add(dAndPrice2);
        list.add(dAndPrice3);
        final CalendarView calendarView = (CalendarView) mContentView.findViewById(R.id.calendarView);
        calendarView.setListDayAndPrice(list);
        calendarView.setDateViewClick(new CalendarView.DateViewClick() {

            @Override
            public void dateClick() {
                Toast.makeText(mContext, "点击了：" + calendarView.getSelectMonth(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dia_calendar;
    }
}
