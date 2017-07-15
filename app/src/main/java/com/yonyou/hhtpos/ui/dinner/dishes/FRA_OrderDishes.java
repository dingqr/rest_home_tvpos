package com.yonyou.hhtpos.ui.dinner.dishes;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_DishTypeList;
import com.yonyou.hhtpos.bean.DishTypeEntity;
import com.yonyou.hhtpos.bean.RightTitleEntity;
import com.yonyou.hhtpos.bean.dish.DishDataEntity;
import com.yonyou.hhtpos.presenter.IGetAllDishesPresenter;
import com.yonyou.hhtpos.presenter.Impl.GetAllDishesPresenterImpl;
import com.yonyou.hhtpos.util.AnimationUtil;
import com.yonyou.hhtpos.util.NavigationUtil;
import com.yonyou.hhtpos.view.IGetAllDishesView;
import com.yonyou.hhtpos.widgets.RightListView;
import com.yonyou.hhtpos.widgets.RightNavigationView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;


/**
 * Created by zj on 2017/7/11.
 * 邮箱：zjuan@yonyou.com
 * 描述：点菜页面-获取所有菜品/菜类
 * 右侧导航及菜品列表
 */
public class FRA_OrderDishes extends BaseFragment implements IGetAllDishesView {
    @Bind(R.id.layout_dish_root)
    RelativeLayout layoutRoot;
    @Bind(R.id.rv_orderdish_list)
    LRecyclerView mRecyclerView;
    @Bind(R.id.view_navigation_right)
    RightNavigationView mRightNavigationView;

    //每一页请求多少条数据
    private static final int REQUEST_COUNT = 5;
    //当前页数
    private int mCurrentPage = 1;
    //列表显示的列数
    private int mColumnNum = 4;
    private ADA_DishTypeList mAdapter;
    private LRecyclerViewAdapter mLuRecyclerViewAdapter;
    private ArrayList<DishTypeEntity> datas = new ArrayList<>();

    //点菜飞入动画
    private int refreshPos;
    private RightListView mRightListView;
    public View startView;

    //请求接口
    private IGetAllDishesPresenter mPresenter;
    //    测试公司ID：DIE49JkEU29JHD819HRh19hGDAY1 测试门店ID：C13352966C000000A60000000016E000
    private String compId = "DIE49JkEU29JHD819HRh19hGDAY1";
    private String shopId = "C13352966C000000A60000000016E000";

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
        return layoutRoot;
    }

    @Override
    protected void initViewsAndEvents() {
        mPresenter = new GetAllDishesPresenterImpl(mContext, this);
        mPresenter.getAllDishes(compId, shopId);
        // 去除LRecyclerView的默认的下拉刷新效果
        mRecyclerView.setPullRefreshEnabled(false);

        mAdapter = new ADA_DishTypeList(mContext);
        //设置LayoutManager必须在设置setAdapter之前
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, mColumnNum);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        //setAdapter
        mLuRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);

        mRecyclerView.addItemDecoration(new SpaceItemDecoration());
        //设置底部加载颜色-
        mRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.dark, R.color.color_dcdcdc);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint(mContext.getResources().getString(R.string.loading_note), mContext.getResources().getString(R.string.no_more_note), "");

        mRecyclerView.setHasFixedSize(true);
        //去除loadmore
        mRecyclerView.setLoadMoreEnabled(false);

        mRightListView = mRightNavigationView.getRightListView();

        setData();
        mRightNavigationView.setData(NavigationUtil.getRightDefaultData());

        initListener();
        //空页面
//        showEmptyHyperLink(mContext, API.URL_OPERATION_PALTFORM,"");
    }

    private void initListener() {
        mAdapter.setOnActionOrderDishListener(new ADA_DishTypeList.OnActionOrderDishListener() {

            @Override
            public void OnActionOrderDish(final View iv_start, int position) {
                //设置菜品列表的多选效果，实际位置要在原來的基础上减掉头部占的位置
                int mPosition = position - 1;
                DishTypeEntity dishTypeEntity = mAdapter.getDataList().get(mPosition);
                dishTypeEntity.isCheck = true;
                mAdapter.notifyItemChanged(mPosition);
                //刷新角标数量
                mRightNavigationView.refreshCount(mAdapter.getDataList().get(mPosition).id, true);

                refreshPos = getTitleIdByPosition(mAdapter.getDataList().get(mPosition).id);

                startView = iv_start;

                int startPos = mRightListView.getFirstVisiblePosition();
                int endPos = mRightListView.getLastVisiblePosition();
                if (refreshPos >= startPos && refreshPos <= endPos) {
                    AnimationUtil.doFlyAnimation(mContext, layoutRoot, mRightListView, iv_start, refreshPos, startPos);
                } else {
                    return;
                }
            }
        });
        //滑动更新完右侧标题角标数量再进行动画
        mRightNavigationView.setOnDoAnimListener(new RightNavigationView.OnDoAnimListener() {
            @Override
            public void doAnim() {
                int firstVisiblePosition = mRightListView.getFirstVisiblePosition();
                AnimationUtil.doFlyAnimation(mContext, layoutRoot, mRightListView, startView, refreshPos, firstVisiblePosition);

            }
        });
    }

    /**
     * 根据id得到右侧刷新的位置
     *
     * @param mId
     * @return
     */
    private int getTitleIdByPosition(String mId) {
        int refreshPos = -1;
        for (int i = 0; i < NavigationUtil.getRightDefaultData().size(); i++) {
            RightTitleEntity rightTitleEntity = NavigationUtil.getRightDefaultData().get(i);
            if (rightTitleEntity.id.equals(mId)) {
                refreshPos = i;
                break;
            }
        }
        return refreshPos;
    }

    private void setData() {
        for (int i = 0; i < 50; i++) {
            DishTypeEntity dishTypeEntity = new DishTypeEntity();
            dishTypeEntity.name = "item=" + i;
            Random rand = new Random();
            int uid = rand.nextInt(15);
            dishTypeEntity.id = StringUtil.getString(uid);
            datas.add(dishTypeEntity);
        }
        mAdapter.update(datas);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_order_dishes;
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
     * 设置item之间的间距
     */
    public class SpaceItemDecoration extends LuRecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration() {
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            outRect.left = 5;
            outRect.right = 5;
            outRect.bottom = 5;
            //header占了一个位置，故从位置1开始显示实际的item-第一行不设置顶部间距(UI)
            if (itemPosition <= mColumnNum) {
                outRect.top = 3;
            } else {
                outRect.top = 5;
            }

        }
    }

    @Override
    public void getAllDishes(DishDataEntity dishDataEntity) {

    }

}
