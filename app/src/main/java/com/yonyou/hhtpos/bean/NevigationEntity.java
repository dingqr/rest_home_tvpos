package com.yonyou.hhtpos.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2017/6/23.
 * 邮箱：zjuan@yonyou.com
 * 描述：左侧导航栏实体
 */
public class NevigationEntity {
    /**
     * 用户头像
     */
    public String user_logo;
    /**
     * 消息数量
     */
    public String message_count;
    /**
     * 父菜单数据
     */
    public List<String> groupData = new ArrayList<>();
    /**
     * 子菜单数据
     */
    public List<List<String>> childDta = new ArrayList<>();
    /**
     * 营业时段
     */
    public String business_time;
    /**
     * 当前登录店铺
     */
    public String shop_name;
}
