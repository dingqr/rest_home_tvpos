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
import com.yonyou.hhtpos.adapter.ADA_MultipleSelector;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;

import java.util.ArrayList;


/**
 * Created by ybing on 2017/7/7.
 * 邮箱：ybing@yonyou.com
 * 描述：多类型筛选，每个类型内为多项选择组件
 */

public class MultipleSelectView extends LinearLayout implements ADA_MultipleSelector.OnItemClickListener {

    /**筛选框的类别*/
    private static final int TAKE_OUT_TYPE = 0;
    private static final int MARKET_TYPE = 1;
    private static final int DISH_REMARK = 2;

    /**筛选框的标题*/
    private TextView filtrationType;

    /**上下文*/
    private Context mContext;

    /**筛选列表*/
    private RecyclerView mRecyclerView;


    /**筛选框的选项数据*/
    private FilterItemEntity filterItemEntity;

    /**数据适配器*/
    private ADA_MultipleSelector mAdapter;

    /**当前选中的实体*/
    private FilterOptionsEntity currentBean;

    public MultipleSelectView(Context context) {
        this(context, null);
    }

    public MultipleSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }


    private void initView() {
        //初始化view
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.mult_select_view, this);
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
                mAdapter = new ADA_MultipleSelector(mContext, filterItemEntity.getOptions());
            }
            //按照类别设置recyclerView的layoutManager 和adapter
            if (filterItemEntity.getOptions().get(0).getType() != -1){
                switch (filterItemEntity.getOptions().get(0).getType()){
                    case TAKE_OUT_TYPE:
                        layoutManger = new GridLayoutManager(mContext, 3);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    case MARKET_TYPE:
                        layoutManger = new GridLayoutManager(mContext, 3);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    case DISH_REMARK:
                        filtrationType.setVisibility(View.GONE);
                        layoutManger = new GridLayoutManager(mContext, 4);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    default:
                        filtrationType.setVisibility(View.GONE);
                        layoutManger = new GridLayoutManager(mContext, 3);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                }
            }
            mAdapter.setmOnItemClickListener(this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
    public ArrayList<FilterOptionsEntity> getSelectedList(){
        mAdapter.notifyDataSetChanged();
        return  mAdapter.getSelectedItem();
    }

    public void reset(){
        for(int i=0;i<filterItemEntity.getOptions().size();i++){
            filterItemEntity.getOptions().get(i).setCheck(false);
        }
        mAdapter.updateDataSet(filterItemEntity.getOptions());
    }
    @Override
    public void onItemClick(View view, int position) {
        currentBean = filterItemEntity.getOptions().get(position);
    }
}
