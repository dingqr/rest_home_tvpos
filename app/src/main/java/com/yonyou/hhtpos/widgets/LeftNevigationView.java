package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.yonyou.hhtpos.R;

import java.util.List;


/**
 * Created by zj on 2017/6/23.
 * 邮箱：zjuan@yonyou.com
 * 描述：左侧导航栏：二次封装二级列表
 */
public class LeftNevigationView extends LinearLayout {
    private Context mContext;
    private LeftExpandableView expandableview;

    public LeftNevigationView(Context context) {
        this(context, null);
    }

    public LeftNevigationView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public LeftNevigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        View view = View.inflate(mContext,R.layout.left_nevigation_linear,null);
        expandableview = (LeftExpandableView) view.findViewById(R.id.expandableview);

    }

    /**
     * 传入数据
     *
     * @param groupData 父菜单
     * @param childData 子菜单
     */
    public void setData(List<String> groupData, List<List<String>> childData) {
        if (groupData != null && childData != null) {
            expandableview.setData(groupData, childData);
        }
    }
}
