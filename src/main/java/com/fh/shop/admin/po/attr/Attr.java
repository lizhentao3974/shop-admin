package com.fh.shop.admin.po.attr;

import java.io.Serializable;

public class Attr implements Serializable {

    private Long id;

    private String attrName;

    private Long typeId;

    private String attrValue;

    public String getAttrValue() {
        return attrValue;
    }

    public Attr setAttrValue(String attrValue) {
        this.attrValue = attrValue;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Attr setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAttrName() {
        return attrName;
    }

    public Attr setAttrName(String attrName) {
        this.attrName = attrName;
        return this;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Attr setTypeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }
}
