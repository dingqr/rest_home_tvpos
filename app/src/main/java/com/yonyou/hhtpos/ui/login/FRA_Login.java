package com.yonyou.hhtpos.ui.login;

import android.view.View;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.ui.activation.ACT_VerifyPhone;
import com.yonyou.hhtpos.ui.home.ACT_Home;

import butterknife.OnClick;

/**
 * 登录fragment
 * 作者：liushuofei on 2017/6/26 18:02
 */
public class FRA_Login extends BaseFragment {

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_login;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @OnClick({R.id.tv_forget_pwd, R.id.rb_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pwd:
                readyGoThenKill(ACT_VerifyPhone.class);
                break;

            case R.id.rb_login:
                readyGoThenKill(ACT_Home.class);
                break;
        }
    }
}
