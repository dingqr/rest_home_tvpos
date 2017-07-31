package com.yonyou.hhtpos.presenter.Impl;

import android.content.Context;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.StoreEntity;
import com.yonyou.hhtpos.interactor.IGetAllShopsInteractor;
import com.yonyou.hhtpos.interactor.Impl.GetAllShopsInteractorImpl;
import com.yonyou.hhtpos.presenter.IGetAllShopsPresenter;
import com.yonyou.hhtpos.view.IGetAllShopsView;
import com.yonyou.hhtpos.view.ITSClearTableView;

import java.util.List;


/**
 * Created by ybing on 2017/7/31.
 * 激活--获取所有门店列表
 */

public class GetAllShopsPresenterImpl implements IGetAllShopsPresenter {

    private Context mContext;
    private IGetAllShopsView mView;
    private IGetAllShopsInteractor mInteractor;

    public GetAllShopsPresenterImpl(Context mContext, final IGetAllShopsView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mInteractor = new GetAllShopsInteractorImpl(new GetAllShopsListener());
    }



    @Override
    public void getAllShops( String companyId) {
        mView.showDialogLoading(mContext.getResources().getString(R.string.common_loading_message));
        mInteractor.getAllShops(companyId);
    }

    private class GetAllShopsListener implements BaseLoadedListener<List<StoreEntity>>{

        @Override
        public void onSuccess(int event_tag, List<StoreEntity> data) {
            mView.dismissDialogLoading();
            mView.getShops(data);
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
