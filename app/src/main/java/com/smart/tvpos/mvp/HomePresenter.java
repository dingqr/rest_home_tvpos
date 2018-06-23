package com.smart.tvpos.mvp;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.bean.AdmitLivingEntity;
import com.smart.tvpos.bean.ChartCommonEntity;
import com.smart.tvpos.bean.HomeHeadEntity;
import com.smart.tvpos.global.API;
import com.smart.tvpos.manager.ReqCallBack;
import com.smart.tvpos.manager.RequestManager;
import com.smart.tvpos.util.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JoJo on 2018/6/23.
 * wechat：18510829974
 * description：
 */
public class HomePresenter implements IHomePresenter {
    private IHomeView mView;

    public HomePresenter(IHomeView baseView) {
        this.mView = baseView;
    }

    /**
     * 1. 入住养老院数等
     *
     * @param requestType branchNum
     */
    @Override
    public void getHeaderData(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("name", "hafuadmin");
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<HomeHeadEntity>() {

            @Override
            public void onReqSuccess(HomeHeadEntity result) {
                mView.getHeaderData(result);
            }

            @Override
            public void onFailure(String result) {
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    /**
     * 2. 接待入住
     *
     * @param requestType admitInOut
     */
    @Override
    public void getAdmitLivingData(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("name", "hafuadmin");
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<AdmitLivingEntity>() {

            @Override
            public void onReqSuccess(AdmitLivingEntity result) {
                mView.getAdmitLivingData(result);
            }

            @Override
            public void onFailure(String result) {
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    /**
     * 3. 入住用户
     *
     * @param requestType user
     */
    @Override
    public void getLivingUserData(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("name", "hafuadmin");
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String result) {
                Log.e("TAG", "user=" + result);
                resolveJson(result);
                mView.getLivingUserData(mLivingUserList);
            }

            @Override
            public void onFailure(String result) {
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    /**
     * 4.	近三个月报警数据
     *
     * @param requestType warning
     */
    @Override
    public void getAlertData(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("name", "hafuadmin");
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String result) {
                Log.e("TAG", "user=" + result);
            }

            @Override
            public void onFailure(String result) {
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    /**
     * 5.	入住养老入住\用户整体趋势
     *
     * @param requestType branchUserTrend
     */
    @Override
    public void getLivingTrendData(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("name", "hafuadmin");
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String result) {
                Log.e("TAG", "admitInOut=" + result);
            }

            @Override
            public void onFailure(String result) {
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    private List<ChartCommonEntity> mLivingUserList = new ArrayList<>();

    /**
     * 手动解析json数组
     *
     * @param jsonString
     */
    private Map<String, Integer> resolveJson(String jsonString) {

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Integer>>() {
        }.getType();
        Map<String, Integer> map = new HashMap<>();
        if (!TextUtils.isEmpty(jsonString)) {
            map = gson.fromJson(jsonString, type);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            Log.e("TAG", entry.getKey() + "---|---" + entry.getValue().toString());
            ChartCommonEntity commonEntity = new ChartCommonEntity();
            commonEntity.keyName = entry.getKey();
            commonEntity.value = entry.getValue();
            commonEntity.showTxt = entry.getKey();
            mLivingUserList.add(commonEntity);
        }
        return map;
    }

//
//    private void formatJsonData(Map<String, ChartCommonEntity> mapList) {
//        for (Map.Entry<String, ChartCommonEntity> entry : mapList.entrySet()) {
//            Log.e("TAG", entry.getKey() + "|" + entry.getValue().toString());
////            ChartCommonEntity bean = entry.getValue();
////            bean.keyName = entry.getKey();
////            mRecordList.add(bean);
//        }
//    }
}
