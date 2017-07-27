package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_Coupon;
import com.yonyou.hhtpos.adapter.CouponEntity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zj on 2017/7/27.
 * 邮箱：zjuan@yonyou.com
 * 描述：服务台结账-选择优惠券弹窗
 */
public class DIA_Coupon {
    @Bind(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.gv_coupon)
    GridView mCouponGridView;
    protected Dialog mDialog;
    protected View mContentView;
    protected Context mContext;
    private ADA_Coupon mAdapter;

    private ArrayList<CouponEntity> mData = new ArrayList<>();

    public DIA_Coupon(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_coupon, null);
        mDialog.setContentView(mContentView);
        ButterKnife.bind(this, mContentView);
        tvDialogTitle.setText(mContext.getResources().getString(R.string.string_coupon));
        initListener();
        mCouponGridView.setHorizontalSpacing(70);
        mCouponGridView.setVerticalSpacing(36);
        mAdapter = new ADA_Coupon(mContext);
        mCouponGridView.setAdapter(mAdapter);
        setData();
    }

    private void setData() {
        for (int i = 0; i < 10; i++) {
            CouponEntity couponEntity = new CouponEntity();
            mData.add(couponEntity);
        }
        mAdapter.update(mData);
    }

    private void initListener() {
        mCouponGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSelectItem(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });
    }

    public Dialog show() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 1220; // 设置宽度
        lp.height = 940;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        return mDialog;
    }
}
