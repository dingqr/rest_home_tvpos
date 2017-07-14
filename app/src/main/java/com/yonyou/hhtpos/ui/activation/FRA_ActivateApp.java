package com.yonyou.hhtpos.ui.activation;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.ui.store.ACT_BindStore;
import com.yonyou.hhtpos.util.TimeUtil;
import com.yonyou.hhtpos.view.IActivateAppView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 激活当前应用fragment
 * 作者：liushuofei on 2017/6/26 18:02
 */
public class FRA_ActivateApp extends BaseFragment implements IActivateAppView {

    @Bind(R.id.rb_next_step)
    RadioButton rbNextStep;
    @Bind(R.id.tv_get_code)
    TextView tvGetCode;

    /**
     * 倒计时工具类
     */
    private TimeUtil timer;
    /**
     * 验证码倒计时总时间
     */
    private final int msgTime = 60 * 1000;
    /**
     * 倒计时间隔时间
     */
    private final int countDownInterval = 1000;

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
        timer = new TimeUtil(msgTime, countDownInterval, mContext);
        timer.setView(tvGetCode);
        timer.setTxt(mContext.getString(R.string.activate_app_get_code));
        timer.setColor(ContextCompat.getColor(mContext, R.color.color_666666));

//        HashMap<String,String> hashMap = new HashMap<>();
//        RequestManager.getInstance().requestGetByAsyn("https://kyfw.12306.cn/otn/", hashMap, new ReqCallBack<String>() {
//
//            @Override
//            public void onReqSuccess(String bean) {
//                ToastUtil.makeText(mContext, "request success", false);
//            }
//
//            @Override
//            public void onFailure(String result) {
//                ToastUtil.makeText(mContext, "failure", false);
//            }
//
//            @Override
//            public void onReqFailed(ErrorBean errorBean) {
//                ToastUtil.makeText(mContext, "request failed", false);
//            }
//        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_activate_application;
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

    @OnClick({R.id.tv_get_code, R.id.rb_next_step})
    public void onClick(View v) {
        switch (v.getId()) {
            // 获取激活码
            case R.id.tv_get_code:
                timer.start();
                break;

            // 下一步
            case R.id.rb_next_step:
                readyGoThenKill(ACT_BindStore.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void requestActivateCode() {

    }

    @Override
    public void doActivate() {

    }


}
