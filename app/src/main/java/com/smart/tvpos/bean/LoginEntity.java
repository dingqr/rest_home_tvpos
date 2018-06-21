package com.smart.tvpos.bean;

import java.util.List;

/**
 * Created by JoJo on 2018/6/21.
 * wechat：18510829974
 * description：登录返回的实体
 */
public class LoginEntity {

    /**
     * status : success
     * success : true
     * error : false
     * data : {"sign":"MjlESlM4TjZHQUU5Q0k=","id":29,"name":"hafuadmin"}
     * msg : 操作成功
     * errorCode : 0
     * debug : {"persist":0.047182,"msgs":["SQL Connect in 0.007787s at SFrame::run::12 resthome at 47.94.149.206:3306","SQL Execute in 0.013932s at SFrame::run::12 INSERT INTO logRequest(`m`,`c`,`a`,`request`,`ip`,`cookie`,`session`,`created`,`updated`) VALUES('api','AdminTv','login','{\\\"m\\\":\\\"api\\\",\\\"c\\\":\\\"AdminTv\\\",\\\"a\\\":\\\"login\\\",\\\"name\\\":\\\"hafuadmin\\\",\\\"password\\\":\\\"666666\\\"}','123.116.246.107','[]','[]','2018-06-21 18:48:39','2018-06-21 18:48:39')","SQL Query in 0.004385s at TAdminUserBase->row::312 SELECT * FROM adminUser   WHERE `name`='hafuadmin'    LIMIT 0,1","SQL All 3 Access Consume 0.026s"]}
     */

    private String status;
    private boolean success;
    private boolean error;
    private UserEntity data;
    private String msg;
    private int errorCode;
    private DebugBean debug;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public UserEntity getData() {
        return data;
    }

    public void setData(UserEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public DebugBean getDebug() {
        return debug;
    }

    public void setDebug(DebugBean debug) {
        this.debug = debug;
    }

    public static class DataBean {
        /**
         * sign : MjlESlM4TjZHQUU5Q0k=
         * id : 29
         * name : hafuadmin
         */

        private String sign;
        private int id;
        private String name;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class DebugBean {
        /**
         * persist : 0.047182
         * msgs : ["SQL Connect in 0.007787s at SFrame::run::12 resthome at 47.94.149.206:3306","SQL Execute in 0.013932s at SFrame::run::12 INSERT INTO logRequest(`m`,`c`,`a`,`request`,`ip`,`cookie`,`session`,`created`,`updated`) VALUES('api','AdminTv','login','{\\\"m\\\":\\\"api\\\",\\\"c\\\":\\\"AdminTv\\\",\\\"a\\\":\\\"login\\\",\\\"name\\\":\\\"hafuadmin\\\",\\\"password\\\":\\\"666666\\\"}','123.116.246.107','[]','[]','2018-06-21 18:48:39','2018-06-21 18:48:39')","SQL Query in 0.004385s at TAdminUserBase->row::312 SELECT * FROM adminUser   WHERE `name`='hafuadmin'    LIMIT 0,1","SQL All 3 Access Consume 0.026s"]
         */

        private double persist;
        private List<String> msgs;

        public double getPersist() {
            return persist;
        }

        public void setPersist(double persist) {
            this.persist = persist;
        }

        public List<String> getMsgs() {
            return msgs;
        }

        public void setMsgs(List<String> msgs) {
            this.msgs = msgs;
        }
    }
}
