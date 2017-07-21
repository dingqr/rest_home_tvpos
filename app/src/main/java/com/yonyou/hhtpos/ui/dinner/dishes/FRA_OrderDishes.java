package com.yonyou.hhtpos.ui.dinner.dishes;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_DishTypeList;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.dish.DishDataEntity;
import com.yonyou.hhtpos.bean.dish.DishTypesEntity;
import com.yonyou.hhtpos.bean.dish.DishesEntity;
import com.yonyou.hhtpos.bean.dish.RequestAddDishEntity;
import com.yonyou.hhtpos.dialog.DIA_AddTempDishes;
import com.yonyou.hhtpos.dialog.DIA_OrderDishCount;
import com.yonyou.hhtpos.presenter.IAddDishPresenter;
import com.yonyou.hhtpos.presenter.IGetAllDishesPresenter;
import com.yonyou.hhtpos.presenter.Impl.AddDishPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.GetAllDishesPresenterImpl;
import com.yonyou.hhtpos.util.AnimationUtil;
import com.yonyou.hhtpos.view.IAddDishView;
import com.yonyou.hhtpos.view.IGetAllDishesView;
import com.yonyou.hhtpos.widgets.RightListView;
import com.yonyou.hhtpos.widgets.RightNavigationView;

import java.util.List;

import butterknife.Bind;

import static com.yonyou.hhtpos.R.id.ll_content;
import static com.yonyou.hhtpos.R.id.rv_orderdish_list;
import static com.yonyou.hhtpos.util.FiltrationUtil.getCookery;
import static com.yonyou.hhtpos.util.FiltrationUtil.getDishRemark;


/**
 * Created by zj on 2017/7/11.
 * 邮箱：zjuan@yonyou.com
 * 描述：点菜页面-获取所有菜品/菜类
 * 右侧导航及菜品列表
 */
public class FRA_OrderDishes extends BaseFragment implements IGetAllDishesView,IAddDishView{
    @Bind(R.id.layout_dish_root)
    RelativeLayout layoutRoot;
    @Bind(ll_content)
    FrameLayout flContent;
    @Bind(rv_orderdish_list)
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

    //点菜飞入动画
    private int refreshPos;
    private RightListView mRightListView;
    public View startView;

    //获取所有菜品/菜类presenter
    private IGetAllDishesPresenter mPresenter;
    //    测试公司ID：DIE49JkEU29JHD819HRh19hGDAY1 测试门店ID：C13352966C000000A60000000016E000
    private String compId = "DIE49JkEU29JHD819HRh19hGDAY1";
    private String shopId = "C13352966C000000A60000000016E000";
    //菜品/菜类实体
    private DishDataEntity mDishDataBean;

    //添加菜品presenter
    private IAddDishPresenter mAddDishPresenter;
    private LinearLayout.LayoutParams mParams;

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

        mAddDishPresenter = new AddDishPresenterImpl(mContext,this);
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
        //设置测试数据
//        setData();
//        mRightNavigationView.setData(NavigationUtil.getRightDefaultData());

        initListener();

        mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //空页面
//        showEmptyHyperLink(mContext, API.URL_OPERATION_PALTFORM,"");
    }

    private void initListener() {
        mAdapter.setOnActionOrderDishListener(new ADA_DishTypeList.OnActionOrderDishListener() {
            @Override
            public void OnActionOrderDish(View iv_start, int pos, DishesEntity dishesEntity) {

                RequestAddDishEntity requestAddDishEntity = new RequestAddDishEntity();
                requestAddDishEntity.companyId = dishesEntity.companyId;

//                requestAddDishEntity.dishClassid = dishesEntity.
//                mAddDishPresenter.requestAddDish();
                //设置菜品列表的多选效果，实际位置要在原來的基础上减掉头部占的位置
//                int mPosition = position - 1;
//                DishesEntity dishesEntity = mAdapter.getDataList().get(mPosition);
//                dishesEntity.isCheck = true;
//                mAdapter.notifyItemChanged(mPosition);
//                //刷新角标数量
//                mRightNavigationView.refreshCount(dishesEntity.dishTypeRelateId, true);
//                //刷新右侧标题对应的位置
//                refreshPos = getRefreshPosById(dishesEntity.dishTypeRelateId);
//                //动画开始的View
//                startView = iv_start;
//
//                int startPos = mRightListView.getFirstVisiblePosition();
//                int endPos = mRightListView.getLastVisiblePosition();
//                if (refreshPos >= startPos && refreshPos <= endPos) {
//                    AnimationUtil.doFlyAnimation(mContext, layoutRoot, mRightListView, iv_start, refreshPos, startPos);
//                } else {
//                    return;
//                }
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
        //临时菜
        mRightNavigationView.getBottomTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DIA_AddTempDishes(mContext).getDialog().show();
            }
        });
        //点击菜品进行数据筛选
        mRightNavigationView.setOnItemClickListener(new RightNavigationView.OnItemClickListener() {
            @Override
            public void onItemClick(int count, String title, final int postion) {
                if (mDishDataBean != null && mDishDataBean.dishTypes.size() > 0) {
                    List<DishesEntity> dishes = mDishDataBean.dishTypes.get(postion).dishes;
                    // restore view helper
                    restoreViewHelper();
                    if (dishes != null && dishes.size() > 0) {
                        mParams.setMargins(6, 0, 6, 0);
                        flContent.setLayoutParams(mParams);
                        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
                        mAdapter.update(dishes, true);
                    } else {
                        //设置空页面距离顶部的间距
                        mParams.setMargins(12, 0, 12, 0);
                        flContent.setLayoutParams(mParams);
                        setLoadingTargetView(flContent);
                        showEmpty(R.drawable.ic_wm_dishes_empty, mContext.getResources().getString(R.string.string_empty_dishes));
                    }
                    //菜类下的菜品为空时，展示空页面
                }
            }
        });
    }

    /**
     * 根据id得到右侧刷新的位置
     *
     * @param mId
     * @return
     */
    private int getRefreshPosById(String mId) {
        int refreshPos = -1;
        for (int i = 0; i < mDishDataBean.dishTypes.size(); i++) {
            DishTypesEntity dishTypesEntity = mDishDataBean.dishTypes.get(i);
            if (dishTypesEntity.relateId.equals(mId)) {
                refreshPos = i;
                break;
            }
        }
        return refreshPos;
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
                outRect.top = 0;
            } else {
                outRect.top = 5;
            }
//            //header占了列表头部的一个位置,设置bottom为0
//            if (itemPosition == 0) {
//                outRect.bottom = 5;
//            }
        }
    }

    /**
     * 获取所有菜品菜类
     * @param dishDataEntity
     */
    @Override
    public void getAllDishes(DishDataEntity dishDataEntity) {
        this.mDishDataBean = dishDataEntity;
        if (mDishDataBean != null) {
            //给右侧菜类设置数据
            mRightNavigationView.setData(mDishDataBean.dishTypes);
            //给菜品设置默认数据
            mAdapter.update(mDishDataBean.dishTypes.get(1).dishes);
            mRightNavigationView.getRightListView().getRLAdapter().setSelectItem(1);
            mRightNavigationView.getRightListView().getRLAdapter().notifyDataSetChanged();
        }
       FilterItemEntity cookeryOption = new FilterItemEntity();
        cookeryOption.setTitle("");
        cookeryOption.setOptions(getCookery());

       FilterItemEntity remarkOption = new FilterItemEntity();
        remarkOption.setTitle("");
        remarkOption.setOptions(getDishRemark());
        //服务员点菜时蔬 数量弹框
        DIA_OrderDishCount dia_orderDishCount = new DIA_OrderDishCount(mContext,cookeryOption,remarkOption);
    }

    /**
     * 点菜
     * @param result
     */
    @Override
    public void requestAddDish(String result) {

    }

}
