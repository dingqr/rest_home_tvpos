package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

import com.yonyou.hhtpos.R;
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
        //去除child默认点击效果
        setSelector(R.drawable.bg_child_selector);
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
                if (mCommonListener != null) {
                    mCommonListener.onItemClick(groupPosition, mChildData.get(groupPosition).get(childPosition), childPosition);
                }
                return true;
            }
        });
        //设置点击效果
        setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListViw, View view, int i, long l) {
                int childrenCount = expandableListViw.getExpandableListAdapter().getChildrenCount(i);
                //只有当当前group没有子菜单时，才将该点击事件交个group处理
                if (childrenCount == 0) {
                    if (mCommonListener != null) {
                        mCommonListener.onItemClick(i, mGroupData.get(i), 0);
                    }

                }
                mAdapter.setSelectedGroupItem(i);
                mAdapter.setGroupIsClicked(true);
                mAdapter.notifyDataSetChanged();
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = 250;
//        int height = heightMeasureSpec;
//        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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

    private OnCommonItemClickListener mCommonListener;

    //设置点击某个item的监听
    interface OnCommonItemClickListener {
        void onItemClick(int groupPosition, String childPosition, long id);
    }

    public void setOnCommonItemClickListener(OnCommonItemClickListener commonListener) {
        this.mCommonListener = commonListener;
    }


    /**
     * 测试数据
     */
    private static final String[] mGroup = {"堂食", "外带", "外卖", "预定", "会员管理"};
    //子视图显示文字
    private String[][] mChild = new String[][]{
            {},
            {},
            {"百度外卖", "饿了么", "美团外卖"},
            {"预定申请", "预定总览", "桌台预定查询"},
            {}

    };

    private void setDefaultData() {
        for (int i = 0; i < mGroup.length; i++) {
            mGroupData.add(mGroup[i]);
        }
        for (int i = 0; i < mGroup.length; i++) {
            List<String> child = new ArrayList<String>();
            for (int j = 0; j < mChild[i].length; j++) {
                child.add(mChild[i][j]);
            }
            mChildData.add(child);
        }
    }
}
