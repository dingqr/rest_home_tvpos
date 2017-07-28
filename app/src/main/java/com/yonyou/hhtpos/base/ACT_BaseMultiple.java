package com.yonyou.hhtpos.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.application.MyApplication;
import com.yonyou.hhtpos.dialog.DIA_Navigation;

import butterknife.Bind;

/**
 * 多重布局基类
 * 作者：liushuofei on 2017/6/23 09:29
 */
public abstract class ACT_BaseMultiple extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.iv_menu)
    ImageView mMenuImg;
    @Bind(R.id.fl_left)
    protected FrameLayout flLeft;
    @Bind(R.id.fl_right)
    protected FrameLayout flRight;
    @Bind(R.id.layout_bill_info_root)
    LinearLayout layoutRoot;
    /**左侧导航栏 */
    private DIA_Navigation dia_navigation;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_base_multiple;
    }

    @Override
    protected void initViewsAndEvents() {
        // 初始化
        initView();

        // 左侧的权重动态设置
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT);
        params.weight = getLeftWeight();
        flLeft.setLayoutParams(params);

        // 替换left fragment
        FragmentTransaction leftTrans = getSupportFragmentManager().beginTransaction();
        leftTrans.add(R.id.fl_left, getLeftContent());
        leftTrans.commitAllowingStateLoss();

        // 替换right fragment
        FragmentTransaction rightTrans = getSupportFragmentManager().beginTransaction();
        rightTrans.add(R.id.fl_right, getRightContent());
        rightTrans.commitAllowingStateLoss();

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

    protected abstract float getLeftWeight();

    protected abstract Fragment getLeftContent();

    protected abstract Fragment getRightContent();

    public LinearLayout getLayoutRoot() {
        return layoutRoot;
    }


}
