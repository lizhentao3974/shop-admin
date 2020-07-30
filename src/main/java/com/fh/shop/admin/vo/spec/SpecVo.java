package com.fh.shop.admin.vo.spec;

import com.fh.shop.admin.po.spec.SpecName;
import com.fh.shop.admin.po.spec.SpecValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpecVo implements Serializable {

    private SpecName specName = new SpecName();

    private List<SpecValue> specValues = new ArrayList();

    public SpecName getSpecName() {
        return specName;
    }

    public SpecVo setSpecName(SpecName specName) {
        this.specName = specName;
        return this;
    }

    public List<SpecValue> getSpecValues() {
        return specValues;
    }

    public SpecVo setSpecValues(List<SpecValue> specValues) {
        this.specValues = specValues;
        return this;
    }
}
