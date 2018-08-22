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
import com.smart.tvpos.global.API;
import com.smart.tvpos.manager.ReqCallBack;
import com.smart.tvpos.manager.RequestManager;
import com.smart.tvpos.util.Constants;
import com.smart.tvpos.util.DateUtils;
import com.smart.tvpos.widgets.SegmentBarChartView;
import com.smart.tvpos.widgets.ShadowCurveChartView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

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
    @Bind(R.id.body_examination_report)
    RadioGroup radioGroup;
    @Bind(R.id.curveViewUp)
    ShadowCurveChartView shadowCurveChartView;

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

    List<String> mdateList = new ArrayList<String>();
    List<Float> mSbpList = new ArrayList<Float>();
    List<Float> mDbpList = new ArrayList<Float>();
    List<Float> mSpo2List = new ArrayList<Float>();
    List<Float> mFlipidsCholList = new ArrayList<Float>();
    List<Float> mPr2List = new ArrayList<Float>();
    List<Float> mHrList = new ArrayList<Float>();
    List<Float> mRespRrList = new ArrayList<Float>();
    List<Float> mGluList = new ArrayList<Float>();
    List<Float> mTempList = new ArrayList<Float>();
    List<Float> mFlipidsTrigList = new ArrayList<Float>();
    List<Float> mFlipidsHdlList = new ArrayList<Float>();
    List<Float> mFlipidsLdlList = new ArrayList<Float>();
    List<Float> mAssxhdbList = new ArrayList<Float>();
    List<Float> mHtcList = new ArrayList<Float>();

    private static final String REQUSET_ELDERLY_DATA = "userEveryday";

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

        requestElderlyData();

//        test();
    }

    private void test(){

        String data = new String("{\"details\":{\"userId\":1,\"inOutId\":1,\"bedId\":105,\"nurseId\":1,\"height\":\"177\",\"weight\":\"70\"," +
                "\"disease\":\"高血压,冠心病\",\"habit\":\"生活习惯\",\"character\":\"性格特点\",\"eat\":\"饮食偏好\",\"birth\":\"1955-11-22\"," +
                "\"id\":1,\"nurseName\":\"专护\",\"buildingId\":1,\"floorId\":6,\"roomId\":21,\"bedName\":\"1号床位\",\"buildingName\":\"哈福主楼\",\"floorName\":\"6F\"," +
                "\"roomName\":\"601\",\"age\":62}," +
                "\"staff\":[{\"staffId\":1,\"name\":\"曹志祥\",\"id\":1},{\"staffId\":6,\"name\":\"魔笛\",\"id\":6},{\"staffId\":123,\"name\":\"陈峥嵘\",\"id\":123}]," +
                "\"nurseJob\":[{\"id\":93098,\"imgPath\":\"upload\\/api\\/job\\/job_123_1_143_20180709042216_460.jpg\"," +
                "\"imgPathOriginal\":\"upload\\/api\\/job\\/job_123_1_143_20180709042216.jpg\"}," +
                "{\"id\":93097,\"imgPath\":\"upload\\/api\\/job\\/job_123_1_139_20180709041508_460.jpg\"," +
                "\"imgPathOriginal\":\"upload\\/api\\/job\\/job_123_1_139_20180709041508.jpg\"}," +
                "{\"id\":93096,\"imgPath\":\"upload\\/api\\/job\\/job_123_1_138_20180709041002_460.jpg\"," +
                "\"imgPathOriginal\":\"upload\\/api\\/job\\/job_123_1_138_20180709041002.jpg\"}," +
                "{\"id\":53659,\"imgPath\":\"upload\\/api\\/job\\/job_1_1_1_20180717092407_460.jpg\"," +
                "\"imgPathOriginal\":\"upload\\/api\\/job\\/job_1_1_1_20180717092407.jpg\"}," +
                "{\"id\":36228,\"imgPath\":\"upload\\/api\\/job\\/job_1_1_1_20180625035726_460.jpg\"," +
                "\"imgPathOriginal\":\"upload\\/api\\/job\\/job_1_1_1_20180625035726.jpg\"}]," +
                "\"healthDataList\":[" +
                "{\"checkDate\":\"2018-12-11 15:18:32\",\"sbp\":\"125\",\"dbp\":\"73\",\"spo2\":\"97\",\"glu\":\"8\",\"flipidsChol\":\"36.3\"}," +
                "{\"checkDate\":\"2018-08-09 11:12:16\",\"sbp\":\"121\",\"dbp\":\"73\",\"spo2\":\"97\",\"glu\":\"5\",\"flipidsChol\":\"36.6\"}," +
                "{\"checkDate\":\"2018-08-08 10:24:33\",\"sbp\":\"116\",\"dbp\":\"66\",\"spo2\":\"97\",\"glu\":\"10\",\"flipidsChol\":\"36.5\"}," +
                "{\"checkDate\":\"2018-08-07 15:18:32\",\"sbp\":\"125\",\"dbp\":\"73\",\"spo2\":\"97\",\"glu\":\"8\",\"flipidsChol\":\"36.3\"}," +
                "{\"checkDate\":\"2018-08-03 11:12:16\",\"sbp\":\"121\",\"dbp\":\"73\",\"spo2\":\"97\",\"glu\":\"4\",\"flipidsChol\":\"36.6\"}," +
                "{\"checkDate\":\"2018-08-02 10:24:33\",\"sbp\":\"116\",\"dbp\":\"66\",\"spo2\":\"97\",\"glu\":\"7\",\"flipidsChol\":\"36.5\"}," +
                "{\"checkDate\":\"2018-07-30 11:21:43\",\"sbp\":\"115\",\"dbp\":\"54\",\"spo2\":\"96\",\"glu\":\"10\",\"flipidsChol\":\"36.7\"}," +
                "{\"checkDate\":\"2018-07-29 10:00:05\",\"sbp\":\"125\",\"dbp\":\"70\",\"spo2\":\"97\",\"glu\":\"5\",\"flipidsChol\":\"36.5\"}," +
                "{\"checkDate\":\"2018-07-25 11:21:02\",\"sbp\":\"99\",\"dbp\":\"59\",\"spo2\":\"97\",\"glu\":\"4\",\"flipidsChol\":\"36.6\"}," +
                "{\"checkDate\":\"2018-07-19 10:04:39\",\"sbp\":\"119\",\"dbp\":\"75\",\"spo2\":\"97\",\"glu\":\"2\",\"flipidsChol\":\"36.7\"}]," +
                "\"mattressList\":[{\"awake\":165,\"light\":3.5,\"deep\":3.5,\"time\":\"2018-08-12\"}," +
                "{\"awake\":112,\"light\":3.5,\"deep\":1.5,\"time\":\"2018-08-13\"},{\"awake\":2.2,\"light\":4.5,\"deep\":2.0,\"time\":\"2018-08-14\"}," +
                "{\"awake\":114,\"light\":4.5,\"deep\":1.5,\"time\":\"2018-08-15\"},{\"awake\":115,\"light\":6,\"deep\":2.5,\"time\":\"2018-08-16\"}]," +
                "\"averageT\":540,\"averageL\":608}");

        ElderlyDetailInfoEntity entity = new ElderlyDetailInfoEntity();
        entity.fromJsonString(data);

        updateBasicInfo(entity.getDetails(), entity.getStaffList());
        updateSleepReport(entity);
        updateCommonDetailView(entity.getNurseJobList());
        updateHealthDataList(entity.getHealthDataListS());
    }

    private void updateBasicInfo(ElderlyBasicInfoEntity details, List<ElderlyStaffEntity> staffList){

        if(null != details){
            heightView.setText(details.getHeight() + "cm");
            weightView.setText(details.getWeight() + "kg");
            foodPreferView.setText(details.getEat());
            lifeStyleView.setText(details.getHabit());
            personCharacterView.setText(details.getCharacter());
            commonDiseaseView.setText(details.getDisease());
        }

        if(null != staffList && !staffList.isEmpty()){
            StringBuilder sb = new StringBuilder(nursingWorkerView.getText().toString());
            for(ElderlyStaffEntity staff : staffList){
                sb.append(staff.getName()).append(" ");
            }
            nursingWorkerView.setText(sb.toString());
        }
    }

    private void updateSleepReport(ElderlyDetailInfoEntity entity){

        thisWeekSleepAveg.setText(entity.getAverageT() + "h");
        lastWeekSleepAveg.setText(entity.getAverageL() + "h");

        String weekBegin = DateUtils.getInstance().getDateWeekBegin();
        String weekEnd = DateUtils.getInstance().getDateWeekEnd();
        weekEnd = DateUtils.getInstance().getMonthDay(weekEnd, "yyyy-MM-dd", "-");

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

        for(ElderlyHealthDataEntity entity : list){
            if(null != entity.getCheckDate()){

                String date = DateUtils.getInstance().getMonthDay(entity.getCheckDate(), null, ".");
                mdateList.add(date);
                //血压
                Float sbp = (null == entity.getSbp()) ? 2f : Float.parseFloat(entity.getSbp());
                Float dbp = (null == entity.getSbp()) ? 2f : Float.parseFloat(entity.getDbp());
                mSbpList.add(sbp);
                mDbpList.add(dbp);
                //血脂
                Float flipidsTrig = (null == entity.getFlipidsTrig()) ? 2f : Float.parseFloat(entity.getFlipidsTrig());
                Float flipidsChol = (null == entity.getFlipidsChol()) ? 2f : Float.parseFloat(entity.getFlipidsChol());
                Float flipidsHdl = (null == entity.getFlipidsHdl()) ? 2f : Float.parseFloat(entity.getFlipidsHdl());
                Float flipidsLDL = (null == entity.getFlipidsLDL()) ? 2f : Float.parseFloat(entity.getFlipidsLDL());
                mFlipidsTrigList.add(flipidsTrig);
                mFlipidsCholList.add(flipidsChol);
                mFlipidsHdlList.add(flipidsHdl);
                mFlipidsLdlList.add(flipidsLDL);
                //血糖
                Float glu = (null == entity.getGlu()) ? 2f : Float.parseFloat(entity.getGlu());
                mGluList.add(glu);
                //心电图
                Float pr2 = (null == entity.getPr2()) ? 2f : Float.parseFloat(entity.getPr2());
                Float hr = (null == entity.getHr()) ? 2f : Float.parseFloat(entity.getHr());
                mPr2List.add(pr2);
                mHrList.add(hr);
                //体温
                Float temp = (null == entity.getTemp()) ? 2f : Float.parseFloat(entity.getTemp());
                mTempList.add(temp);
                //血红蛋白
                Float assxhdb = (null == entity.getAssxhdb()) ? 2f : Float.parseFloat(entity.getAssxhdb());
                mAssxhdbList.add(assxhdb);
                //血常规
                //尿常规
                //三分类
                //生化
                //糖化血红蛋白
            }
        }

        initBodyExaminationView();
    }

    private void requestElderlyData(){

        HashMap<String, String> params = new HashMap<>();
        params.put("a", REQUSET_ELDERLY_DATA);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
//        params.put("inOutId", String.valueOf(mInOutId));
        params.put("inOutId", String.valueOf(1));
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
                intent.putExtra(Constants.ORIGIN_SELECTED_PIC_PATH, mOriginPics.get(position));
                intent.putExtra(Constants.ORIGIN_SELECTED_PIC_POSITION, position);
                intent.putStringArrayListExtra(Constants.ORIGIN_PIC_PATH_LIST, mOriginPics);

                startActivity(intent);
            }
        });
    }

    private void initBodyExaminationView(){

        //默认显示血压
        ArrayList<List<Float>> listGroup = new ArrayList<List<Float>>();
        listGroup.add(mSbpList);
        listGroup.add(mDbpList);
        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "mmHg");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                ArrayList<List<Float>> listGroup = new ArrayList<List<Float>>();
                switch (checkedId) {
                    case R.id.blood_pressure:
                        listGroup.add(mSbpList);
                        listGroup.add(mDbpList);
                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "mmHg");
                        break;
                    case R.id.blood_lipid:
                        listGroup.add(mFlipidsCholList);
//                        listGroup.add(mFlipidsTrigList);
//                        listGroup.add(mFlipidsHdlList);
//                        listGroup.add(mFlipidsLdlList);
                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
                        break;
                    case R.id.blood_sugar:
                        listGroup.add(mGluList);
                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodSugarAxisText, listGroup.size(), "");
                        break;
                    case R.id.electrocardiogram:
//                        listGroup.add(mPr2List);
//                        listGroup.add(mHrList);
                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
                        break;
                    case R.id.body_temperature:
//                        listGroup.add(mTempList);
                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
                        break;
                    case R.id.blood_routine:
                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
                        break;
                    case R.id.urine_routine:
                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
                        break;
                    case R.id.hemoglobin:
//                        listGroup.add(mAssxhdbList);
                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
                        break;
                    case R.id.three_classification:
                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
                        break;
                    case R.id.biochemistry:
                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
                        break;
                    case R.id.glycated_hemoglobin:
                        shadowCurveChartView.updateData(listGroup, mdateList, Constants.BloodPressureAxisText, listGroup.size(), "");
                        break;
                }
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
