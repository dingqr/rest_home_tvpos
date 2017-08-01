package com.yonyou.hhtpos.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


/**
 * Created by qyd on 2016/12/12.
 * 用户实体对象
 */

@Entity
public class UserEntity {


    private Long id;

    /**手机号*/
    @Id
    public String phone;
    /**用户名*/
    public String name;
    /**加密ID*/
    public String token;
    /**所属机构*/
    public String its_name;
    /**餐厅ID*/
    public String shop_id;
    /**消费企业ID*/
    public String company_id;
    /**用户头像*/
    public String logo;
    /**用户头像（新字段）*/
    public String head_image;
//    /**角色 0普通用户1- 餐厅管理员 2-消费企业管理员 4-餐饮企业管理员 8-消费企业普通员工 16-餐厅接单员 32-餐厅服务员*/
    /**角色 0普通用户 2-消费企业管理员 8-消费企业普通员工 */
    public int role;
    /**用户user_id*/
    public String user_id;
    /**尊称性别*/
    public Integer customer_gender;
    /**尊称名称*/
    public String customer_name;

    /**企业认证 0-认证，1-未认证*/
    private int source;
    /**雇员id*/
    private String employeeId;
    /**雇员名字*/
    private String employeeName;
    /**雇员头像*/
    private String employeeHead;

    @Generated(hash = 1729990473)
    public UserEntity(Long id, String phone, String name, String token,
            String its_name, String shop_id, String company_id, String logo,
            String head_image, int role, String user_id, Integer customer_gender,
            String customer_name, int source) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.token = token;
        this.its_name = its_name;
        this.shop_id = shop_id;
        this.company_id = company_id;
        this.logo = logo;
        this.head_image = head_image;
        this.role = role;
        this.user_id = user_id;
        this.customer_gender = customer_gender;
        this.customer_name = customer_name;
        this.source = source;
    }
    @Generated(hash = 1433178141)
    public UserEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getIts_name() {
        return this.its_name;
    }
    public void setIts_name(String its_name) {
        this.its_name = its_name;
    }
    public String getShop_id() {
        return this.shop_id;
    }
    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
    public String getCompany_id() {
        return this.company_id;
    }
    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
    public String getLogo() {
        return this.logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getHead_image() {
        return this.head_image;
    }
    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }
    public int getRole() {
        return this.role;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public String getUser_id() {
        return this.user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public Integer getCustomer_gender() {
        return this.customer_gender;
    }
    public void setCustomer_gender(Integer customer_gender) {
        this.customer_gender = customer_gender;
    }
    public String getCustomer_name() {
        return this.customer_name;
    }
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    public int getSource() {
        return this.source;
    }
    public void setSource(int source) {
        this.source = source;
    }


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeHead() {
        return employeeHead;
    }

    public void setEmployeeHead(String employeeHead) {
        this.employeeHead = employeeHead;
    }
}
