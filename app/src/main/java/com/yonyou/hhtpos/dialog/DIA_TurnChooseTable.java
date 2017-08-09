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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.yonyou.framework.library.adapter.rv.MultiItemTypeAdapter;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_TableArea;
import com.yonyou.hhtpos.adapter.ADA_TableChooseList;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.bean.MealAreaEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.layout_empty)
    LinearLayout layoutEmpty;
    private Context mContext;
    private ADA_TableArea mTableAreaAdapter;

    /**
     * 桌台列表
     */
    private int mColumnNum = 4; //列表显示列数
    private ADA_TableChooseList mAdapter;
    private LRecyclerViewAdapter mLuRecyclerViewAdapter;
    private CanteenTableEntity mTableEntity;
    private List<CanteenTableEntity> mTableList = new ArrayList<>();

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

        initListener();

    }

    /**
     * 设置监听
     */
    private void initListener() {
        //餐区
        mTableAreaAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                mTableAreaAdapter.setSelectItem(position);
                mTableAreaAdapter.notifyDataSetChanged();
                if (mAreaListener != null) {
                    mAreaListener.onMealAreaResult(mTableAreaAdapter.getDataList().get(position), position);
                }
                //默认选中第一个
                if (mTableList != null && mTableList.size() > 0) {
                    mAdapter.setSelectItem(0);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        //桌台
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                int mPosition = position - 1;
                mAdapter.setSelectItem(mPosition);
                mAdapter.notifyDataSetChanged();
                //选择的桌台
                mTableEntity = mAdapter.getDataList().get(mPosition);
                CommonUtils.makeEventToast(mContext, mAdapter.getDataList().get(mPosition).tableName + "--position--" + mPosition, false);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @OnClick({R.id.iv_close, R.id.rb_finish_choose})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;
            case R.id.rb_finish_choose:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                if (mTableListener != null && mTableEntity != null) {
                    mTableListener.onChooseTableResult(mTableEntity);
                }
                break;
        }
    }

    /**
     * 刷新餐区
     */
    public void refreshMealAreaData(List<MealAreaEntity> mealAreas) {
        if (mealAreas != null && mealAreas.size() >= 1) {
            mTableAreaAdapter.update(mealAreas, true);
        }
    }

    /**
     * 刷新桌台列表
     *
     * @param tableList
     */
    public void refreshTableList(List<CanteenTableEntity> tableList) {
        if (tableList != null && tableList.size() > 0) {
            mTableList = tableList;
            layoutEmpty.setVisibility(View.GONE);
            mLRecyclerView.setVisibility(View.VISIBLE);
            mLRecyclerView.setAdapter(mLuRecyclerViewAdapter);
            mTableEntity = mTableList.get(0);
            mAdapter.setSelectItem(0);
            mAdapter.update(mTableList, true);
        }
    }

    /**
     * 无可用桌台的情况
     */
    public void showNoData() {
        layoutEmpty.setVisibility(View.VISIBLE);
        mLRecyclerView.setVisibility(View.GONE);
        mAdapter.update(mTableList, true);
    }

    public Dialog getDialog() {
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 1010; // 设置宽度
        lp.height = 710;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    public void show() {
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
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

    public ADA_TableChooseList getTableListAdapter() {
        return mAdapter;
    }

    /**
     * 选择转出的目标桌台回调结果
     */
    public interface OnChooseTableListener {
        void onChooseTableResult(CanteenTableEntity tableEntity);
    }

    private OnChooseTableListener mTableListener;

    public void setOnChooseResultListener(OnChooseTableListener listener) {
        this.mTableListener = listener;
    }

    /**
     * 选择餐区回调
     */
    public interface OnChooseMealAreaListener {
        void onMealAreaResult(MealAreaEntity areaEntity, int position);
    }

    private OnChooseMealAreaListener mAreaListener;

    public void setOnChooseMealAreaListener(OnChooseMealAreaListener listener) {
        this.mAreaListener = listener;
    }
}
