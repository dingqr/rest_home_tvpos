package com.smart.tvpos.mvp;

/**
 * Created by JoJo on 2018/6/23.
 * wechat：18510829974
 * description：
 */
public interface IHomePresenter {
    void getHeaderData(String requestType);

    void getLivingUserData(String requestType);

    void getAlertData(String requestType);

    void getAbilityNum(String requestType);

    void getUserHealthDataNum(String requestType);

    void getUserNurseData(String requestType);

    void getBraceletNew(String requestType);

    void getMattressNew(String requestType);

    void getStaffData(String requestType);

    void getBranchAddress(String requestType);

    void getNurseProgressList(String requestType);

    void getParty(String requestType);

    void getUserWarning6(String requestType);

    void getAfficheNew(String requestType);
}
