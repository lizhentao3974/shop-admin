package com.fh.shop.admin.po.spec;

import java.io.Serializable;

public class SpecName implements Serializable {
    private Long id;

    private String specName;

    private Integer nameSort;

    public Long getId() {
        return id;
    }

    public SpecName setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSpecName() {
        return specName;
    }

    public SpecName setSpecName(String specName) {
        this.specName = specName;
        return this;
    }

    public Integer getNameSort() {
        return nameSort;
    }

    public SpecName setNameSort(Integer nameSort) {
        this.nameSort = nameSort;
        return this;
    }
}
