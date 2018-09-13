package com.smart.tvpos.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

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
}
