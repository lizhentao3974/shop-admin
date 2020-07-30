package com.fh.shop.admin.controller.type;

import com.fh.shop.admin.biz.type.ITypeService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.type.TypeParam;
import com.fh.shop.admin.param.type.TypeSearchParam;
import com.fh.shop.admin.param.type.TypeUpdateParam;
import com.fh.shop.admin.vo.type.TypeRelateVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/type")
public class TypeController {

    @Resource(name = "typeService")
    private ITypeService typeService;

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "type/add";
    }

    @RequestMapping("/toList")
    public String toList() {
        return "type/list";
    }

    @RequestMapping("/toEdit")
    public String toEdit() {
        return "type/edit";
    }

    @RequestMapping("/addType")
    @ResponseBody
    public ServerResponse addType(TypeParam typeParam) {
        return typeService.addType(typeParam);
    }

    @RequestMapping("/queryPageList")
    @ResponseBody
    public DataTableResult queryPageList(TypeSearchParam typeSearchParam) {
        return typeService.queryPageList(typeSearchParam);
    }

    @RequestMapping("/deleteType")
    @ResponseBody
    public ServerResponse deleteType(Long id) {
        return typeService.deleteType(id);
    }

    @RequestMapping("/findTypeById")
    @ResponseBody
    public ServerResponse findTypeById(Long id) {
        return typeService.findTypeById(id);
    }

    @RequestMapping("/updateType")
    @ResponseBody
    public ServerResponse updateType(TypeUpdateParam typeUpdateParam) {
        return typeService.updateType(typeUpdateParam);
    }

    @RequestMapping("/findTypeAll")
    @ResponseBody
    public ServerResponse findTypeAll() {
        return typeService.findTypeAll();
    }

    @RequestMapping("/findTypeRelate")
    @ResponseBody
    public ServerResponse findTypeRelate(Long typeId) {
        return typeService.findTypeRelate(typeId);
    }
}
