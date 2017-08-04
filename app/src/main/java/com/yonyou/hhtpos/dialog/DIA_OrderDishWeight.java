package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
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
import com.yonyou.hhtpos.widgets.MultipleSelectView;

import java.util.ArrayList;

/**
 * 服务员点菜称重弹框
 * 作者：ybing on 2017/7/11
 * 邮箱：ybing@yonyou.com
 */

public class DIA_OrderDishWeight implements View.OnClickListener {
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
    private FiltrationView fvCookery;
    private MultipleSelectView fvRemark;
    private EditText etOtherRemark;
    private LinearLayout llDishCookery;
    private LinearLayout llDishRemark;

    private TextView tvDishName;
    private TextView tvHotSale;
    private TextView tvDishPrice;
    private TextView tvChiefRecommend;
    private TextView tvDishTaste;

    /**
     * 选项数据
     */
    private boolean cookeryEmptyFlag;
    private boolean remarkEmptyFlag;
    /**
     * 数据回调接口
     */
    private DishDataCallback dishDataCallback;
    /**
     * 数据回调数据状态
     */
    private boolean flag = true;

    public DIA_OrderDishWeight(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_order_dish_weight, null);
        mDialog.setContentView(mContentView);

        rbFinishSelect = (RadioButton) mContentView.findViewById(R.id.rb_finish_select);
        ibClose = (ImageButton) mContentView.findViewById(R.id.ib_close);
        iwvDishWeight = (InputWeightView) mContentView.findViewById(R.id.iwv_dish_weight);
        fvCookery = (FiltrationView) mContentView.findViewById(R.id.fv_cookery);
        fvRemark = (MultipleSelectView) mContentView.findViewById(R.id.fv_remark);
        llDishCookery = (LinearLayout) mContentView.findViewById(R.id.ll_dish_cookery);
        llDishRemark = (LinearLayout) mContentView.findViewById(R.id.ll_dish_remark);
        etOtherRemark = (EditText) mContentView.findViewById(R.id.et_other_remark);

        tvDishName = (TextView) mContentView.findViewById(R.id.tv_dish_name);
        tvDishPrice = (TextView) mContentView.findViewById(R.id.tv_dish_price);
        tvHotSale = (TextView) mContentView.findViewById(R.id.ib_hot_sale);
        tvChiefRecommend = (TextView) mContentView.findViewById(R.id.ib_chief_recommend);
        tvDishTaste = (TextView) mContentView.findViewById(R.id.tv_dish_taste);

        ibClose.setOnClickListener(this);
        rbFinishSelect.setOnClickListener(this);

        WeightEntity weightEntity = new WeightEntity("斤", "输入重量");
        iwvDishWeight.setData(weightEntity);
    }


    public DIA_OrderDishWeight setData(DataBean dataBean) {
        //设置菜品名称
        tvDishName.setText(StringUtil.getString(dataBean.getDishName()));
        //设置菜品价格
        tvDishPrice.setText(StringUtil.getString(dataBean.getPrice()));
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
        } else {
            tvHotSale.setVisibility(View.GONE);
            tvChiefRecommend.setVisibility(View.GONE);
        }
        //TODO 设置菜品口味
        tvDishTaste.setText(StringUtil.getString(""));

        if (dataBean != null) {
            //获取菜品做法列表
            if (dataBean.getPractices() != null && dataBean.getPractices().size() > 0) {
                FilterItemEntity cookeryOption = new FilterItemEntity();
                ArrayList<FilterOptionsEntity> options = new ArrayList<>();
                for (int i = 0; i < dataBean.getPractices().size(); i++) {
                    if (dataBean.getPractices().get(i) != null) {
                        FilterOptionsEntity foe = new FilterOptionsEntity();
                        foe.setOption(dataBean.getPractices().get(i).practiceName);
                        foe.setOptionId(dataBean.getPractices().get(i).relateId);
                        foe.setCheck(dataBean.getPractices().get(i).isCheck);
                        foe.setType(FiltrationView.COOKERY);
                        options.add(foe);
                    }
                }
                if (options.size() > 0) {
                    cookeryOption.setOptions(options);
                    cookeryOption.setTitle("");
                    fvCookery.setData(cookeryOption);
                } else {
                    llDishCookery.setVisibility(View.GONE);
                    cookeryEmptyFlag = true;
                }
            } else {
                llDishCookery.setVisibility(View.GONE);
                cookeryEmptyFlag = true;
            }

            //获取菜品备注列表
            if (dataBean.getRemarks() != null && dataBean.getRemarks().size() > 0) {
                FilterItemEntity remarkOption = new FilterItemEntity();
                ArrayList<FilterOptionsEntity> options = new ArrayList<>();
                for (int i = 0; i < dataBean.getRemarks().size(); i++) {
                    if (dataBean.getRemarks().get(i) != null) {
                        FilterOptionsEntity foe = new FilterOptionsEntity();
                        foe.setOption(dataBean.getRemarks().get(i).remarkName);
                        foe.setOptionId(dataBean.getRemarks().get(i).relateId);
                        foe.setCheck(dataBean.getRemarks().get(i).isCheck);
                        foe.setType(MultipleSelectView.DISH_REMARK);
                        options.add(foe);
                    }
                }
                if (options.size() > 0) {
                    remarkOption.setOptions(options);
                    remarkOption.setTitle("");
                    fvRemark.setData(remarkOption);
                } else {
                    llDishRemark.setVisibility(View.GONE);
                    remarkEmptyFlag = true;
                }
            } else {
                llDishRemark.setVisibility(View.GONE);
                remarkEmptyFlag = true;
            }
            if (dataBean.getWeight() > 0) {
                iwvDishWeight.setWeight(dataBean.getWeight());
            }
            //设置手填备注
            etOtherRemark.setText(StringUtil.getString(dataBean.getRemark()));
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                if (mDialog != null) {
                    if (!cookeryEmptyFlag)
                        fvCookery.reset();
                    if (!remarkEmptyFlag)
                        fvRemark.reset();
                    etOtherRemark.setText("");
                    iwvDishWeight.reset();
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
                        if (!remarkEmptyFlag)
                            fvRemark.reset();
                        etOtherRemark.setText("");
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
            dishCallBackEntity.setDishWeight(Double.parseDouble(iwvDishWeight.getNumber()));
            //做法
            if (!cookeryEmptyFlag) {
                dishCallBackEntity.setListShowPractice(fvCookery.getSelectedData().getOption());
                dishCallBackEntity.setPractices(fvCookery.getSelectedData().getOptionId());
            }
            //备注
            dishCallBackEntity.setRemark(checkRemark(remarkEmptyFlag).get(0));
            dishCallBackEntity.setListShowRemark(checkRemark(remarkEmptyFlag).get(1));
            dishCallBackEntity.setRemarks(checkRemark(remarkEmptyFlag).get(2));
            return dishCallBackEntity;
        } else {
            return null;
        }
    }

    @NonNull
    private ArrayList<String> checkRemark(boolean remarkEmptyFlag) {
        ArrayList<String> remarkSet = new ArrayList<>();
        String otherRemark = etOtherRemark.getText().toString().trim();
        remarkSet.add(StringUtil.getString(otherRemark));
        //备注
        if (!remarkEmptyFlag && fvRemark.getSelectedList() != null) {
            ArrayList<FilterOptionsEntity> dishRemarks = fvRemark.getSelectedList();
            StringBuilder sb = new StringBuilder();
            StringBuilder idSb = new StringBuilder();
            if (dishRemarks.size() > 0) {
                for (int i = 0; i < dishRemarks.size() - 1; i++) {
                    sb.append(dishRemarks.get(i).getOption());
                    sb.append(",");
                    idSb.append(dishRemarks.get(i).getOptionId());
                    idSb.append(",");
                }
                sb.append(dishRemarks.get(dishRemarks.size() - 1).getOption());
                idSb.append(dishRemarks.get(dishRemarks.size() - 1).getOptionId());
            }
            if (!TextUtils.isEmpty(otherRemark)) {
                sb.append(",");
                sb.append(otherRemark);
            }
            remarkSet.add(sb.toString());
            remarkSet.add(idSb.toString());
        }
        return remarkSet;
    }


    public Dialog getDialog() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
//        lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 9; // 设置宽度
//        lp.height = ScreenUtil.getScreenHeight((Activity)mContext)/ 10 * 8; // 设置高度
        lp.width = 756;
        lp.height = 758;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    public void setDishDataCallback(DishDataCallback dishDataCallback) {
        this.dishDataCallback = dishDataCallback;
    }
}
