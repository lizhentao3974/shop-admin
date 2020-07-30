package com.fh.shop.admin.po.area;

import java.io.Serializable;

public class Area implements Serializable {
    private Long id;

    private String areaName;

    private Long fId;

    public Long getId() {
        return id;
    }

    public Area setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAreaName() {
        return areaName;
    }

    public Area setAreaName(String areaName) {
        this.areaName = areaName;
        return this;
    }

    public Long getfId() {
        return fId;
    }

    public Area setfId(Long fId) {
        this.fId = fId;
        return this;
    }
}
