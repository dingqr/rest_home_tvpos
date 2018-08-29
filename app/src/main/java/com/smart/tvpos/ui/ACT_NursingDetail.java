package com.smart.tvpos.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.adapter.ADA_CommonDetailPic;
import com.smart.tvpos.bean.ElderlyBasicInfoEntity;
import com.smart.tvpos.bean.ElderlyDetailInfoEntity;
import com.smart.tvpos.bean.ElderlyHealthDataEntity;
import com.smart.tvpos.bean.ElderlyMattressEntity;
import com.smart.tvpos.bean.ElderlyNurseJobEntity;
import com.smart.tvpos.bean.ElderlyStaffEntity;
import com.smart.tvpos.bean.SleepDataEntity;
import com.smart.tvpos.bean.SleepRealTimeEntity;
import com.smart.tvpos.global.API;
import com.smart.tvpos.manager.ReqCallBack;
import com.smart.tvpos.manager.RequestManager;
import com.smart.tvpos.util.Constants;
import com.smart.tvpos.util.DateUtils;
import com.smart.tvpos.widgets.LineChartView;
import com.smart.tvpos.widgets.SegmentBarChartView;
import com.smart.tvpos.widgets.ShadowCurveChartView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ACT_NursingDetail extends BaseActivity {

    //基本信息
    @Bind(R.id.tv_title)
    TextView titleTextView;
    @Bind(R.id.elderly_height)
    TextView heightView;
    @Bind(R.id.elderly_weight)
    TextView weightView;
    @Bind(R.id.elderly_food_prefer)
    TextView foodPreferView;
    @Bind(R.id.elderly_life_style)
    TextView lifeStyleView;
    @Bind(R.id.elderly_personality_character)
    TextView personCharacterView;
    @Bind(R.id.elderly_common_disease)
    TextView commonDiseaseView;

    //看护信息
    @Bind(R.id.elderly_name)
    TextView elderlyNameView;
    @Bind(R.id.elderly_age)
    TextView elderlyAgeView;
    @Bind(R.id.nursing_address)
    TextView nursingAddrView;
    @Bind(R.id.nursing_level)
    TextView nursingLevelView;
    @Bind(R.id.nursing_worker)
    TextView nursingWorkerView;
    @Bind(R.id.list_common_detail_pic)
    GridView listCommonDetailPicView;
    @Bind(R.id.elderly_avatar_detail)
    ImageView elderlyAvatarView;
    @Bind(R.id.iv_user)
    ImageView titleAvatarView;
    @Bind(R.id.img_present_none)
    ImageView imgPresentNone;

    //体检数据
    @Bind(R.id.day_current_show)
    TextView dayCurrentShow;
    @Bind(R.id.icon_changeable)
    ImageView iconChange;
    @Bind(R.id.body_examination_report)
    RadioGroup radioGroup;
    @Bind(R.id.shadow_line_chart_view)
    ShadowCurveChartView shadowCurveChartView;
    @Bind(R.id.line_chart_view)
    LineChartView lineChartView;
    @Bind(R.id.data_loading)
    ImageView dataLoading;
    @Bind(R.id.data_current_none)
    ImageView dataCurrentNone;

    //睡眠报告
    @Bind(R.id.week_date)
    TextView weekDateView;
    @Bind(R.id.this_week_sleep_data)
    TextView thisWeekSleepAveg;
    @Bind(R.id.last_week_sleep_data)
    TextView lastWeekSleepAveg;
    @Bind(R.id.this_week_sleep_chart)
    SegmentBarChartView segmentBarChartView;

    private String mElderlyName;
    private int mElderlyAge;
    private String mElderlySex;
    private String mElderlyAddress;
    private String mElderlyLevel;
    private String mAvatar;
    private int mInOutId;

    List<String> mThumbnailPics;
    ArrayList<String> mOriginPics;
    ADA_CommonDetailPic commonDetailPicAdapter;

    private List<SleepDataEntity> weekData = new ArrayList<SleepDataEntity>();

//    List<String> mdateList;
    Map<Integer, Float> mSbpList = new HashMap<>();
    Map<Integer, Float> mDbpList = new HashMap<>();
    Map<Integer, Float> mSpo2List = new HashMap<>();
    Map<Integer, Float> mFlipidsCholList = new HashMap<>();
    Map<Integer, Float> mPr2List = new HashMap<>();
    Map<Integer, Float> mHrList = new HashMap<>();
    Map<Integer, Float> mRespRrList = new HashMap<>();
    Map<Integer, Float> mGluList = new HashMap<>();
    Map<Integer, Float> mTempList = new HashMap<>();
    Map<Integer, Float> mFlipidsTrigList = new HashMap<>();
    Map<Integer, Float> mFlipidsHdlList = new HashMap<>();
    Map<Integer, Float> mFlipidsLdlList = new HashMap<>();
    Map<Integer, Float> mAssxhdbList = new HashMap<>();
    Map<Integer, Float> mHtcList = new HashMap<>();

    private List<SleepRealTimeEntity> mSleepRealTimeList = new ArrayList<>();
    //显示数据--1：昨天，2：前天 默认显示昨天
    private int requestDay = 1;
    //区分心率和呼吸：1：心率，2：呼吸
    private int dataType = 0;

    private static final String REQUSET_ELDERLY_DATA = "userEveryday";
    private static final String REQUSET_REAL_TIME_DATA = "userEveryday_healthData";

    @Override
    protected long getRefreshTime() {
        return 0;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

        if(null != extras){
            mElderlyName = extras.getString(Constants.ELDERLY_NAME);
            mElderlyAge = extras.getInt(Constants.ELDERLY_AGE);
            mElderlySex = extras.getString(Constants.ELDERLY_SEX);
            mElderlyAddress = extras.getString(Constants.ELDERLY_NURSING_ADDRESS);
            mAvatar = extras.getString(Constants.ELDERLY_AVATAR);
            mElderlyLevel = extras.getString(Constants.ELDERLY_NURSING_LEVEL);
            mInOutId = extras.getInt(Constants.ELDERLY_IN_OUT_ID);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_nursing_detail;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

        mThumbnailPics = new ArrayList<String>();
        mOriginPics = new ArrayList<String>();

        ButterKnife.bind(ACT_NursingDetail.this);

        requestElderlyData();
        //获取昨天的实时数据
        requestDayRealTimeData(DateUtils.getInstance().getDateBefore(12, "-"), false);
    }

    @OnClick(R.id.day_current_show)
    public void selectDayForData(View view){

        ArrayList<Map<Integer, Float>> listGroup = new ArrayList<Map<Integer, Float>>();
        switch (view.getId()){
            case R.id.day_current_show:

                if(requestDay == 1){
                    //当前数据时昨天，更新请求天和view显示为前天
                    dayCurrentShow.setText(R.string.the_day_before_yesterday);
                    requestDay = 2;
                }
                else {
                    dayCurrentShow.setText(R.string.yesterday);
                    requestDay = 1;
                }
                hideChartView();
                dataLoading.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(R.drawable.data_loading).into(dataLoading);
                requestDayRealTimeData(DateUtils.getInstance().getDateBefore(12, "-"), true);
                break;
            default:
        }
    }


    private void updateBasicInfo(ElderlyBasicInfoEntity details, List<ElderlyStaffEntity> staffList){

        if(null != details){
            if(null != details.getHeight()){
                heightView.setText(details.getHeight() + getString(R.string.unit_cm));
            }
            if(null != details.getWeight()){
                weightView.setText(details.getWeight() + getString(R.string.unit_kg));
            }
            String tmp = details.getEat();
            foodPreferView.setText((null == tmp) ? tmp : tmp.replace("/", "\n"));
            tmp = details.getHabit();
            lifeStyleView.setText((null == tmp) ? tmp : tmp.replace("/", "\n"));
            tmp = details.getCharacter();
            personCharacterView.setText((null == tmp) ? tmp : tmp.replace("/", "\n"));
            tmp = details.getDisease();
            commonDiseaseView.setText((null == tmp) ? tmp : tmp.replace("/", "\n"));
        }

        if(null != staffList && !staffList.isEmpty()){
            StringBuilder sb = new StringBuilder(nursingWorkerView.getText().toString());
            for(ElderlyStaffEntity staff : staffList){
                sb.append(staff.getName()).append(" ");
            }
            nursingWorkerView.setText(sb.toString());
        }
    }

    private void updateCommonDetailView(List<ElderlyNurseJobEntity> list){

        //
        titleTextView.setText(mElderlyName);
        elderlyNameView.setText(mElderlyName);
        elderlyAgeView.setText("" + mElderlyAge + "岁");
        nursingAddrView.setText(mElderlyAddress);
        nursingLevelView.setText(mElderlyLevel);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_user_avatar)
                .error(R.drawable.ic_user_avatar)
                .priority(Priority.HIGH)
                .transform(new CircleCrop());
        Glide.with(mContext)
                .load(API.IMG_SERVER_IP + mAvatar)
                .apply(requestOptions)
                .into(elderlyAvatarView);
        Glide.with(mContext)
                .load(API.IMG_SERVER_IP + mAvatar)
                .apply(requestOptions)
                .into(titleAvatarView);

        if(Constants.ELDERLY_SEX_FEMAIL.equals(mElderlySex)){
            Drawable drawable = getResources().getDrawable(R.drawable.icon_female);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
            // param 左上右下
            elderlyNameView.setCompoundDrawables(null, null, drawable, null);
        }

        //图片数据
        if(null != list && !list.isEmpty()){
            for(ElderlyNurseJobEntity nurseJob : list){
                mThumbnailPics.add(nurseJob.getImgPath());
                mOriginPics.add(nurseJob.getImgPathOriginal());
            }

            commonDetailPicAdapter = new ADA_CommonDetailPic(mContext, mThumbnailPics, mOriginPics);
            listCommonDetailPicView.setAdapter(commonDetailPicAdapter);

            imgPresentNone.setVisibility(View.GONE);
            listCommonDetailPicView.setVisibility(View.VISIBLE);
        }

        listCommonDetailPicView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(mContext, ACT_ImagePlayer.class);
//                intent.putExtra(Constants.ORIGIN_SELECTED_PIC_PATH, mOriginPics.get(position));
                intent.putExtra(Constants.ORIGIN_SELECTED_PIC_POSITION, position);
                intent.putStringArrayListExtra(Constants.ORIGIN_PIC_PATH_LIST, mOriginPics);

                startActivity(intent);
            }
        });
    }

    private void updateSleepReport(ElderlyDetailInfoEntity entity){

        thisWeekSleepAveg.setText(entity.getAverageT()+ getString(R.string.unit_h));
        lastWeekSleepAveg.setText(entity.getAverageL() + getString(R.string.unit_h));

        String weekBegin = DateUtils.getInstance().getDateWeekBegin();
        weekBegin = (null == weekBegin) ? null : weekBegin.replace("-", ".");
        String weekEnd = DateUtils.getInstance().getDateWeekEnd();
        weekEnd = DateUtils.getInstance().getMonthDay(weekEnd, "yyyy-MM-dd", ".");

        weekDateView.setText(weekBegin + " - " + weekEnd);

        List<ElderlyMattressEntity> list = entity.getMattressList();
        if(null != list && !list.isEmpty()){
            for(ElderlyMattressEntity dayEntity : list){

                int day = DateUtils.getInstance().toWeekDayIndex(dayEntity.getTime(), "yyyy-MM-dd");
                if(day != -1){
                    SleepDataEntity dayData = new SleepDataEntity(Float.parseFloat(dayEntity.getDeep()), Float.parseFloat(dayEntity.getLight()), day);
                    weekData.add(dayData);
                }

            }
        }
        segmentBarChartView.updateData(weekData);
    }

    private void updateHealthDataList(List<ElderlyHealthDataEntity> list){

        if(null == list || list.isEmpty()){
            return;
        }

        List<String> dateList = DateUtils.getInstance().getDatesBefore(14, ".");
        for(int i = list.size() - 1; i > 0; i--){
            ElderlyHealthDataEntity entity = list.get(i);

            if(null != entity.getCheckDate()){

                String date = DateUtils.getInstance().getMonthDay(entity.getCheckDate(), null, ".");
                int pos = dateList.indexOf(date);

                if(pos == -1){
                    continue;
                }
                //血压
                Float sbp = (null == entity.getSbp()) ? -1f : Float.parseFloat(entity.getSbp());
                Float dbp = (null == entity.getSbp()) ? -1f : Float.parseFloat(entity.getDbp());
                mSbpList.put(pos, sbp);
                mDbpList.put(pos,dbp);
                //血脂
                Float flipidsTrig = (null == entity.getFlipidsTrig()) ? -1f : Float.parseFloat(entity.getFlipidsTrig());
                Float flipidsChol = (null == entity.getFlipidsChol()) ? -1f : Float.parseFloat(entity.getFlipidsChol());
                Float flipidsHdl = (null == entity.getFlipidsHdl()) ? -1f : Float.parseFloat(entity.getFlipidsHdl());
                Float flipidsLDL = (null == entity.getFlipidsLDL()) ? -1f : Float.parseFloat(entity.getFlipidsLDL());
                mFlipidsTrigList.put(pos,flipidsTrig);
                mFlipidsCholList.put(pos,flipidsChol);
                mFlipidsHdlList.put(pos,flipidsHdl);
                mFlipidsLdlList.put(pos,flipidsLDL);
                //血氧
                Float spo2 = (null == entity.getSpo2()) ? -1f : Float.parseFloat(entity.getSpo2());
                mSpo2List.put(pos,spo2);
                //血糖
                Float glu = (null == entity.getGlu()) ? -1f : Float.parseFloat(entity.getGlu());
                mGluList.put(pos,glu);
//                //心电图
//                Float pr2 = (null == entity.getPr2()) ? 2f : Float.parseFloat(entity.getPr2());
//                Float hr = (null == entity.getHr()) ? 2f : Float.parseFloat(entity.getHr());
//                mPr2List.put(pos,pr2);
//                mHrList.put(pos,hr);
                //体温
                Float temp = (null == entity.getTemp()) ? 2f : Float.parseFloat(entity.getTemp());
                mTempList.put(pos,temp);
//                //血红蛋白
//                Float assxhdb = (null == entity.getAssxhdb()) ? 2f : Float.parseFloat(entity.getAssxhdb());
//                mAssxhdbList.put(pos,assxhdb);
                //血常规
                //尿常规
                //三分类
                //生化
                //糖化血红蛋白
            }
        }

        initBodyExaminationView();
    }

    private void initBodyExaminationView(){

        //默认显示血压
        ArrayList<Map<Integer, Float>> listGroup = new ArrayList<Map<Integer, Float>>();
        listGroup.add(mSbpList);
        listGroup.add(mDbpList);
        shadowCurveChartView.updateData(listGroup, Constants.BloodPressureAxisText, listGroup.size(), "mmHg");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                ArrayList<Map<Integer, Float>> listGroup = new ArrayList<Map<Integer, Float>>();
                dataType = 0;
                switch (checkedId) {
                    case R.id.blood_pressure:
                        if(checkData(mSbpList.values()) || checkData(mDbpList.values())){
                            listGroup.add(mSbpList);
                            listGroup.add(mDbpList);
                            showShadowChartView();
                            shadowCurveChartView.updateData(listGroup, Constants.BloodPressureAxisText, listGroup.size(), "mmHg");
                        }
                        else {
                            hideChartView();
                        }
                        break;
                    case R.id.blood_lipid:

//                        listGroup.add(mFlipidsTrigList);
//                        listGroup.add(mFlipidsHdlList);
//                        listGroup.add(mFlipidsLdlList);
//                        shadowCurveChartView.updateXAxisText(mXAxisTextDate);
                        if(checkData(mFlipidsCholList.values())){
                            showShadowChartView();
                            listGroup.add(mFlipidsCholList);
                            shadowCurveChartView.updateData(listGroup, Constants.BloodLipidAxisText, listGroup.size(), "");
                        }
                        else {
                            hideChartView();
                        }
                        break;
                    case R.id.blood_spo2:
                        if(checkData(mSpo2List.values())){
                            showShadowChartView();
                            listGroup.add(mSpo2List);
                            shadowCurveChartView.updateData(listGroup, Constants.BloodSpo2AxisText, listGroup.size(), "");
                        }
                        else {
                            hideChartView();
                        }
                        break;
                    case R.id.blood_sugar:
                        if(checkData(mGluList.values())){
                            showShadowChartView();
                            listGroup.add(mGluList);
                            shadowCurveChartView.updateData(listGroup, Constants.BloodSugarAxisText, listGroup.size(), "");
                        }
                        else {
                            hideChartView();
                        }
                        break;
                    case R.id.body_temperature:
                        if(checkData(mTempList.values())){
                            showShadowChartView();
                            listGroup.add(mTempList);
                            shadowCurveChartView.updateData(listGroup, Constants.TemperatureAxisText, listGroup.size(), "");
                        }
                        else {
                            hideChartView();
                        }
                        break;
                    case R.id.heart_rate:
                        dataType = 1;

                        //更新数据
                        if(requestDay == 1 && !mSleepRealTimeList.isEmpty()){
//                            lineChartView.updateData(mSleepRealTimeList, Constants.HeartRateAxisText, dataType, "");
//
//                            //控件布局更新
//                            showLineChartView();
                            updateSleepRealTimeView(true);
                        }
                        else {
                            requestDay = 1;
                            dayCurrentShow.setText(R.string.yesterday);
                            hideChartView();
                            dataLoading.setVisibility(View.VISIBLE);
                            Glide.with(mContext).load(R.drawable.data_loading).into(dataLoading);
                            requestDayRealTimeData(DateUtils.getInstance().getDateBefore(12, "-"),true);
                        }
                        break;
                    case R.id.breathe:
                        dataType = 2;
                        showLineChartView();

                        if(requestDay == 1 && !mSleepRealTimeList.isEmpty()){
                            updateSleepRealTimeView(true);
//                            lineChartView.updateData(mSleepRealTimeList, Constants.BloodSugarAxisText, dataType, "");
                        }
                        else {
//                            requestDayRealTimeData(DateUtils.getInstance().getDateBefore(requestDay, "-"));
                            //tmp
                            requestDay = 1;
                            dayCurrentShow.setText(R.string.yesterday);
                            hideChartView();
                            dataLoading.setVisibility(View.VISIBLE);
                            Glide.with(mContext).load(R.drawable.data_loading).into(dataLoading);
                            requestDayRealTimeData(DateUtils.getInstance().getDateBefore(12, "-"), true);
                        }
                        break;
//                    case R.id.electrocardiogram:
////                        listGroup.add(mPr2List);
////                        listGroup.add(mHrList);
//                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
//                        break;
//                    case R.id.blood_routine:
//                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
//                        break;
//                    case R.id.urine_routine:
//                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
//                        break;
//                    case R.id.hemoglobin:
////                        listGroup.add(mAssxhdbList);
//                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
//                        break;
//                    case R.id.three_classification:
//                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
//                        break;
//                    case R.id.biochemistry:
//                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
//                        break;
//                    case R.id.glycated_hemoglobin:
//                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
//                        break;
                }
            }
        });
    }

    private boolean checkData(Collection<Float> list){

        for(float f : list){
            if(f != -1.0f){
                return true;
            }
        }
        return false;
    }

    private void showShadowChartView(){
        dataLoading.setVisibility(View.GONE);
        dayCurrentShow.setVisibility(View.INVISIBLE);
        iconChange.setVisibility(View.INVISIBLE);
        lineChartView.setVisibility(View.GONE);
        shadowCurveChartView.setVisibility(View.VISIBLE);

        dataCurrentNone.setVisibility(View.GONE);
    }

    private void showLineChartView(){

        dayCurrentShow.setVisibility(View.VISIBLE);
        iconChange.setVisibility(View.VISIBLE);
        lineChartView.setVisibility(View.VISIBLE);
        shadowCurveChartView.setVisibility(View.GONE);

        dataCurrentNone.setVisibility(View.GONE);
    }

    private void hideChartView(){

        dayCurrentShow.setVisibility(View.GONE);
        iconChange.setVisibility(View.GONE);
        lineChartView.setVisibility(View.GONE);
        shadowCurveChartView.setVisibility(View.GONE);

        dataCurrentNone.setVisibility(View.VISIBLE);
    }

    private void updateSleepRealTimeView(boolean updateInstant){

        if(dataType == 0 || !updateInstant){
            return;
        }
        dataLoading.setVisibility(View.GONE);
        if(mSleepRealTimeList.isEmpty()){
            hideChartView();
        }
        else {
            showLineChartView();
            if(dataType == 1){
                lineChartView.updateData(mSleepRealTimeList, Constants.HeartRateAxisText, dataType, "");
            }
            else if(dataType == 2) {
                lineChartView.updateData(mSleepRealTimeList, Constants.BreathAxisText, dataType, "");
            }
        }
    }

    private void requestElderlyData(){

        HashMap<String, String> params = new HashMap<>();
        params.put("a", REQUSET_ELDERLY_DATA);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        params.put("inOutId", String.valueOf(mInOutId));
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<ElderlyDetailInfoEntity>() {

            @Override
            public void onReqSuccess(ElderlyDetailInfoEntity result) {

                updateBasicInfo(result.getDetails(), result.getStaffList());
                updateSleepReport(result);
                updateCommonDetailView(result.getNurseJobList());
                updateHealthDataList(result.getHealthDataListS());
                Log.d("aqua", "onReqSuccess: rusult : " + result.toString());
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

    private void requestDayRealTimeData(String day, final boolean updateInstant){

        HashMap<String, String> params = new HashMap<>();
        params.put("a", REQUSET_REAL_TIME_DATA);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        params.put("inOutId", String.valueOf(mInOutId));
        params.put("date", day);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<List<SleepRealTimeEntity>>() {

            @Override
            public void onReqSuccess(List<SleepRealTimeEntity> result) {

                mSleepRealTimeList = result;
                updateSleepRealTimeView(updateInstant);
                Log.d("aqua", "onReqSuccess: rusult : " + result.size());
            }

            @Override
            public void onFailure(String result) {
                updateSleepRealTimeView(updateInstant);
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                updateSleepRealTimeView(updateInstant);
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }
}
