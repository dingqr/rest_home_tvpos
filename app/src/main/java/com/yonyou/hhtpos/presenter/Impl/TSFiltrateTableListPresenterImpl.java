package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.interactor.ITSFiltrateTableListInteractor;
import com.yonyou.hhtpos.interactor.ITableListInteractor;
import com.yonyou.hhtpos.interactor.Impl.TSFiltrateTableListInteractorImpl;
import com.yonyou.hhtpos.interactor.Impl.TableListInteractorImpl;
import com.yonyou.hhtpos.presenter.ITSFiltrateTableListPresenter;
import com.yonyou.hhtpos.presenter.ITableListPresenter;
import com.yonyou.hhtpos.view.ITSFiltrateTableView;
import com.yonyou.hhtpos.view.ITableListView;

import java.util.List;

/**
 * Created by ybing on 2017/7/27.
 * 邮箱：ybing@yonyou.com
 * 描述：堂食-桌台筛选列表
 */
public class TSFiltrateTableListPresenterImpl implements ITSFiltrateTableListPresenter {
    private Context mContext;
    private ITSFiltrateTableView mTableListView;
    private ITSFiltrateTableListInteractor mTableListInteractor;

    public TSFiltrateTableListPresenterImpl(Context context, ITSFiltrateTableView tableListView) {
        this.mContext = context;
        this.mTableListView = tableListView;
        mTableListInteractor = new TSFiltrateTableListInteractorImpl(new TableListListener());
    }

    @Override
    public void requestFiltrateTableList(String diningAreaRelateId, String shopId, String tableOperation) {
        mTableListView.showLoading(mContext.getResources().getString(R.string.common_loading_message));
        mTableListInteractor.requestFiltrateTableList(diningAreaRelateId,shopId,tableOperation);
    }

    private class TableListListener implements BaseLoadedListener<List<CanteenTableEntity>> {

        @Override
        public void onSuccess(int event_tag, List<CanteenTableEntity> canteenTableEntityList) {
            mTableListView.hideLoading();
            mTableListView.filtrateTable(canteenTableEntityList);
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
