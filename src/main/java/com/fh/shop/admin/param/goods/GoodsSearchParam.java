package com.fh.shop.admin.param.goods;

import com.fh.shop.admin.param.PageUtil;

public class GoodsSearchParam extends PageUtil {

    private String productName;

    private Long brandId;

    public String getProductName() {
        return productName;
    }

    public GoodsSearchParam setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Long getBrandId() {
        return brandId;
    }

    public GoodsSearchParam setBrandId(Long brandId) {
        this.brandId = brandId;
        return this;
    }
}
