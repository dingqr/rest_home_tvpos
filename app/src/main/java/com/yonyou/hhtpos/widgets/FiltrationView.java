package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.FiltrationAdapter;
import com.yonyou.hhtpos.bean.FilterDataEntity;

import java.util.List;


/**
 * Created by ybing on 2017/6/23.
 */

public class FiltrationView extends LinearLayout implements FiltrationAdapter.OnItemClickListener{
    /**筛选框的标题*/
    private String title;
    private TextView filtrationType;

    /**上下文*/
    private Context mContext;

    /**筛选列表*/
    private RecyclerView mRecyclerView;

    /**筛选框的选项数据*/
    private List<FilterDataEntity> mDatas;

    /**数据适配器*/
    private FiltrationAdapter mAdapter;

    /**recycView的布局管理器*/
    private RecyclerView.LayoutManager layoutManager;

    /**当前实体*/
    private FilterDataEntity currentBean;

    public FiltrationView(Context context) {
       this(context,null);
    }

    public FiltrationView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public FiltrationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }


    private void initView() {
        //初始化view
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.filtration_view,this);
        filtrationType = (TextView)convertView.findViewById(R.id.tv_filtration_type);
        mRecyclerView = (RecyclerView)convertView.findViewById(R.id.rv_filtration_options);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public void setData(List<FilterDataEntity> mDatas) {
        this.mDatas = mDatas;
        filtrationType.setText(title);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new FiltrationAdapter(mContext,mDatas);
        mAdapter.setmOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        //找到当前选中的实体
        for (int i=0;i<mDatas.size();i++){
            FilterDataEntity bean = mDatas.get(i);
            if (bean.isCheck()) {
                // 当前选中实体类
                currentBean = bean;
                break;
            }
        }
    }

    public FilterDataEntity getSelectedData(){
        return currentBean;
    }

    @Override
    public void onItemClick(View view, int position) {
        currentBean = mDatas.get(position);
    }
}
