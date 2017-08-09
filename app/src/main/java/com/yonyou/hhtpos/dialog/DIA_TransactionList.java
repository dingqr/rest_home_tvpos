package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_TransactionList;
import com.yonyou.hhtpos.bean.TransactionRecordEntity;
import com.yonyou.hhtpos.util.DP2PX;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zj on 2017/8/7.
 * 邮箱：zjuan@yonyou.com
 * 描述：会员-交易记录弹窗
 */
public class DIA_TransactionList implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.rl_transaction_list)
    LuRecyclerView mRecyclerView;
    @Bind(R.id.tv_total_record_num)
    TextView tvTotalRecordNum;
    protected Dialog mDialog;
    protected View mContentView;
    protected Context mContext;
    private ArrayList<TransactionRecordEntity> datas = new ArrayList<>();
    private ADA_TransactionList mAdapter;
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter;

    public DIA_TransactionList(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_member_coupon_list, null);
        mDialog.setContentView(mContentView);
        ButterKnife.bind(this, mContentView);
        initRecyclerView();
        setData();
    }

    /**
     * 初始化列表View的设置
     */
    private void initRecyclerView() {
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, DP2PX.dip2px(mContext, 30));
            mSwipeRefreshLayout.setColorSchemeColors(
                    mContext.getResources().getColor(R.color.gplus_color_1),
                    mContext.getResources().getColor(R.color.gplus_color_2),
                    mContext.getResources().getColor(R.color.gplus_color_3),
                    mContext.getResources().getColor(R.color.gplus_color_4));
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }

        mAdapter = new ADA_TransactionList(mContext);
        //设置LayoutManager必须在设置setAdapter之前
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //setAdapter
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        //loadmore
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                loadMore();
            }
        });
        //设置底部加载颜色-
        mRecyclerView.setFooterViewColor(R.color.color_eb6247, R.color.color_999999, R.color.color_FFFFFF);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint(mContext.getResources().getString(R.string.loading_note), mContext.getResources().getString(R.string.no_more_note), "");
        mRecyclerView.setHasFixedSize(true);
    }

    /**
     * 传入数据
     */
    private void setData() {
        for (int i = 0; i < 30; i++) {
            TransactionRecordEntity transactionRecordEntity = new TransactionRecordEntity();
            datas.add(transactionRecordEntity);
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
