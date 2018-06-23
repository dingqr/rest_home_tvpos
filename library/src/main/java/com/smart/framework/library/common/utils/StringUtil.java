package com.smart.framework.library.common.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by qyd on 2016/12/14.
 */

public class StringUtil {

    /**
     * 返回不为NULL的字符串
     *
     * @param string
     * @return
     */
    public static String getString(String string) {
        return string == null ? "" : string;
    }

    public static String getString(int string) {
        return string + "";
    }

    public static String getString(long string) {
        return string + "";
    }

    public static String getString(double string) {
        return string + "";
    }

    /**
     * 返回纠正科学计数法后的金额字符串
     * 如果金额为空，或者为null，则返回空字符串
     *
     * @param moneyAmount
     * @return
     */
    public static String getFormattedMoney(String moneyAmount) {
        if (!TextUtils.isEmpty(moneyAmount)) {
            double parseDouble = Double.parseDouble(new BigDecimal(getString(moneyAmount)).toPlainString());
            return new DecimalFormat("0.00").format(parseDouble);
        }
        return "";
    }

    /**
     * 转成折扣格式
     *
     * @param discountRate
     * @return
     */
    public static String getFormatDiscount(String discountRate) {
        double v = Double.parseDouble(discountRate) / 10.00;
        DecimalFormat mFormat = new DecimalFormat("0.0");
        return mFormat.format(discountRate);
    }

    /**
     * 获取格式化的保留两位数的数
     */
    public static String getFormatPercentRate(float dataValue) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(dataValue);
    }
}
