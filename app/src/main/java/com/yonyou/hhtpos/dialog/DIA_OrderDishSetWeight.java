package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
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

import java.util.ArrayList;

import static com.yonyou.hhtpos.util.FiltrationUtil.getCookeryFish;

/**
 * 服务员点菜 固定价格称重 弹框
 * 作者：ybing on 2017/7/11
 * 邮箱：ybing@yonyou.com
 */

public class DIA_OrderDishSetWeight implements View.OnClickListener {
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
    private EditText etOtherRemark;

    /**
     * 选项数据
     */
    private DataBean dataBean;

    /**
     * 数据回调接口
     */
    private DishDataCallback dishDataCallback;
    /**
     * 数据回调数据状态
     */
    private boolean flag = true;

    public DIA_OrderDishSetWeight(Context mContext, DataBean dataBean) {
        this.mContext = mContext;
        this.dataBean = dataBean;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_order_dish_fixed_price, null);
        mDialog.setContentView(mContentView);

        rbFinishSelect = (RadioButton) mContentView.findViewById(R.id.rb_finish_select);
        ibClose = (ImageButton) mContentView.findViewById(R.id.ib_close);
        iwvDishWeight = (InputWeightView) mContentView.findViewById(R.id.iwv_dish_weight);
        fvCookery = (FiltrationView) mContentView.findViewById(R.id.fv_cookery);

        etOtherRemark = (EditText) mContentView.findViewById(R.id.et_other_remark);

        ibClose.setOnClickListener(this);
        rbFinishSelect.setOnClickListener(this);


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
            }
        }


        WeightEntity weightEntity = new WeightEntity("斤", "输入重量");
        iwvDishWeight.setData(weightEntity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                mDialog.dismiss();
                break;
            case R.id.rb_finish_select:
                DishCallBackEntity dishCallBackEntity = initDishCallbackEntity();
                if (flag) {
                    if (dishDataCallback != null) {
                        dishDataCallback.sendItems(dishCallBackEntity);
                    }
                    fvCookery.reset();
                    mDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    private DishCallBackEntity initDishCallbackEntity() {
        DishCallBackEntity dishCallBackEntity = new DishCallBackEntity();
        double dishWeight = Double.parseDouble(iwvDishWeight.getNumber());
        String cookery = fvCookery.getSelectedData().getOption();
        String remark = etOtherRemark.getText().toString().trim();

        if (dishWeight > 0.00 && dishWeight < 99.99) {
            //重量
            dishCallBackEntity.setDishWeight(dishWeight);
            if (!TextUtils.isEmpty(cookery)) {
                //做法
                dishCallBackEntity.setDishCookery(cookery);
                flag = true;
                //备注
                dishCallBackEntity.setDishRemark(StringUtil.getString(remark));

            } else {
                flag = false;
                CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_cookery), false);
            }
        } else {
            flag = false;
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_weight), false);
        }
        return dishCallBackEntity;
    }

    public Dialog getDialog() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;// 背景灰度
        lp.width = 756;
        lp.height = 720;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return mDialog;
    }

    public void setDishDataCallback(DishDataCallback dishDataCallback) {
        this.dishDataCallback = dishDataCallback;
    }
}
