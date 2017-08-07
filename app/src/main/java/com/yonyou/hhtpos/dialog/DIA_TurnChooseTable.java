package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yonyou.framework.library.adapter.rv.MultiItemTypeAdapter;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_TableArea;
import com.yonyou.hhtpos.adapter.ADA_TableChooseList;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.bean.TableAreaEntity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zj on 2017/7/10.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食-转入桌台选择弹窗
 */
public class DIA_TurnChooseTable {
    private Dialog mDialog;
    private View mContentView;
    @Bind(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @Bind(R.id.rv_table_area)
    RecyclerView rvTableArea;
    @Bind(R.id.lrecyclerview)
    LRecyclerView mLRecyclerView;
    private Context mContext;
    private ArrayList<TableAreaEntity> mTableAreaList = new ArrayList<>();
    private String[] tableAreas = {"全部餐厅", "一楼宴会厅", "一楼大厅", "一楼包厢"};
    private ADA_TableArea mTableAreaAdapter;

    /**
     * 桌台列表
     */
    private int mColumnNum = 4; //列表显示列数
    private ADA_TableChooseList mAdapter;
    private LRecyclerViewAdapter mLuRecyclerViewAdapter;
    private ArrayList<CanteenTableEntity> mTableList = new ArrayList<>();

    public DIA_TurnChooseTable(Context context) {
        mContext = context;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_turn_choose_table, null);
        ButterKnife.bind(this, mContentView);
        mDialog.setContentView(mContentView);

        tvDialogTitle.setText(mContext.getResources().getString(R.string.title_choose_table));


        //餐区选择横向列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rvTableArea.setLayoutManager(linearLayoutManager);
        mTableAreaAdapter = new ADA_TableArea(mContext);
        rvTableArea.setAdapter(mTableAreaAdapter);


        //桌台网格列表

        //去除LRecyclerView的默认的下拉刷新效果
        mLRecyclerView.setPullRefreshEnabled(false);
        mAdapter = new ADA_TableChooseList(mContext);
        //设置LayoutManager必须在设置setAdapter之前
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, mColumnNum);
        mLRecyclerView.setLayoutManager(gridLayoutManager);
        //setAdapter
        mLuRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mLRecyclerView.addItemDecoration(new SpaceItemDecoration());
        //设置底部加载颜色-
        mLRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.dark, R.color.color_dcdcdc);
        //设置底部加载文字提示
        mLRecyclerView.setFooterViewHint(mContext.getResources().getString(R.string.loading_note), mContext.getResources().getString(R.string.no_more_note), "");

        mLRecyclerView.setHasFixedSize(true);
        setData();

        initListener();

    }

    /**
     * 设置监听
     */
    private void initListener() {
        mTableAreaAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                mTableAreaAdapter.setSelectItem(position);
                mTableAreaAdapter.notifyDataSetChanged();
                CommonUtils.makeEventToast(mContext,mTableAreaList.get(position).name,false);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                mAdapter.setSelectItem(position);
                mAdapter.notifyDataSetChanged();
                CommonUtils.makeEventToast(mContext,mTableList.get(position).tableName,false);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @OnClick({R.id.iv_close})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;
        }
    }

    /**
     * 测试数据
     */
    public void setData() {
        for (int i = 0; i < tableAreas.length; i++) {
            TableAreaEntity tableAreaEntity = new TableAreaEntity();
            tableAreaEntity.name = tableAreas[i];
            mTableAreaList.add(tableAreaEntity);
        }
        mTableAreaAdapter.update(mTableAreaList);

        for (int i = 0; i < 30; i++) {
            CanteenTableEntity canteenTableEntity = new CanteenTableEntity();
            canteenTableEntity.tableName = "item=" + i;
            mTableList.add(canteenTableEntity);
        }
        mAdapter.update(mTableList);
    }

    public Dialog show() {
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 1010; // 设置宽度
        lp.height = 710;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        return mDialog;
    }


    /**
     * 设置item之间的间距
     */
    public class SpaceItemDecoration extends LuRecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration() {
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            outRect.left = 5;
            outRect.right = 5;
            outRect.bottom = 5;
            outRect.top = 5;
//            //header占了一个位置，故从位置1开始显示实际的item-第一行不设置顶部间距(UI)
//            if (itemPosition <= mColumnNum) {
//                outRect.top = 7;
//            } else {
//                outRect.top = 5;
//            }

        }
    }

}
