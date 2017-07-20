package com.yonyou.hhtpos.ui.book;

import android.view.View;
import android.widget.LinearLayout;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.WaiterEntity;
import com.yonyou.hhtpos.dialog.DIA_ChooseWaiter;
import com.yonyou.hhtpos.presenter.IChooseWaiterPresenter;
import com.yonyou.hhtpos.presenter.Impl.ChooseWaiterPresenterImpl;
import com.yonyou.hhtpos.view.IChooseWaiterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 预订详情
 * 作者：liushuofei on 2017/6/29 15:26
 */
public class FRA_BookDetail extends BaseFragment implements IChooseWaiterView {

    @Bind(R.id.ll_content)
    LinearLayout mContentLayout;
    //请求接口：查询所有服务员
    private String shopId = "C13352966C000000A60000000016E000";//测试参数
    private List<WaiterEntity> mWaiterList = new ArrayList<>();
    private IChooseWaiterPresenter mChooseWaiterPresenter;
    private DIA_ChooseWaiter diaChooseWaiter;
    private WaiterEntity mChoosedWaiter;

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
        return mContentLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        //showCompanyInfo();
        mChooseWaiterPresenter = new ChooseWaiterPresenterImpl(mContext, this);
        mChooseWaiterPresenter.requestWaiterList(shopId);
        diaChooseWaiter = new DIA_ChooseWaiter(mContext);
        //选中的服务员
        diaChooseWaiter.setOnWaiterSelectedListener(new DIA_ChooseWaiter.OnWaiterSelectedListener() {
            @Override
            public void onWaiterSelected(WaiterEntity waiterEntity) {
                //选择服务员弹窗
//                if (mWaiterList.size() > 0) {
//                    diaChooseWaiter.show();
//                }
                mChoosedWaiter = waiterEntity;
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_book_detail;
    }

    @OnClick({R.id.tv_guest_arriving})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_guest_arriving:

                break;
        }
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

    /**
     * 获取该店中所有的服务员
     *
     * @param waiterList
     */
    @Override
    public void requestWaiterList(List<WaiterEntity> waiterList) {
        if (waiterList != null && waiterList.size() > 0) {
            this.mWaiterList = waiterList;
            diaChooseWaiter.setData(waiterList);
        }
    }
}
