package com.fh.shop.admin.po.type;

import java.io.Serializable;

public class TypeBrand implements Serializable {

    private Long id;

    private Long typeId;

    private Long brandId;

    public Long getId() {
        return id;
    }

    public TypeBrand setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getTypeId() {
        return typeId;
    }

    public TypeBrand setTypeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public Long getBrandId() {
        return brandId;
    }

    public TypeBrand setBrandId(Long brandId) {
        this.brandId = brandId;
        return this;
    }
}
