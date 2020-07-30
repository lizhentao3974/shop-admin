package com.fh.shop.admin.vo.brand;

public class BrandVo {
    private Long id;

    private String brandName;

    private String logo;

    private String isRecommend;

    private Integer sort;

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLogo() {
        return logo;
    }

    public BrandVo setLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public Long getId() {
        return id;
    }

    public BrandVo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBrandName() {
        return brandName;
    }

    public BrandVo setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }
}
