package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by dell on 2017/6/30.
 */

public class TableEntity implements Serializable, Cloneable {
    private TableCapacityEntity tce;
    private FilterOptionsEntity foe;

    public TableEntity( FilterOptionsEntity foe,TableCapacityEntity tce) {
        this.tce = tce;
        this.foe = foe;
    }

    public TableCapacityEntity getTce() {
        return tce;
    }

    public void setTce(TableCapacityEntity tce) {
        this.tce = tce;
    }

    public FilterOptionsEntity getFoe() {
        return foe;
    }

    public void setFoe(FilterOptionsEntity foe) {
        this.foe = foe;
    }
}
