package com.yonyou.hhtpos.ui.dinner.dishes;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_DishesList;
import com.yonyou.hhtpos.bean.dish.DishListEntity;
import com.yonyou.hhtpos.popup.POP_DishesEdit;
import com.yonyou.hhtpos.presenter.IDishEditPresenter;
import com.yonyou.hhtpos.presenter.IDishListPresenter;
import com.yonyou.hhtpos.presenter.Impl.DishEditPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.DishListPresenterImpl;
import com.yonyou.hhtpos.view.IDishEditView;
import com.yonyou.hhtpos.view.IDishListView;

import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * 已点菜品列表
 * 作者：liushuofei on 2017/7/11 10:48
 */
public class FRA_DishesList extends BaseFragment implements IDishListView, IDishEditView, AdapterView.OnItemClickListener, POP_DishesEdit.OnEditListener {

    @Bind(R.id.lv_dishes)
    ListView mListView;

    private ADA_DishesList mAdapter;
    private DishListEntity.Dishes currentBean;
    private String dishId;
    private int quantity;

    /**中间者 */
    private IDishListPresenter mDishListPresenter;
    private IDishEditPresenter mDishEditPresenter;

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
        return mListView;
    }

    @Override
    protected void initViewsAndEvents() {
        mAdapter = new ADA_DishesList(mContext);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mDishListPresenter = new DishListPresenterImpl(mContext, this);
        mDishListPresenter.requestDishList("C50242AC980000009200000000257000", true);

        mDishEditPresenter = new DishEditPresenterImpl(mContext, this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_dishes_list;
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
    public void requestDishList(DishListEntity bean) {
        if (null == bean)
            return;

        List<DishListEntity.Dishes> dataList = bean.getDishes();

        if (null == dataList || dataList.size() == 0){
            // 无数据页面
            showEmpty(R.drawable.default_no_dishes, mContext.getString(R.string.dishes_no_data));
        }else {
            mAdapter.update(dataList, true);
            //将右侧菜类的角标数量数据传递到右侧页面
            EventBus.getDefault().post(bean.getDishTypelist());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        currentBean = (DishListEntity.Dishes)parent.getAdapter().getItem(position);
        dishId = currentBean.getId();
        quantity = currentBean.getQuantity();

        POP_DishesEdit popup = new POP_DishesEdit(mContext, this);
        popup.showAsDropDown(v, v.getWidth() + 8, -(v.getHeight() + 4));
    }

    /**
     * 修改数量回调
     * @param isAdd
     */
    @Override
    public void updateCount(boolean isAdd) {
        if (isAdd){
            quantity++;
        }else {
            quantity--;
        }
        mDishEditPresenter.updateQuantity("", dishId, String.valueOf(quantity), shopId);
    }

    /**
     * 修改菜品回调
     */
    @Override
    public void updateDish() {

    }

    /**
     * 修改菜品状态回调
     * @param status
     */
    @Override
    public void updateDishStatus(String status) {
        mDishEditPresenter.updateDishStatus("", status, dishId, shopId);
    }

    /**
     * 删除菜品回调
     */
    @Override
    public void deleteDish() {
        mDishEditPresenter.deleteDish("", dishId, shopId);
    }

    @Override
    public void updateQuantitySuccess() {
        CommonUtils.makeEventToast(mContext, "修改数量成功", false);
        mDishListPresenter.requestDishList("C50242AC980000009200000000257000", false);
    }

    @Override
    public void updateDishSuccess() {
        CommonUtils.makeEventToast(mContext, "修改菜品成功", false);
        mDishListPresenter.requestDishList("C50242AC980000009200000000257000", false);
    }

    @Override
    public void deleteDishSuccess() {
        CommonUtils.makeEventToast(mContext, "删除成功", false);
        mDishListPresenter.requestDishList("C50242AC980000009200000000257000", false);
    }

    @Override
    public void updateDishStatusSuccess() {
        CommonUtils.makeEventToast(mContext, "修改菜品状态成功", false);
        mDishListPresenter.requestDishList("C50242AC980000009200000000257000", false);
    }
}
