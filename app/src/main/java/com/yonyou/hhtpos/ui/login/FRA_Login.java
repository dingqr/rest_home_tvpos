package com.yonyou.hhtpos.ui.login;

import android.content.Context;
import android.content.Intent;
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
import com.yonyou.framework.library.common.log.Elog;
import com.yonyou.framework.library.common.utils.AppSharedPreferences;
import com.yonyou.framework.library.common.utils.ReturnObject;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.common.utils.ValidateRule;
import com.yonyou.framework.library.common.utils.Validator;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.db.entity.UserEntity;
import com.yonyou.hhtpos.presenter.ILoginPresenter;
import com.yonyou.hhtpos.presenter.Impl.LoginPresenterImpl;
import com.yonyou.hhtpos.ui.activation.ACT_VerifyPhone;
import com.yonyou.hhtpos.ui.home.ACT_Home;
import com.yonyou.hhtpos.util.Constants;
import com.yonyou.hhtpos.util.SpUtil;
import com.yonyou.hhtpos.view.ILoginView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录fragment
 * 作者：liushuofei on 2017/6/26 18:02
 */
public class FRA_Login extends BaseFragment implements ILoginView {
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.et_user_phone)
    EditText etUserPhone;
    @Bind(R.id.et_user_pwd)
    EditText etUserPwd;
    @Bind(R.id.rb_login)
    RadioButton rbLogin;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;


    //用户手机号 11位数字 符合 手机号的规律
    private String userPhone;
    //用户密码 6位数字
    private String userPwd;

    private ILoginPresenter mPresenter;

    private AppSharedPreferences sharePre;
//    private String userToken;
    private String shopId;
    private String shopName;
    private String employeePhone;
    private String employeeId;
    private String employeeHead;

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
        sharePre = new AppSharedPreferences(mContext);
        Bundle argus = getArguments();
        shopId = argus.getString(SpUtil.SHOP_ID);
        shopName = argus.getString(SpUtil.SHOP_NAME);

        employeePhone = sharePre.getString(SpUtil.EMPLOYEE_PHONE);
        if (!TextUtils.isEmpty(shopName)) {
            tvShopName.setText(shopName);
        }
        if (!TextUtils.isEmpty(employeePhone)) {
            etUserPhone.setText(employeePhone);
        }
        mPresenter = new LoginPresenterImpl(this.getContext(), this);
        showSoftInput(etUserPwd);
        showSoftInput(etUserPhone);
        //默认登录按钮不可点击并且是置灰状态
        rbLogin.setChecked(false);
        rbLogin.setClickable(false);
        etUserPhone.addTextChangedListener(new InputWatcher());
        etUserPwd.addTextChangedListener(new InputWatcher());
//        }
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
                //忘记密码
                readyGoThenKill(ACT_VerifyPhone.class);
                break;

            case R.id.rb_login:
                //登录
                if (doValidatePhone() && doValidatePwd()) {
                    userPhone = etUserPhone.getText().toString();
                    userPwd = etUserPwd.getText().toString();
                    mPresenter.login("", "", userPhone, userPwd, shopId);
                } else {
                    CommonUtils.makeEventToast(mContext, mContext.getString(R.string.phone_pwd_error), false);
                }
                break;
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
     * 验证手机号 是否为空，是否满足手机号的规则
     *
     * @return
     */
    private boolean doValidatePhone() {
        Validator validator = new Validator();
        TextView[] widgets = new TextView[]{etUserPhone};
        validator.addRules(ValidateRule.IS_NOT_EMPTY);
        for (TextView w : widgets) {
            ReturnObject ro = validator.val(w.getText());
            if (!ro.isSuccess) {
                CommonUtils.makeEventToast(mContext, w.getHint().toString(), false);
                w.requestFocus();
                return false;
            }
        }
        validator.addRules(ValidateRule.IS_MOBILE_NUMBER);
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
     * 验证密码 是否为空，是否为6位纯数字
     *
     * @return
     */
    private boolean doValidatePwd() {
        Validator validator = new Validator();
        TextView[] widgets = new TextView[]{etUserPwd};
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
            if (TextUtils.isEmpty(etUserPhone.getText().toString()) || TextUtils.isEmpty(etUserPwd.getText().toString())) {
                rbLogin.setChecked(false);
                rbLogin.setClickable(false);
            } else {
                rbLogin.setChecked(true);
                rbLogin.setClickable(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    @Override
    public void login(UserEntity dataBean) {
        if (null != dataBean) {

            //保存登录用户信息
            sharePre.putString(SpUtil.EMPLOYEE_PHONE, StringUtil.getString(userPhone));
            sharePre.putString(SpUtil.EMPLOYEE_NAME, StringUtil.getString(dataBean.getEmployeeName()));
            sharePre.putString(SpUtil.EMPLOYEE_ID, StringUtil.getString(dataBean.getEmployeeId()));
            sharePre.putString(SpUtil.EMPLOYEE_HEAD, StringUtil.getString(dataBean.getEmployeeHead()));
            sharePre.putString(SpUtil.USER_TOKEN, StringUtil.getString(dataBean.getToken()));

            //保存shopId
            sharePre.putString(SpUtil.SHOP_ID, StringUtil.getString(shopId));
            //保存shopName
            sharePre.putString(SpUtil.SHOP_NAME, StringUtil.getString(shopName));
            Constants.SHOP_ID = shopId;
            Constants.TOKEN = dataBean.getToken();
            Constants.SHOP_NAME = shopName;

            Elog.e("SHOP_ID", sharePre.getString(SpUtil.SHOP_ID));
            Elog.e("SHOP_NAME", sharePre.getString(SpUtil.SHOP_NAME));
            Elog.e("TOKEN", sharePre.getString(SpUtil.USER_TOKEN));

            Intent intent = new Intent(getActivity(),ACT_Home.class);
            intent.putExtra(SpUtil.SHOP_NAME,shopName);
            intent.putExtra(SpUtil.SHOP_ID,shopId);
            startActivity(intent);
            this.getActivity().finish();
        }
    }
}
