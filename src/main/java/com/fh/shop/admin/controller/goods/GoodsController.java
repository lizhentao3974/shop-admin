package com.fh.shop.admin.controller.goods;

import com.fh.shop.admin.biz.goods.IGoodsService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.goods.GoodsAddParam;
import com.fh.shop.admin.param.goods.GoodsSearchParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Resource(name = "goodsService")
    private IGoodsService goodsService;

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "goods/add";
    }

    @RequestMapping("/toList")
    public String toList() {
        return "goods/list";
    }

    @RequestMapping("/addGoods")
    @ResponseBody
    public ServerResponse addGoods(GoodsAddParam goodsAddParam) {
        return goodsService.addGoods(goodsAddParam);
    }

    @RequestMapping("/findGoodsList")
    @ResponseBody
    public DataTableResult findGoodsList(GoodsSearchParam goodsSearchParam) {
        return goodsService.findGoodsList(goodsSearchParam);
    }

    @RequestMapping(value = "/updateHot", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateHot(Long goodsCommonId, String hot) {
        return goodsService.updateHot(goodsCommonId, hot);
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateStatus(Long goodsCommonId, String status) {
        return goodsService.updateStatus(goodsCommonId, status);
    }

    @RequestMapping(value = "/deleteHotProductCache", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteHotProductCache() {
        return goodsService.deleteHotProductCache();
    }

    @PostMapping("/deleteGoodsById")
    @ResponseBody
    public ServerResponse deleteGoodsById(Long id) {
        return goodsService.deleteGoodsById(id);
    }
}
