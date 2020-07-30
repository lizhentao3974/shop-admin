package com.fh.shop.admin.mapper.brand;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.PageUtil;
import com.fh.shop.admin.param.brand.BrandSearchParam;
import com.fh.shop.admin.po.brand.Brand;

import java.util.List;

public interface IBrandMapper extends BaseMapper<Brand> {
    void addBrand1(Brand brand);

    Long queryBrandCount(BrandSearchParam brandSearchParam);

    List<Brand> queryBrandList(BrandSearchParam brandSearchParam);

    void deleteBrand(Long id);

    void updateBrand(Brand brand);

    List<Brand> querySelectBrand();

    Brand queryBrandById(Long id);

    Long queryBrandIdByName(String brandName);
}
