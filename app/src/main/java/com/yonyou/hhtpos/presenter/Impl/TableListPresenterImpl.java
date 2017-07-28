package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.interactor.ITableListInteractor;
import com.yonyou.hhtpos.interactor.Impl.TableListInteractorImpl;
import com.yonyou.hhtpos.presenter.ITableListPresenter;
import com.yonyou.hhtpos.view.ITableListView;

import java.util.List;

/**
 * Created by zj on 2017/7/20.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食-桌台列表
 */
public class TableListPresenterImpl implements ITableListPresenter {
    private Context mContext;
    private ITableListView mTableListView;
    private ITableListInteractor mTableListInteractor;

    public TableListPresenterImpl(Context context, ITableListView tableListView) {
        this.mContext = context;
        this.mTableListView = tableListView;
        mTableListInteractor = new TableListInteractorImpl(new TableListListener());
    }

    @Override
    public void requestTableList(String diningAreaRelateId, String shopId, String tableStatus) {
        mTableListView.showLoading(mContext.getResources().getString(R.string.common_loading_message));
        mTableListInteractor.requestTableList(diningAreaRelateId,shopId,tableStatus);
    }

    private class TableListListener implements BaseLoadedListener<List<CanteenTableEntity>> {

        @Override
        public void onSuccess(int event_tag, List<CanteenTableEntity> canteenTableEntityList) {
            mTableListView.hideLoading();
            mTableListView.requestTableList(canteenTableEntityList);
        }

        @Override
        public void onError(String msg) {
            mTableListView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mTableListView.hideLoading();
            mTableListView.showException(msg);
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mTableListView.showBusinessError(error);
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
