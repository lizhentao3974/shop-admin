package com.fh.shop.admin.param.type;

import java.io.Serializable;

public class TypeUpdateParam implements Serializable {
    private Long typeId;

    private String typeName;

    private String brandIds;

    private String specIds;

    private String attrNames;

    private String attrValues;

    public Long getTypeId() {
        return typeId;
    }

    public TypeUpdateParam setTypeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public TypeUpdateParam setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public TypeUpdateParam setBrandIds(String brandIds) {
        this.brandIds = brandIds;
        return this;
    }

    public String getSpecIds() {
        return specIds;
    }

    public TypeUpdateParam setSpecIds(String specIds) {
        this.specIds = specIds;
        return this;
    }

    public String getAttrNames() {
        return attrNames;
    }

    public TypeUpdateParam setAttrNames(String attrNames) {
        this.attrNames = attrNames;
        return this;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public TypeUpdateParam setAttrValues(String attrValues) {
        this.attrValues = attrValues;
        return this;
    }
}
