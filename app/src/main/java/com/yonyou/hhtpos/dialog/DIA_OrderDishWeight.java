package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.WeightEntity;
import com.yonyou.hhtpos.widgets.FiltrationView;
import com.yonyou.hhtpos.widgets.InputWeightView;
import com.yonyou.hhtpos.widgets.MultipleSelectView;

import static com.yonyou.hhtpos.util.FiltrationUtil.getCookery;
import static com.yonyou.hhtpos.util.FiltrationUtil.getDishRemark;

/**
 * 服务员点菜称重弹框
 * 作者：ybing on 2017/7/11
 * 邮箱：ybing@yonyou.com
 */

public class DIA_OrderDishWeight implements View.OnClickListener{
    /**上下文 */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**界面控件 */
    private RadioButton rbFinishSelect;
    private ImageButton ibClose;
    private InputWeightView iwvDishWeight;
    private FiltrationView fvCookery;
    private MultipleSelectView fvRemark;
    private EditText etOtherReason;

    /**选项数据*/

    private FilterItemEntity cookeryOption;
    private FilterItemEntity remarkOption;

    public DIA_OrderDishWeight(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_order_dish_weight, null);
        mDialog.setContentView(mContentView);

        rbFinishSelect =(RadioButton) mContentView.findViewById(R.id.rb_finish_select);
        ibClose =(ImageButton) mContentView.findViewById(R.id.ib_close);
        iwvDishWeight =(InputWeightView) mContentView.findViewById(R.id.iwv_dish_weight);
        fvCookery =(FiltrationView) mContentView.findViewById(R.id.fv_cookery);
        fvRemark =(MultipleSelectView) mContentView.findViewById(R.id.fv_remark);

        etOtherReason =(EditText) mContentView.findViewById(R.id.et_other_reason);

        ibClose.setOnClickListener(this);
        rbFinishSelect.setOnClickListener(this);

        cookeryOption = new FilterItemEntity();
        cookeryOption.setTitle("");
        cookeryOption.setOptions(getCookery());
        fvCookery.setData(cookeryOption);

        remarkOption = new FilterItemEntity();
        remarkOption.setTitle("");
        remarkOption.setOptions(getDishRemark());
        fvRemark.setData(remarkOption);

        WeightEntity weightEntity = new WeightEntity("斤","输入重量");
        iwvDishWeight.setData(weightEntity);
//
//        if (fvRefundReason.getSelectedData().getOption() == "其他原因"){
//            etEnterRefundReason.setFocusable(true);
//            etEnterRefundReason.setFocusableInTouchMode(true);
//            etEnterRefundReason.setLongClickable(true);
//        }else{
//            etEnterRefundReason.setFocusable(false);
//            etEnterRefundReason.setFocusableInTouchMode(false);
//            etEnterRefundReason.setLongClickable(false);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_confirm_refund:
                break;
            default:
                break;
        }
    }
    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
//        lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 9; // 设置宽度
//        lp.height = ScreenUtil.getScreenHeight((Activity)mContext)/ 10 * 8; // 设置高度
        lp.width = 756;
        lp.height= 758;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

}
