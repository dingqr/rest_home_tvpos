package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.WeightEntity;
import com.yonyou.hhtpos.bean.dish.DataBean;
import com.yonyou.hhtpos.bean.dish.DishCallBackEntity;
import com.yonyou.hhtpos.interfaces.DishDataCallback;
import com.yonyou.hhtpos.widgets.FiltrationView;
import com.yonyou.hhtpos.widgets.InputWeightView;

import java.util.ArrayList;

/**
 * 服务员点菜 设置时价称重 弹框
 * 作者：ybing on 2017/7/11
 * 邮箱：ybing@yonyou.com
 */

public class DIA_OrderDishSetPrice implements View.OnClickListener {
    /**
     * 上下文
     */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**
     * 界面控件
     */
    private RadioButton rbFinishSelect;
    private ImageButton ibClose;
    private InputWeightView iwvDishWeight;
    private InputWeightView iwvDishPrice;
    private FiltrationView fvCookery;
    private EditText etOtherRemark;
    private LinearLayout llCookery;

    private TextView tvDishName;
    private TextView tvHotSale;
    private TextView tvDishPrice;
    private TextView tvChiefRecommend;

    /**
     * 选项数据 做法列表是否为空 默认false-列表不为空
     */
    private boolean cookeryEmptyFlag;
    /**
     * 数据回调接口
     */
    private DishDataCallback dishDataCallback;

    public DIA_OrderDishSetPrice(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_order_dish_set_price, null);
        mDialog.setContentView(mContentView);

        rbFinishSelect = (RadioButton) mContentView.findViewById(R.id.rb_finish_select);
        ibClose = (ImageButton) mContentView.findViewById(R.id.ib_close);
        iwvDishWeight = (InputWeightView) mContentView.findViewById(R.id.iwv_dish_weight);
        iwvDishPrice = (InputWeightView) mContentView.findViewById(R.id.iwv_dish_price);
        fvCookery = (FiltrationView) mContentView.findViewById(R.id.fv_cookery);
        llCookery = (LinearLayout) mContentView.findViewById(R.id.ll_dish_cookery);

        tvDishName = (TextView) mContentView.findViewById(R.id.tv_dish_name);
        tvDishPrice = (TextView) mContentView.findViewById(R.id.tv_dish_price);
        tvHotSale = (TextView) mContentView.findViewById(R.id.ib_hot_sale);
        tvChiefRecommend = (TextView) mContentView.findViewById(R.id.ib_chief_recommend);

        etOtherRemark = (EditText) mContentView.findViewById(R.id.et_other_remark);

        ibClose.setOnClickListener(this);
        rbFinishSelect.setOnClickListener(this);

        WeightEntity weightEntity = new WeightEntity("斤", "输入重量");
        iwvDishWeight.setData(weightEntity);
        WeightEntity priceEntity = new WeightEntity("元", "输入时价");
        iwvDishPrice.setData(priceEntity);
    }

    public DIA_OrderDishSetPrice setData(DataBean dataBean) {
        //设置菜品名称
        tvDishName.setText(StringUtil.getString(dataBean.getDishName()));
        //设置菜品价格
        if (!TextUtils.isEmpty(dataBean.getPrice())) {
            tvDishPrice.setText(StringUtil.getString(dataBean.getPrice()));
        }
        //设置菜品标签
        if (dataBean.getLabels() != null && dataBean.getLabels().size() > 0) {
            if (!TextUtils.isEmpty(dataBean.getLabels().get(0).labelName)) {
                tvHotSale.setVisibility(View.VISIBLE);
                tvHotSale.setText(StringUtil.getString(dataBean.getLabels().get(0).labelName));
            }
            if (dataBean.getLabels().size() > 1 &&
                    dataBean.getLabels().get(1) != null && !TextUtils.isEmpty(dataBean.getLabels().get(1).labelName)) {
                tvChiefRecommend.setVisibility(View.VISIBLE);
                tvChiefRecommend.setText(StringUtil.getString(dataBean.getLabels().get(1).labelName));
            }
        }else{
            tvHotSale.setVisibility(View.GONE);
            tvChiefRecommend.setVisibility(View.GONE);
        }

        if (dataBean != null) {
            //获取菜品做法列表
            if (dataBean.getPractices() != null && dataBean.getPractices().size() > 0) {
                FilterItemEntity cookeryOption = new FilterItemEntity();
                ArrayList<FilterOptionsEntity> options = new ArrayList<>();
                for (int i = 0; i < dataBean.getPractices().size(); i++) {
                    FilterOptionsEntity foe = new FilterOptionsEntity();
                    foe.setOption(dataBean.getPractices().get(i).practiceName);
                    foe.setOptionId(dataBean.getPractices().get(i).relateId);
                    foe.setType(FiltrationView.COOKERY);
                    options.add(foe);
                }
                cookeryOption.setOptions(options);
                cookeryOption.setTitle("");
                fvCookery.setData(cookeryOption);
            } else {
                cookeryEmptyFlag = true;
                llCookery.setVisibility(View.GONE);
            }
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;
            case R.id.rb_finish_select:
                DishCallBackEntity dishCallBackEntity = initDishCallBackEntity();

                if (dishDataCallback != null) {
                    if (verifyInput()) {
                        dishDataCallback.sendItems(dishCallBackEntity);
                        if (!cookeryEmptyFlag)
                            fvCookery.reset();
                        etOtherRemark.setText("");
                        iwvDishPrice.reset();
                        iwvDishWeight.reset();
                        mDialog.dismiss();
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean verifyInput() {
        //称重的数量为0
        if (TextUtils.isEmpty(iwvDishWeight.getNumber())) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_weight), false);
            return false;
        }
        //称重的数量超出范围
        if (!TextUtils.isEmpty(iwvDishWeight.getNumber())) {
            double dishWeight = Double.parseDouble(iwvDishWeight.getNumber());
            if (dishWeight < 0.00 || dishWeight > 99.99) {
                CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_weight), false);
                return false;
            }
        }
        //价格的数量为0
        if (TextUtils.isEmpty(iwvDishPrice.getNumber())) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_price), false);
            return false;
        }
        //价格的数量超出范围
        if (!TextUtils.isEmpty(iwvDishPrice.getNumber())) {
            double dishPrice = Double.parseDouble(iwvDishPrice.getNumber());
            if (dishPrice < 0.00 || dishPrice > 9999.99) {
                CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_price), false);
                return false;
            }
        }
        //有做法列表但是没有选择
        if (!cookeryEmptyFlag && fvCookery.getSelectedData() == null) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_cookery), false);
            return false;
        }
        return true;
    }

    private DishCallBackEntity initDishCallBackEntity() {
        DishCallBackEntity dishCallBackEntity = new DishCallBackEntity();
        if (verifyInput()) {
            //重量
            double dishWeight = Double.parseDouble(iwvDishWeight.getNumber());
            dishCallBackEntity.setDishWeight(dishWeight);
            //价格
            dishCallBackEntity.setDishPrice(iwvDishPrice.getNumber());
            //做法
            if (!cookeryEmptyFlag) {
                dishCallBackEntity.setListShowPractice(fvCookery.getSelectedData().getOption());
                dishCallBackEntity.setPractices(fvCookery.getSelectedData().getOptionId());
            }
            //手输备注
            String dishRemark = etOtherRemark.getText().toString().trim();
            dishCallBackEntity.setRemark(StringUtil.getString(dishRemark));
            return dishCallBackEntity;
        } else return null;
    }

    public Dialog getDialog() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
//        lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 9; // 设置宽度
//        lp.height = ScreenUtil.getScreenHeight((Activity)mContext)/ 10 * 8; // 设置高度
        lp.width = 756;
        lp.height = 784;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    public void setDishDataCallback(DishDataCallback dishDataCallback) {
        this.dishDataCallback = dishDataCallback;
    }
}
