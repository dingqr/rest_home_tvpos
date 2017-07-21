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
import android.widget.RadioButton;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.dish.DishCallBackEntity;
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
    private ModifyCountView mcvDishCount;

    /**
     * 选项数据
     */

    private FilterItemEntity cookeryOption;
    private FilterItemEntity remarkOption;

    /**
     * 数据回调接口
     */
    private DishDataCallback dishDataCallback;
    /**
     * 数据回调数据状态
     */
    private boolean flag = true;

    public DIA_OrderDishCount(Context mContext, FilterItemEntity cookeryOption, FilterItemEntity remarkOption) {
        this.mContext = mContext;
        this.cookeryOption = cookeryOption;
        this.remarkOption = remarkOption;
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

        etOtherRemark = (EditText) mContentView.findViewById(R.id.et_other_remark);

        ibClose.setOnClickListener(this);
        rbFinishSelect.setOnClickListener(this);


        fvCookery.setData(cookeryOption);

        fvRemark.setData(remarkOption);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_finish_select:
                DishCallBackEntity dishCallBackEntity = initDishCallbackEntity();
                if (flag){
                    if (dishDataCallback != null) {
                        dishDataCallback.sendItems(dishCallBackEntity);
                    }
                    fvCookery.reset();
                    fvRemark.reset();
                    etOtherRemark.setText("");
                    mDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    private DishCallBackEntity initDishCallbackEntity() {
        DishCallBackEntity dishCallBackEntity = new DishCallBackEntity();
        int dishCount = mcvDishCount.getCount();
        String dishCookery = fvCookery.getSelectedData().getOption();
        ArrayList<FilterOptionsEntity> dishRemarks = fvRemark.getSelectedList();
        String otherRemark = etOtherRemark.getText().toString().trim();

        if (dishCount > 0) {
            //数量
            dishCallBackEntity.setDishCount(String.valueOf(dishCount));
            if (!TextUtils.isEmpty(dishCookery)) {
                //做法
                dishCallBackEntity.setDishCookery(dishCookery);
                flag = true;
                //备注
                StringBuilder sb = new StringBuilder();
                if (dishRemarks.size()>0){
                    for (int i=0;i<dishRemarks.size()-1;i++){
                        sb.append(dishRemarks.get(i).getOption());
                        sb.append(",");
                    }
                    sb.append(dishRemarks.get(dishRemarks.size()).getOption());
                }
                if (!TextUtils.isEmpty(otherRemark)){
                    sb.append(",");
                    sb.append(otherRemark);
                }
                dishCallBackEntity.setDishRemark(sb.toString());
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
