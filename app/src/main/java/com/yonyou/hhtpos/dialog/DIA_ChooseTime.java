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
import com.yonyou.hhtpos.bean.DistributeTimeEntity;
import com.yonyou.hhtpos.util.NavigationUtil;
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
    @Bind(R.id.minute_wheel_view)
    WheelView wheelMinute;//分
    private View mContentView;
    private Dialog mDialog;
    private Context mContext;
    //时
    private ArrayList<String> mHoursList = new ArrayList<>();
    //分
    private ArrayList<String> mMinuteList = new ArrayList<>();

    private final DistributeTimeEntity mTimeEntity;
    private OnTimeSelectedListener mTimeSelectedListener;

    public DIA_ChooseTime(Context context, ArrayList<String> hoursList, ArrayList<String> minuteList) {
        this.mContext = context;

        if (hoursList != null && minuteList != null) {
            this.mHoursList = hoursList;
            this.mMinuteList = minuteList;
        } else {
            //测试数据
            mHoursList = NavigationUtil.getDefaultHourData();
            mMinuteList = NavigationUtil.getDefaultSecondData();
        }

        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dia_wm_choose_time, null);
        ButterKnife.bind(this, mContentView);
        mDialog.setContentView(mContentView);

        //设置不无限滚动
        wheelHour.setCyclic(false);
        wheelMinute.setCyclic(false);


        wheelHour.setAdapter(new ArrayWheelAdapter(mHoursList, 0));
        wheelMinute.setAdapter(new ArrayWheelAdapter(mMinuteList, 0));

        //设置初始值
        wheelHour.setCurrentItem(0);
        wheelMinute.setCurrentItem(0);
        mTimeEntity = new DistributeTimeEntity();
        mTimeEntity.hour = mHoursList.get(wheelHour.getCurrentItem());
        mTimeEntity.minute = mMinuteList.get(wheelMinute.getCurrentItem());

        initListener();

    }

    /**
     * 设置监听
     */
    private void initListener() {
        wheelHour.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mTimeEntity.hour = mHoursList.get(index);
            }
        });
        wheelMinute.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mTimeEntity.minute = mMinuteList.get(index);
            }
        });
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
                //回调被选中的时间
                if (mTimeSelectedListener != null) {
                    mTimeSelectedListener.onTimeSelected(mTimeEntity);
                }
                break;
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

    /***
     * 回调时间选择后的结果
     */
    public interface OnTimeSelectedListener {
        void onTimeSelected(DistributeTimeEntity timeEntity);
    }

    public void setOnTimeSelectedListener(OnTimeSelectedListener mTimeSelectedListener) {
        this.mTimeSelectedListener = mTimeSelectedListener;
    }
}
