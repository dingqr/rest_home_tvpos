package com.yonyou.hhtpos.ui.mine;

/**
 * Created by ybing on 2017/8/5.
 * 邮箱：ybing@yonyou.com
 * 描述：我要交班
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;

import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_CashType;
import com.yonyou.hhtpos.bean.mine.CashTypeEntity;

import java.util.ArrayList;

import butterknife.Bind;

import static com.yonyou.hhtpos.util.FiltrationUtil.getCashTypies;

public class FRA_PassWork extends BaseFragment {
    @Bind(R.id.lv_cash_type)
    RecyclerView lvCashType;
    ADA_CashType adaCashType;

    ArrayList<CashTypeEntity> cashTypeEntities = new ArrayList<>();
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
        //现金类型写死
        cashTypeEntities = getCashTypies();
        adaCashType= new ADA_CashType(mContext,cashTypeEntities);
        lvCashType.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        lvCashType.setAdapter(adaCashType);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_pass_work;
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

