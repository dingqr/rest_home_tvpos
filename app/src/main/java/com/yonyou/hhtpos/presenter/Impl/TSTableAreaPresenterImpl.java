package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.MealAreaEntity;
import com.yonyou.hhtpos.interactor.ITSClearTableInteractor;
import com.yonyou.hhtpos.interactor.ITSTableAreaInteractor;
import com.yonyou.hhtpos.interactor.Impl.TSClearTableInteractorImpl;
import com.yonyou.hhtpos.interactor.Impl.TSTableAreaInteractorImpl;
import com.yonyou.hhtpos.presenter.ITSClearTablePresenter;
import com.yonyou.hhtpos.presenter.ITSTableAreaPresenter;
import com.yonyou.hhtpos.view.ITSClearTableView;
import com.yonyou.hhtpos.view.ITSTableAreaListView;

import java.util.List;


/**
 * Created by ybing on 2017/8/1.
 * 堂食获取所有餐区列表
 */

public class TSTableAreaPresenterImpl implements ITSTableAreaPresenter {

    private Context mContext;
    private ITSTableAreaListView mView;
    private ITSTableAreaInteractor mInteractor;

    public TSTableAreaPresenterImpl(Context mContext, final ITSTableAreaListView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new TSTableAreaInteractorImpl(new TableAeraListListener());
    }



    @Override
    public void getTableArea( String shopId) {
        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.getTableList(shopId);
    }

    private class TableAeraListListener implements BaseLoadedListener<List<MealAreaEntity>>{

        @Override
        public void onSuccess(int event_tag, List<MealAreaEntity> data) {
            mView.dismissDialogLoading();
            mView.getTableAreaList(data);
        }

        @Override
        public void onError(String msg) {
            mView.dismissDialogLoading();
            mView.hideLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onException(String msg) {
            mView.hideLoading();
            mView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, msg, false);
        }

        @Override
        public void onBusinessError(ErrorBean error) {
            mView.hideLoading();
            mView.dismissDialogLoading();
            CommonUtils.makeEventToast(mContext, error.getMsg(), false);
        }
    }
}
