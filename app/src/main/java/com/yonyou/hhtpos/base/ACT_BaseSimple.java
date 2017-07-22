package com.yonyou.hhtpos.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.application.MyApplication;
import com.yonyou.hhtpos.dialog.DIA_Navigation;
import com.yonyou.hhtpos.dialog.DIA_ScanCodeNew;

import butterknife.Bind;

/**
 * 导航栏 + 内容基类
 * 作者：liushuofei on 2017/6/23 09:28
 */
public abstract class ACT_BaseSimple extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.fl_content)
    protected FrameLayout flContent;
    @Bind(R.id.iv_menu)
    ImageView mMenuImg;

    /**左侧导航栏 */
    private DIA_Navigation dia_navigation;

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

        mMenuImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_menu:
                dia_navigation = new DIA_Navigation(mContext, MyApplication.dataList);
                dia_navigation.getDialog().show();
                break;

            default:
                break;
        }
    }

    @Override
    public void finish() {
        if (null != dia_navigation){
            dia_navigation.getDialog().dismiss();
        }
        super.finish();
    }

    protected abstract void initView();

    protected abstract Fragment getContentFragment();
}
