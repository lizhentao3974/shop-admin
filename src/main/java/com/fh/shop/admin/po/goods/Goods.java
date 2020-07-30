package com.fh.shop.admin.po.goods;

import java.io.Serializable;
import java.math.BigDecimal;

public class Goods implements Serializable {

    private Long id;//             bigint(20)主键id

    private Long commonId;//       bigint(20)对应goods-common的主键Id

    private String productName;//    varchar(100)sku格式：【spu名字 + sku对应的规格名->如：小米6s红色32G】

    private BigDecimal price;//          decimal(10,2)

    private Long stock;//          bigint(20)

    private String specValueInfo;//  varchar(200)sku规格值信息，例如：1:红色,3:豪华版

    private String specValueId;//    varchar(200)sku规格值Id,例如：【11,23】

    private String status;

    private String isHot;

    private String mainImage;

    private Long colorId;

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
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

    public Long getId() {
        return id;
    }

    public Goods setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCommonId() {
        return commonId;
    }

    public Goods setCommonId(Long commonId) {
        this.commonId = commonId;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public Goods setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Goods setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Long getStock() {
        return stock;
    }

    public Goods setStock(Long stock) {
        this.stock = stock;
        return this;
    }

    public String getSpecValueInfo() {
        return specValueInfo;
    }

    public Goods setSpecValueInfo(String specValueInfo) {
        this.specValueInfo = specValueInfo;
        return this;
    }

    public String getSpecValueId() {
        return specValueId;
    }

    public Goods setSpecValueId(String specValueId) {
        this.specValueId = specValueId;
        return this;
    }
}
