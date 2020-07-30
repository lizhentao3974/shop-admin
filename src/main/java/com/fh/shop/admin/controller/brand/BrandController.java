package com.fh.shop.admin.controller.brand;

import com.fh.shop.admin.annotation.LogAnnotation;
import com.fh.shop.admin.biz.brand.IBrandService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.PageUtil;
import com.fh.shop.admin.param.brand.BrandSearchParam;
import com.fh.shop.admin.po.brand.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

@Controller()
@RequestMapping("/brand")
public class BrandController {

    @Resource(name = "brandService")
    private IBrandService brandService;


    @RequestMapping("/toBrandList")
    public String toBrandList() {
        return "/brand/list";
    }

    @RequestMapping("/queryBrandList")
    @ResponseBody
    public DataTableResult queryBrandList(BrandSearchParam brandSearchParam) {
        return brandService.queryBrandList(brandSearchParam);
    }

    @RequestMapping("/addBrand")
    @ResponseBody
    @LogAnnotation(info = "添加品牌")
    public ServerResponse addBrand(Brand brand) {
        brandService.addBrand(brand);
        return ServerResponse.success();
    }

    @RequestMapping("/deleteBrand")
    @ResponseBody
    @LogAnnotation(info = "删除品牌")
    public ServerResponse deleteBrand(Long id, HttpServletRequest request) {
        Brand brand = brandService.queryBrandById(id);
        if (StringUtils.isNotEmpty(brand.getLogo())) {
            String logo = brand.getLogo();
            String realPath = request.getServletContext().getRealPath(logo);
            File file = new File(realPath);
            if (file.exists()) {
                file.delete();
            }
        }
        brandService.deleteBrand(id);
        return ServerResponse.success();
    }

    @RequestMapping("/editBrand")
    public ModelAndView editBrand(Brand brand) {
        ModelAndView mav = new ModelAndView("/brand/updateBrand");
        mav.addObject("brand", brand);
        return mav;
    }

    @RequestMapping("/updateBrand")
    @ResponseBody
    @LogAnnotation(info = "修改品牌")
    public ServerResponse updateBrand(Brand brand, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(brand.getLogo())) {
            if (StringUtils.isNotEmpty(brand.getOldLogo())) {
                String oldlogo = brand.getOldLogo();
                String realPath = request.getServletContext().getRealPath(oldlogo);
                File file = new File(realPath);
                if (file.exists()) {
                    file.delete();
                }
            }
        } else {
            brand.setLogo(brand.getOldLogo());
        }


        brandService.updateBrand(brand);
        return ServerResponse.success();
    }

    @RequestMapping("/querySelectBrand")
    @ResponseBody
    public ServerResponse querySelectBrand() {
        List<Brand> brandList = brandService.querySelectBrand();
        return ServerResponse.success(brandList);
    }

    @RequestMapping(value = "/updateIsRecommend", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateIsRecommend(Long id, String isRecommend) {
        return brandService.updateIsRecommend(id, isRecommend);
    }

    @RequestMapping(value = "/deleteBrandCache", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteBrandCache() {
        return brandService.deleteBrandCache();
    }
}
