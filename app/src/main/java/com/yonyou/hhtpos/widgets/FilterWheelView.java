package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_FilterWheel;
import com.yonyou.hhtpos.adapter.ADA_Filtration;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.wm.OpenOrderEntity;
import com.yonyou.hhtpos.dialog.DIA_TakeOutOpenOrder;


/**
 * Created by ybing on 2017/6/23.
 * 邮箱：ybing@yonyou.com
 * 描述：单项筛选横向循环滚动组件
 */

public class FilterWheelView extends LinearLayout {
    /**
     * 筛选框的标题
     */
    private TextView filtrationType;

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 筛选列表
     */
    private LoopRecyclerView mLoopRecyclerView;
    /**
     * 筛选列表布局管理
     */
    LinearLayoutManager linearManager;
    /**
     * 筛选框的选项数据
     */
    private FilterItemEntity filterItemEntity;

    /**
     * 数据适配器
     */
    private ADA_FilterWheel mAdapter;

    /**
     * 当前实体
     */
    private FilterOptionsEntity currentBean;

    /**
     * 滚动标尺
     */
    private ImageView ivStandard;

    /**Adapter items 回调*/
    private AdaItemCallback adaItemCallback;

    public FilterWheelView(Context context) {
        this(context, null);
    }

    public FilterWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }


    private void initView() {
        //初始化view
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.filter_wheel_view, this);
        filtrationType = (TextView) convertView.findViewById(R.id.tv_filtration_type);
        mLoopRecyclerView = (LoopRecyclerView) convertView.findViewById(R.id.rv_filtration_options);
        linearManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mLoopRecyclerView.setLayoutManager(linearManager);
        ivStandard = (ImageView) convertView.findViewById(R.id.iv_standard);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    public void setData(FilterItemEntity filterItemEntity) {
        this.filterItemEntity = filterItemEntity;
        initData();
    }
    private void initData(){
        if (filterItemEntity != null) {
            if (!TextUtils.isEmpty(filterItemEntity.getTitle())) {
                filtrationType.setText(filterItemEntity.getTitle());
            }
            if (filterItemEntity.getOptions() != null) {
                mAdapter = new ADA_FilterWheel(mContext, filterItemEntity.getOptions());
            }
            mLoopRecyclerView.setAdapter(mAdapter);
            if (adaItemCallback!=null){
                adaItemCallback.sendItems(mAdapter.getItemRawCount());
            }
            mLoopRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int items = mAdapter.getItemRawCount();
                        int firstItemPosition =linearManager.findFirstVisibleItemPosition();
                        mAdapter.highlightItem(firstItemPosition % items);
                    }
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        mAdapter.reset();
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
            //找到当前选中的实体
            for (int i = 0; i < filterItemEntity.getOptions().size(); i++) {
                FilterOptionsEntity bean = filterItemEntity.getOptions().get(i);
                if (bean.isCheck()) {
                    // 当前选中实体类
                    currentBean = bean;
                    break;
                }
            }
        }
    }

    public interface AdaItemCallback{
        void sendItems(int items);
    }

    public void setAdaItemCallback(AdaItemCallback adaItemCallback) {
        this.adaItemCallback = adaItemCallback;
    }
}
