package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by ybing on 2017/7/26.
 * 员工实体类
 */

public class EmployeeEntity implements Serializable{
    private String workState;
    private String employeePosition;

    public String getWorkState() {
        return workState;
    }

    public void setWorkState(String workState) {
        this.workState = workState;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "workState='" + workState + '\'' +
                ", employeePosition='" + employeePosition + '\'' +
                '}';
    }
}
