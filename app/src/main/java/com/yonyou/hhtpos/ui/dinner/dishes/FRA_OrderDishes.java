package com.yonyou.hhtpos.ui.dinner.dishes;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_DishTypeList;
import com.yonyou.hhtpos.bean.dish.DataBean;
import com.yonyou.hhtpos.bean.dish.DishCallBackEntity;
import com.yonyou.hhtpos.bean.dish.DishDataEntity;
import com.yonyou.hhtpos.bean.dish.DishListEntity;
import com.yonyou.hhtpos.bean.dish.DishTypesEntity;
import com.yonyou.hhtpos.bean.dish.DishesEntity;
import com.yonyou.hhtpos.bean.dish.RequestAddDishEntity;
import com.yonyou.hhtpos.dialog.DIA_CollectForegift;
import com.yonyou.hhtpos.dialog.DIA_OrderDishCount;
import com.yonyou.hhtpos.dialog.DIA_OrderDishNorms;
import com.yonyou.hhtpos.dialog.DIA_OrderDishSetPrice;
import com.yonyou.hhtpos.dialog.DIA_OrderDishSetWeight;
import com.yonyou.hhtpos.dialog.DIA_OrderDishWeight;
import com.yonyou.hhtpos.interfaces.DishDataCallback;
import com.yonyou.hhtpos.presenter.IAddDishPresenter;
import com.yonyou.hhtpos.presenter.IGetAllDishesPresenter;
import com.yonyou.hhtpos.presenter.Impl.AddDishPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.GetAllDishesPresenterImpl;
import com.yonyou.hhtpos.util.AnimationUtil;
import com.yonyou.hhtpos.view.IAddDishView;
import com.yonyou.hhtpos.view.IGetAllDishesView;
import com.yonyou.hhtpos.widgets.RightListView;
import com.yonyou.hhtpos.widgets.RightNavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import static com.yonyou.hhtpos.R.id.ll_content;
import static com.yonyou.hhtpos.R.id.rv_orderdish_list;


/**
 * Created by zj on 2017/7/11.
 * 邮箱：zjuan@yonyou.com
 * 描述：点菜页面-获取所有菜品/菜类
 * 右侧导航及菜品列表
 */
public class FRA_OrderDishes extends BaseFragment implements IGetAllDishesView, IAddDishView {
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
    private String mTableBillId = "C50242AC980000009200000000257000";
    //菜品/菜类实体
    private DishDataEntity mDishDataBean;
    //已点菜类、角标数量
    private List<DishListEntity.DishType> mDishTypeList = new ArrayList<>();

    //添加菜品presenter
    private IAddDishPresenter mAddDishPresenter;
    private LinearLayout.LayoutParams mParams;
    private RequestAddDishEntity requestAddDishEntity;
    //菜品在列表的位置
    private int mPosition;

    private DIA_OrderDishSetPrice mDiaCurrentDishWeight;//称重、时价
    private DIA_OrderDishSetWeight mDiaWeightNormal;//称重，无备注
    private DIA_OrderDishWeight mDiaWeightRemarks;//称重,有备注
    private DIA_OrderDishNorms mDiaStandards;//规格
    private DIA_OrderDishCount mDiaNormal;//normal
    private List<DishListEntity.Dishes> mOrderedDishes = new ArrayList<>();

    /**
     * 接收右侧角标数量的数据集合
     *
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onRefreshRightCount(DishListEntity bean) {
        this.mDishTypeList = bean.getDishTypelist();
        this.mOrderedDishes = bean.getDishes();
        if (mDishDataBean != null && mDishDataBean.dishTypes != null && mDishDataBean.dishTypes.size() > 0) {
            setRightDishTypeData();
            setDishesCheckStatus(mDishDataBean);
        }
    }

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

        mAddDishPresenter = new AddDishPresenterImpl(mContext, this);
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

        //点菜时的弹窗
        //时价、重量弹窗
        mDiaCurrentDishWeight = new DIA_OrderDishSetPrice(mContext);//有时价、重量
        mDiaStandards = new DIA_OrderDishNorms(mContext);//规格的弹窗
        //称重、有备注列表
        mDiaWeightRemarks = new DIA_OrderDishWeight(mContext);
        //称重、无备注列表
        mDiaWeightNormal = new DIA_OrderDishSetWeight(mContext);
        mDiaNormal = new DIA_OrderDishCount(mContext);//有做法，备注，数量的弹窗 -normal


        initListener();

        mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //空页面
//        showEmptyHyperLink(mContext, API.URL_OPERATION_PALTFORM,"");


    }

    private void initListener() {
        /**
         *1、无规格、无做法，直接加入购物车；
         *2.有规格或有做法，弹窗；
         2、时价的价格未设置，弹窗；设置的价格，直接加入购物车；
         3、套餐（N选N），弹窗；套餐（固定），直接加入购物车；
         4、称重菜，弹窗；
         5、必填：做法、规格；备注选填
         */
        mAdapter.setOnActionOrderDishListener(new ADA_DishTypeList.OnActionOrderDishListener() {
            @Override
            public void OnActionOrderDish(View iv_start, int position, DishesEntity dishesEntity) {
                //动画开始的View
                startView = iv_start;
                mPosition = position - 1;

                requestAddDishEntity = new RequestAddDishEntity();
                requestAddDishEntity.companyId = dishesEntity.companyId;
                requestAddDishEntity.dishClassid = dishesEntity.dishTypeRelateId;
                requestAddDishEntity.dishName = dishesEntity.dishName;
                requestAddDishEntity.dishId = dishesEntity.id;
                requestAddDishEntity.setDishPrice(dishesEntity.getPrice());
                requestAddDishEntity.dishType = dishesEntity.dishType;
                requestAddDishEntity.shopId = dishesEntity.shopId;
                requestAddDishEntity.unit = (dishesEntity.isWeigh.equals("Y")) ? 1 : 0;

                requestAddDishEntity.dishRelateId = dishesEntity.relateId;
                //不确定的字段
                requestAddDishEntity.dishStatus = "8";//等叫

                //缺少的字段
                //把所有已选做法名连接到一起的字符串，逗号分隔
                requestAddDishEntity.listShowPractice = "";
                //把所有已选和手填的备注名连接到一起的字符串，逗号分隔
                requestAddDishEntity.listShowRemark = "";
                requestAddDishEntity.practices = "";
                requestAddDishEntity.quantity = "1";
                requestAddDishEntity.remarks = "";
                requestAddDishEntity.remark = "";
                requestAddDishEntity.standardId = "";

                //写死的字段
                requestAddDishEntity.tableBillId = mTableBillId;


                //传入弹窗的bean
                DataBean dataBean = setDialogData(dishesEntity);

                //称重、时价
                if (dishesEntity.isWeigh.equals("Y") && dishesEntity.isCurrentDish != null && dishesEntity.isCurrentDish.equals("Y")) {
                    mDiaCurrentDishWeight.setData(dataBean).getDialog().show();
                    return;
                }
                //称重、有备注列表
                if (dishesEntity.isWeigh.equals("Y") && dishesEntity.remarks != null && dishesEntity.remarks.size() > 0) {
                    mDiaWeightRemarks.setData(dataBean).getDialog().show();
                    return;
                }
                //称重、无备注列表
                if (dishesEntity.isWeigh.equals("Y") && dishesEntity.remarks != null && dishesEntity.remarks.size() == 0) {
                    mDiaWeightNormal.setData(dataBean).getDialog().show();
                    return;
                }
                //规格
                if (dishesEntity.standards != null && dishesEntity.standards.size() > 0) {
                    mDiaStandards.setDataBean(dataBean).getDialog().show();
                    return;
                }
                //无规格、无做法，直接加入购物车；
                if (dishesEntity.practices.size() == 0 && dishesEntity.standards.size() == 0) {
                    mAddDishPresenter.requestAddDish(requestAddDishEntity);
                    return;
                }
                //不考虑临时菜和套餐的其他普通情况-只有数量、做法、备注
                mDiaNormal.setData(dataBean).getDialog().show();

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
//                new DIA_AddTempDishes(mContext).getDialog().show();
                new DIA_CollectForegift(getActivity()).show();
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
                }
            }
        });
        //根据菜品类型，设置不同的弹窗
        showDialogByDishType();

    }

    /**
     * 根据菜品类型，设置不同的弹窗
     */
    private void showDialogByDishType() {
        mDiaCurrentDishWeight.setDishDataCallback(new DishDataCallback() {
            @Override
            public void sendItems(DishCallBackEntity bean) {
//                Elog.e("TAG", "时价、称重=" + bean.toString());
                //重量
                requestAddDishEntity.quantity = StringUtil.getString(bean.getDishWeight());
                //时价
                requestAddDishEntity.setDishPrice(bean.getDishPrice());
                //做法
                if (!TextUtils.isEmpty(bean.getPractices())) {
                    requestAddDishEntity.practices = bean.getPractices();
                }
                //备注：手填
                if (!TextUtils.isEmpty(bean.getRemark())) {
                    requestAddDishEntity.remark = bean.getRemark();
                }
                mAddDishPresenter.requestAddDish(requestAddDishEntity);
            }
        });
        mDiaStandards.setDishDataCallback(new DishDataCallback() {
            @Override
            public void sendItems(DishCallBackEntity bean) {
//                Elog.e("TAG", "规格=" + bean.toString());
                //规格：必填
                if (!TextUtils.isEmpty(requestAddDishEntity.standardId)) {
                    requestAddDishEntity.standardId = bean.getDishStandardId();
                }
                //数量
                requestAddDishEntity.quantity = StringUtil.getString(bean.getDishCount());
                //备注：手填
                if (!TextUtils.isEmpty(bean.getRemark())) {
                    requestAddDishEntity.remark = bean.getRemark();
                }
                mAddDishPresenter.requestAddDish(requestAddDishEntity);
            }
        });
        mDiaWeightRemarks.setDishDataCallback(new DishDataCallback() {
            @Override
            public void sendItems(DishCallBackEntity bean) {
//                Elog.e("TAG", "称重、有备注列表=" + bean.toString());
                //重量
                requestAddDishEntity.quantity = StringUtil.getString(bean.getDishWeight());
                //做法
                if (!TextUtils.isEmpty(bean.getPractices())) {
                    requestAddDishEntity.practices = bean.getPractices();
                }
                //备注：列表
                if (!TextUtils.isEmpty(bean.getListShowRemark()) && !TextUtils.isEmpty(bean.getRemarks())) {
                    requestAddDishEntity.listShowRemark = bean.getListShowRemark();
                    requestAddDishEntity.remarks = bean.getRemarks();
                }
                //备注：手填
                if (!TextUtils.isEmpty(bean.getRemark())) {
                    requestAddDishEntity.remark = bean.getRemark();
                }
                mAddDishPresenter.requestAddDish(requestAddDishEntity);
            }
        });
        mDiaWeightNormal.setDishDataCallback(new DishDataCallback() {
            @Override
            public void sendItems(DishCallBackEntity bean) {
//                Elog.e("TAG", "称重、无备注列表=" + bean.toString());
                //重量
                requestAddDishEntity.quantity = StringUtil.getString(bean.getDishWeight());
                //做法
                if (!TextUtils.isEmpty(bean.getPractices())) {
                    requestAddDishEntity.practices = bean.getPractices();
                }
                //备注：手填
                if (!TextUtils.isEmpty(bean.getRemark())) {
                    requestAddDishEntity.remark = bean.getRemark();
                }
                mAddDishPresenter.requestAddDish(requestAddDishEntity);
            }
        });
        mDiaNormal.setDishDataCallback(new DishDataCallback() {
            @Override
            public void sendItems(DishCallBackEntity bean) {
                //数量
                requestAddDishEntity.quantity = StringUtil.getString(bean.getDishCount());
                //做法
                if (!TextUtils.isEmpty(bean.getPractices())) {
                    requestAddDishEntity.practices = bean.getPractices();
                }
                //备注：列表
                if (!TextUtils.isEmpty(bean.getListShowRemark()) && !TextUtils.isEmpty(bean.getRemarks())) {
                    requestAddDishEntity.listShowRemark = bean.getListShowRemark();
                    requestAddDishEntity.remarks = bean.getRemarks();
                }
                //备注：手填
                if (!TextUtils.isEmpty(bean.getRemark())) {
                    requestAddDishEntity.remark = bean.getRemark();
                }
                mAddDishPresenter.requestAddDish(requestAddDishEntity);

            }
        });
    }

    /**
     * 给弹出的弹窗传入数据
     *
     * @param dishesEntity
     * @return
     */
    @NonNull
    private DataBean setDialogData(DishesEntity dishesEntity) {
        DataBean dataBean = new DataBean();
        dataBean.setDishName(dishesEntity.dishName);
        dataBean.setPrice(dishesEntity.getPrice());
        if (dishesEntity.labels.size() > 0 && dishesEntity.labels != null) {
            dataBean.setLabels(dishesEntity.labels);
        }
        if (dishesEntity.practices.size() > 0 && dishesEntity.practices != null) {
            dataBean.setPractices(dishesEntity.practices);
        }
        if (dishesEntity.remarks.size() > 0 && dishesEntity.remarks != null) {
            dataBean.setRemarks(dishesEntity.remarks);
        }
        if (dishesEntity.standards.size() > 0 && dishesEntity.standards != null) {
            dataBean.setStandards(dishesEntity.standards);
        }
        if (dishesEntity.tastes.size() > 0 && dishesEntity.tastes != null) {
            dataBean.setTastes(dishesEntity.tastes);
        }
        return dataBean;
    }

    /**
     * 设置菜品列表的多选效果，实际位置要在原來的基础上减掉头部占的位置
     *
     * @param startView
     * @param position
     */
    private void orderSuccessful(View startView, int position) {
        DishesEntity dishesEntity = mAdapter.getDataList().get(mPosition);
        dishesEntity.isCheck = true;
        mAdapter.notifyItemChanged(mPosition);
        //刷新角标数量
        mRightNavigationView.refreshCount(dishesEntity.dishTypeRelateId, true);
        //刷新右侧标题对应的位置
        refreshPos = getRefreshPosById(dishesEntity.dishTypeRelateId);
        int startPos = mRightListView.getFirstVisiblePosition();
        int endPos = mRightListView.getLastVisiblePosition();
        if (refreshPos >= startPos && refreshPos <= endPos) {
            AnimationUtil.doFlyAnimation(mContext, layoutRoot, mRightListView, startView, refreshPos, startPos);
        } else {
            return;
        }
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
        return true;
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
     * 设置已点菜的选中状态
     *
     * @param dishDataEntity
     */
    private void setDishesCheckStatus(DishDataEntity dishDataEntity) {
        for (int i = 0; i < mOrderedDishes.size(); i++) {
            //遍历已点菜的id
            String dishRelateId = mOrderedDishes.get(i).getDishRelateId();
            for (int j = 0; j < dishDataEntity.dishTypes.size(); j++) {
                List<DishesEntity> dishes = dishDataEntity.dishTypes.get(j).dishes;
                if (dishes != null && dishes.size() > 0) {
                    for (int k = 0; k < dishes.size(); k++) {
                        //所有菜品的唯一标识
                        String relateId = dishes.get(k).relateId;
                        if (dishRelateId.equals(relateId)) {
                            dishes.get(k).isCheck = true;
                            continue;
                        }
                    }
                }

            }
        }
    }

    /**
     * 给右侧菜类设置数据
     */
    private void setRightDishTypeData() {
        List<DishTypesEntity> dishTypes = mDishDataBean.dishTypes;
        //菜类唯一标识：relateId ,匹配菜类，刷新角标数量
        if (mDishTypeList.size() > 0) {
            for (int i = 0; i < mDishTypeList.size(); i++) {
                String dishClassId = mDishTypeList.get(i).getDishClassId();
                for (int j = 0; j < dishTypes.size(); j++) {
                    String relateId = dishTypes.get(j).relateId;
                    if (dishClassId.equals(relateId)) {
                        dishTypes.get(j).count = Integer.parseInt(mDishTypeList.get(i).getQuantity());
                        break;
                    }
                }
            }
        }
        //给右侧菜类设置数据
        mRightNavigationView.setData(mDishDataBean.dishTypes);
    }

    /**
     * 获取所有菜品菜类
     *
     * @param dishDataEntity
     */
    @Override
    public void getAllDishes(DishDataEntity dishDataEntity) {
        this.mDishDataBean = dishDataEntity;
        if (mDishDataBean != null) {
            //设置已点菜的选中状态
            setDishesCheckStatus(dishDataEntity);
            //给右侧菜类设置数据
            setRightDishTypeData();
            //给菜品设置默认数据
            mAdapter.update(mDishDataBean.dishTypes.get(1).dishes);
            mRightNavigationView.getRightListView().getRLAdapter().setSelectItem(1);
            mRightNavigationView.getRightListView().getRLAdapter().notifyDataSetChanged();
        }

    }

    /**
     * 点菜
     *
     * @param result
     */
    @Override
    public void requestAddDish(String result) {
        CommonUtils.makeEventToast(mContext, "点菜成功", false);
        orderSuccessful(startView, mPosition);
    }

}
