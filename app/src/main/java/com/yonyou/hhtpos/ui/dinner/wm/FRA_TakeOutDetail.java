package com.yonyou.hhtpos.ui.dinner.wm;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_TakeOutOrderDetail;
import com.yonyou.hhtpos.bean.wm.WMDishDetailEntity;
import com.yonyou.hhtpos.bean.wm.WMOrderDetailEntity;
import com.yonyou.hhtpos.presenter.IOrderDetailPresenter;
import com.yonyou.hhtpos.presenter.Impl.OrderDetailPresenterImpl;
import com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes;
import com.yonyou.hhtpos.view.IWMOrderDetailView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zj on 2017/7/6.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖订单详情页面
 */
public class FRA_TakeOutDetail extends BaseFragment implements IWMOrderDetailView{
    @Bind(R.id.rl_root_view)
    RelativeLayout rlRootView;
    //列表上层悬浮的标题，默认隐藏
    @Bind(R.id.suspension_title)
    LinearLayout suspension_title;
    @Bind(R.id.lv_wm_order_detail)
    ListView wmListView;
    private ADA_TakeOutOrderDetail mAdapter;
    private ArrayList<WMDishDetailEntity> dataList = new ArrayList<>();
    //请求外面订单详情接口
    private IOrderDetailPresenter mPresenter;
    //测试参数
    private String tableBillId ="C50242AC980000009200000000257000";

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
        return rlRootView;
    }

    @Override
    protected void initViewsAndEvents() {
        mPresenter = new OrderDetailPresenterImpl(mContext,FRA_TakeOutDetail.this);
        mPresenter.requestWMOrderDetail(tableBillId);
        //有数据页面
        mAdapter = new ADA_TakeOutOrderDetail(mContext);

        //添加头布局
        View header_view = View.inflate(mContext, R.layout.item_take_out_header, null);
        //列表中需要悬浮的部分
        View suspension_head_title = View.inflate(mContext, R.layout.suspension_header_view, null);
        wmListView.addHeaderView(header_view);
        wmListView.addHeaderView(suspension_head_title);
        wmListView.setAdapter(mAdapter);

        setData();
        mAdapter.update(dataList);
        wmListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    suspension_title.setVisibility(View.VISIBLE);
                } else {
                    suspension_title.setVisibility(View.GONE);
                }
            }
        });
        // 无数据页面
//        showEmpty(R.drawable.default_no_order_detail, mContext.getResources().getString(R.string.empty_msg), ContextCompat.getColor(mContext, R.color.color_e9e9e9), ContextCompat.getColor(mContext, R.color.color_222222),mContext.getResources().getString(R.string.empty_msg_other));
    }

    private void setData() {
        for (int i = 0; i < 10; i++) {
            WMDishDetailEntity wmDishDetailEntity = new WMDishDetailEntity();
            wmDishDetailEntity.dishName = "肉肉" + i;
            dataList.add(wmDishDetailEntity);
        }
    }

    @OnClick({R.id.btn_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_right:
                readyGo(ACT_OrderDishes.class);
                break;
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_take_out_detail;
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
    public void requestWMOrderDetail(WMOrderDetailEntity orderDetailEntity) {
        Log.e("TAG", "orderDetailEntity="+orderDetailEntity);
    }
}
