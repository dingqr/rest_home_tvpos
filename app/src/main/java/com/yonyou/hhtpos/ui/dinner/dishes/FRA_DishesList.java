package com.yonyou.hhtpos.ui.dinner.dishes;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_DishesList;
import com.yonyou.hhtpos.bean.dish.DishListEntity;
import com.yonyou.hhtpos.dialog.DIA_AutoDismiss;
import com.yonyou.hhtpos.dialog.DIA_DoubleConfirm;
import com.yonyou.hhtpos.dialog.DIA_SwitchTable;
import com.yonyou.hhtpos.global.DishConstants;
import com.yonyou.hhtpos.global.ReceiveConstants;
import com.yonyou.hhtpos.popup.POP_DishesEdit;
import com.yonyou.hhtpos.popup.POP_DishesPlaceOrderEdit;
import com.yonyou.hhtpos.presenter.IDishEditPresenter;
import com.yonyou.hhtpos.presenter.IDishListPresenter;
import com.yonyou.hhtpos.presenter.Impl.DishEditPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.DishListPresenterImpl;
import com.yonyou.hhtpos.view.IDishEditView;
import com.yonyou.hhtpos.view.IDishListView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 已点菜品列表
 * 作者：liushuofei on 2017/7/11 10:48
 */
public class FRA_DishesList extends BaseFragment implements IDishListView, IDishEditView, AdapterView.OnItemClickListener, POP_DishesEdit.OnEditListener, POP_DishesPlaceOrderEdit.OnPlaceEditListener, SwipeRefreshLayout.OnRefreshListener, DIA_SwitchTable.OnConfirmListener {

    @Bind(R.id.lv_dishes)
    ListView mListView;
    @Bind(R.id.tv_total_price)
    TextView tvTotalPrice;
    @Bind(R.id.tv_place_order)
    TextView tvPlaceOrder;
    @Bind(R.id.srl_dishes)
    SwipeRefreshLayout mDishesSwipeRefresh;

    private ADA_DishesList mAdapter;
    private DishListEntity.Dishes currentBean;
    private String dishId;
    private int quantity;

    /**中间者 */
    private IDishListPresenter mDishListPresenter;
    private IDishEditPresenter mDishEditPresenter;

    /**右侧操作栏弹窗 */
    private POP_DishesEdit editPopup;
    private POP_DishesPlaceOrderEdit placeOrderEditPopup;

    private String shopId = "C13352966C000000A60000000016E000";

    /**未下单菜品的id列表 */
    private String dishIds = "";
    /**总价格 */
    private double totalPrice;
    /**账单id */
    private String tableBillId;
    /**是否为右侧传递过来的 */
    private boolean isRightRefresh;

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
        return mDishesSwipeRefresh;
    }

    @Override
    protected void initViewsAndEvents() {
        mAdapter = new ADA_DishesList(mContext);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mDishListPresenter = new DishListPresenterImpl(mContext, this);
        mDishEditPresenter = new DishEditPresenterImpl(mContext, this);

        // 刷新操作
        mDishesSwipeRefresh.setOnRefreshListener(this);
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
        return true;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    public void requestDishList(DishListEntity bean) {
        //reset state
        mDishesSwipeRefresh.setRefreshing(false);

        if (null == bean)
            return;

        List<DishListEntity.Dishes> dataList = bean.getDishes();

        if (null == dataList || dataList.size() == 0) {
            // 无数据页面
            showEmpty(R.drawable.default_no_dishes, mContext.getString(R.string.dishes_no_data));
            // 发送一个空对象
             EventBus.getDefault().post(new DishListEntity());
            // 重置价格
            tvTotalPrice.setText("0.00");
        } else {
            // 设置未下单菜品id列表
            setDishIds(dataList);
            // 设置总价格
            setDishTotalPrice(dataList);
            //将右侧菜类的角标数量数据传递到右侧页面
            if (!isRightRefresh){
                EventBus.getDefault().post(bean);
            }

            mAdapter.update(dataList, true);
        }
    }

    /**
     * 设置未下单菜品id列表
     * @param dataList 菜品列表
     * @return
     */
    private void setDishIds(List<DishListEntity.Dishes> dataList){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < dataList.size(); i++){
            DishListEntity.Dishes bean = dataList.get(i);
            if (TextUtils.isEmpty(bean.getOrderTime())){
                if (i > 0){
                    stringBuffer.append(",");
                }
                stringBuffer.append(bean.getId());
            }
        }

        dishIds = stringBuffer.toString();
    }

    /**
     * 设置总价格
     * @param dataList
     */
    private void setDishTotalPrice(List<DishListEntity.Dishes> dataList){
        totalPrice = 0.00;
        for (int i = 0; i < dataList.size(); i++){
            DishListEntity.Dishes bean = dataList.get(i);
            if (TextUtils.isEmpty(bean.getOrderTime())){
                totalPrice+= Double.parseDouble(bean.getDishPrice()) * Double.parseDouble(bean.getQuantity());
            }
        }
        tvTotalPrice.setText(StringUtil.getFormattedMoney(totalPrice + ""));
    }

    @Override
    public void requestPlaceOrder() {
        // 有下单操作
        ((ACT_OrderDishes)getActivity()).setHasPlaceOrder(true);

        // Toast提示
        DIA_AutoDismiss dia_autoDismiss = new DIA_AutoDismiss(mContext, mContext.getString(R.string.tip_place_order_success));
        dia_autoDismiss.show();

        isRightRefresh = false;
        mDishListPresenter.requestDishList(tableBillId, false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        currentBean = (DishListEntity.Dishes) parent.getAdapter().getItem(position);
        dishId = currentBean.getId();
        quantity = (int)Double.parseDouble(currentBean.getQuantity());

        // 未下单操作弹窗
        if (TextUtils.isEmpty(currentBean.getOrderTime())){
            editPopup = new POP_DishesEdit(mContext, this, currentBean.getDishStatus());
            editPopup.showAsDropDown(v, v.getWidth() + 8, -(v.getHeight() + 4));
        } else {
            placeOrderEditPopup = new POP_DishesPlaceOrderEdit(mContext, this, currentBean);
            placeOrderEditPopup.showAsDropDown(v, v.getWidth() + 8, -(v.getHeight() + 4));
        }
    }

    /**
     * 修改数量回调
     *
     * @param isAdd
     */
    @Override
    public void updateCount(boolean isAdd) {
        if (isAdd) {
            quantity++;
        } else {
            quantity--;
        }

        // 如果减到0，就删除菜品
        if (quantity == 0) {
            deleteDish();
        } else {
            mDishEditPresenter.updateQuantity("", dishId, String.valueOf(quantity), shopId);
        }
    }

    /**
     * 修改菜品回调
     */
    @Override
    public void updateDish() {

    }

    /**
     * 修改菜品状态回调
     *
     * @param status
     */
    @Override
    public void updateDishStatus(String status) {
        mDishEditPresenter.updateDishStatus("", status, dishId, shopId);
    }

    /**
     * 退菜和赠菜回调
     * @param mode
     */
    @Override
    public void specialHandleDish(String mode) {
        if (null != placeOrderEditPopup){
            placeOrderEditPopup.dismiss();
        }

        // 取消赠送
        if (mode.equals(DishConstants.SERVE_DISH) && hasGift()){
            DIA_DoubleConfirm dia_doubleConfirm = new DIA_DoubleConfirm(mContext, mContext.getString(R.string.tip_cancel_gift_dish), new DIA_DoubleConfirm.OnSelectedListener() {
                @Override
                public void confirm() {
                    mDishEditPresenter.cancelGiftDish(currentBean.getId(), shopId, "");
                }
            });
            dia_doubleConfirm.getDialog().show();
        }else {
            DIA_SwitchTable dia_switchTable = new DIA_SwitchTable(mContext, currentBean, mode, this);
            dia_switchTable.show();
        }
    }

    /**
     * 删除菜品回调
     */
    @Override
    public void deleteDish() {
        DIA_DoubleConfirm dia_doubleConfirm = new DIA_DoubleConfirm(mContext, mContext.getString(R.string.tip_delete_dish), new DIA_DoubleConfirm.OnSelectedListener() {
            @Override
            public void confirm() {
                mDishEditPresenter.deleteDish("", dishId, shopId);
            }
        });
        dia_doubleConfirm.getDialog().show();
    }

    @Override
    public void updateQuantitySuccess() {
        // Toast提示
        DIA_AutoDismiss dia_autoDismiss = new DIA_AutoDismiss(mContext, mContext.getString(R.string.tip_update_count_success));
        dia_autoDismiss.show();

        isRightRefresh = false;
        mDishListPresenter.requestDishList(tableBillId, false);
    }

    @Override
    public void updateDishSuccess() {
        // Toast提示
        DIA_AutoDismiss dia_autoDismiss = new DIA_AutoDismiss(mContext, mContext.getString(R.string.tip_update_dish_success));
        dia_autoDismiss.show();

        isRightRefresh = false;
        mDishListPresenter.requestDishList(tableBillId, false);
    }

    @Override
    public void deleteDishSuccess() {
        if (null != editPopup) {
            editPopup.dismiss();
        }

        // Toast提示
        DIA_AutoDismiss dia_autoDismiss = new DIA_AutoDismiss(mContext, mContext.getString(R.string.tip_delete_dish_success));
        dia_autoDismiss.show();

        isRightRefresh = false;
        mDishListPresenter.requestDishList(tableBillId, false);
    }

    @Override
    public void updateDishStatusSuccess() {
        // Toast提示
        DIA_AutoDismiss dia_autoDismiss = new DIA_AutoDismiss(mContext, mContext.getString(R.string.tip_update_dish_status_success));
        dia_autoDismiss.show();

        isRightRefresh = false;
        mDishListPresenter.requestDishList(tableBillId, false);
    }

    @Override
    public void handleDishSuccess() {
        // Toast提示
        DIA_AutoDismiss dia_autoDismiss = new DIA_AutoDismiss(mContext, mContext.getString(R.string.tip_return_serve_dish_success));
        dia_autoDismiss.show();

        isRightRefresh = false;
        mDishListPresenter.requestDishList(tableBillId, false);
    }

    @Override
    public void confirmWeightSuccess() {
        // Toast提示
        DIA_AutoDismiss dia_autoDismiss = new DIA_AutoDismiss(mContext, mContext.getString(R.string.tip_confirm_weight_success));
        dia_autoDismiss.show();

        isRightRefresh = false;
        mDishListPresenter.requestDishList(tableBillId, false);
    }

    @Override
    public void switchTableSuccess() {
        // Toast提示
        DIA_AutoDismiss dia_autoDismiss = new DIA_AutoDismiss(mContext, mContext.getString(R.string.tip_switch_table_success));
        dia_autoDismiss.show();

        isRightRefresh = false;
        mDishListPresenter.requestDishList(tableBillId, false);
    }

    @Override
    public void cancelGiftDishesSuccess() {
        // Toast提示
        DIA_AutoDismiss dia_autoDismiss = new DIA_AutoDismiss(mContext, mContext.getString(R.string.tip_cancel_gift_dishes_success));
        dia_autoDismiss.show();

        isRightRefresh = false;
        mDishListPresenter.requestDishList(tableBillId, false);
    }

    @OnClick({R.id.tv_total_price, R.id.tv_place_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_total_price:

                break;
            case R.id.tv_place_order:
                if (!TextUtils.isEmpty(dishIds)){
                    mDishListPresenter.requestPlaceOrder(dishIds);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        mDishListPresenter.requestDishList(tableBillId, false);
    }

    @Override
    public void onConfirm(String mode, String count) {
        // 退菜或赠菜
        if (mode.equals(DishConstants.RETURN_DISH) || mode.equals(DishConstants.SERVE_DISH)){
            mDishEditPresenter.specialHandleDish(mode, currentBean.getId(), shopId, count);
        }
        // 称重确认
        else if (mode.equals(DishConstants.DISH_WEIGHT)){
            mDishEditPresenter.confirmWeightDish(currentBean.getId(), count, shopId);
        }
        // 转台
        else if (mode.equals(DishConstants.DISH_TURN)){
            // TODO:tableBillId
            mDishEditPresenter.switchTable(currentBean.getId(), count, shopId, "C5BA09D3380000008800000000257000");
        }
    }

    /**
     * 有赠送记录
     * @return
     */
    private boolean hasGift(){
        if (null != currentBean){
            List<DishListEntity.Dishes.Abnormal> abnormalList = currentBean.getAbnormalList();
            if (null != abnormalList && abnormalList.size() > 0){
                for (int i = 0; i < abnormalList.size(); i++){
                    DishListEntity.Dishes.Abnormal bean = abnormalList.get(i);
                    if (!TextUtils.isEmpty(bean.getDishAbnormalStatus()) && bean.getDishAbnormalStatus().equals(DishConstants.SERVE_DISH)){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 账单id
     * @param tableBillId
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onReceiveTableBillId(String tableBillId) {
        this.tableBillId = tableBillId;
        mDishListPresenter.requestDishList(tableBillId, true);
    }

    @Override
    protected void onReceiveBroadcast(int intent, Bundle bundle) {
        if (intent == ReceiveConstants.REFRESH_LEFT_DISHES){
            isRightRefresh = true;
            mDishListPresenter.requestDishList(tableBillId, false);
        }
    }
}
