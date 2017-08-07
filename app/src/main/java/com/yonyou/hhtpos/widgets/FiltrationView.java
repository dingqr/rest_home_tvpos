package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    /**
     * 筛选框的类别
     */
    public static final int VIEW_DISH_TYPE = 0;
    public static final int VIEW_DISH_AREA = 1;
    public static final int VIEW_RESERVE_STATUS = 2;
    public static final int VIEW_TAKEOUT_TYPE = 3;
    public static final int REFUND_REASON = 4;
    public static final int ORDER_RESOURCE = 5;
    public static final int COOKERY = 6;
    public static final int DISH_NORMS = 7;
    public static final int FREE_REASON = 9;
    public static final int WORK_STATE = 10;
    public static final int EMPLOYEE_POSITION = 11;

    /**
     * 筛选框的标题
     */
    private TextView filtrationType;
    private TextView optionChange;
    private RelativeLayout rlContent;


    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 筛选列表
     */
    private RecyclerView mRecyclerView;

    /**
     * 筛选框的选项数据
     */
    private FilterItemEntity filterItemEntity;

    /**
     * 数据适配器
     */
    private ADA_Filtration mAdapter;

    /**
     * 当前实体
     */
    private FilterOptionsEntity currentBean;

    /**
     * 选中实体的回调类
     */
    private SelectCallback selectCallback;

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
        rlContent = (RelativeLayout) convertView.findViewById(R.id.rl_content);
        filtrationType = (TextView) convertView.findViewById(R.id.tv_filtration_type);
        optionChange = (TextView) convertView.findViewById(R.id.tv_change);
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
            if (!TextUtils.isEmpty(filterItemEntity.getTitle())) {
                filtrationType.setText(filterItemEntity.getTitle());
            }
            if (filterItemEntity.getOptions() != null && filterItemEntity.getOptions().size() > 0) {
                mAdapter = new ADA_Filtration(mContext, filterItemEntity.getOptions());
            }
            //按照类别设置recyclerView的layoutManager 和adapter
            if (filterItemEntity.getOptions().size() > 0 &&
                    filterItemEntity.getOptions().get(0).getType() != -1) {
                switch (filterItemEntity.getOptions().get(0).getType()) {
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
                    case VIEW_TAKEOUT_TYPE:
                        filtrationType.setVisibility(View.GONE);
                        rlContent.setVisibility(View.GONE);
                        layoutManger = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    case REFUND_REASON:
                        if (filterItemEntity.getOptions().size() > 8)
                            optionChange.setVisibility(View.VISIBLE);
//                        layoutManger = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
                        layoutManger = new GridLayoutManager(mContext, 4);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    case ORDER_RESOURCE:
                        layoutManger = new GridLayoutManager(mContext, 2);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    case COOKERY:
                        filtrationType.setVisibility(View.GONE);
                        rlContent.setVisibility(View.GONE);
                        layoutManger = new GridLayoutManager(mContext, 4);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    case DISH_NORMS:
                        filtrationType.setVisibility(View.GONE);
                        rlContent.setVisibility(View.GONE);
                        layoutManger = new GridLayoutManager(mContext, 4);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    case FREE_REASON:
                        if (filterItemEntity.getOptions().size() > 8)
                            optionChange.setVisibility(View.VISIBLE);
                        layoutManger = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    case WORK_STATE:
                        layoutManger = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    case EMPLOYEE_POSITION:
                        layoutManger = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                    default:
                        layoutManger = new GridLayoutManager(mContext, 3);
                        mRecyclerView.setLayoutManager(layoutManger);
                        break;
                }
                mAdapter.setmOnItemClickListener(this);
                mRecyclerView.setAdapter(mAdapter);
            }

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
        if (selectCallback != null) {
            selectCallback.sendSelectedItem(currentBean);
        }
    }

    /**
     * 把所有选项都置为未选中
     */
    public void reset() {
        if (filterItemEntity.getOptions() != null && filterItemEntity.getOptions().size() > 0) {
            for (int i = 0; i < filterItemEntity.getOptions().size(); i++) {
                filterItemEntity.getOptions().get(i).setCheck(false);
            }
            currentBean = null;
            mAdapter.update(filterItemEntity.getOptions());
        }
    }

    public interface SelectCallback {
        void sendSelectedItem(FilterOptionsEntity foe);
    }

    public void setSelectCallback(SelectCallback selectCallback) {
        this.selectCallback = selectCallback;
    }
}
