package com.fh.shop.admin.po.product;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Product implements Serializable {
    private Long id;

    private String productName;

    private BigDecimal price;

    private Long brandId;

    @TableField(exist = false)
    private String brandName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inputDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modificationDate;

    private String imgFile;

    @TableField(exist = false)
    private String oldImgFile;

    private Integer isHot;

    private Integer whether;

    private Long one;

    private Long two;

    private Long three;

    private String oneTypeName;

    private String twoTypeName;

    private String threeTypeName;

    public Long getId() {
        return id;
    }

    public Product setId(Long id) {
        this.id = id;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public Product setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Long getBrandId() {
        return brandId;
    }

    public Product setBrandId(Long brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getBrandName() {
        return brandName;
    }

    public Product setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Product setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public Product setInputDate(Date inputDate) {
        this.inputDate = inputDate;
        return this;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public Product setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public String getImgFile() {
        return imgFile;
    }

    public Product setImgFile(String imgFile) {
        this.imgFile = imgFile;
        return this;
    }

    public String getOldImgFile() {
        return oldImgFile;
    }

    public Product setOldImgFile(String oldImgFile) {
        this.oldImgFile = oldImgFile;
        return this;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public Product setIsHot(Integer isHot) {
        this.isHot = isHot;
        return this;
    }

    public Integer getWhether() {
        return whether;
    }

    public Product setWhether(Integer whether) {
        this.whether = whether;
        return this;
    }

    public Long getOne() {
        return one;
    }

    public Product setOne(Long one) {
        this.one = one;
        return this;
    }

    public Long getTwo() {
        return two;
    }

    public Product setTwo(Long two) {
        this.two = two;
        return this;
    }

    public Long getThree() {
        return three;
    }

    public Product setThree(Long three) {
        this.three = three;
        return this;
    }

    public String getOneTypeName() {
        return oneTypeName;
    }

    public Product setOneTypeName(String oneTypeName) {
        this.oneTypeName = oneTypeName;
        return this;
    }

    public String getTwoTypeName() {
        return twoTypeName;
    }

    public Product setTwoTypeName(String twoTypeName) {
        this.twoTypeName = twoTypeName;
        return this;
    }

    public String getThreeTypeName() {
        return threeTypeName;
    }

    public Product setThreeTypeName(String threeTypeName) {
        this.threeTypeName = threeTypeName;
        return this;
    }
}
