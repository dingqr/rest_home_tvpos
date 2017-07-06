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
 * Created by zj on 2017/7/6.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖开单选择时间弹窗页面
 */
public class DIA_ChooseTime {
    @Bind(R.id.btn_cancel)
    TextView btnCancel;
    @Bind(R.id.btn_confirm)
    TextView btnConfirm;
    @Bind(R.id.hour_wheel_view)
    WheelView wheelHour;//時
    @Bind(R.id.second_wheel_view)
    WheelView wheelSecond;//分
//    @Bind(R.id.wheel_zhanwei)
//    WheelView wheel_zhanwei;//分
    private View mContentView;
    private Dialog mDialog;
    private Context mContext;
    private ArrayList<String> secondList = new ArrayList<>();
    private ArrayList<String> hoursList = new ArrayList<>();

    //时
    private String[] hours = {"14", "15", "16", "17", "18"};

    //分
    private String[] seconds = {"00", "15", "30", "45","55"};

    public DIA_ChooseTime(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dia_wm_choose_time, null);
        ButterKnife.bind(this, mContentView);
        mDialog.setContentView(mContentView);
        initData();
        //设置不无限滚动
        wheelHour.setCyclic(false);
        wheelSecond.setCyclic(false);


        wheelHour.setAdapter(new ArrayWheelAdapter(hoursList, 0));
        wheelSecond.setAdapter(new ArrayWheelAdapter(secondList, 0));
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

    @OnClick({R.id.btn_cancel, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;
            case R.id.btn_confirm:
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
        for (int i = 0; i < seconds.length; i++) {
            secondList.add(seconds[i]);
        }
        for (int i = 0; i < hours.length; i++) {
            hoursList.add(hours[i]);
        }
    }

    public Dialog show() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 440; // 设置宽度
        lp.height = 430;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        return mDialog;
    }
}
