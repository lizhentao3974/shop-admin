package com.fh.shop.admin.controller.spec;

import com.fh.shop.admin.annotation.LogAnnotation;
import com.fh.shop.admin.biz.spec.ISpecService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.spec.SpecAndValue;
import com.fh.shop.admin.param.spec.SpecSearchParam;
import com.fh.shop.admin.param.spec.SpecUpdateParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/spec")
public class SpecController {

    @Resource(name = "specService")
    private ISpecService specService;

    @RequestMapping("/toAdd")
    public String toSpecList() {
        return "spec/add";
    }

    @RequestMapping("/toList")
    public String toList() {
        return "spec/list";
    }

    @RequestMapping("/toEdit")
    public String toEdit() {
        return "spec/edit";
    }

    @RequestMapping("/addSpec")
    @ResponseBody
    @LogAnnotation(info = "添加了一个规格")
    public ServerResponse addSpec(SpecAndValue specAndValue) {
        return specService.addSpec(specAndValue);
    }

    @RequestMapping("/queryPageList")
    @ResponseBody
    public DataTableResult queryPageList(SpecSearchParam specSearchParam) {
        return specService.queryPageList(specSearchParam);
    }

    @RequestMapping("/deleteSpec")
    @ResponseBody
    @LogAnnotation(info = "删除了一个规格")
    public ServerResponse deleteSpec(Long id) {
        return specService.deleteSpec(id);
    }

    @RequestMapping("/findSpecById")
    @ResponseBody
    public ServerResponse findSpecById(Long id) {
        return specService.findSpecById(id);
    }

    @RequestMapping("/updateSpec")
    @ResponseBody
    @LogAnnotation(info = "修改了一个规格")
    public ServerResponse updateSpec(SpecUpdateParam specUpdateParam) {
        return specService.updateSpec(specUpdateParam);
    }

    @RequestMapping("/findSpecList")
    @ResponseBody
    public ServerResponse findSpecList() {
        return specService.findSpecList();
    }
}
