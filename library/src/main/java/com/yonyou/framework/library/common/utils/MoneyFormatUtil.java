package com.yonyou.framework.library.common.utils;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.yonyou.framework.library.R;
import com.yonyou.framework.library.common.CommonUtils;

import java.text.DecimalFormat;

/**
 * Author: ljt@yonyou.com
 * Date&Time: 2017/03/03,09:58
 * For：EditText输入金额时的校验规则工具类
 */
public class MoneyFormatUtil {

    /** 默认最大整数位数 */
    private static final int DEFAULT_MAX_INTEGER_COUNT = 5;
    /** 默认最大小数位数 */
    private static final int DEFAULT_MAX_DECIMAL_COUNT = 2;

    /**
     * 规范金额输入格式
     * @param mEditText 当前输入金额的EditText
     * @param view 确认按钮，用于设置未输入时不可点击，需要为该View设置background，设置其state_enabled属性
     *             可传入RadioButton、TextView、LinearLayout等
     */
    public static void formatMoney(EditText mEditText, View view) {
        if(null != view) {
            setTextWatcher(mEditText,view);
        }
        setTextFilter(mEditText);
    }

    /**
     * 屏蔽不规范的金额格式，默认可输入两位小数
     * @param context
     * @param etPayment
     * @return
     */
    public static String getFormattedMoney(Context context, EditText etPayment) {
        String moneyAmount = etPayment.getText().toString().trim();

        // 不足两位小数时，自动补全
        moneyAmount = new DecimalFormat("0.00").format(Double.valueOf(moneyAmount));

        // 删除多余的0
        int num = moneyAmount.length();
        int zeroIndex = -1;
        for (int i = 0; i < num; i++) {
            char c = moneyAmount.charAt(i);
            if (c != '0') {
                zeroIndex = i;
                break;
            } else if (i == num - 4) {
                // 因为已经自动补全两位小数，所以数字最小长度即为4位：0.00
                // 所以如果0出现的位置为第0个位置时，则将0标记为zeroIndex
                zeroIndex = i;
                break;
            }
        }
        if (zeroIndex != -1) {
            moneyAmount = moneyAmount.substring(zeroIndex);
        }

        // 屏蔽输入金额为0、或者只输入 . 的情况
        if (moneyAmount.equals("0.00") || (moneyAmount.contains(".") && moneyAmount.lastIndexOf(".")==moneyAmount.length()-1)) {
            CommonUtils.makeEventToast(context, context.getResources().getString(R.string.limit_money_amount), false);
            return "";
        }

        return moneyAmount;
    }
    
    // 设置EditText的输入监视器
    private static void setTextWatcher(final EditText mEditText, final View vPayment) {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //只有输入了金额时，确认修改按钮才可以点击
                if (TextUtils.isEmpty(mEditText.getText().toString())) {
                    vPayment.setEnabled(false);
                } else {
                    vPayment.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 设置EditText输入内容的过滤器
     * @param mEditText
     */
    private static void setTextFilter(EditText mEditText) {
        mEditText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        mEditText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // 只输入"."时自动补零
                if (dest.toString().length() == 0 && source.equals(".")) {
                    return "0.";
                }

                // 设置小数点前可输入最多位数
                // 当前金额已经满足最大位数，但当前输入的是小数点，则允许输入
                if (dest.toString().length() == DEFAULT_MAX_INTEGER_COUNT && source.equals(".")) {
                    return ".";
                }

                // 当前金额数已经满足最多位数，并且当前输入非小数点，则不允许继续输入
                if (dest.toString().length() == DEFAULT_MAX_INTEGER_COUNT && !dest.toString().contains(".")) {
                    return "";
                }

                // 设置小数点后可输入最多位数
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if (mlength >= DEFAULT_MAX_DECIMAL_COUNT + 1) {
                        return "";
                    }
                }
                return null;
            }
        }});

    }

}
