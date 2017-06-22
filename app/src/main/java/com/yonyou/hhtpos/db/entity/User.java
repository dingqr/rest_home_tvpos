package com.yonyou.hhtpos.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by ljt at 16:22 on 2016/12/20.
 * E-Mail:ljt@yonyou.com
 * Xxx:Xxxxxxxxx
 */
@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;

    private String name;
    private int age;
    private int gender;
    @Generated(hash = 274504284)
    public User(Long id, String name, int age, int gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getGender() {
        return this.gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }

    

}
