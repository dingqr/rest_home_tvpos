package com.yonyou.hhtpos.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.utils.ReturnObject;
import com.yonyou.framework.library.common.utils.ValidateRule;
import com.yonyou.framework.library.common.utils.Validator;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.presenter.ILoginPresenter;
import com.yonyou.hhtpos.presenter.IResetPwdPresenter;
import com.yonyou.hhtpos.presenter.Impl.LoginPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.ResetPwdPresenterImpl;
import com.yonyou.hhtpos.ui.home.ACT_Home;
import com.yonyou.hhtpos.view.IResetPwdView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ybing on 2017/6/27.
 */

public class FRA_ResetNewPwd extends BaseFragment implements IResetPwdView{

    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.tv_user_phone)
    TextView tvUserPhone;
    @Bind(R.id.et_new_pwd)
    EditText etNewPwd;
    @Bind(R.id.et_confirm_new_pwd)
    EditText etConfirmNewPwd;
    @Bind(R.id.rb_finish)
    RadioButton rbFinish;

    private String userPhone;
    private String msgCode;
    //用户密码 6位数字
    private String userNewPwd;
    //确认新密码 6位数字
    private String userConfirmNewPwd;

    private IResetPwdPresenter mPresenter;
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
        return llContent;
    }

    @Override
    protected void initViewsAndEvents() {
        mPresenter = new ResetPwdPresenterImpl(this.getContext(),this);
        showSoftInput(etConfirmNewPwd);
        showSoftInput(etNewPwd);
        //默认完成按钮不可点击并且是置灰状态
        rbFinish.setChecked(false);
        rbFinish.setClickable(false);
        etNewPwd.addTextChangedListener(new InputWatcher());
        etConfirmNewPwd.addTextChangedListener(new InputWatcher());
        Bundle argus = getArguments();
        userPhone = argus.get(ACT_ResetNewPwd.MOBILE_NO).toString();
        msgCode = argus.get(ACT_ResetNewPwd.SMS_CODE).toString();
        tvUserPhone.setText(userPhone.substring(0,3)+"****"+ userPhone.substring(8,11));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_reset_new_pwd;
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

    @OnClick(R.id.rb_finish)
    public void onClick() {
        userNewPwd = etNewPwd.getText().toString();
        userConfirmNewPwd = etConfirmNewPwd.getText().toString();
        if (doValidatePwd()){
            if (userNewPwd.equals(userConfirmNewPwd)) {
                mPresenter.resetPwd(userPhone,msgCode,userNewPwd);
            }
            else {
                CommonUtils.makeEventToast(mContext,mContext.getString(R.string.different_pwd), false);
            }
        }
    }

    /**
     * 指定输入框获取焦点并且弹出键盘
     *
     * @param editText
     */
    private void showSoftInput(final EditText editText) {
        View foucsView = getActivity().getWindow().getCurrentFocus();
        if (null != foucsView) {
            foucsView.clearFocus();
        }
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    /**
     * 验证密码 是否为空，是否为6位纯数字
     *
     * @return
     */
    private boolean doValidatePwd() {
        Validator validator = new Validator();
        TextView[] widgets = new TextView[]{etNewPwd,etConfirmNewPwd};
        validator.addRules(ValidateRule.IS_NOT_EMPTY);
        for (TextView w : widgets) {
            ReturnObject ro = validator.val(w.getText());
            if (!ro.isSuccess) {
                CommonUtils.makeEventToast(mContext, w.getHint().toString(), false);
                w.requestFocus();
                return false;
            }
        }
        validator.addRules(ValidateRule.IS_VALID_PASSWORD_PURE_NUMBER_SIX);
        for (TextView w : widgets) {
            ReturnObject ro = validator.val(w.getText());
            if (!ro.isSuccess) {
                CommonUtils.makeEventToast(mContext, ro.getErrorMessage(), false);
                w.requestFocus();
                return false;
            }
        }
        return true;
    }

    /**
     * 输入框监视器
     */
    class InputWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //只有手机号和密码有数据时，登录按钮才可以点击
            if (TextUtils.isEmpty(etNewPwd.getText().toString()) || TextUtils.isEmpty(etConfirmNewPwd.getText().toString())) {
                rbFinish.setChecked(false);
                rbFinish.setClickable(false);
            } else{
                rbFinish.setChecked(true);
                rbFinish.setClickable(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    @Override
    public void resetPwd(String result) {
        readyGoThenKill(ACT_Login.class);
    }
}
