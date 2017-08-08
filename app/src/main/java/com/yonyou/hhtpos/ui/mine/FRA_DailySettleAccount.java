package com.yonyou.hhtpos.ui.mine;

/**
 * Created by ybing on 2017/8/5.
 * 邮箱：ybing@yonyou.com
 * 描述：每日结算
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_CashType;
import com.yonyou.hhtpos.adapter.ADA_DailySettleAccount;
import com.yonyou.hhtpos.bean.mine.CashTypeEntity;
import com.yonyou.hhtpos.bean.mine.DailyAccountEntity;

import java.util.ArrayList;

import butterknife.Bind;

import static com.yonyou.hhtpos.util.FiltrationUtil.getCashTypies;
import static com.yonyou.hhtpos.util.FiltrationUtil.getDailyAccounts;

public class FRA_DailySettleAccount extends BaseFragment {
    @Bind(R.id.lv_daily_account)
    RecyclerView lvDailyAccount;

    private ADA_DailySettleAccount mAdapter;

    ArrayList<DailyAccountEntity> dailyAccountEntities = new ArrayList<>();

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
        return lvDailyAccount;
    }

    @Override
    protected void initViewsAndEvents() {
        //现金类型写死
        dailyAccountEntities = getDailyAccounts();
        mAdapter= new ADA_DailySettleAccount(mContext,dailyAccountEntities);
        lvDailyAccount.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        lvDailyAccount.setAdapter(mAdapter);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_daily_settlt_account;
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

