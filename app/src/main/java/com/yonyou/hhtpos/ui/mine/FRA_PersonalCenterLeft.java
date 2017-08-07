package com.yonyou.hhtpos.ui.mine;


import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;

import butterknife.Bind;

/**
 * 个人中心左侧页面
 * 作者：ybing on 2017/8/4 16:47
 */
public class FRA_PersonalCenterLeft extends BaseFragment
        implements View.OnClickListener{
    @Bind(R.id.rl_pass_work)
    RelativeLayout rlPassWork;
    @Bind(R.id.rl_modify_password)
    RelativeLayout rlModifyPassword;
    @Bind(R.id.rl_daily_settle_account)
    RelativeLayout rlDailySettleAccount;
    @Bind(R.id.rl_open_cash_box)
    RelativeLayout rlOpenCashBox;
    @Bind(R.id.im_left_pass_work)
    View redPassWork;
    @Bind(R.id.im_left_modify_password)
    View redModifyPassword;
    @Bind(R.id.im_left_daily_settle_account)
    View redDailySettleAccount;
    @Bind(R.id.im_left_open_cash_box)
    View redOpenCashBox;

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
        rlPassWork.setOnClickListener(this);
        rlModifyPassword.setOnClickListener(this);
        rlDailySettleAccount.setOnClickListener(this);
        rlOpenCashBox.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_pass_work:
                setCheck(redPassWork,rlPassWork);
                setUnCheck(redModifyPassword,rlModifyPassword);
                setUnCheck(redDailySettleAccount,rlDailySettleAccount);
                setUnCheck(redOpenCashBox,rlOpenCashBox);
                break;

            case R.id.rl_modify_password:
                setUnCheck(redPassWork,rlPassWork);
                setCheck(redModifyPassword,rlModifyPassword);
                setUnCheck(redDailySettleAccount,rlDailySettleAccount);
                setUnCheck(redOpenCashBox,rlOpenCashBox);
                break;

            case R.id.rl_daily_settle_account:
                setUnCheck(redPassWork,rlPassWork);
                setUnCheck(redModifyPassword,rlModifyPassword);
                setCheck(redDailySettleAccount,rlDailySettleAccount);
                setUnCheck(redOpenCashBox,rlOpenCashBox);
                break;

            case R.id.rl_open_cash_box:
                setUnCheck(redPassWork,rlPassWork);
                setUnCheck(redModifyPassword,rlModifyPassword);
                setUnCheck(redDailySettleAccount,rlDailySettleAccount);
                setCheck(redOpenCashBox,rlOpenCashBox);
                break;

            default:
                break;
        }
    }
    private void setCheck(View v,RelativeLayout rl){
        v.setVisibility(View.VISIBLE);
        rl.setBackgroundColor(Color.parseColor("#eaeaea"));
    }
    private void setUnCheck(View v,RelativeLayout rl){
        v.setVisibility(View.GONE);
        rl.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_personal_center_left;
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
}
