package com.fh.shop.admin.po.spec;

import java.io.Serializable;

public class SpecValue implements Serializable {
    private Long id;

    private String specValue;

    private Integer valueSort;

    private Long specId;

    public Long getId() {
        return id;
    }

    public SpecValue setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSpecValue() {
        return specValue;
    }

    public SpecValue setSpecValue(String specValue) {
        this.specValue = specValue;
        return this;
    }

    public Integer getValueSort() {
        return valueSort;
    }

    public SpecValue setValueSort(Integer valueSort) {
        this.valueSort = valueSort;
        return this;
    }

    public Long getSpecId() {
        return specId;
    }

    public SpecValue setSpecId(Long specId) {
        this.specId = specId;
        return this;
    }
}
