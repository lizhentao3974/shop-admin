package com.fh.shop.admin.biz.brand;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.PageUtil;
import com.fh.shop.admin.param.brand.BrandSearchParam;
import com.fh.shop.admin.po.brand.Brand;

import java.util.List;
import java.util.Map;

public interface IBrandService {
    DataTableResult queryBrandList(BrandSearchParam brandSearchParam);

    void addBrand1(Brand brand);

    void addBrand(Brand brand);

    void deleteBrand(Long id);

    void updateBrand(Brand brand);

    List<Brand> querySelectBrand();

    Brand queryBrandById(Long id);

    Long queryBrandIdByName(String brandName);

    ServerResponse updateIsRecommend(Long id, String isRecommend);

    ServerResponse deleteBrandCache();
}
