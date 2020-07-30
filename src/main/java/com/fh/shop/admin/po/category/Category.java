package com.fh.shop.admin.po.category;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

public class Category implements Serializable {

    private Long id;

    private String categoryName;

    @TableField(exist = false)
    private String oldCategoryName;

    private Long fatherId;

    private Long typeId;

    private String typeName;

    public String getOldCategoryName() {
        return oldCategoryName;
    }

    public void setOldCategoryName(String oldCategoryName) {
        this.oldCategoryName = oldCategoryName;
    }

    public Long getId() {
        return id;
    }

    public Category setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Category setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public Category setFatherId(Long fatherId) {
        this.fatherId = fatherId;
        return this;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Category setTypeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public Category setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }
}
