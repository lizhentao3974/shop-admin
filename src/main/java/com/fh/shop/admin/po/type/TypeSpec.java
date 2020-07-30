package com.fh.shop.admin.po.type;

import java.io.Serializable;

public class TypeSpec implements Serializable {

    private Long id;

    private Long typeId;

    private Long specId;

    public Long getId() {
        return id;
    }

    public TypeSpec setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getTypeId() {
        return typeId;
    }

    public TypeSpec setTypeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public Long getSpecId() {
        return specId;
    }

    public TypeSpec setSpecId(Long specId) {
        this.specId = specId;
        return this;
    }
}
