package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_PreviewList;
import com.yonyou.hhtpos.adapter.ADA_ReserveOrderList;
import com.yonyou.hhtpos.adapter.ADA_SelectTableArea;
import com.yonyou.hhtpos.adapter.ADA_SelectTableCapacity;
import com.yonyou.hhtpos.bean.PreviewListEntity;
import com.yonyou.hhtpos.bean.ReserveOrderListEntity;

import java.util.ArrayList;

import static com.yonyou.hhtpos.application.MyApplication.getContext;


/**
 * Created by ybing on 2017/7/3.
 * 包房预定列表弹窗
 */

public class DIA_ReserveList {

    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    private ADA_ReserveOrderList mAdapter;
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter;
    private ArrayList<ReserveOrderListEntity> orderListEntities;

    private TextView title;
    private ImageButton close;
    private SwipeRefreshLayout mSwipeRefreshView;
    private LuRecyclerView mLuRecyclerView;

    public DIA_ReserveList(Context mContext, ArrayList<ReserveOrderListEntity> orderListEntities) {
        this.mContext = mContext;
        this.orderListEntities = orderListEntities;

        initView();
        initData();
    }


    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_reserve_list, null);
        mDialog.setContentView(mContentView);

        title = (TextView)mContentView.findViewById(R.id.tv_title);
        close = (ImageButton)mContentView.findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mSwipeRefreshView = (SwipeRefreshLayout) mContentView.findViewById(R.id.order_list_swipe_layout);
        mLuRecyclerView = (LuRecyclerView) mContentView.findViewById(R.id.order_list_lrv_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        mLuRecyclerView.setLayoutManager(linearLayoutManager);
        mLuRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.dark, android.R.color.white);
        mLuRecyclerView.setFooterViewHint(mContext.getResources().getString(R.string.loading_note),
                mContext.getResources().getString(R.string.no_more_note), null);
        //加载更多
        mLuRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
    }

    private void loadMore() {
        mLuRecyclerView.setNoMore(true);
    }

    private void initData() {
        title.setText("包房002（6预订）");
        mAdapter = new ADA_ReserveOrderList(mContext,orderListEntities);
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mAdapter);
        mLuRecyclerView.setAdapter(mLuRecyclerViewAdapter);
    }

    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.RIGHT);
        mDialog.getWindow().setWindowAnimations(R.style.style_right_in_anim);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.5f;// 背景灰度
        lp.width = 590;
        lp.height = 970;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }
}

//        mListView = (ListView)mContentView.findViewById(R.id.order_list);

//        ArrayList<PreviewListEntity> datas = new ArrayList<>();
//        for (int i=0; i<10;i++){
//            datas.add(new PreviewListEntity());
//        }
//        adapter = new ADA_PreviewList(mContext);
//        mListView.setAdapter(adapter);
//        adapter.update(datas);
