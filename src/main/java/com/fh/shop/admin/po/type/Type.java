package com.fh.shop.admin.po.type;

import java.io.Serializable;

public class Type implements Serializable {
    private Long id;

    private String typeName;

    public Long getId() {
        return id;
    }

    public Type setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public Type setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }
}
