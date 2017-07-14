package com.yonyou.hhtpos.ui.dinner.dishes;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.yonyou.hhtpos.util.NavigationUtil;
import com.yonyou.hhtpos.widgets.RightListView;
import com.yonyou.hhtpos.widgets.RightNavigationView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;


/**
 * Created by zj on 2017/7/11.
 * 邮箱：zjuan@yonyou.com
 * 描述：点菜页面-右侧导航及菜品列表
 */
public class FRA_OrderDishes extends BaseFragment {
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

    //动画
    private PathMeasure mPathMeasure;
    private int mRefreshPosition = -1;
    private int refreshPos;
    private RightListView mRightListView;
    //贝塞尔曲线中间过程的点的坐标
    private float[] mCurrentPosition = new float[2];
    private boolean showAnimation;
    public View startView;
    private RelativeLayout rl_badge;
    private TextView tv_count;

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
        mRightListView = mRightNavigationView.getRightListView();
//        //设置刷新时动画的颜色，可以设置4个
//        if (mSwiperefreshLayout != null) {
//            mSwiperefreshLayout.setProgressViewOffset(false, 0, DP2PX.dip2px(mContext, 30));
//            mSwiperefreshLayout.setColorSchemeColors(
//                    getResources().getColor(R.color.gplus_color_1),
//                    getResources().getColor(R.color.gplus_color_2),
//                    getResources().getColor(R.color.gplus_color_3),
//                    getResources().getColor(R.color.gplus_color_4));
//            mSwiperefreshLayout.setOnRefreshListener(this);
//        }
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
        //loadmore
//        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
////                loadMore();
//            }
//        });
        //设置底部加载颜色-
        mRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.dark, R.color.color_dcdcdc);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint(mContext.getResources().getString(R.string.loading_note), mContext.getResources().getString(R.string.no_more_note), "");

        mRecyclerView.setHasFixedSize(true);
        //去除loadmore
        mRecyclerView.setLoadMoreEnabled(false);

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
                    doFlyAnimation(iv_start, refreshPos, startPos);
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
                doFlyAnimation(startView, refreshPos, firstVisiblePosition);

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


    /**
     * 飞入的动画效果
     *
     * @param startView
     * @param refreshPos
     */
    private void doFlyAnimation(View startView, int refreshPos, int firstVisiblePosition) {
//      一、创造出执行动画的主题---imageview
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        final ImageView goods = new ImageView(mContext);
        goods.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_fly_anim));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(30, 30);
        layoutRoot.addView(goods, params);

//        二、计算动画开始/结束点的坐标的准备工作
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        layoutRoot.getLocationInWindow(parentLocation);

        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        startView.getLocationInWindow(startLoc);

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];


        /**
         * 动画结束位置
         */
        //根据右侧角标刷新的位置，拿到对应的ListView的itemView，作为动画的结束view
        View endView = mRightListView.getChildAt(refreshPos - firstVisiblePosition);
        LinearLayout view = (LinearLayout) endView;
        //获取结束位置的角标View对象rl_badge
        rl_badge = (RelativeLayout) view.findViewById(R.id.rl_badge);

        if (endView == null) {
//            Log.e("TAG", "endView==null");
            layoutRoot.removeView(goods);
            return;
        }
        endView.getLocationInWindow(endLoc);

//        三、正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + startView.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + startView.getHeight() / 2;

        //商品掉落后的终点坐标：起始点-父布局起始点+终点图片的1/5
        float toX = endLoc[0] - parentLocation[0] + endView.getWidth() / 5 + 80;
        float toY = endLoc[1] - parentLocation[1] + 10;

//        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        //属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(500);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                //获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
//      五、 开始执行动画
        valueAnimator.start();

//      六、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                // 购物车的数量加1
//                i++;
//                count.setText(String.valueOf(i));
                // 把移动的图片imageview从父布局里移除
                layoutRoot.removeView(goods);
                ObjectAnimator.ofFloat(rl_badge, "translationY", 0, 8, -2, 0).setDuration(400).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
