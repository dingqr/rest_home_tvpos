package com.smart.tvpos.bean;

import java.util.Date;

public class LatestDynamicEntity {
    private String id;
    private String name;
    private Date partyStart;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPartyStart() {
        return partyStart;
    }

    public void setPartyStart(Date partyStart) {
        this.partyStart = partyStart;
    }
}
