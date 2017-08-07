package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yonyou.framework.library.common.CommonUtils;
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
 * 描述：退款原因弹窗
 */
public class DIA_RefundReason {
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    @Bind(R.id.reason_wheel_view)
    WheelView reasonWheelView;
    private View mContentView;
    private Dialog mDialog;
    private Context mContext;
    private ArrayList<String> refoundReasons = new ArrayList<>();;
    private String[] strings = {"服务员不满意", "服务员不好看", "服务员点错菜", "服务员太胖",
            "服务员难交流", "服务员卖萌", "服务员太高", "服务员太瘦", "餐厅菜不好吃",
            "餐厅不注意卫生", "服务员太漂亮", "服务员太多"};
    private String selectedReason;

    public DIA_RefundReason(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dia_refund_reason, null);
        ButterKnife.bind(this,mContentView);
        mDialog.setContentView(mContentView);
        initData();
        reasonWheelView.setCyclic(false);
        reasonWheelView.setAdapter(new ArrayWheelAdapter(refoundReasons, 0));
        //设置初始值
        reasonWheelView.setCurrentItem(0);
        selectedReason = refoundReasons.get(reasonWheelView.getCurrentItem());
        //选中后的回调
        reasonWheelView.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectedReason = refoundReasons.get(index);
            }
        });
    }

    @OnClick({R.id.tv_cancel,R.id.tv_confirm})
    public void onClick(View v){
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
                CommonUtils.makeEventToast(mContext,selectedReason,false);
                break;
        }
    }

    /**
     * 传入数据
     */
    private void initData() {
        for (int i = 0; i < strings.length; i++) {
            refoundReasons.add(strings[i]);
        }
    }

    public Dialog show() {
        mDialog.getWindow().setGravity(Gravity.BOTTOM);
        mDialog.getWindow().setWindowAnimations(R.style.style_bottom_in_anim);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = ActionBar.LayoutParams.MATCH_PARENT; // 设置宽度
//        lp.height = 436;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        return mDialog;
    }
}
