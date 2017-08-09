package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_MemberCoupon;
import com.yonyou.hhtpos.adapter.CouponEntity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zj on 2017/8/7.
 * 邮箱：zjuan@yonyou.com
 * 描述：会员-优惠券列表弹窗
 */
public class DIA_MemberCouponList implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.lv_coupon_list)
    ListView mListView;
    @Bind(R.id.tv_total_record_num)
    TextView tvTotalRecordNum;
    protected Dialog mDialog;
    protected View mContentView;
    protected Context mContext;
    private ArrayList<CouponEntity> datas = new ArrayList<>();
    private ADA_MemberCoupon mAdapter;

    public DIA_MemberCouponList(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_transaction_list, null);
        mDialog.setContentView(mContentView);
        ButterKnife.bind(this, mContentView);
        initRecyclerView();
        setData();
    }

    /**
     * 初始化列表View的设置
     */
    private void initRecyclerView() {

        mAdapter = new ADA_MemberCoupon(mContext);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 传入数据
     */
    private void setData() {
        for (int i = 0; i < 30; i++) {
            CouponEntity couponEntity = new CouponEntity();
            datas.add(couponEntity);
        }
        mAdapter.update(datas, true);
        tvTotalRecordNum.setText("(" + datas.size() + ")");
    }

    public Dialog show() {
        mDialog.getWindow().setGravity(Gravity.RIGHT);
        mDialog.getWindow().setWindowAnimations(R.style.style_right_in_anim);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
//        lp.dimAmount = 0.2f; //背景灰度 -0.0全透明
        lp.width = 590; // 设置宽度
        lp.height = 970;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        return mDialog;
    }

    @OnClick({R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                if (null != mDialog) {
                    mDialog.dismiss();
                }
                break;
        }
    }

    @Override
    public void onRefresh() {

    }
}
