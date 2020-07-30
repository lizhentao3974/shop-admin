package com.fh.shop.admin.controller.category;

import com.fh.shop.admin.biz.category.ICategoryService;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.category.UpdateCateParam;
import com.fh.shop.admin.po.category.Category;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Resource(name = "categoryService")
    private ICategoryService categoryService;

    @RequestMapping("toCateList")
    public String toTypeList() {
        return "category/list";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public ServerResponse findAll() {
        return categoryService.findCateList();
    }

    @RequestMapping("/delCate")
    @ResponseBody
    public ServerResponse delCate(@RequestParam("ids[]") Long[] ids) {
        return categoryService.delCate(ids);
    }

    @RequestMapping("/addCate")
    @ResponseBody
    public ServerResponse addCate(Category category) {
        return categoryService.addCate(category);
    }

    @RequestMapping("/find")
    @ResponseBody
    public ServerResponse find(Long id) {
        return categoryService.find(id);
    }

    @RequestMapping("/update")
    @ResponseBody
    public ServerResponse update(UpdateCateParam cateParam) {
        return categoryService.update(cateParam);
    }

    @RequestMapping("/findCateById")
    @ResponseBody
    public ServerResponse findCateById(Long id) {
        return categoryService.findCateById(id);
    }

}
