package com.yonyou.hhtpos.ui.dinner.dishes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.log.Elog;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_DishesList;
import com.yonyou.hhtpos.bean.dish.DataBean;
import com.yonyou.hhtpos.bean.dish.DishCallBackEntity;
import com.yonyou.hhtpos.bean.dish.DishDataEntity;
import com.yonyou.hhtpos.bean.dish.DishListEntity;
import com.yonyou.hhtpos.bean.dish.DishPracticeEntity;
import com.yonyou.hhtpos.bean.dish.DishRemarkEntity;
import com.yonyou.hhtpos.bean.dish.DishStandardEntity;
import com.yonyou.hhtpos.bean.dish.DishTypesEntity;
import com.yonyou.hhtpos.bean.dish.DishesEntity;
import com.yonyou.hhtpos.bean.dish.RequestAddDishEntity;
import com.yonyou.hhtpos.bean.dish.RequestEditDishEntity;
import com.yonyou.hhtpos.dialog.DIA_AutoDismiss;
import com.yonyou.hhtpos.dialog.DIA_DoubleConfirm;
import com.yonyou.hhtpos.dialog.DIA_OrderDishCount;
import com.yonyou.hhtpos.dialog.DIA_OrderDishNorms;
import com.yonyou.hhtpos.dialog.DIA_OrderDishSetPrice;
import com.yonyou.hhtpos.dialog.DIA_OrderDishSetWeight;
import com.yonyou.hhtpos.dialog.DIA_OrderDishWeight;
import com.yonyou.hhtpos.dialog.DIA_SwitchTable;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.global.DishConstants;
import com.yonyou.hhtpos.global.DishTypeConstants;
import com.yonyou.hhtpos.global.ReceiveConstants;
import com.yonyou.hhtpos.interfaces.DishDataCallback;
import com.yonyou.hhtpos.popup.POP_DishesEdit;
import com.yonyou.hhtpos.popup.POP_DishesPlaceOrderEdit;
import com.yonyou.hhtpos.presenter.IDishEditPresenter;
import com.yonyou.hhtpos.presenter.IDishListPresenter;
import com.yonyou.hhtpos.presenter.Impl.DishEditPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.DishListPresenterImpl;
import com.yonyou.hhtpos.ui.dinner.check.ACT_CheckOut;
import com.yonyou.hhtpos.view.IDishEditView;
import com.yonyou.hhtpos.view.IDishListView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import static com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes.FROM_WHERE;
import static com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes.TABLE_BILL_ID;

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
    @Bind(R.id.tv_order_status)
    TextView tvOrderStatus;

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

    private String shopId = API.shopId;

    /**未下单菜品的id列表 */
    private String dishIds = "";
    /**总价格 */
    private double totalPrice;
    /**账单id */
    private String tableBillId;
    /**是否为右侧传递过来的 */
    private boolean isRightRefresh;
    /**修改菜品实体类 */
    private RequestAddDishEntity requestAddDishEntity;
    private RequestEditDishEntity requestEditDishEntity;
    /**菜品数据 */
    private DishDataEntity dishDataEntity;

    private DIA_OrderDishSetPrice mDiaCurrentDishWeight;//称重、时价
    private DIA_OrderDishSetWeight mDiaWeightNormal;//称重，无备注
    private DIA_OrderDishWeight mDiaWeightRemarks;//称重,有备注
    private DIA_OrderDishNorms mDiaStandards;//规格
    private DIA_OrderDishCount mDiaNormal;//normal
    private int saleManner;

    /**
     * 账单id
     *
     * @param tableBillId
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onReceiveTableBillId(String tableBillId) {
        this.tableBillId = tableBillId;
        mDishListPresenter.requestDishList(tableBillId, true);
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

        //点菜时的弹窗
        //时价、重量弹窗
        mDiaCurrentDishWeight = new DIA_OrderDishSetPrice(mContext);//有时价、重量
        mDiaStandards = new DIA_OrderDishNorms(mContext);//规格的弹窗
        //称重、有备注列表
        mDiaWeightRemarks = new DIA_OrderDishWeight(mContext);
        //称重、无备注列表
        mDiaWeightNormal = new DIA_OrderDishSetWeight(mContext);
        mDiaNormal = new DIA_OrderDishCount(mContext);//有做法，备注，数量的弹窗 -normal

        //1：堂食  2：外卖  3：外带
        ACT_OrderDishes activity = (ACT_OrderDishes) getActivity();
        saleManner = activity.getFromWhere();

        setUpListenerForDialog();
    }

    private void setUpListenerForDialog(){
        mDiaCurrentDishWeight.setDishDataCallback(new DishDataCallback() {
            @Override
            public void sendItems(DishCallBackEntity bean) {
                Elog.e("TAG", "时价、称重=" + bean.toString());
                //重量
                requestAddDishEntity.quantity = StringUtil.getString(bean.getDishWeight());
                //时价
                requestAddDishEntity.setDishPrice(bean.getDishPrice());
                //做法
                if (!TextUtils.isEmpty(bean.getListShowPractice()) && !TextUtils.isEmpty(bean.getPractices())) {
                    requestAddDishEntity.practices = bean.getPractices();
                    requestAddDishEntity.listShowPractice = bean.getListShowPractice();
                }
                //备注：手填
                if (!TextUtils.isEmpty(bean.getRemark())) {
                    requestAddDishEntity.remark = bean.getRemark();
                }
                //mAddDishPresenter.requestAddDish(requestAddDishEntity);
            }
        });
        mDiaStandards.setDishDataCallback(new DishDataCallback() {
            @Override
            public void sendItems(DishCallBackEntity bean) {
                Elog.e("TAG", "规格=" + bean.toString());
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
                //mAddDishPresenter.requestAddDish(requestAddDishEntity);
            }
        });
        mDiaWeightRemarks.setDishDataCallback(new DishDataCallback() {
            @Override
            public void sendItems(DishCallBackEntity bean) {
                Elog.e("TAG", "称重、有备注列表=" + bean.toString());
                //重量
                requestAddDishEntity.quantity = StringUtil.getString(bean.getDishWeight());
                //做法
                if (!TextUtils.isEmpty(bean.getListShowPractice()) && !TextUtils.isEmpty(bean.getPractices())) {
                    requestAddDishEntity.practices = bean.getPractices();
                    requestAddDishEntity.listShowPractice = bean.getListShowPractice();
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

                RequestEditDishEntity currentBean = new RequestEditDishEntity();
                currentBean.setShopId(API.shopId);

                // 修改菜品
                // mDishEditPresenter.updateDish(new RequestEditDishEntity());
            }
        });
        mDiaWeightNormal.setDishDataCallback(new DishDataCallback() {
            @Override
            public void sendItems(DishCallBackEntity bean) {
                Elog.e("TAG", "称重、无备注列表=" + bean.toString());
                //重量
                requestAddDishEntity.quantity = StringUtil.getString(bean.getDishWeight());
                //做法
                if (!TextUtils.isEmpty(bean.getListShowPractice()) && !TextUtils.isEmpty(bean.getPractices())) {
                    requestAddDishEntity.practices = bean.getPractices();
                    requestAddDishEntity.listShowPractice = bean.getListShowPractice();
                }
                //备注：手填
                if (!TextUtils.isEmpty(bean.getRemark())) {
                    requestAddDishEntity.remark = bean.getRemark();
                }
                //mAddDishPresenter.requestAddDish(requestAddDishEntity);
            }
        });
        mDiaNormal.setDishDataCallback(new DishDataCallback() {
            @Override
            public void sendItems(DishCallBackEntity bean) {
                Elog.e("TAG", "数量、做法、备注列表、备注：手填=" + bean.toString());
                RequestEditDishEntity requestEditDishEntity = new RequestEditDishEntity();
                requestEditDishEntity.setShopId(API.shopId);
                requestEditDishEntity.setDishType(DishTypeConstants.TYPE_DISH);
                requestEditDishEntity.setId(currentBean.getId());

                //数量
                requestEditDishEntity.setQuantity(StringUtil.getString(bean.getDishCount()));
                //做法
                if (!TextUtils.isEmpty(bean.getListShowPractice()) && !TextUtils.isEmpty(bean.getPractices())) {
                    requestEditDishEntity.setPractices(bean.getPractices());
                }
                //备注：列表
                if (!TextUtils.isEmpty(bean.getListShowRemark()) && !TextUtils.isEmpty(bean.getRemarks())) {
                    requestEditDishEntity.setRemarks(bean.getRemarks());
                }
                //备注：手填
                if (!TextUtils.isEmpty(bean.getRemark())) {
                    requestEditDishEntity.setRemark(bean.getRemark());
                }
                mDishEditPresenter.updateDish(requestEditDishEntity);
            }
        });
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
            if (hasNotOrderDishes(dataList)){
                setNotOrderTotalPrice(dataList);
            }else {
                setOrderedTotalPrice(dataList);
            }
            //将右侧菜类的角标数量数据传递到右侧页面
            if (!isRightRefresh) {
                EventBus.getDefault().post(bean);
            }

            mAdapter.update(dataList, true);
        }
    }

    /**
     * 有未下单菜品
     * @param dataList
     * @return
     */
    private boolean hasNotOrderDishes(List<DishListEntity.Dishes> dataList){
        for (int i = 0; i < dataList.size(); i++){
            DishListEntity.Dishes bean = dataList.get(i);
            if (null != bean && TextUtils.isEmpty(bean.getOrderTime())){
                return true;
            }
        }
        return false;
    }

    /**
     * 设置未下单菜品id列表
     *
     * @param dataList 菜品列表
     * @return
     */
    private void setDishIds(List<DishListEntity.Dishes> dataList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < dataList.size(); i++) {
            DishListEntity.Dishes bean = dataList.get(i);
            if (TextUtils.isEmpty(bean.getOrderTime())) {
                if (i > 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append(bean.getId());
            }
        }

        dishIds = stringBuffer.toString();
    }

    /**
     * 设置未下单总价格
     *
     * @param dataList
     */
    private void setNotOrderTotalPrice(List<DishListEntity.Dishes> dataList) {
        totalPrice = 0.00;
        for (int i = 0; i < dataList.size(); i++) {
            DishListEntity.Dishes bean = dataList.get(i);
            if (TextUtils.isEmpty(bean.getOrderTime())) {
                totalPrice += Double.parseDouble(bean.getDishPrice()) * Double.parseDouble(bean.getQuantity());
            }
        }
        // 未下单
        tvOrderStatus.setText(mContext.getString(R.string.string_not_order));
        // 价格
        tvTotalPrice.setText(StringUtil.getFormattedMoney(totalPrice + ""));
        // 下单
        tvPlaceOrder.setText(mContext.getString(R.string.dishes_order));
    }

    /**
     * 设置下单总价格
     *
     * @param dataList
     */
    private void setOrderedTotalPrice(List<DishListEntity.Dishes> dataList) {
        totalPrice = 0.00;
        for (int i = 0; i < dataList.size(); i++) {
            DishListEntity.Dishes bean = dataList.get(i);
            if (!TextUtils.isEmpty(bean.getOrderTime())) {
                totalPrice += Double.parseDouble(bean.getDishPrice()) * Double.parseDouble(bean.getQuantity());
            }
        }
        // 已下单
        tvOrderStatus.setText(mContext.getString(R.string.string_ordered));
        // 价格
        tvTotalPrice.setText(StringUtil.getFormattedMoney(totalPrice + ""));
        // 去结账
        tvPlaceOrder.setText(mContext.getResources().getString(R.string.check_out));
    }

    @Override
    public void requestPlaceOrder() {
        // 有下单操作
        ((ACT_OrderDishes) getActivity()).setHasPlaceOrder(true);

        // Toast提示
        DIA_AutoDismiss dia_autoDismiss = new DIA_AutoDismiss(mContext, mContext.getString(R.string.tip_place_order_success));
        dia_autoDismiss.show();

        mDishListPresenter.requestDishList(tableBillId, false);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        currentBean = (DishListEntity.Dishes) parent.getAdapter().getItem(position);
        dishId = currentBean.getId();
        quantity = (int) Double.parseDouble(currentBean.getQuantity());

        // 未下单操作弹窗
        if (TextUtils.isEmpty(currentBean.getOrderTime())) {
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
            mDishEditPresenter.updateQuantity("", dishId, String.valueOf(quantity), API.shopId, String.valueOf(currentBean.getUnit()));
        }
    }

    /**
     * 修改菜品回调
     */
    @Override
    public void updateDish() {
        // 隐藏操作栏
        if (null != editPopup) {
            editPopup.dismiss();
        }

        DishesEntity dishesEntity = getDishesEntity();
        if (null == dishesEntity)
            return;

        requestAddDishEntity = new RequestAddDishEntity();
        //requestAddDishEntity.companyId = currentBean.companyId;
        requestAddDishEntity.dishClassid = currentBean.getDishClassId();
        requestAddDishEntity.dishName = currentBean.getDishName();
        requestAddDishEntity.dishId = currentBean.getId();
        requestAddDishEntity.setDishPrice(currentBean.getDishPrice());
        requestAddDishEntity.dishType = currentBean.getDishType();
        requestAddDishEntity.shopId = API.shopId;
        requestAddDishEntity.unit = currentBean.getUnit();

        requestAddDishEntity.dishRelateId = currentBean.getDishRelateId();
        //不确定的字段
        requestAddDishEntity.dishStatus = String.valueOf(currentBean.getDishStatus());

        //缺少的字段
        //把所有已选做法名连接到一起的字符串，逗号分隔
        requestAddDishEntity.listShowPractice = "";
        //把所有已选和手填的备注名连接到一起的字符串，逗号分隔
        requestAddDishEntity.listShowRemark = "";
        requestAddDishEntity.practices = "";
        requestAddDishEntity.quantity = currentBean.getQuantity();
        requestAddDishEntity.remarks = "";
        requestAddDishEntity.remark = "";
        requestAddDishEntity.standardId = "";

        //写死的字段
        requestAddDishEntity.tableBillId = tableBillId;

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
        mDiaNormal.setData(dataBean).getDialog().show();
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
        // 菜品名称
        dataBean.setDishName(dishesEntity.dishName);
        // 价格
        dataBean.setPrice(dishesEntity.getPrice());
        // 标签
        if (dishesEntity.labels != null && dishesEntity.labels.size() > 0 ) {
            dataBean.setLabels(dishesEntity.labels);
        }
        // 0：不是称重菜  1：是称重菜
        if (currentBean.getUnit() == 0){
            // 数量
            dataBean.setQuantity((int)Double.parseDouble(currentBean.getQuantity()));
        }else {
            // 斤
            dataBean.setWeight(Double.parseDouble(currentBean.getQuantity()));
        }
        // 备注：手填
        dataBean.setRemark(StringUtil.getString(currentBean.getRemark()));
        // 做法（单选）
        if (dishesEntity.practices != null && dishesEntity.practices.size() > 0 ) {
            // 设置已选做法
            List<DishPracticeEntity> practices = dishesEntity.practices;
            for (int i = 0; i < practices.size(); i++){
                if (practices.get(i).relateId.equals(currentBean.getPractice())){
                    practices.get(i).isCheck = true;
                    break;
                }
            }
            dataBean.setPractices(practices);
        }
        // 备注（多选）
        if (dishesEntity.remarks != null && dishesEntity.remarks.size() > 0 ) {
            // 设置已选备注
            List<DishRemarkEntity> remarks = dishesEntity.remarks;
            List<String> remarkList = currentBean.getRemarks();
            if (null != remarkList && remarkList.size() > 0){
                for (int i = 0; i < remarkList.size(); i++){
                    String remarkId = remarkList.get(i);
                    for (int j = 0; j < remarks.size(); j++){
                        if (remarkId.equals(remarks.get(j).relateId)){
                            remarks.get(j).isCheck = true;
                            break;
                        }
                    }
                }
            }

            dataBean.setRemarks(remarks);
        }
        // 规格（单选）
        if (dishesEntity.standards != null && dishesEntity.standards.size() > 0 ) {
            // 设置已选规格
            List<DishStandardEntity> standards = dishesEntity.standards;
            for (int i = 0; i < standards.size(); i++){
                if (standards.get(i).relateId.equals(currentBean.getStandardId())){
                    standards.get(i).isCheck = true;
                }
            }
            dataBean.setStandards(standards);
        }
        // 口味
        if (dishesEntity.tastes != null && dishesEntity.tastes.size() > 0 ) {
            dataBean.setTastes(dishesEntity.tastes);
        }
        return dataBean;
    }

    /**
     * 匹配到当前菜品
     *
     * @return
     */
    private DishesEntity getDishesEntity() {
        String currentClassId = currentBean.getDishClassId();
        String currentDishId = currentBean.getDishRelateId();

        // 分类列表
        List<DishTypesEntity> dishTypes = dishDataEntity.dishTypes;
        if (null != dishTypes && dishTypes.size() > 0) {
            for (int i = 0; i < dishTypes.size(); i++) {
                DishTypesEntity dishTypesEntity = dishTypes.get(i);
                if (currentClassId.equals(dishTypesEntity.relateId)) {

                    // 菜品列表
                    List<DishesEntity> dishes = dishTypesEntity.dishes;

                    if (null != dishes && dishes.size() > 0) {
                        for (int j = 0; j < dishes.size(); j++) {
                            DishesEntity dishesEntity = dishes.get(j);

                            if (currentDishId.equals(dishesEntity.relateId)) {
                                return dishesEntity;
                            }
                        }
                    }

                }
            }
        }

        return null;
    }

    /**
     * 修改菜品状态回调
     *
     * @param status
     */
    @Override
    public void updateDishStatus(String status) {
        mDishEditPresenter.updateDishStatus("", status, dishId, API.shopId);
    }

    /**
     * 退菜和赠菜回调
     *
     * @param mode
     */
    @Override
    public void specialHandleDish(String mode) {
        if (null != placeOrderEditPopup) {
            placeOrderEditPopup.dismiss();
        }

        // 取消赠送
        if (mode.equals(DishConstants.SERVE_DISH) && hasGift()) {
            DIA_DoubleConfirm dia_doubleConfirm = new DIA_DoubleConfirm(mContext, mContext.getString(R.string.tip_cancel_gift_dish), new DIA_DoubleConfirm.OnSelectedListener() {
                @Override
                public void confirm() {
                    mDishEditPresenter.cancelGiftDish(currentBean.getId(), API.shopId, "");
                }
            });
            dia_doubleConfirm.getDialog().show();
        } else {
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
                mDishEditPresenter.deleteDish("", dishId, API.shopId);
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

    @OnClick({R.id.iv_back, R.id.tv_total_price, R.id.tv_place_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ((ACT_OrderDishes)getActivity()).onBackPressed();
                break;

            case R.id.tv_total_price:

                break;
            case R.id.tv_place_order:
                // 下单
                if (tvPlaceOrder.getText().toString().equals(mContext.getString(R.string.dishes_order))){
                    if (!TextUtils.isEmpty(dishIds)) {
                        mDishListPresenter.requestPlaceOrder(dishIds, tableBillId, StringUtil.getString(saleManner));
                    }
                }else {
                    // 去结账
                    Bundle bundle = new Bundle();
                    bundle.putString(TABLE_BILL_ID, tableBillId);
                    bundle.putInt(FROM_WHERE, saleManner);
                    readyGoThenKill(ACT_CheckOut.class, bundle);
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
        if (mode.equals(DishConstants.RETURN_DISH) || mode.equals(DishConstants.SERVE_DISH)) {
            mDishEditPresenter.specialHandleDish(mode, currentBean.getId(), API.shopId, count);
        }
        // 称重确认
        else if (mode.equals(DishConstants.DISH_WEIGHT)) {
            mDishEditPresenter.confirmWeightDish(currentBean.getId(), count, API.shopId);
        }
        // 转台
        else if (mode.equals(DishConstants.DISH_TURN)) {
            // TODO:tableBillId
            mDishEditPresenter.switchTable(currentBean.getId(), count, API.shopId, "C5BA09D3380000008800000000257000");
        }
    }

    /**
     * 有赠送记录
     *
     * @return
     */
    private boolean hasGift() {
        if (null != currentBean) {
            List<DishListEntity.Dishes.Abnormal> abnormalList = currentBean.getAbnormalList();
            if (null != abnormalList && abnormalList.size() > 0) {
                for (int i = 0; i < abnormalList.size(); i++) {
                    DishListEntity.Dishes.Abnormal bean = abnormalList.get(i);
                    if (!TextUtils.isEmpty(bean.getDishAbnormalStatus()) && bean.getDishAbnormalStatus().equals(DishConstants.SERVE_DISH)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 所有的菜类数据
     *
     * @param dishDataEntity 菜类数据
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onReceiveDishList(DishDataEntity dishDataEntity) {
        this.dishDataEntity = dishDataEntity;
    }

    @Override
    protected void onReceiveBroadcast(int intent, Bundle bundle) {
        if (intent == ReceiveConstants.REFRESH_LEFT_DISHES) {
            isRightRefresh = true;
            mDishListPresenter.requestDishList(tableBillId, false);
        }
    }
}
