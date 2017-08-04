package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.dish.DishListEntity;
import com.yonyou.hhtpos.global.DishConstants;
import com.yonyou.hhtpos.widgets.NumberKeybordView;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by zj on 2017/7/11.
 * 邮箱：zjuan@yonyou.com
 * 描述：服务员点菜-菜品转台弹窗
 */
public class DIA_SwitchTable implements View.OnClickListener{
    @Bind(R.id.tv_dialog_title)
    TextView tvDialogTitle;

    private Context mContext;
    private DishListEntity.Dishes currentBean;
    private String mode;
    private OnConfirmListener onConfirmListener;

    private Dialog mDialog;
    private View mContentView;
    private ImageView ivClose;
    private TextView mDishName;
    private EditText mCount;
    private NumberKeybordView numberGridView;
    private RadioButton mConfirm;

    public DIA_SwitchTable(Context context, DishListEntity.Dishes currentBean, String mode, OnConfirmListener onConfirmListener) {
        mContext = context;
        this.currentBean = currentBean;
        this.mode = mode;
        this.onConfirmListener = onConfirmListener;

        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dia_switch_table, null);
        ButterKnife.bind(this, mContentView);
        numberGridView = (NumberKeybordView) mContentView.findViewById(R.id.number_keybord_view);
        ivClose = (ImageView) mContentView.findViewById(R.id.iv_close);
        mDishName = (TextView) mContentView.findViewById(R.id.tv_dish_name);
        mCount = (EditText) mContentView.findViewById(R.id.et_money);
        mConfirm = (RadioButton) mContentView.findViewById(R.id.rb_confirm);
        mConfirm.setOnClickListener(this);
        mDialog.setContentView(mContentView);

        //不使用系统软件盘输入
        disableShowInput(mCount);
        // 非称重菜
        if (currentBean.getUnit() == 0){
            // 最大值
            numberGridView.setMaxIntValue(getOperationalQuantity());
            // 整数
            numberGridView.setInputMode(NumberKeybordView.INTEGER);
        }else {
            // 带小数点
            numberGridView.setInputMode(NumberKeybordView.DECIMAL);
        }

        // EditText
        numberGridView.setEtMoney(mCount);

//        tvDialogTitle.setText(mContext.getResources().getString(R.string.string_switch_table));

        // 标题
        tvDialogTitle.setText(getTitle());
        // 菜名
        mDishName.setText(StringUtil.getString(currentBean.getDishName()));
        // hint
        mCount.setHint(getHint());

        initListener();
    }

    /**
     * 获取标题
     * @return
     */
    private String getTitle(){
        if (mode.equals(DishConstants.RETURN_DISH)){
            return mContext.getString(R.string.return_dish);
        }else if (mode.equals(DishConstants.SERVE_DISH)){
            return mContext.getString(R.string.serve_dish);
        }else if (mode.equals(DishConstants.DISH_WEIGHT)){
            return mContext.getString(R.string.weight_confirm);
        }else if (mode.equals(DishConstants.DISH_TURN)){
            return mContext.getString(R.string.string_switch_table);
        }
        return "";
    }

    /**
     * 获取提示语
     * @return
     */
    private String getHint(){
        if (mode.equals(DishConstants.RETURN_DISH)){
            return "最多退" + getOperationalQuantity();
        }else if (mode.equals(DishConstants.SERVE_DISH)){
            return "最多赠" + getOperationalQuantity();
        }else if (mode.equals(DishConstants.DISH_WEIGHT)){
            return "0.00斤";
        } else if (mode.equals(DishConstants.DISH_TURN)){
            return "最多转" + getOperationalQuantity();
        }
        return "";
    }

    /**
     * 获取可操作数量（数量 - 已赠 - 已退）
     * @return
     */
    private int getOperationalQuantity(){
        int quantity = (int)Double.parseDouble(currentBean.getQuantity());

        // 非称重菜
        if (currentBean.getUnit() == 0){
            // 有退赠记录
            if (null != currentBean.getAbnormalList() && currentBean.getAbnormalList().size() > 0){
                List<DishListEntity.Dishes.Abnormal> abnormalList = currentBean.getAbnormalList();
                for (int i = 0; i < abnormalList.size(); i++){
                    int handleQuantity = (int)Double.parseDouble(abnormalList.get(i).getQuantity());
                    quantity-= handleQuantity;
                }
            }
        }

        return quantity;
    }

    private void initListener() {
        // 关闭按钮
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });

        //数字键盘点击
        numberGridView.setOnKeybordListener(new NumberKeybordView.onKeybordClickListener() {
            @Override
            public void onNumberReturn(String number) {
//                CommonUtils.makeEventToast(mContext, number, false);
                mCount.setText(number);
            }

            @Override
            public void clearNumber() {
                CommonUtils.makeEventToast(mContext, "清除文本", false);
            }

            @Override
            public void onLongClickClearAllNumber(boolean b) {

            }
        });
    }

    public Dialog show() {
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 1200; // 设置宽度
//        lp.height = 910;//设置高度
        lp.height = 950;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCount.setFocusableInTouchMode(true);
            }
        }, 500);
        return mDialog;
    }

    /**
     * 禁止使用系统软件盘
     *
     * @param editText
     */
    public void disableShowInput(EditText editText) {
//        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(etMoney.getWindowToken(), 0);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                    boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(editText, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_confirm:
                // 关闭弹窗
                if (null != mDialog){
                    mDialog.dismiss();
                }

                onConfirmListener.onConfirm(mode, mCount.getText().toString());
                break;

            default:
                break;
        }
    }

    public interface OnConfirmListener{
        void onConfirm(String mode, String count);
    }
}
