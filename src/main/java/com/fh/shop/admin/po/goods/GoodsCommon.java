package com.fh.shop.admin.po.goods;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsCommon implements Serializable {
    private Long id;

    private String productName;

    private BigDecimal price;//        decimal(10,2)spu的价格

    private Long stock;//        bigint(20)spu的库存

    private Long brandId;//      bigint(20)

    private String brandName;//   varchar(50)

    private Long cate1;//        bigint(20)一级分类id

    private Long cate2;//        bigint(20)二级分类id

    private Long cate3;//        bigint(20)三级分类id

    private String cateName;//     varchar(100)分类名格式：【手机数码->手机->智能手机】

    private String attrInfo;//     属性格式：[1:'材质',33:'不锈钢',34:'亮面';2:'使用场景',44:'室内',45:'户外']

    private String specInfo;//     规格格式[1:'颜色',33:'红色',34:'蓝色';2:'内存',44:'32G',45:'64G']

    private String status;

    private String isHot;

    private String mainImage;

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public GoodsCommon setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
        return this;
    }

    public Long getId() {
        return id;
    }

    public GoodsCommon setId(Long id) {
        this.id = id;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public GoodsCommon setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public GoodsCommon setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Long getStock() {
        return stock;
    }

    public GoodsCommon setStock(Long stock) {
        this.stock = stock;
        return this;
    }

    public Long getBrandId() {
        return brandId;
    }

    public GoodsCommon setBrandId(Long brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getBrandName() {
        return brandName;
    }

    public GoodsCommon setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public Long getCate1() {
        return cate1;
    }

    public GoodsCommon setCate1(Long cate1) {
        this.cate1 = cate1;
        return this;
    }

    public Long getCate2() {
        return cate2;
    }

    public GoodsCommon setCate2(Long cate2) {
        this.cate2 = cate2;
        return this;
    }

    public Long getCate3() {
        return cate3;
    }

    public GoodsCommon setCate3(Long cate3) {
        this.cate3 = cate3;
        return this;
    }

    public String getCateName() {
        return cateName;
    }

    public GoodsCommon setCateName(String cateName) {
        this.cateName = cateName;
        return this;
    }

    public String getAttrInfo() {
        return attrInfo;
    }

    public GoodsCommon setAttrInfo(String attrInfo) {
        this.attrInfo = attrInfo;
        return this;
    }
}
