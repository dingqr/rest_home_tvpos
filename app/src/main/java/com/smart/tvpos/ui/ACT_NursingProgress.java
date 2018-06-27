package com.smart.tvpos.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.adapter.ADA_NurseProgress;
import com.smart.tvpos.bean.UserNurseDataEntity;
import com.smart.tvpos.bean.UserNurseListEntity;
import com.smart.tvpos.global.API;
import com.smart.tvpos.manager.ReqCallBack;
import com.smart.tvpos.manager.RequestManager;
import com.smart.tvpos.util.Constants;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by JoJo on 2018/6/23.
 * wechat：18510829974
 * description：护理进度页面
 */
public class ACT_NursingProgress extends BaseActivity {
    @Bind(R.id.recyclerview)
    LRecyclerView mRecyclerView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ADA_NurseProgress mAdapter;
    private int mColumnNum = 4;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_nursing_progress;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
        initRecyclerView();
        requestNet();
    }

    /**
     * 初始化配置RecyclerView
     */
    private void initRecyclerView() {
        mAdapter = new ADA_NurseProgress(mContext);
        //配置列表样式
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, mColumnNum);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        //设置外层列表Adapter
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        //设置item之间的间距
        mRecyclerView.addItemDecoration(new SpaceItemDecoration());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadMoreEnabled(false);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {


            }
        });
//        //设置头部文字颜色
//        mRecyclerView.setHeaderViewColor(R.color.color_2e84ba, R.color.color_2e84ba, R.color.color_FFFFFF);
        //设置底部加载颜色-loading动画颜色,文字颜色,footer的背景颜色
        mRecyclerView.setFooterViewColor(R.color.color_2e84ba, R.color.color_2e84ba, R.color.color_2e84ba);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint(MyApplication.getContext().getString(R.string.list_footer_loading), MyApplication.getContext().getString(R.string.list_footer_end), MyApplication.getContext().getString(R.string.list_footer_network_error));
    }

    /**
     * 设置item之间的间距
     */
    public class SpaceItemDecoration extends LuRecyclerView.ItemDecoration {

        public SpaceItemDecoration() {
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            switch (itemPosition) {
                case 6:
                case 9:
                case 13:
                case 17:
                case 20:
                    outRect.bottom = 0;
                    outRect.top = 0;
                    outRect.right = 0;
                    outRect.left = 0;
                    break;
            }
            //设计图item之间的间距为30
            outRect.left = 15;
            outRect.right = 15;
            outRect.top = 15;
            outRect.bottom = 15;
            //header占了一个位置，故从位置1开始显示实际的item-第一行不设置顶部间距(UI)
            if (itemPosition <= mColumnNum) {
                outRect.top = 22;//设计图recyclerview距离上方控件为44px
            } else {
                outRect.top = 15;
            }


            //设置放大的效果
//            if (itemPosition == 6) {
//                outRect.bottom = -15;
//                outRect.top = -15;
//            }
//            //header占了列表头部的一个位置,设置bottom为0
//            if (itemPosition == 0) {
//                outRect.bottom = 5;
//            }
        }
    }

    private void requestNet() {
        requestWarningShow("userNurse");
        showLoading(MyApplication.getContext().getString(R.string.common_loading_message));
    }

    /**
     * 12.	用户护理进度
     *
     * @param requestType userNurse
     */
    private void requestWarningShow(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("name", "hafuadmin");
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<UserNurseDataEntity>() {

            @Override
            public void onReqSuccess(UserNurseDataEntity bean) {
                hideLoading();
                if (bean == null || bean.getUser() == null || bean.getUser().size() == 0) {
                    return;
                }
                List<UserNurseListEntity> userNurseLis = bean.getUser();
                mAdapter.update(userNurseLis, true);
                UserNurseListEntity userNurseBean = bean.getUser().get(0);
                Elog.e("TAG" + "userNurse=" + userNurseBean.getBuildingName());
            }

            @Override
            public void onFailure(String result) {
                hideLoading();
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                hideLoading();
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return mRecyclerView;
    }


    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
