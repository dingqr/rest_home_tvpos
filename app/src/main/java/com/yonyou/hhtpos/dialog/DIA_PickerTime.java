package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ArrayWheelAdapter;
import com.yonyou.hhtpos.widgets.WheelView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by zj on 2017/6/29.
 * 邮箱：zjuan@yonyou.com
 * 描述：选择到店时间时弹窗-时间选择控件
 */
public class DIA_PickerTime {
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    @Bind(R.id.wheel_month)
    WheelView wheelMonth;//月
    @Bind(R.id.wheel_day)
    WheelView wheelDay;//日
    @Bind(R.id.wheel_hour)
    WheelView wheelHour;//時
    @Bind(R.id.wheel_second)
    WheelView wheelSecond;//分
//    @Bind(R.id.wheel_zhanwei)
//    WheelView wheel_zhanwei;//分
    private View mContentView;
    private Dialog mDialog;
    private Context mContext;
    private ArrayList<String> monthList = new ArrayList<>();
    private ArrayList<String> dayList = new ArrayList<>();
    private ArrayList<String> duringsList = new ArrayList<>();
    private ArrayList<String> hoursList = new ArrayList<>();
    private ArrayList<String> emptysList = new ArrayList<>();

    //设置一个空发WheelView占位置，可以设置两个WheelView直接的间距
    private String[] emptys = {" "};
    //月份
    private String[] months = {"04", "05", "06", "07", "08", "09"};
    //日
    private String[] days = {"30周一", "29周二", "28周三", "27周五", "26周六", "25周日"};
    //时段
    private String[] durings = {"早晨09:30-10:00", "早晨11:30-14:00", "早晨09:30-10:00", "早晨11:30-14:00", "早晨09:30-10:00"};

    //时分
    private String[] hours = {"12:20", "12:40", "12:50", "12:20", "12:40", "12:50"};

    public DIA_PickerTime(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dia_picker_time, null);
        ButterKnife.bind(this, mContentView);
        mDialog.setContentView(mContentView);
        initData();
        //设置不无限滚动
        wheelMonth.setCyclic(false);
        wheelDay.setCyclic(false);
        wheelHour.setCyclic(false);
        wheelSecond.setCyclic(false);


        wheelMonth.setAdapter(new ArrayWheelAdapter(monthList, 0));
        wheelDay.setAdapter(new ArrayWheelAdapter(dayList, 0));
        wheelHour.setAdapter(new ArrayWheelAdapter(duringsList, 0));
        wheelSecond.setAdapter(new ArrayWheelAdapter(hoursList, 0));
//        wheel_zhanwei.setAdapter(new ArrayWheelAdapter(emptysList, 0));

//        //设置初始值
//        wheelMonth.setCurrentItem(0);
//        selectedReason = refoundReasons.get(wheelMonth.getCurrentItem());
//        //选中后的回调
//        wheelMonth.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(int index) {
//                selectedReason = refoundReasons.get(index);
//            }
//        });
    }

    @OnClick({R.id.tv_cancel, R.id.tv_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;
            case R.id.tv_confirm:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;
        }
    }

    /**
     * 传入数据
     */
    private void initData() {
        for (int i = 0; i < months.length; i++) {
            monthList.add(months[i]);
        }
        for (int i = 0; i < emptys.length; i++) {
            emptysList.add(emptys[i]);
        }
        for (int i = 0; i < days.length; i++) {
            dayList.add(days[i]);
        }
        for (int i = 0; i < durings.length; i++) {
            duringsList.add(durings[i]);
        }
        for (int i = 0; i < hours.length; i++) {
            hoursList.add(hours[i]);
        }
    }

    public Dialog show() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 780; // 设置宽度
        lp.height = 480;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        return mDialog;
    }
}
