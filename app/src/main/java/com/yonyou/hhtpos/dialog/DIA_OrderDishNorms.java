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
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.WeightEntity;
import com.yonyou.hhtpos.bean.dish.DataBean;
import com.yonyou.hhtpos.bean.dish.DishCallBackEntity;
import com.yonyou.hhtpos.util.DishDataCallback;
import com.yonyou.hhtpos.widgets.FiltrationView;
import com.yonyou.hhtpos.widgets.InputWeightView;
import com.yonyou.hhtpos.widgets.ModifyCountView;

import java.util.ArrayList;

import static com.yonyou.hhtpos.util.FiltrationUtil.getCookeryFish;
import static com.yonyou.hhtpos.util.FiltrationUtil.getDishNorms;
import static com.yonyou.hhtpos.util.FiltrationUtil.getDishRemark;

/**
 * 服务员点菜 设定规格 弹框
 * 作者：ybing on 2017/7/11
 * 邮箱：ybing@yonyou.com
 */

public class DIA_OrderDishNorms implements View.OnClickListener{
    /**上下文 */
    protected Context mContext;
    protected Dialog mDialog;
    protected View mContentView;

    /**界面控件 */
    private RadioButton rbFinishSelect;
    private ImageButton ibClose;
    private FiltrationView fvDishNorms;
    private EditText etOtherRemark;
    private ModifyCountView mcvDishCount;

    /**选项数据*/
    private DataBean dataBean;
    /**
     * 数据回调接口
     */
    private DishDataCallback dishDataCallback;
    /**
     * 数据回调数据状态
     */
    private boolean flag = true;

    public DIA_OrderDishNorms(Context mContext, DataBean dataBean) {
        this.mContext = mContext;
        this.dataBean = dataBean;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_order_dish_norms, null);
        mDialog.setContentView(mContentView);

        rbFinishSelect =(RadioButton) mContentView.findViewById(R.id.rb_finish_select);
        ibClose =(ImageButton) mContentView.findViewById(R.id.ib_close);
        fvDishNorms =(FiltrationView) mContentView.findViewById(R.id.fv_dish_norms);
        etOtherRemark =(EditText) mContentView.findViewById(R.id.et_other_remark);
        mcvDishCount = (ModifyCountView)mContentView.findViewById(R.id.mcv_dish_count);

        ibClose.setOnClickListener(this);
        rbFinishSelect.setOnClickListener(this);

        if (dataBean != null) {
            //获取菜品规格列表
            if (dataBean.getStandards() != null && dataBean.getStandards().size() > 0) {
                FilterItemEntity dishNorms = new FilterItemEntity();
                ArrayList<FilterOptionsEntity> options = new ArrayList<>();
                for (int i = 0; i < dataBean.getPractices().size(); i++) {
                    FilterOptionsEntity foe = new FilterOptionsEntity();
                    foe.setOption(dataBean.getStandards().get(i).standardName);
                    foe.setType(FiltrationView.DISH_NORMS);
                    options.add(foe);
                }
                dishNorms.setOptions(options);
                dishNorms.setTitle("");
                fvDishNorms.setData(dishNorms);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_finish_select:
                DishCallBackEntity dishCallBackEntity = initDishCallbackEntity();
                if (flag){
                    if (dishDataCallback != null) {
                        dishDataCallback.sendItems(dishCallBackEntity);
                    }
                    fvDishNorms.reset();
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
        String dishNorm = fvDishNorms.getSelectedData().getOption();
        int dishCount = mcvDishCount.getCount();
        String dishRemark = etOtherRemark.getText().toString().trim();

        if (!TextUtils.isEmpty(dishNorm)){
            //规格
            dishCallBackEntity.setDishStandard(dishNorm);
            if (dishCount>0){
                //数量
                dishCallBackEntity.setDishCount(String.valueOf(dishCount));
                flag = true;
                //备注
                dishCallBackEntity.setDishRemark(StringUtil.getString(dishRemark));
            }else{
                flag = false;
                CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_count), false);
            }
        }else{
            flag = false;
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_standard), false);
        }
        return dishCallBackEntity;
    }

    public Dialog getDialog(){
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
//        lp.width = ScreenUtil.getScreenWidth((Activity) mContext) / 10 * 9; // 设置宽度
//        lp.height = ScreenUtil.getScreenHeight((Activity)mContext)/ 10 * 8; // 设置高度
        lp.width = 756;
        lp.height= 720;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    public void setDishDataCallback(DishDataCallback dishDataCallback) {
        this.dishDataCallback = dishDataCallback;
    }
}
