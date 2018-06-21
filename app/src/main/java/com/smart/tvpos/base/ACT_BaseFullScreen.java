package com.smart.tvpos.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.smart.framework.library.base.BaseActivity;
import com.smart.tvpos.R;

import butterknife.Bind;

/**
 * 作者：liushuofei on 2017/6/26 15:44
 * 邮箱：lsf@yonyou.com
 */
public abstract class ACT_BaseFullScreen extends BaseActivity {

    @Bind(R.id.fl_content)
    protected FrameLayout flContent;
    @Bind(R.id.tv_server_mode)
    protected TextView tvServerMode;
    @Bind(R.id.tv_hot_line)
    protected TextView tvHotLine;
    @Bind(R.id.tv_app_version)
    protected TextView tvAppVersion;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_base_full_screen;
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
