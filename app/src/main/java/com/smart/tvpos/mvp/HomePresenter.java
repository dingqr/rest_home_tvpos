package com.smart.tvpos.mvp;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.bean.AbilityNumEntity;
import com.smart.tvpos.bean.AdmitLivingEntity;
import com.smart.tvpos.bean.BranchAddressEntity;
import com.smart.tvpos.bean.ChartCommonEntity;
import com.smart.tvpos.bean.EquipmentStatusEntity;
import com.smart.tvpos.bean.HomeHeadEntity;
import com.smart.tvpos.bean.JobItemEntity;
import com.smart.tvpos.bean.LatestDynamicEntity;
import com.smart.tvpos.bean.LatestWarnEntity;
import com.smart.tvpos.bean.NurseLevelEntity;
import com.smart.tvpos.bean.StaffEntity;
import com.smart.tvpos.bean.TrendDataEntity;
import com.smart.tvpos.bean.UserHealthDataNum;
import com.smart.tvpos.bean.WarningEntity;
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
//    @Override
//    public void getAdmitLivingData(String requestType) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("a", requestType);
//        params.put("id", Constants.USER_ID);
//        params.put("sign", Constants.USER_SIGN);
//        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<AdmitLivingEntity>() {
//
//            @Override
//            public void onReqSuccess(AdmitLivingEntity result) {
//                mView.getAdmitLivingData(result);
//            }
//
//            @Override
//            public void onFailure(String result) {
//                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
//            }
//
//            @Override
//            public void onReqFailed(ErrorBean error) {
//                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
//            }
//        });
//    }

    /**
     * 3. 入住用户
     *
     * @param requestType user
     */
    @Override
    public void getLivingUserData(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String result) {
                Elog.e("TAG", "user=" + result);
                mView.getLivingUserData(resolveLivingUserJson(result));
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
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<WarningEntity>() {

            @Override
            public void onReqSuccess(WarningEntity result) {
                Elog.e("TAG", "warning=" + result.getNumA());
                mView.getAlertData(result);
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

    @Override
    public void getAbilityNum(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<List<AbilityNumEntity>>() {

            @Override
            public void onReqSuccess(List<AbilityNumEntity> result) {
                mView.getAbilityNum(result);
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


    @Override
    public void getUserHealthDataNum(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<UserHealthDataNum>() {

            @Override
            public void onReqSuccess(UserHealthDataNum result) {
                mView.getUserHealthDataNum(result);
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
//    @Override
//    public void getLivingTrendData(String requestType) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("a", requestType);
//        params.put("id", Constants.USER_ID);
//        params.put("sign", Constants.USER_SIGN);
//        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<String>() {
//
//            @Override
//            public void onReqSuccess(String result) {
//                Elog.e("TAG", "branchUserTrend=" + result);
//                mView.getLivingTrendData(resolveTrendJson(result));
//            }
//
//            @Override
//            public void onFailure(String result) {
//                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
//            }
//
//            @Override
//            public void onReqFailed(ErrorBean error) {
//                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
//            }
//        });
//    }


    /**
     * 6.	护理级别
     *
     * @param requestType userByNurse
     */
    @Override
    public void getUserNurseData(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<List<NurseLevelEntity>>() {

            @Override
            public void onReqSuccess(List<NurseLevelEntity> result) {
                Elog.e("TAG", "nurse=" + result);
                mView.getUserNurseData(result);
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

    @Override
    public void getBraceletNew(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<List<EquipmentStatusEntity>>() {

            @Override
            public void onReqSuccess(List<EquipmentStatusEntity> result) {
                Elog.e("TAG", "nurse=" + result);
//                mView.getBraceletNew(result);
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

    @Override
    public void getMattressNew(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<List<EquipmentStatusEntity>>() {

            @Override
            public void onReqSuccess(List<EquipmentStatusEntity> result) {
                Elog.e("TAG", "nurse=" + result);
                mView.getMattressNew(result);
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
     * 7.	员工统计
     *
     * @param requestType staff
     */
    @Override
    public void getStaffData(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<List<StaffEntity>>() {

            @Override
            public void onReqSuccess(List<StaffEntity> result) {
                Elog.e("TAG", "staff=" + result);
                mView.getStaffData(result);
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
     * 8：分院的护理进度
     */

    /**
     * 分院的地址
     * @param requestType  branchList
     */
    @Override
    public void getBranchAddress(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<List<BranchAddressEntity>>() {

            @Override
            public void onReqSuccess(List<BranchAddressEntity> result) {
                Elog.e("TAG", "branchList=" + result);
                mView.getBranchAddress(result);
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
     * 9.	分院已完成/未完成项目
     * @param requestType jobItem
     */
    @Override
    public void getNurseProgressList(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<List<JobItemEntity>>() {

            @Override
            public void onReqSuccess(List<JobItemEntity> result) {
                Elog.e("TAG", "jobItem=" + result);
                mView.getNurseProgressList(result);
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

    @Override
    public void getUserWarning6(String requestType) {

        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<List<LatestWarnEntity>>() {

            @Override
            public void onReqSuccess(List<LatestWarnEntity> result) {
                mView.getUserWarning6(result);
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

    @Override
    public void getAfficheNew(String requestType) {

        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<List<LatestDynamicEntity>>() {

            @Override
            public void onReqSuccess(List<LatestDynamicEntity> result) {
                mView.getAfficheNew(result);
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

    //入住用户:总数
    public int livingTotal;

    /**
     * 解析入住用户的json数据
     *
     * @param jsonString
     */
    private List<ChartCommonEntity> resolveLivingUserJson(String jsonString) {
        List<ChartCommonEntity> mLivingUserList = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Integer>>() {
        }.getType();
        Map<String, Integer> map = new HashMap<>();
        if (!TextUtils.isEmpty(jsonString)) {
            map = gson.fromJson(jsonString, type);
        }
        for (Map.Entry<String, Integer> item : map.entrySet()) {
            Elog.e("map", item.getKey() + "---|---" + item.getValue().toString());
            ChartCommonEntity commonEntity = new ChartCommonEntity();
            commonEntity.keyName = item.getKey();
            commonEntity.value = item.getValue();
            commonEntity.showTxt = item.getKey();
            livingTotal += item.getValue();
            mLivingUserList.add(commonEntity);
        }
//        Elog.e("TAG", "livingTotal=" + livingTotal);
        return mLivingUserList;
    }

//    /**
//     * 解析入住养老院\用户整体趋势的json数据
//     *
//     * @param jsonString
//     */
//    private List<TrendDataEntity> resolveTrendJson(String jsonString) {
//        List<TrendDataEntity> mTrendList = new ArrayList<>();
//        Gson gson = new Gson();
//        Type type = new TypeToken<Map<String, TrendDataEntity>>() {
//        }.getType();
//        Map<String, TrendDataEntity> map = new HashMap<>();
//        if (!TextUtils.isEmpty(jsonString)) {
//            map = gson.fromJson(jsonString, type);
//        }
//        for (Map.Entry<String, TrendDataEntity> item : map.entrySet()) {
//            Elog.e("map", item.getKey() + "---|---" + item.getValue().toString());
//            TrendDataEntity bean = item.getValue();
//            bean.setKeyName(item.getKey());
//            mTrendList.add(bean);
//        }
//        return mTrendList;
//    }
}
