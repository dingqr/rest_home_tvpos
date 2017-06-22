package com.yonyou.framework.library.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.umeng.analytics.MobclickAgent;
import com.yonyou.framework.library.R;
import com.yonyou.framework.library.view.BaseView;

import butterknife.ButterKnife;

/**
 * 作者：addison on 15/12/15 14:15
 * 邮箱：lsf@yonyou.com
 */
public abstract class BaseActivity extends BaseAppCompatActivity implements BaseView {

    protected Toolbar mToolbar;

    protected Activity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isApplyKitKatTranslucency()) {
            setSystemBarTintDrawable(getResources().getDrawable(R.drawable.sr_primary));
        }

        //PushAgent.getInstance(mContext).onAppStart();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        mContext = this;
        ButterKnife.bind(this);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, null);
    }



    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
    }

//    @Override
//    public void showBusinessError(ErrorBean error) {
//        if (null != error.getCode() && error.getCode().equals("10000"))
//            toggleshowLogin();
//    }

    @Override
    public void showDialogLoading(String msg) {
        toggleShowDialogLoading(msg);
    }

    @Override
    public void dismissDialogLoading() {
        dismissDialogLoad();
    }

    protected abstract boolean isApplyKitKatTranslucency();
}

