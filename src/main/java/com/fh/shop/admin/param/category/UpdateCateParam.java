package com.fh.shop.admin.param.category;

import com.fh.shop.admin.po.category.Category;

import java.io.Serializable;

public class UpdateCateParam implements Serializable {

    private Category category;

    private Integer relateFlag;

    private String ids;

    public Category getCategory() {
        return category;
    }

    public UpdateCateParam setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Integer getRelateFlag() {
        return relateFlag;
    }

    public UpdateCateParam setRelateFlag(Integer relateFlag) {
        this.relateFlag = relateFlag;
        return this;
    }

    public String getIds() {
        return ids;
    }

    public UpdateCateParam setIds(String ids) {
        this.ids = ids;
        return this;
    }
}
