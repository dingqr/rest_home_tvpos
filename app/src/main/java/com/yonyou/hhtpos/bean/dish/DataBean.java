package com.yonyou.hhtpos.bean.dish;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2017/7/21.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
public class DataBean implements Serializable {
    private List<DishPracticeEntity> practices = new ArrayList<>();
    private List<DishRemarkEntity> remarks = new ArrayList<>();
    private List<DishStandardEntity> standards = new ArrayList<>();

    public List<DishPracticeEntity> getPractices() {
        return practices;
    }

    public void setPractices(List<DishPracticeEntity> practices) {
        this.practices = practices;
    }

    public List<DishRemarkEntity> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<DishRemarkEntity> remarks) {
        this.remarks = remarks;
    }

    public List<DishStandardEntity> getStandards() {
        return standards;
    }

    public void setStandards(List<DishStandardEntity> standards) {
        this.standards = standards;
    }
}
