package com.fh.shop.admin.biz.product;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.product.Product;

import java.util.List;
import java.util.Map;

public interface IProductService {

    ServerResponse addProduct(Product product);

    Map queryProduct(ProductSearchParam productSearchParam);

    ServerResponse deleteProduct(Long id);

    Product editProduct(Long id);

    ServerResponse updateProduct(Product product);

    ServerResponse deleteAll(Long[] ids);

    List<Product> findProduct(ProductSearchParam productSearchParam);

    ServerResponse updateStatus(Long id, Integer stauts);

    ServerResponse updateIsup(Long id, Integer isup);

}
