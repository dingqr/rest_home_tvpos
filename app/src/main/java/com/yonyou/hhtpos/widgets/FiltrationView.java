package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_Filtration;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;


/**
 * Created by ybing on 2017/6/23.
 * 邮箱：ybing@yonyou.com
 * 描述：单项筛选组件
 */

public class FiltrationView extends LinearLayout implements ADA_Filtration.OnItemClickListener {
    /**筛选框的类别*/
    private static final int VIEW_DISH_TYPE = 0;
    private static final int VIEW_DISH_AREA = 1;
    private static final int VIEW_RESERVE_STATUS = 2;

    /**筛选框的标题*/
    private TextView filtrationType;

    /**上下文*/
    private Context mContext;

    /**筛选列表*/
    private RecyclerView mRecyclerView;


    /**筛选框的选项数据*/
    private FilterItemEntity filterItemEntity;

    /**数据适配器*/
    private ADA_Filtration mAdapter;

    /**当前实体*/
    private FilterOptionsEntity currentBean;

    public FiltrationView(Context context) {
        this(context, null);
    }

    public FiltrationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FiltrationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }


    private void initView() {
        //初始化view
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.filtration_view, this);
        filtrationType = (TextView) convertView.findViewById(R.id.tv_filtration_type);
        mRecyclerView = (RecyclerView) convertView.findViewById(R.id.rv_filtration_options);
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
        RecyclerView.LayoutManager layoutManger;
        if (filterItemEntity != null) {
            if (!TextUtils.isEmpty(filterItemEntity.getTitle())){
                filtrationType.setText(filterItemEntity.getTitle());
            }
            if (filterItemEntity.getOptions() != null){
                mAdapter = new ADA_Filtration(mContext, filterItemEntity.getOptions());
            }
            //按照类别设置recyclerView的layoutManager
            if (filterItemEntity.getOptions().get(0).getType() != -1){
                switch (filterItemEntity.getOptions().get(0).getType()){
                    case VIEW_DISH_TYPE:
                        layoutManger = new GridLayoutManager(mContext, 3);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    case VIEW_DISH_AREA:
                        layoutManger = new GridLayoutManager(mContext, 3);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    case VIEW_RESERVE_STATUS:
                        layoutManger = new GridLayoutManager(mContext, 3);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    default:
                        layoutManger = new GridLayoutManager(mContext, 3);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                }
            }
            mAdapter.setmOnItemClickListener(this);
            mRecyclerView.setAdapter(mAdapter);
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

    public FilterOptionsEntity getSelectedData() {
        return currentBean;
    }

    @Override
    public void onItemClick(View view, int position) {
        currentBean = filterItemEntity.getOptions().get(position);
    }
}
