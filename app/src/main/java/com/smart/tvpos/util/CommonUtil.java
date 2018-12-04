package com.smart.tvpos.util;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static <T> List<T> fromJsonArray(JSONArray jsonArray, Class clazz){
        List tList = new ArrayList<>();
        if(null == jsonArray || jsonArray.size() == 0){
            return tList;
        }

        for(int i = 0; i < jsonArray.size(); i++){
            tList.add(JSON.parseObject(jsonArray.get(i).toString(), clazz));
        }
        return tList;
    }

    /**
     * 对rgb色彩加入透明度
     * @param alpha     透明度，取值范围 0.0f -- 1.0f.
     * @param baseColor
     * @return a color with alpha made from base color
     */
    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

    public static int[] formatArithmeticSequence(int maxNum, int size){
        int dlta = maxNum / size;
        int[] list = new int[size];
        if(dlta == 0){
            for (int i = 0; i < size; ++i){
                list[i] = 50 * i;
            }
        }
        else {
            for (int i = 1 ;; i++) {
                if(dlta < 10){
                    dlta = 10;
                    break;
                }
                else if(dlta < 20){
                    dlta = 20;
                    break;
                }
                else if(dlta < 30){
                    dlta = 30;
                    break;
                }
                else if(dlta < 51 * i) {
                    dlta = 50 * i;
                    break;
                }
            }

            for (int i = 0; i < size; ++i){
                list[i] = dlta * i;
            }
        }
        return list;
    }
}
