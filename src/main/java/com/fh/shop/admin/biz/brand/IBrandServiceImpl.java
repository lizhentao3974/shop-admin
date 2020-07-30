package com.fh.shop.admin.biz.brand;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.param.PageUtil;
import com.fh.shop.admin.param.brand.BrandSearchParam;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.util.RedisUtil;
import com.fh.shop.admin.vo.brand.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("brandService")
public class IBrandServiceImpl implements IBrandService {

    @Autowired
    private IBrandMapper brandMapper;

    @Override
    public DataTableResult queryBrandList(BrandSearchParam brandSearchParam) {
        Long count = brandMapper.queryBrandCount(brandSearchParam);

        List<Brand> brandList = brandMapper.queryBrandList(brandSearchParam);

        List<BrandVo> brandVoList = new ArrayList<>();
        for (Brand brand : brandList) {
            BrandVo brandVo = new BrandVo();
            brandVo.setId(brand.getId());
            brandVo.setBrandName(brand.getBrandName());
            brandVo.setLogo(brand.getLogo());
            brandVo.setSort(brand.getSort());
            brandVo.setIsRecommend(brand.getIsRecommend());
            brandVoList.add(brandVo);
        }

        return new DataTableResult(brandSearchParam.getDraw(), count, count, brandVoList);
    }

    @Override
    public void addBrand1(Brand brand) {
        brandMapper.addBrand1(brand);
    }

    @Override
    public void addBrand(Brand brand) {
        brandMapper.insert(brand);
    }


    @Override
    public void deleteBrand(Long id) {
        brandMapper.deleteBrand(id);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateBrand(brand);
    }

    @Override
    public List<Brand> querySelectBrand() {
        List<Brand> brandList = brandMapper.querySelectBrand();
        return brandList;
    }

    @Override
    public Brand queryBrandById(Long id) {
        Brand brand = brandMapper.queryBrandById(id);
        return brand;
    }

    @Override
    public Long queryBrandIdByName(String brandName) {
        Long brandId = brandMapper.queryBrandIdByName(brandName);
        return brandId;
    }

    @Override
    public ServerResponse updateIsRecommend(Long id, String isRecommend) {
        Brand brand = new Brand();
        brand.setId(id);
        brand.setIsRecommend(isRecommend);
        brandMapper.updateById(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBrandCache() {
        RedisUtil.delete(SystemConstant.RECOMMEND_BRAND_KEY);
        return ServerResponse.success();
    }
}
