package com.fh.shop.admin.param.spec;

import java.io.Serializable;

public class SpecUpdateParam implements Serializable {

    private Long id;

    private String specName;

    private String nameSort;

    private String specValues;

    public Long getId() {
        return id;
    }

    public SpecUpdateParam setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSpecName() {
        return specName;
    }

    public SpecUpdateParam setSpecName(String specName) {
        this.specName = specName;
        return this;
    }

    public String getNameSort() {
        return nameSort;
    }

    public SpecUpdateParam setNameSort(String nameSort) {
        this.nameSort = nameSort;
        return this;
    }

    public String getSpecValues() {
        return specValues;
    }

    public SpecUpdateParam setSpecValues(String specValues) {
        this.specValues = specValues;
        return this;
    }
}
