package com.yonyou.hhtpos.ui.activation;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.presenter.IVerifyPhonePresenter;
import com.yonyou.hhtpos.presenter.Impl.VerifyPhonePresenterImpl;
import com.yonyou.hhtpos.ui.login.ACT_ResetNewPwd;
import com.yonyou.hhtpos.util.TimeUtil;
import com.yonyou.hhtpos.util.VerifyUtil;
import com.yonyou.hhtpos.view.IVerifyPhoneView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 验证手机号fragment
 * 作者：liushuofei on 2017/6/27 10:21
 */
public class FRA_VerifyPhone extends BaseFragment implements IVerifyPhoneView{

    @Bind(R.id.ll_root)
    LinearLayout mRoot;
    @Bind(R.id.tv_get_code)
    TextView tvGetCode;
    @Bind(R.id.rb_submit)
    RadioButton rbSubmit;
    @Bind(R.id.et_mobile_number)
    EditText mMobileNo;
    @Bind(R.id.et_sms_code)
    EditText mSmsCode;

    /**倒计时工具类 */
    private TimeUtil timer;
    /**验证码倒计时总时间 */
    private final int msgTime = 60 * 1000;
    /**倒计时间隔时间 */
    private final int countDownInterval = 1000;

    private IVerifyPhonePresenter mVerifyPhonePresenter;
    private String smsCode;
    private String mobileNo;

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
        return mRoot;
    }

    @Override
    protected void initViewsAndEvents() {
        rbSubmit.setChecked(false);
        rbSubmit.setClickable(false);

        timer = new TimeUtil(msgTime, countDownInterval, mContext);
        timer.setView(tvGetCode);
        timer.setTxt(mContext.getString(R.string.verify_phone_get_code));
        timer.setColor(ContextCompat.getColor(mContext, R.color.color_999999));

        mVerifyPhonePresenter = new VerifyPhonePresenterImpl(mContext, this);


        mMobileNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    // 此处为得到焦点时的处理内容

                }else {
                    // 此处为失去焦点时的处理内容
                    verifyMobile();
                }
            }
        });

        mSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null != s && s.length() == 4){
                    rbSubmit.setChecked(true);
                    rbSubmit.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_verify_phone;
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

    @OnClick({R.id.tv_get_code, R.id.rb_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                if (verifyMobile()){
                    mVerifyPhonePresenter.sendSmsCode("", "", mobileNo);
                }
                break;

            case R.id.rb_submit:
                smsCode = mSmsCode.getText().toString().trim();
                mVerifyPhonePresenter.verifyPhone("", "", mobileNo, smsCode);
                break;
        }
    }

    @Override
    public void sendSmsCode() {
        timer.start();
    }

    private boolean verifyMobile(){
        if (null != mMobileNo){
            mobileNo = mMobileNo.getText().toString().trim();
        }

        if (TextUtils.isEmpty(mobileNo)){
            CommonUtils.makeEventToast(mContext, "请填写手机号", false);
            return false;
        }

        if (!VerifyUtil.checkMobile(mobileNo)){
            CommonUtils.makeEventToast(mContext, "手机号格式错误", false);
            return false;
        }

        return true;
    }

    @Override
    public void verifyPhone() {
        Bundle bundle = new Bundle();
        bundle.putString(ACT_ResetNewPwd.SMS_CODE, smsCode);
        bundle.putString(ACT_ResetNewPwd.MOBILE_NO, mobileNo);
        readyGo(ACT_ResetNewPwd.class, bundle);
        getActivity().finish();
    }
}
