package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

import com.yonyou.hhtpos.adapter.ELVAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2017/6/22.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
public class LeftExpandableView extends ExpandableListView {
    /**
     * 上下文
     */
    private Context mContext;

    private String backgroudColor = "#2E2E2E";
    //一级菜单数据
    private List<String> mGroupData = new ArrayList<>();
    //二级菜单数据
    private List<List<String>> mChildData = new ArrayList<>();
    private ELVAdapter mAdapter;
    private OnELVGroupClickListener mGroupListener;
    private OnELVChildClickListener mChildListener;

    public LeftExpandableView(Context context) {
        this(context, null);
    }

    public LeftExpandableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftExpandableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setBackgroundColor(Color.parseColor(backgroudColor));
        setDivider(null);
        setChoiceMode(CHOICE_MODE_SINGLE);
        setGroupIndicator(null);

        initListener();

    }

    private void initListener() {
        //设置点击child效果
        setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                mAdapter.setGroupIsClicked(false);
                mAdapter.setSelectChildItem(groupPosition, childPosition);
                //设置点击child时，选中到对应的group
                mAdapter.setSelectedGroupItem(groupPosition);
                mAdapter.notifyDataSetChanged();
                if (mChildListener != null) {
                    mChildListener.onChildClick(parent, v, groupPosition, childPosition, id);
                }
                return true;
            }
        });
        //设置点击效果
        setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListViw, View view, int i, long l) {
                mAdapter.setSelectedGroupItem(i);
                mAdapter.setGroupIsClicked(true);
                mAdapter.notifyDataSetChanged();
                if (mGroupListener != null) {
                    mGroupListener.onGroupClick(expandableListViw, view, i, l);
                }
                return false;
            }
        });
        //设置当前只会展开一个group
        setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                    if (groupPosition != i) {
                        collapseGroup(i);
                    }
                }
            }
        });
    }

    /**
     * 传入数据
     */
    public void setData(List<String> groupData, List<List<String>> childData) {
        this.mGroupData = groupData;
        this.mChildData = childData;
        mAdapter = new ELVAdapter(mGroupData, mChildData, mContext);
        setAdapter(mAdapter);
    }

    /**
     * 设置默认选中对应的group
     */
    public void setCheckCurrentGroup(int position) {
        if (mAdapter != null) {
            mAdapter.setSelectedGroupItem(position);
        }
    }

    interface OnELVGroupClickListener {
        void onGroupClick(ExpandableListView expandableListViw, View view, int groupPosition, long l);
    }

    interface OnELVChildClickListener {
        void onChildClick(ExpandableListView parent, View v,
                          int groupPosition, int childPosition, long id);
    }

    public void setGroupListener(OnELVGroupClickListener mGroupListener) {
        this.mGroupListener = mGroupListener;
    }

    public void setChildListener(OnELVChildClickListener mChildListener) {
        this.mChildListener = mChildListener;
    }


}
