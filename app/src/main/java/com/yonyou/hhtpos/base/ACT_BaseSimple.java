package com.yonyou.hhtpos.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.framework.library.common.utils.AppSharedPreferences;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.application.MyApplication;
import com.yonyou.hhtpos.dialog.DIA_Navigation;
import com.yonyou.hhtpos.dialog.DIA_ScanCodeNew;
import com.yonyou.hhtpos.ui.mine.ACT_PersonalCenter;
import com.yonyou.hhtpos.util.Constants;
import com.yonyou.hhtpos.util.SpUtil;

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
    @Bind(R.id.tv_login_shop)
    TextView tvLoginShop;
    @Bind(R.id.iv_user_logo)
    ImageView userLogo;

    /**左侧导航栏 */
    private DIA_Navigation dia_navigation;

    private AppSharedPreferences sharePre;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_base_simple;
    }

    @Override
    protected void initViewsAndEvents() {
        // 初始化
        initView();

        //设置店名
        if (!TextUtils.isEmpty(Constants.SHOP_NAME)){
            tvLoginShop.setText(Constants.SHOP_NAME);
        }

        // 替换fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_content, getContentFragment());
        transaction.commitAllowingStateLoss();

        mMenuImg.setOnClickListener(this);
        userLogo.setOnClickListener(this);

        sharePre = new AppSharedPreferences(this);
        String shopName = sharePre.getString(SpUtil.SHOP_NAME);
        tvLoginShop.setText(StringUtil.getString(shopName));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_menu:
                dia_navigation = new DIA_Navigation(mContext, MyApplication.dataList);
                dia_navigation.getDialog().show();
                break;
            case R.id.iv_user_logo:
                readyGo(ACT_PersonalCenter.class);
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
