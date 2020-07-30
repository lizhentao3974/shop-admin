package com.fh.shop.admin.vo.type;

import com.fh.shop.admin.po.attr.Attr;
import com.fh.shop.admin.po.type.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TypeVo implements Serializable {

    private Type type = new Type();

    private List<Long> brandIdList = new ArrayList<>();

    private List<Long> specIdList = new ArrayList<>();

    private List<Attr> attrList = new ArrayList<>();

    public Type getType() {
        return type;
    }

    public TypeVo setType(Type type) {
        this.type = type;
        return this;
    }

    public List<Long> getBrandIdList() {
        return brandIdList;
    }

    public TypeVo setBrandIdList(List<Long> brandIdList) {
        this.brandIdList = brandIdList;
        return this;
    }

    public List<Long> getSpecIdList() {
        return specIdList;
    }

    public TypeVo setSpecIdList(List<Long> specIdList) {
        this.specIdList = specIdList;
        return this;
    }

    public List<Attr> getAttrList() {
        return attrList;
    }

    public TypeVo setAttrList(List<Attr> attrList) {
        this.attrList = attrList;
        return this;
    }
}
