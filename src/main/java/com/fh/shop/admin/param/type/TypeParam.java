package com.fh.shop.admin.param.type;

import java.io.Serializable;

public class TypeParam implements Serializable {

    private String typeName;

    private String brandIds;

    private String specIds;

    private String attrNames;

    private String attrValues;

    public String getAttrNames() {
        return attrNames;
    }

    public TypeParam setAttrNames(String attrNames) {
        this.attrNames = attrNames;
        return this;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public TypeParam setAttrValues(String attrValues) {
        this.attrValues = attrValues;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public TypeParam setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public TypeParam setBrandIds(String brandIds) {
        this.brandIds = brandIds;
        return this;
    }

    public String getSpecIds() {
        return specIds;
    }

    public TypeParam setSpecIds(String specIds) {
        this.specIds = specIds;
        return this;
    }
}
