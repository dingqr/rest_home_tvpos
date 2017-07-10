package com.yonyou.hhtpos.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.widgets.FiltrationView;

import java.util.ArrayList;

import static com.yonyou.hhtpos.util.FiltrationUtil.getOrderOptions;

/**
 * 外卖开单对话框
 * 作者：ybing on 2017/6/29 13:36
 * 邮箱：ybing@yonyou.com
 */

public class DIA_TakeOutOpenOrder implements View.OnClickListener{
    /**上下文 */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;
    /**界面控件 */
    private RadioButton rbReserveTime;
    private RadioButton rbRightNow;
    private RadioButton rbConfirmOpenOrder;
    private ImageButton ibClose;
    private FiltrationView filtrationView;
    private FilterItemEntity filterItemEntity;
    private EditText etSelectDeliverTime;

    public DIA_TakeOutOpenOrder(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_take_out_open_order, null);
        mDialog.setContentView(mContentView);

        filtrationView =(FiltrationView) mContentView.findViewById(R.id.fv_order_source);
        rbReserveTime =(RadioButton) mContentView.findViewById(R.id.rb_reserve_time);
        rbRightNow =(RadioButton) mContentView.findViewById(R.id.rb_right_now);
        rbConfirmOpenOrder =(RadioButton) mContentView.findViewById(R.id.rb_confirm_open_order);
        ibClose =(ImageButton) mContentView.findViewById(R.id.ib_close);
        etSelectDeliverTime =(EditText) mContentView.findViewById(R.id.et_select_deliver_time);

        ibClose.setOnClickListener(this);
        rbReserveTime.setOnClickListener(this);
        rbRightNow.setOnClickListener(this);

        filterItemEntity = new FilterItemEntity();
        filterItemEntity.setTitle("");
        filterItemEntity.setOptions(getOrderOptions());
        filtrationView.setData(filterItemEntity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_reserve_time:
                etSelectDeliverTime.setVisibility(View.VISIBLE);

                break;
            case R.id.rb_right_now:
                etSelectDeliverTime.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
//        lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 9; // 设置宽度
//        lp.height = ScreenUtil.getScreenHeight((Activity)mContext)/ 10 * 8; // 设置高度
        lp.width = 580;
        lp.height= 920;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

}
