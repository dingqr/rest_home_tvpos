package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_RightTitle;
import com.yonyou.hhtpos.bean.RightTitleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2017/6/23.
 * 邮箱：zjuan@yonyou.com
 * 描述：右侧带角标可刷新角标数量的导航栏
 */
public class RightNavigationView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private RightListView rightListView;
    private List<RightTitleEntity> mData = new ArrayList<>();
    private TextView headTitle;
    private TextView bottomTitle;
    private OnItemClickListener mListener;
    private LinearLayout llTopTitle;
    private LinearLayout llBottomTitle;

    public RightNavigationView(Context context) {
        this(context, null);
    }

    public RightNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
        initListener();
    }

    /**
     * 初始化
     */
    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.right_nevigation_linear, this);
        rightListView = (RightListView) view.findViewById(R.id.rightListView);
        headTitle = (TextView) view.findViewById(R.id.head_title);
        bottomTitle = (TextView) view.findViewById(R.id.bottom_title);
        llTopTitle = (LinearLayout) view.findViewById(R.id.ll_top_title);
        llBottomTitle = (LinearLayout) view.findViewById(R.id.ll_bottom_title);
    }

    /**
     * 监听器
     */
    private void initListener() {
        rightListView.setOnRightListViewItemClickListener(new RightListView.OnRightListViewItemClickListener() {
            @Override
            public void onItemClick(int count, String title, int position) {
                //设置点击列表时，重置外部两个标题的字体颜色
                bottomTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
                headTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
                if (mListener != null) {
                    mListener.onItemClick(count, title, position);
                }
            }
        });
        llTopTitle.setOnClickListener(this);
        llBottomTitle.setOnClickListener(this);
    }

    /**
     * 设置外部标题点击时选中颜色
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_top_title:
                headTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
                bottomTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
                rightListView.getRLAdapter().setSelectItem(-1);
                rightListView.getRLAdapter().notifyDataSetChanged();
                break;
            case R.id.ll_bottom_title:
                bottomTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
                headTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
                rightListView.getRLAdapter().setSelectItem(-1);
                rightListView.getRLAdapter().notifyDataSetChanged();
                break;
        }
    }

    /**
     * 传入数据
     *
     * @param datas
     */
    public void setData(List<RightTitleEntity> datas) {
        this.mData = datas;
        rightListView.setData(mData);
    }

    /**
     * 外界点击item时，刷新对应item的id值相匹配的右侧标题角标的数量
     *
     * @param id
     * @param refreshCount
     */
    public void refreshCount(String id, boolean refreshCount) {
        ADA_RightTitle adapter = (ADA_RightTitle) rightListView.getAdapter();
        adapter.refreshCount(rightListView, id, refreshCount);
        adapter.notifyDataSetChanged();
    }


    interface OnItemClickListener {
        void onItemClick(int count, String title, int postion);
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }
}
