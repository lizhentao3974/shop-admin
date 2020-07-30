package com.fh.shop.admin.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.product.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IProductMapper extends BaseMapper<Product> {

    Long queryCount(ProductSearchParam productSearchParam);

    List<Product> queryProduct(ProductSearchParam productSearchParam);

    Product editProduct(Long id);

    List<Product> findProduct(ProductSearchParam productSearchParam);

}
