package com.fh.shop.admin.po.brand;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

public class Brand implements Serializable {
    private Long id;

    private String brandName;

    private String logo;

    private Integer sort;

    private String isRecommend;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    @TableField(exist = false)
    private String oldLogo;

    public String getOldLogo() {
        return oldLogo;
    }

    public Brand setOldLogo(String oldLogo) {
        this.oldLogo = oldLogo;
        return this;
    }

    public String getLogo() {
        return logo;
    }

    public Brand setLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Brand setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBrandName() {
        return brandName;
    }

    public Brand setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }
}
