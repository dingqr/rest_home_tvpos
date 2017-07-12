package com.yonyou.hhtpos.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 左侧导航栏新的实体类
 * 作者：liushuofei on 2017/7/11 17:38
 */
public class NavigationNewEntity implements Serializable {

    /**一级目录名 */
    private String functionName;
    /**一级目录id */
    private String id;
    /**二级列表 */
    private List<SecondList> childList;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SecondList> getChildList() {
        return childList;
    }

    public void setChildList(List<SecondList> childList) {
        this.childList = childList;
    }

    public class SecondList implements Serializable{
        /**二级目录名 */
        private String functionName;
        /**二级目录id */
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFunctionName() {
            return functionName;
        }

        public void setFunctionName(String functionName) {
            this.functionName = functionName;
        }
    }
}
