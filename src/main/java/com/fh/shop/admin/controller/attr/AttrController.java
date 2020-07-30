package com.fh.shop.admin.controller.attr;

import com.fh.shop.admin.biz.attr.IAttrService;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.attr.AttrUpdateParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/attr")
public class AttrController {

    @Resource(name = "attrService")
    private IAttrService attrService;

    @RequestMapping("/toEdit")
    public String toEdit() {
        return "attr/edit";
    }

    @RequestMapping("/findAttr")
    @ResponseBody
    public ServerResponse findAttr(Long id) {
        return attrService.findAttr(id);
    }

    @RequestMapping("/updateAttr")
    @ResponseBody
    public ServerResponse updateAttr(AttrUpdateParam attrUpdateParam) {
        return attrService.updateAttr(attrUpdateParam);
    }
}
