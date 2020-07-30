package com.fh.shop.admin.param.product;

import com.fh.shop.admin.param.PageUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ProductSearchParam extends PageUtil {
    private String productName;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Long brandId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minCreateDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxCreateDate;

    private Long one;

    private Long two;

    private Long three;

    public Long getOne() {
        return one;
    }

    public ProductSearchParam setOne(Long one) {
        this.one = one;
        return this;
    }

    public Long getTwo() {
        return two;
    }

    public ProductSearchParam setTwo(Long two) {
        this.two = two;
        return this;
    }

    public Long getThree() {
        return three;
    }

    public ProductSearchParam setThree(Long three) {
        this.three = three;
        return this;
    }

    public Date getMinCreateDate() {
        return minCreateDate;
    }

    public ProductSearchParam setMinCreateDate(Date minCreateDate) {
        this.minCreateDate = minCreateDate;
        return this;
    }

    public Date getMaxCreateDate() {
        return maxCreateDate;
    }

    public ProductSearchParam setMaxCreateDate(Date maxCreateDate) {
        this.maxCreateDate = maxCreateDate;
        return this;
    }


    public Long getBrandId() {
        return brandId;
    }

    public ProductSearchParam setBrandId(Long brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ProductSearchParam setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public ProductSearchParam setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public ProductSearchParam setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }
}
