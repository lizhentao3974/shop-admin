package com.fh.shop.admin.po.attr;

import java.io.Serializable;

public class AttrValue implements Serializable {

    private Long id;

    private String attrValue;

    private Long attrId;

    private Long typeId;

    public Long getTypeId() {
        return typeId;
    }

    public AttrValue setTypeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public Long getAttrId() {
        return attrId;
    }

    public AttrValue setAttrId(Long attrId) {
        this.attrId = attrId;
        return this;
    }

    public Long getId() {
        return id;
    }

    public AttrValue setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public AttrValue setAttrValue(String attrValue) {
        this.attrValue = attrValue;
        return this;
    }
}
