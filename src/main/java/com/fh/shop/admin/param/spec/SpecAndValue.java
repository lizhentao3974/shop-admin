package com.fh.shop.admin.param.spec;

import java.io.Serializable;

public class SpecAndValue implements Serializable {
    private String specNames;

    private String nameSorts;

    private String specValues;

    public String getSpecNames() {
        return specNames;
    }

    public SpecAndValue setSpecNames(String specNames) {
        this.specNames = specNames;
        return this;
    }

    public String getNameSorts() {
        return nameSorts;
    }

    public SpecAndValue setNameSorts(String nameSorts) {
        this.nameSorts = nameSorts;
        return this;
    }

    public String getSpecValues() {
        return specValues;
    }

    public SpecAndValue setSpecValues(String specValues) {
        this.specValues = specValues;
        return this;
    }
}
