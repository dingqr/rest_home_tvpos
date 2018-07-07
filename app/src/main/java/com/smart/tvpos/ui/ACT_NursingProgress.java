package com.smart.tvpos.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
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
import butterknife.OnClick;

/**
 * Created by JoJo on 2018/6/23.
 * wechat：18510829974
 * description：护理进度页面
 */
public class ACT_NursingProgress extends BaseActivity {
    @Bind(R.id.recyclerview)
    LRecyclerView mRecyclerView;
    //    @Bind(R.id.recyclerview)
//    ScaleRecyclerView mRecyclerView;
    @Bind(R.id.tv_sub_title)
    TextView tvSubTitle;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ADA_NurseProgress mAdapter;
    private int mColumnNum = 4;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_nursing_progress;
    }

    @OnClick({R.id.tv_sub_title})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sub_title:
                readyGo(ACT_WatchingOverview.class);
                break;
        }
    }

    @Override
    protected long getRefreshTime() {
        return 0;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
        tvSubTitle.setText("概览");
        //显示实际的养老院名称
        setHeaderTitle(Constants.BRANCH_NAME);

        initRecyclerView();
//        mRecyclerView.setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);//父控件和子控件之间的焦点获取的关系,意思是焦点优先级是 父亲在后代后面  不加这行会出现焦点有时丢失的问题

        requestNet(true);
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
//        设置外层列表Adapter
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        //设置item之间的间距
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                //设计图item之间的间距为30
                outRect.bottom = 15;
                outRect.top = 15;
                outRect.left = 15;
                outRect.right = 15;
                //header占了一个位置，故从位置1开始显示实际的item
                if (itemPosition <= mColumnNum) {
                    outRect.top = 15;//设计图recyclerview距离上方控件为44px
                } else {
                    outRect.top = 15;
                }
                if (itemPosition % 4 == 0) {
                    //右边第一列
                    outRect.right = 30;
                } else if ((itemPosition - 1) % mColumnNum == 0) {
                    //左边第一列
                    outRect.left = 30;
                }
            }
        });
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setChildDrawingOrderCallback(mAdapter);//这句很关键,让获得焦点item浮在其他item上面
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadMoreEnabled(false);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {


            }
        });
//        //设置头部文字颜色
        mRecyclerView.setHeaderViewColor(R.color.color_2e84ba, R.color.color_2e84ba, R.color.color_FFFFFF);
        //设置底部加载颜色-loading动画颜色,文字颜色,footer的背景颜色
        mRecyclerView.setFooterViewColor(R.color.color_2e84ba, R.color.color_2e84ba, R.color.color_2e84ba);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint(MyApplication.getContext().getString(R.string.list_footer_loading), MyApplication.getContext().getString(R.string.list_footer_end), MyApplication.getContext().getString(R.string.list_footer_network_error));
    }


    private void requestNet(boolean isShowLoading) {
        requestWarningShow("userNurse");
        if (isShowLoading) {
            showLoading(MyApplication.getContext().getString(R.string.common_loading_message));
        }
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

                Elog.e("TAG" + "userNurse=" + bean.getBranch());

                //模拟数据
                List<UserNurseListEntity> userNurseLis = bean.getUser();
                userNurseLis.get(1).setWarningLevel(1);
                userNurseLis.get(6).setWarningLevel(2);
                userNurseLis.get(11).setWarningLevel(3);
                userNurseLis.get(12).setNumF(91);
                userNurseLis.get(12).setNumA(100);
                userNurseLis.get(userNurseLis.size() - 1).setNumF(11);
                userNurseLis.get(userNurseLis.size() - 1).setNumA(100);
                userNurseLis.get(userNurseLis.size() - 2).setNumF(90);
                userNurseLis.get(userNurseLis.size() - 2).setNumA(100);
                userNurseLis.get(12).setNumA(100);
                userNurseLis.get(1).setNumA(20);
                userNurseLis.get(6).setNumA(20);
                userNurseLis.get(11).setNumA(30);
                userNurseLis.get(5).setNumF(10);
                userNurseLis.get(5).setNumA(40);
                userNurseLis.get(4).setNumF(8);
                userNurseLis.get(4).setNumA(100);
                mAdapter.update(userNurseLis, true);
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
