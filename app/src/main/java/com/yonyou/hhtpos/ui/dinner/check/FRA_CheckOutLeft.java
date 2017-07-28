package com.yonyou.hhtpos.ui.dinner.check;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_CheckOutList;
import com.yonyou.hhtpos.bean.check.CheckOrderListEntity;
import com.yonyou.hhtpos.presenter.IQueryBillInfoPresenter;
import com.yonyou.hhtpos.view.IQueryBillInfoView;

import butterknife.Bind;

/**
 * 结账左侧fragment
 * 作者：liushuofei on 2017/7/15 10:24
 */
public class FRA_CheckOutLeft extends BaseFragment implements IQueryBillInfoView {

    @Bind(R.id.tv_header)
    TextView tvHeader;
    @Bind(R.id.lv_check_out)
    ListView lvCheckOut;
    private IQueryBillInfoPresenter mPresenter;
    private String tableBillId = "C5B858B044000000F400000000257000";
    private String compId = "DIE49JkEU29JHD819HRh19hGDAY1";
    private String shopId = "C13352966C000000A60000000016E000";
    private ADA_CheckOutList mAdapter;

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
        return lvCheckOut;
    }

    @Override
    protected void initViewsAndEvents() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.header_check_out_list, null);
        lvCheckOut.addHeaderView(v);

        mAdapter = new ADA_CheckOutList(mContext);
        lvCheckOut.setAdapter(mAdapter);
        for (int i = 0; i < 10; i++) {
            mAdapter.update("");
        }

        lvCheckOut.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    tvHeader.setVisibility(View.VISIBLE);
                } else {
                    tvHeader.setVisibility(View.GONE);
                }
            }
        });
//        mPresenter = new QueryBillInfoPresenterImpl(mContext, this);
//        mPresenter.queryBillInfo(compId, shopId, tableBillId);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_check_out_left;
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

    @Override
    public void queryBillInfo(CheckOrderListEntity checkOrderListEntity) {

    }
}
