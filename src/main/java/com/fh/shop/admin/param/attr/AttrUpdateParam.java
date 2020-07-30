package com.fh.shop.admin.param.attr;

import java.io.Serializable;

public class AttrUpdateParam implements Serializable {

    private Long attrId;

    private String attrName;

    private Long typeId;

    private String attrValues;

    public Long getAttrId() {
        return attrId;
    }

    public AttrUpdateParam setAttrId(Long attrId) {
        this.attrId = attrId;
        return this;
    }

    public String getAttrName() {
        return attrName;
    }

    public AttrUpdateParam setAttrName(String attrName) {
        this.attrName = attrName;
        return this;
    }

    public Long getTypeId() {
        return typeId;
    }

    public AttrUpdateParam setTypeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public AttrUpdateParam setAttrValues(String attrValues) {
        this.attrValues = attrValues;
        return this;
    }
}
