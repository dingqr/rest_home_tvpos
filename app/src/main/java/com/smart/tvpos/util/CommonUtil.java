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
}
