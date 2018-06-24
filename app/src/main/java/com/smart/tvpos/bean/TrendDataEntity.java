package com.smart.tvpos.bean;

/**
 * Created by JoJo on 2018/6/24.
 * wechat：18510829974
 * description：入住养老入住\用户整体趋势 实体
 */
public class TrendDataEntity {

    /**
     * branchAllN : 1 养老院累计数量
     * branchNewN : 1 养老院新增数量
     * userNewN : 1   用户新增数量
     * userAllN : 35  用户累计数量
     */

    private int branchAllN;
    private int branchNewN;
    private int userNewN;
    private int userAllN;
    private String keyName;//月份

    public int getBranchAllN() {
        return branchAllN;
    }

    public void setBranchAllN(int branchAllN) {
        this.branchAllN = branchAllN;
    }

    public int getBranchNewN() {
        return branchNewN;
    }

    public void setBranchNewN(int branchNewN) {
        this.branchNewN = branchNewN;
    }

    public int getUserNewN() {
        return userNewN;
    }

    public void setUserNewN(int userNewN) {
        this.userNewN = userNewN;
    }

    public int getUserAllN() {
        return userAllN;
    }

    public void setUserAllN(int userAllN) {
        this.userAllN = userAllN;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

}
/**
 * {
 * "status": "success",
 * "success": true,
 * "error": false,
 * "data": {
 * "02": {
 * "branchAllN": 1,
 * "branchNewN": 1,
 * "userNewN": 0,
 * "userAllN": 33
 * },
 * "03": {
 * "branchAllN": 1,
 * "branchNewN": 1,
 * "userNewN": 1,
 * "userAllN": 34
 * },
 * "04": {
 * "branchAllN": 1,
 * "branchNewN": 1,
 * "userNewN": 1,
 * "userAllN": 35
 * },
 * "05": {
 * "branchAllN": 1,
 * "branchNewN": 1,
 * "userNewN": 2,
 * "userAllN": 37
 * },
 * "06": {
 * "branchAllN": 1,
 * "branchNewN": 1,
 * "userNewN": 2,
 * "userAllN": 39
 * }
 * },
 * "msg": "操作成功",
 * "errorCode": 0,
 * "debug": {
 * "persist": 0.478779,
 * "msgs": [
 * "SQL Connect in 0.030376s at SFrame::run::12 resthome at 47.94.149.206:3306",
 * "SQL Execute in 0.078074s at SFrame::run::12 INSERT INTO logRequest(`m`,`c`,`a`,`request`,`ip`,`cookie`,`session`,`created`,`updated`) VALUES('api','AdminTv','branchUserTrend','{\\\"m\\\":\\\"api\\\",\\\"c\\\":\\\"AdminTv\\\",\\\"a\\\":\\\"branchUserTrend\\\",\\\"sign\\\":\\\"MjlESlM4TjZHQUU5Q0k=\\\",\\\"id\\\":\\\"29\\\"}','123.113.29.199','[]','[]','2018-06-24 20:11:13','2018-06-24 20:11:13')",
 * "SQL Query in 0.024118s at TAdminUserBase->row::312 SELECT * FROM adminUser   WHERE id=29    LIMIT 0,1",
 * "SQL Query in 0.024071s at CAdminTv->branchUserTrend::239 SELECT count(*) AS `cnt` FROM userInOut   WHERE `branchId`='1' AND `created` BETWEEN '18-02-01' AND '18-03-01'    LIMIT 0,1",
 * "SQL Query in 0.03774s at CAdminTv->branchUserTrend::241 SELECT count(*) AS `cnt` FROM userInOut   WHERE `branchId`='1' AND `created` BETWEEN '18-03-01' AND '18-04-01'    LIMIT 0,1",
 * "SQL Query in 0.042821s at CAdminTv->branchUserTrend::243 SELECT count(*) AS `cnt` FROM userInOut   WHERE `branchId`='1' AND `created` BETWEEN '18-04-01' AND '18-05-01'    LIMIT 0,1",
 * "SQL Query in 0.03981s at CAdminTv->branchUserTrend::245 SELECT count(*) AS `cnt` FROM userInOut   WHERE `branchId`='1' AND `created` BETWEEN '18-05-01' AND '18-06-01'    LIMIT 0,1",
 * "SQL Query in 0.031792s at CAdminTv->branchUserTrend::247 SELECT count(*) AS `cnt` FROM userInOut   WHERE `branchId`='1' AND `created` BETWEEN '18-06-01' AND '18-06-24 20:11:13'    LIMIT 0,1",
 * "SQL Query in 0.031768s at CAdminTv->branchUserTrend::251 SELECT count(*) AS `cnt` FROM userInOut   WHERE `branchId`='1' AND `created` < '18-03-01'    LIMIT 0,1",
 * "SQL Query in 0.025793s at CAdminTv->branchUserTrend::253 SELECT count(*) AS `cnt` FROM userInOut   WHERE `branchId`='1' AND `created` < '18-04-01'    LIMIT 0,1",
 * "SQL Query in 0.02779s at CAdminTv->branchUserTrend::255 SELECT count(*) AS `cnt` FROM userInOut   WHERE `branchId`='1' AND `created` < '18-05-01'    LIMIT 0,1",
 * "SQL Query in 0.035809s at CAdminTv->branchUserTrend::257 SELECT count(*) AS `cnt` FROM userInOut   WHERE `branchId`='1' AND `created` < '18-06-01'    LIMIT 0,1",
 * "SQL Query in 0.037819s at CAdminTv->branchUserTrend::259 SELECT count(*) AS `cnt` FROM userInOut   WHERE `branchId`='1' AND `created` < '18-06-24 20:11:13'    LIMIT 0,1",
 * "SQL All 13 Access Consume 0.468s"
 * ]
 * }
 * }
 */