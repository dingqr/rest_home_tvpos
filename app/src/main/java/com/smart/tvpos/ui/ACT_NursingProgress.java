package com.smart.tvpos.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.ReceiveConstants;
import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.adapter.ADA_BuildingList;
import com.smart.tvpos.adapter.ADA_FloorList;
import com.smart.tvpos.adapter.ADA_NurseProgressModify;
import com.smart.tvpos.bean.BuildingEntity;
import com.smart.tvpos.bean.FloorEntity;
import com.smart.tvpos.bean.UserNurseDataEntity;
import com.smart.tvpos.bean.UserNurseListEntity;
import com.smart.tvpos.global.API;
import com.smart.tvpos.manager.ReqCallBack;
import com.smart.tvpos.manager.RequestManager;
import com.smart.tvpos.util.Constants;

import java.util.ArrayList;
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
    @Bind(R.id.tv_sub_title)
    TextView tvSubTitle;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ADA_NurseProgressModify mAdapter;
    private int mColumnNum = 4;
    //当前选中的楼层
    private FloorEntity mSelectFloorEntity;
    //当前选中的楼宇
    private BuildingEntity mSelectBuildingEntity;
    private ADA_BuildingList mBuildingAdapter;
    private ADA_FloorList mFloorAdapter;
    @Bind(R.id.buildingList)
    ListView listviewbuildingList;
    @Bind(R.id.floorList)
    ListView listviewfloorList;
    @Bind(R.id.tv_building)
    TextView tvBuilding;
    @Bind(R.id.tv_floor)
    TextView tvFloor;

    @Override
    protected void onReceiveBroadcast(int intent, Bundle bundle) {
        if (intent == ReceiveConstants.REFRESH_CURRENT_PAGE) {
            requestNet(false);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_nursing_progress;
    }

    @OnClick({R.id.tv_sub_title, R.id.tv_building, R.id.tv_floor})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sub_title:
                readyGo(ACT_WatchingOverview.class);
                break;
            case R.id.tv_building:
                if (listviewbuildingList.getVisibility() == View.INVISIBLE) {
                    showBuildingList();
                } else {
                    hideBuildingList();
                }
                break;
            case R.id.tv_floor:
                if (listviewfloorList.getVisibility() == View.INVISIBLE) {
                    showFloorList();
                } else {
                    hideFloorList();
                }
                break;
        }
    }

    /**
     * 弹出楼宇列表
     */
    private void showBuildingList() {
        listviewbuildingList.setVisibility(View.VISIBLE);
        listviewbuildingList.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    /*
     * 隐藏楼宇列表
     */
    private void hideBuildingList() {
        listviewbuildingList.setVisibility(View.INVISIBLE);
        listviewbuildingList.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
    }

    /**
     * 弹出楼层列表
     */
    private void showFloorList() {
        listviewfloorList.setVisibility(View.VISIBLE);
        listviewfloorList.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    /**
     * 隐藏楼层列表
     */
    private void hideFloorList() {
        listviewfloorList.setVisibility(View.INVISIBLE);
        listviewfloorList.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
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
//        setHeaderTitle(Constants.BRANCH_NAME + "护理进度");
        setHeaderTitle(Constants.BRANCH_NAME);

        initRecyclerView();
//        mRecyclerView.setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);//父控件和子控件之间的焦点获取的关系,意思是焦点优先级是 父亲在后代后面  不加这行会出现焦点有时丢失的问题
        initListView();

        requestNet(true);

        initListener();
    }


    /**
     * 初始化配置RecyclerView
     */
    private void initRecyclerView() {
        mAdapter = new ADA_NurseProgressModify(mContext);
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
//        //设置头部文字颜色
        mRecyclerView.setHeaderViewColor(R.color.color_2e84ba, R.color.color_2e84ba, R.color.color_FFFFFF);
        //设置底部加载颜色-loading动画颜色,文字颜色,footer的背景颜色
        mRecyclerView.setFooterViewColor(R.color.color_2e84ba, R.color.color_2e84ba, R.color.color_2e84ba);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint(MyApplication.getContext().getString(R.string.list_footer_loading), MyApplication.getContext().getString(R.string.list_footer_end), MyApplication.getContext().getString(R.string.list_footer_network_error));
    }

    private void initListView() {
        mBuildingAdapter = new ADA_BuildingList(mContext);
        listviewbuildingList.setAdapter(mBuildingAdapter);

        mFloorAdapter = new ADA_FloorList(mContext);
        listviewfloorList.setAdapter(mFloorAdapter);
    }

    private void initListener() {
        listviewbuildingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectBuildingEntity = mBuildingAdapter.getDataList().get(i);
                mBuildingAdapter.setCheckCurrentItem(i);
                tvBuilding.setText(mSelectBuildingEntity.getBuildingName());
                mFloorAdapter.update(mSelectBuildingEntity.getList(), true);
                //切换时默认显示列表的第一个
                if (mSelectBuildingEntity.getList().size() > 0) {
                    tvFloor.setText(mSelectBuildingEntity.getList().get(0).getFloorName());
                }
                if (listviewfloorList.getVisibility() == View.INVISIBLE) {
                    showFloorList();
                }
            }
        });
        //楼层切换
        listviewfloorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectFloorEntity = mFloorAdapter.getDataList().get(i);
                mFloorAdapter.setCheckCurrentItem(i);
                tvFloor.setText(mSelectFloorEntity.getFloorName());
                hideFloorList();
                if (listviewbuildingList.getVisibility() == View.VISIBLE) {
                    hideBuildingList();
                }
                //选中的楼和楼层
//                CommonUtils.makeEventToast(MyApplication.getContext(), mSelectBuildingEntity.getBuildingName() + mSelectFloorEntity.getFloorName(), false);
                //切换楼层时,刷新当前护理进度数据列表
                requestWarningShow("userNurse", mSelectBuildingEntity.getBuildingId() + "", mSelectFloorEntity.getFloorId() + "");

            }
        });
    }

    private void requestNet(boolean isShowLoading) {
//        requestWarningShow("userNurse", "", "");
        requestBuildingList("building");
        if (isShowLoading) {
            showLoading(MyApplication.getContext().getString(R.string.common_loading_message));
        }
    }

    /**
     * 1.	当前分院的楼宇,楼层
     *
     * @param requestType building
     */
    private void requestBuildingList(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<List<BuildingEntity>>() {

            @Override
            public void onReqSuccess(List<BuildingEntity> dataList) {
                if (dataList == null || dataList.size() == 0) {
                    return;
                }
                mSelectBuildingEntity = dataList.get(0);
                if (mSelectBuildingEntity == null) {
                    return;
                }
                Elog.e("TAG", "building=" + mSelectBuildingEntity.getBuildingName());

                tvBuilding.setText(mSelectBuildingEntity.getBuildingName());
                mBuildingAdapter.update(dataList, true);

                if (mSelectBuildingEntity.getList().size() > 0) {
                    tvFloor.setText(mSelectBuildingEntity.getList().get(0).getFloorName());
//                    //默认展示当前大楼一楼的数据
                    requestWarningShow("userNurse", mSelectBuildingEntity.getBuildingId() + "", mSelectBuildingEntity.getList().get(0).getFloorId() + "");
                }
                mFloorAdapter.update(mSelectBuildingEntity.getList(), true);
            }

            @Override
            public void onFailure(String result) {
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    /**
     * 2.	用户护理进度
     *
     * @param requestType userNurse
     */
    private void requestWarningShow(String requestType, String buildingId, String floorId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("name", "hafuadmin");
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        params.put("buildingId", buildingId);
        params.put("floorId", floorId);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<UserNurseDataEntity>() {

            @Override
            public void onReqSuccess(UserNurseDataEntity bean) {
                hideLoading();
                if (bean == null || bean.getUser() == null || bean.getUser().size() == 0) {
                    mAdapter.update(new ArrayList<UserNurseListEntity>(), true);
                    return;
                }

                Elog.e("TAG" + "userNurse=" + bean.getBranch());


                List<UserNurseListEntity> userNurseLis = bean.getUser();
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
