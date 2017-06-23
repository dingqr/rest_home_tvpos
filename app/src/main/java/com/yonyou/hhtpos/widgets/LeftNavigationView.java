package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yonyou.hhtpos.R;

import java.util.List;

import static com.yonyou.hhtpos.R.id.expandableview;


/**
 * Created by zj on 2017/6/23.
 * 邮箱：zjuan@yonyou.com
 * 描述：左侧导航栏：二次封装二级列表
 */
public class LeftNavigationView extends LinearLayout {
    private Context mContext;
    private LeftExpandableView expandableView;

    public LeftNavigationView(Context context) {
        this(context, null);
    }

    public LeftNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        // 设置方向
        this.setOrientation(LinearLayout.VERTICAL);

        View view = LayoutInflater.from(mContext).inflate(R.layout.left_nevigation_linear, this);
        expandableView = (LeftExpandableView) view.findViewById(expandableview);
    }

    /**
     * 传入数据
     *
     * @param groupData 父菜单
     * @param childData 子菜单
     */
    public void setData(List<String> groupData, List<List<String>> childData) {
        if (groupData != null && childData != null) {
            expandableView.setData(groupData, childData);
        }
    }
}
