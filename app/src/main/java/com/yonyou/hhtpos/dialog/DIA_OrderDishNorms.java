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
import com.yonyou.hhtpos.bean.dish.DataBean;
import com.yonyou.hhtpos.bean.dish.DishCallBackEntity;
import com.yonyou.hhtpos.interfaces.DishDataCallback;
import com.yonyou.hhtpos.widgets.FiltrationView;
import com.yonyou.hhtpos.widgets.ModifyCountView;

import java.util.ArrayList;

/**
 * 服务员点菜 设定规格 弹框
 * 作者：ybing on 2017/7/11
 * 邮箱：ybing@yonyou.com
 */

public class DIA_OrderDishNorms implements View.OnClickListener {
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
    private FiltrationView fvDishNorms;
    private EditText etOtherRemark;
    private ModifyCountView mcvDishCount;
    private LinearLayout llDishNorms;
    private TextView tvDishName;
    private TextView tvHotSale;
    private TextView tvDishPrice;
    private TextView tvChiefRecommend;

    /**
     * 选项数据
     */
    private boolean normsEmptyFlag;
    /**
     * 数据回调接口
     */
    private DishDataCallback dishDataCallback;

    public DIA_OrderDishNorms(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.style_custom_dialog);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dia_order_dish_norms, null);
        mDialog.setContentView(mContentView);

        rbFinishSelect = (RadioButton) mContentView.findViewById(R.id.rb_finish_select);
        ibClose = (ImageButton) mContentView.findViewById(R.id.ib_close);
        fvDishNorms = (FiltrationView) mContentView.findViewById(R.id.fv_dish_norms);
        etOtherRemark = (EditText) mContentView.findViewById(R.id.et_other_remark);
        mcvDishCount = (ModifyCountView) mContentView.findViewById(R.id.mcv_dish_count);
        llDishNorms = (LinearLayout) mContentView.findViewById(R.id.ll_dish_norms);
        tvDishName = (TextView) mContentView.findViewById(R.id.tv_dish_name);
        tvDishPrice = (TextView) mContentView.findViewById(R.id.tv_dish_price);
        tvHotSale = (TextView) mContentView.findViewById(R.id.ib_hot_sale);
        tvChiefRecommend = (TextView) mContentView.findViewById(R.id.ib_chief_recommend);

        ibClose.setOnClickListener(this);
        rbFinishSelect.setOnClickListener(this);
    }

    public DIA_OrderDishNorms setDataBean(DataBean dataBean) {
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
            if (dataBean.getLabels().size() > 1
                    && dataBean.getLabels().get(1) != null && !TextUtils.isEmpty(dataBean.getLabels().get(1).labelName)) {
                tvChiefRecommend.setVisibility(View.VISIBLE);
                tvChiefRecommend.setText(StringUtil.getString(dataBean.getLabels().get(1).labelName));
            }
        }else{
            tvHotSale.setVisibility(View.GONE);
            tvChiefRecommend.setVisibility(View.GONE);
        }

        if (dataBean != null) {
            //获取菜品规格列表
            if (dataBean.getStandards() != null && dataBean.getStandards().size() > 0) {
                FilterItemEntity dishNorms = new FilterItemEntity();
                ArrayList<FilterOptionsEntity> options = new ArrayList<>();
                for (int i = 0; i < dataBean.getStandards().size(); i++) {
                    FilterOptionsEntity foe = new FilterOptionsEntity();
                    foe.setOption(dataBean.getStandards().get(i).standardName);
                    foe.setOptionId(dataBean.getStandards().get(i).relateId);
                    foe.setType(FiltrationView.DISH_NORMS);
                    options.add(foe);
                }
                dishNorms.setOptions(options);
                dishNorms.setTitle("");
                fvDishNorms.setData(dishNorms);
            } else {
                normsEmptyFlag = true;
                llDishNorms.setVisibility(View.GONE);
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
                    if (!normsEmptyFlag)
                        fvDishNorms.reset();
                    etOtherRemark.setText("");
                    mcvDishCount.reset();
                }
                break;
            case R.id.rb_finish_select:
                DishCallBackEntity dishCallBackEntity = initDishCallBackEntity();
                if (dishDataCallback != null) {
                    if (verifyInput()) {
                        dishDataCallback.sendItems(dishCallBackEntity);
                        if (!normsEmptyFlag)
                            fvDishNorms.reset();
                        etOtherRemark.setText("");
                        mcvDishCount.reset();
                        mDialog.dismiss();
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean verifyInput() {
        //有规格列表但是没有选择
        if (!normsEmptyFlag && fvDishNorms.getSelectedData() == null) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_standard), false);
            return false;
        }
        //菜品的数量为0
        if (mcvDishCount.getCount() <= 0) {
            CommonUtils.makeEventToast(mContext, mContext.getString(R.string.input_dish_count), false);
            return false;
        }
        return true;
    }

    private DishCallBackEntity initDishCallBackEntity() {
        DishCallBackEntity dishCallBackEntity = new DishCallBackEntity();
        if (verifyInput()) {
            //规格
            if (!normsEmptyFlag) {
                dishCallBackEntity.setDishStandard(fvDishNorms.getSelectedData().getOption());
                dishCallBackEntity.setDishStandardId(fvDishNorms.getSelectedData().getOptionId());
            }
            //数量
            dishCallBackEntity.setDishCount(String.valueOf(mcvDishCount.getCount()));
            //备注
            String dishRemark = etOtherRemark.getText().toString().trim();
            dishCallBackEntity.setRemark(StringUtil.getString(dishRemark));
            return dishCallBackEntity;
        } else {
            return null;
        }
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
