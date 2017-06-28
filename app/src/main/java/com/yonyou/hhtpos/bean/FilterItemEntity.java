package com.yonyou.hhtpos.bean;

import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 餐厅预定筛选控件化数据实体类
 * 作者：ybing on 2017/6/27 15:30
 * 邮箱：ybing@yonyou.com
 */
public class FilterItemEntity implements Serializable, Cloneable{

    /**筛选列表选项 table options*/
    private ArrayList<FilterOptionsEntity> options;
    /**筛选列表表头 tabletitle*/
    private String title;
    /**筛选列表RecylerView的布局管理器*/
    private RecyclerView.LayoutManager layoutManager;


    public FilterItemEntity clone() {
        FilterItemEntity bean = null;
        try {
            bean = (FilterItemEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public String toString() {
        return "FilterItemEntity{" +
                "options=" + options +
                ", title='" + title + '\'' +
                ", layoutManager=" + layoutManager +
                '}';
    }

    public FilterItemEntity() {
    }

    public FilterItemEntity(ArrayList<FilterOptionsEntity> options, String title,
                            RecyclerView.LayoutManager layoutManager) {
        this.options = options;
        this.title = title;
        this.layoutManager = layoutManager;
    }

    public ArrayList<FilterOptionsEntity> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<FilterOptionsEntity> options) {
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }
}
