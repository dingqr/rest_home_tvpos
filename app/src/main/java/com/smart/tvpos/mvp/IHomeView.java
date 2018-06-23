package com.smart.tvpos.mvp;


import com.smart.framework.library.base.BaseView;
import com.smart.tvpos.bean.AdmitLivingEntity;
import com.smart.tvpos.bean.ChartCommonEntity;
import com.smart.tvpos.bean.HomeHeadEntity;

import java.util.List;

/**
 * Created by JoJo on 20178/6/18.
 * wechat：18510829974
 * description：
 */
public interface IHomeView extends BaseView {
    void getHeaderData(HomeHeadEntity bean);
    void getAdmitLivingData(AdmitLivingEntity bean);
    void getLivingUserData(List<ChartCommonEntity> dataList);
    void getAlertData(ChartCommonEntity bean);
    void getLivingTrendData(ChartCommonEntity bean);
}
