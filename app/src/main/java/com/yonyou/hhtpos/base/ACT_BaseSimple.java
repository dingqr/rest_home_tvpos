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

    @Bind(R.id.fl_content)
    protected FrameLayout flContent;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_base_simple;
    }

    @Override
    protected void initViewsAndEvents() {
        // 初始化
        initView();


        // 替换fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_content, getContentFragment());
        transaction.commitAllowingStateLoss();
    }

    protected abstract void initView();

    protected abstract Fragment getContentFragment();

}
