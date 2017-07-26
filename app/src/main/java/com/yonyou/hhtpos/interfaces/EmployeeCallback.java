package com.yonyou.hhtpos.interfaces;

import com.yonyou.hhtpos.bean.EmployeeEntity;

/**
 * Created by dell on 2017/7/26.
 * 员工筛选对话框 获取员数据后传递数据用的接口
 */

public interface EmployeeCallback {
    void sendItems(EmployeeEntity bean);
}
