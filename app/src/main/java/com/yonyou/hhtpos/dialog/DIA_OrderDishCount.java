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
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.dish.DataBean;
import com.yonyou.hhtpos.bean.dish.DishCallbackEntity;
import com.yonyou.hhtpos.util.DishDataCallback;
import com.yonyou.hhtpos.widgets.FiltrationView;
import com.yonyou.hhtpos.widgets.ModifyCountView;
import com.yonyou.hhtpos.widgets.MultipleSelectView;

import java.util.ArrayList;

/**
 * 服务员点菜时蔬 数量弹框
 * 作者：ybing on 2017/7/10
 * 邮箱：ybing@yonyou.com
 */

public class DIA_OrderDishCount implements View.OnClickListener {
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
    private FiltrationView fvCookery;
    private MultipleSelectView fvRemark;
    private EditText etOtherRemark;
    private TextView tvHintCookery;
    private TextView tvHintRemark;
    private ModifyCountView mcvDishCount;

    /**
     * 选项数据
     */
//    private DataBean dataBean;

    /**
     * 数据回调接口
     */
    private DishDataCallback dishDataCallback;
    /**
     * 数据回调数据状态
     */
    private boolean flag = true;

    private boolean cookeryEmptyFlag;
    private boolean remarkEmptyFlag;

    public DIA_OrderDishCount(Context mContext) {
        this.mContext = mContext;
//        this.dataBean = dataBean;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_order_dish_vegetable, null);
        mDialog.setContentView(mContentView);

        rbFinishSelect = (RadioButton) mContentView.findViewById(R.id.rb_finish_select);
        ibClose = (ImageButton) mContentView.findViewById(R.id.ib_close);
        fvCookery = (FiltrationView) mContentView.findViewById(R.id.fv_cookery);
        fvRemark = (MultipleSelectView) mContentView.findViewById(R.id.fv_remark);
        mcvDishCount = (ModifyCountView) mContentView.findViewById(R.id.mcv_dish_count);
        tvHintCookery = (TextView) mContentView.findViewById(R.id.tv_hint_cookery);
        tvHintRemark = (TextView) mContentView.findViewById(R.id.tv_hint_remark);

        etOtherRemark = (EditText) mContentView.findViewById(R.id.et_other_remark);

        ibClose.setOnClickListener(this);
        rbFinishSelect.setOnClickListener(this);
        getDialog();
    }

    /**
     * 传入数据
     *
     * @param dataBean
     */
    public DIA_OrderDishCount setData(DataBean dataBean) {
        if (dataBean != null) {
            //获取菜品做法列表
            if (dataBean.getPractices() != null && dataBean.getPractices().size() > 0) {
                FilterItemEntity cookeryOption = new FilterItemEntity();
                ArrayList<FilterOptionsEntity> options = new ArrayList<>();
                for (int i = 0; i < dataBean.getPractices().size(); i++) {
                    FilterOptionsEntity foe = new FilterOptionsEntity();
                    foe.setOption(dataBean.getPractices().get(i).practiceName);
                    foe.setType(FiltrationView.COOKERY);
                    options.add(foe);
                }
                cookeryOption.setOptions(options);
                cookeryOption.setTitle("");
                fvCookery.setData(cookeryOption);
            } else {
                fvCookery.setVisibility(View.GONE);
                tvHintCookery.setVisibility(View.GONE);
                cookeryEmptyFlag = true;
            }

            //获取菜品备注列表
            if (dataBean.getRemarks() != null && dataBean.getRemarks().size() > 0) {
                FilterItemEntity remarkOption = new FilterItemEntity();
                ArrayList<FilterOptionsEntity> options = new ArrayList<>();
                for (int i = 0; i < dataBean.getRemarks().size(); i++) {
                    FilterOptionsEntity foe = new FilterOptionsEntity();
                    foe.setOption(dataBean.getRemarks().get(i).remarkName);
                    foe.setType(MultipleSelectView.DISH_REMARK);
                    options.add(foe);
                }
                remarkOption.setOptions(options);
                remarkOption.setTitle("");
                fvRemark.setData(remarkOption);
            } else {
                fvRemark.setVisibility(View.GONE);
                tvHintRemark.setVisibility(View.GONE);
                remarkEmptyFlag = true;
            }
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_finish_select:
                DishCallbackEntity dishCallBackEntity = initDishCallbackEntity();
                if (flag) {
                    if (dishDataCallback != null) {
                        dishDataCallback.sendItems(dishCallBackEntity);
                    }
                    if (!cookeryEmptyFlag)
                        fvCookery.reset();
                    if (!remarkEmptyFlag)
                        fvRemark.reset();
                    etOtherRemark.setText("");
                    mDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    private DishCallbackEntity initDishCallbackEntity() {
        DishCallbackEntity dishCallBackEntity = new DishCallbackEntity();
        int dishCount = mcvDishCount.getCount();
        if (dishCount > 0) {
            //数量
            dishCallBackEntity.setDishCount(String.valueOf(dishCount));
            if (!cookeryEmptyFlag && fvCookery.getSelectedData() != null) {
                //做法
                String dishCookery = fvCookery.getSelectedData().getOption();
                dishCallBackEntity.setDishCookery(dishCookery);
                flag = true;
                dishCallBackEntity.setDishRemark(checkRemark(remarkEmptyFlag));
            } else if (cookeryEmptyFlag) {
                dishCallBackEntity.setDishCookery("");
                dishCallBackEntity.setDishRemark(checkRemark(remarkEmptyFlag));
            } else {
                flag = false;
                CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_cookery), false);
            }
        } else {
            flag = false;
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_count), false);
        }
        return dishCallBackEntity;
    }

    @NonNull
    private String checkRemark(boolean remarkEmptyFlag) {
        String otherRemark = etOtherRemark.getText().toString().trim();
        //备注
        if (!remarkEmptyFlag && fvRemark.getSelectedList() != null) {
            ArrayList<FilterOptionsEntity> dishRemarks = fvRemark.getSelectedList();
            StringBuilder sb = new StringBuilder();
            if (dishRemarks.size() > 0) {
                for (int i = 0; i < dishRemarks.size() - 1; i++) {
                    sb.append(dishRemarks.get(i).getOption());
                    sb.append(",");
                }
                sb.append(dishRemarks.get(dishRemarks.size() - 1).getOption());
            }
            if (!TextUtils.isEmpty(otherRemark)) {
                sb.append(",");
                sb.append(otherRemark);
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    public Dialog getDialog() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 756;
        lp.height = 784;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    /**
     * 获取菜品数据后传递数据用的接口
     */
    public void setDishDataCallback(DishDataCallback dishDataCallback) {
        this.dishDataCallback = dishDataCallback;
    }
}
