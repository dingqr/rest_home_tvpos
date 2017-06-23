package com.yonyou.hhtpos.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.widgets.LeftExpandableView;

import java.util.List;

import butterknife.Bind;

/**
 * 导航栏 + 内容基类
 * 作者：liushuofei on 2017/6/23 09:28
 */
public abstract class ACT_BaseSimple extends BaseActivity {

    @Bind(R.id.el_navigation)
    LeftExpandableView elNavigation;
    @Bind(R.id.fl_content)
    FrameLayout flContent;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_base_simple;
    }

    @Override
    protected void initViewsAndEvents() {
        // 设置导航栏数据
        elNavigation.setData(getNavigationGroupData(), getNavigationChildData());

        // 替换fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_content, getContentFragment());
        transaction.commitAllowingStateLoss();

        // 初始化
        initView();
    }

    protected abstract List<String> getNavigationGroupData();

    protected abstract List<List<String>> getNavigationChildData();

    protected abstract Fragment getContentFragment();

    protected abstract void initView();
}
