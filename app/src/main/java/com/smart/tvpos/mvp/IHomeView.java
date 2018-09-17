package com.smart.tvpos.mvp;


import com.smart.framework.library.base.BaseView;
import com.smart.tvpos.bean.AdmitLivingEntity;
import com.smart.tvpos.bean.BranchAddressEntity;
import com.smart.tvpos.bean.ChartCommonEntity;
import com.smart.tvpos.bean.EquipmentStatusEntity;
import com.smart.tvpos.bean.HomeHeadEntity;
import com.smart.tvpos.bean.JobItemEntity;
import com.smart.tvpos.bean.LatestWarnEntity;
import com.smart.tvpos.bean.NurseLevelEntity;
import com.smart.tvpos.bean.StaffEntity;
import com.smart.tvpos.bean.TrendDataEntity;
import com.smart.tvpos.bean.UserHealthDataNum;
import com.smart.tvpos.bean.WarningEntity;

import java.util.List;

/**
 * Created by JoJo on 20178/6/18.
 * wechat：18510829974
 * description：
 */
public interface IHomeView extends BaseView {
    void getHeaderData(HomeHeadEntity bean);

//    void getAdmitLivingData(AdmitLivingEntity bean);

    void getLivingUserData(List<ChartCommonEntity> dataList);

    void getAlertData(WarningEntity bean);

    void getUserHealthDataNum(UserHealthDataNum dataList);

    void getUserNurseData(List<NurseLevelEntity> dataList);

    void getBraceletNew(List<EquipmentStatusEntity> dataList);

    void getMattressNew(List<EquipmentStatusEntity> dataList);

    void getStaffData(List<StaffEntity> dataList);

    void getBranchAddress(List<BranchAddressEntity> dataList);

    void getNurseProgressList(List<JobItemEntity> dataList);

    void getUserWarning6(List<LatestWarnEntity> dataList);
}
