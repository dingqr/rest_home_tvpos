package com.yonyou.hhtpos.widgets;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_RightTitle;
import com.yonyou.hhtpos.bean.dish.DishTypesEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2017/6/22.
 * 邮箱：zjuan@yonyou.com
 * 描述：右侧列表导航栏
 */
public class RightListView extends ListView {
    private final Context mContext;
    private ADA_RightTitle mAdapter;
    private List<DishTypesEntity> mData = new ArrayList<>();
    private OnRightListViewItemClickListener mClickListener;

    public RightListView(Context context) {
        this(context, null);
    }

    public RightListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mAdapter = new ADA_RightTitle(mContext, mData);
        setDivider(null);
        //去除默认点击效果
        setSelector(R.drawable.bg_transparent_selector);
        //去除滚动条
        setVerticalScrollBarEnabled(false);
        initListner();

    }

    private void initListner() {
        //设置选中效果
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSelectItem(position);
                mAdapter.notifyDataSetChanged();
                if (mClickListener != null) {
                    mClickListener.onItemClick(mData.get(position).count, mData.get(position).dishTypeName, position);
                }
            }
        });
        //外界刷新角标数量时，回调所刷新的位置和刷新后的数量
        mAdapter.setRefreshCurrentItemListener(new ADA_RightTitle.OnRefreshCurrentItemListener() {
            @Override
            public void onRefreshCount(int refreshPos, int curCount) {
                //给当前被刷新的实体count重新赋值
                DishTypesEntity dishTypeEntity = mData.get(refreshPos);
                dishTypeEntity.count = curCount;
            }
        });
        mAdapter.setOnDoAnimListener(new ADA_RightTitle.OnDoAnimListener() {
            @Override
            public void doAnim() {
                if (onDoAnimListener != null) {
                    onDoAnimListener.doAnim();
                }
            }
        });
    }

    public void setData(List<DishTypesEntity> datas) {
        this.mData = datas;
        mAdapter.refreshData(mData);
        setAdapter(mAdapter);
    }

    interface OnRightListViewItemClickListener {
        void onItemClick(int count, String title, int position);
    }

    public void setOnRightListViewItemClickListener(OnRightListViewItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public ADA_RightTitle getRLAdapter() {
        return mAdapter;
    }


    public interface OnDoAnimListener {
        void doAnim();
    }

    private OnDoAnimListener onDoAnimListener;

    public void setOnDoAnimListener(OnDoAnimListener onDoAnimListener) {
        this.onDoAnimListener = onDoAnimListener;
    }
}
