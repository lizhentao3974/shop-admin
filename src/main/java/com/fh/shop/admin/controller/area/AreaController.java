package com.fh.shop.admin.controller.area;

import com.fh.shop.admin.annotation.LogAnnotation;
import com.fh.shop.admin.biz.area.IAreaService;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.area.Area;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/area")
public class AreaController {

    @Resource(name = "areaService")
    private IAreaService areaService;

    @RequestMapping("/toIndex")
    public String toIndex() {
        return "area/index";
    }

    @RequestMapping("/queryAreaZtree")
    @ResponseBody
    public ServerResponse queryAreaZtree() {
        return areaService.queryAreaZtree();
    }

    @RequestMapping("/addArea")
    @ResponseBody
    @LogAnnotation(info = "添加地区")
    public ServerResponse addArea(Area area) {
        areaService.addArea(area);
        return ServerResponse.success(area.getId());
    }

    @RequestMapping("/deleteAreas")
    @ResponseBody
    @LogAnnotation(info = "删除地区")
    public ServerResponse deleteAreas(@RequestParam("ids[]") Long[] ids) {
        areaService.deleteAreas(ids);
        return ServerResponse.success();
    }

    @RequestMapping("/findArea")
    public @ResponseBody
    ServerResponse findArea(Long id) {
        Area area = areaService.findArea(id);
        return ServerResponse.success(area);
    }

    @RequestMapping("/updateArea")
    @LogAnnotation(info = "修改地区")
    @ResponseBody
    public ServerResponse updateArea(Area area) {
        areaService.updateArea(area);
        return ServerResponse.success();
    }

    @RequestMapping("/findAreaList")
    @ResponseBody
    public ServerResponse findAreaList(Long areaId) {
        return areaService.findAreaList(areaId);
    }
}
