package com.smart.tvpos.bean;

/**
 * Created by JoJo on 2018/6/23.
 * wechat：18510829974
 * description：报警数据
 */
public class WarningEntity {

    /**
     * status : success
     * success : true
     * error : false
     * data : {"numY":127779,"numN":51,"numA":127830}
     * msg : 操作成功
     * errorCode : 0
     * debug : {"persist":0.752572,"msgs":["SQL Connect in 0.033481s at SFrame::run::12 resthome at 47.94.149.206:3306","SQL Execute in 0.083929s at SFrame::run::12 INSERT INTO logRequest(`m`,`c`,`a`,`request`,`ip`,`cookie`,`session`,`created`,`updated`) VALUES('api','AdminTv','warning','{\\\"m\\\":\\\"api\\\",\\\"c\\\":\\\"AdminTv\\\",\\\"a\\\":\\\"warning\\\",\\\"sign\\\":\\\"MjlESlM4TjZHQUU5Q0k=\\\",\\\"id\\\":\\\"29\\\"}','123.113.29.199','{\\\"PHPSESSID\\\":\\\"avouq1cn3ue77t6hqlmt5ken22\\\"}','[]','2018-06-23 17:26:50','2018-06-23 17:26:50')","SQL Query in 0.027134s at TAdminUserBase->row::312 SELECT * FROM adminUser   WHERE id=29    LIMIT 0,1","SQL Query in 0.216129s at CAdminTv->warning::283 SELECT count(*) AS `cnt` FROM waring   WHERE `branchId`='1' AND `created` > '18-03-23' AND state!='未解决'    LIMIT 0,1","SQL Query in 0.204299s at CAdminTv->warning::285 SELECT count(*) AS `cnt` FROM waring   WHERE `branchId`='1' AND `created` > '18-03-23' AND `state`='未解决'    LIMIT 0,1","SQL Query in 0.170473s at CAdminTv->warning::287 SELECT count(*) AS `cnt` FROM waring   WHERE `branchId`='1' AND `created` > '18-03-23'    LIMIT 0,1","SQL All 6 Access Consume 0.735s"]}
     */
    /**
     * numY : 127779
     * numN : 51
     * numA : 127830
     */
    //首页数据监控最近三个月报警数据统计结果
    private int numY;//已解决数
    private int numN;//未解决数
    private int numA;//总数

    //实体共用——监控概览页面报警数据实体返回字段
    /**
     * numHealth	生命体征异常报警数据
     numSleep	离床报警数据
     numMonitoring	当日离线/离开/进入范围报警数据

     numHelp	当日一键求助报警数据
     */
    private int numHelp;
    private int numSleep;
    private int numMonitoring;
    private int numHealth;

    //离线、活跃人数
    private int in;//活跃
    private int out;//离线

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public int getNumY() {
        return numY;
    }

    public void setNumY(int numY) {
        this.numY = numY;
    }

    public int getNumN() {
        return numN;
    }

    public void setNumN(int numN) {
        this.numN = numN;
    }

    public int getNumA() {
        return numA;
    }

    public void setNumA(int numA) {
        this.numA = numA;
    }

    public int getNumHelp() {
        return numHelp;
    }

    public void setNumHelp(int numHelp) {
        this.numHelp = numHelp;
    }

    public int getNumSleep() {
        return numSleep;
    }

    public void setNumSleep(int numSleep) {
        this.numSleep = numSleep;
    }

    public int getNumMonitoring() {
        return numMonitoring;
    }

    public void setNumMonitoring(int numMonitoring) {
        this.numMonitoring = numMonitoring;
    }

    public int getNumHealth() {
        return numHealth;
    }

    public void setNumHealth(int numHealth) {
        this.numHealth = numHealth;
    }
}
