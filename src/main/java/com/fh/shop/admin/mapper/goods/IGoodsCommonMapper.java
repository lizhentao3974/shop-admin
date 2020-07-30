package com.fh.shop.admin.mapper.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.goods.GoodsSearchParam;
import com.fh.shop.admin.po.goods.GoodsCommon;

import java.util.List;

public interface IGoodsCommonMapper extends BaseMapper<GoodsCommon> {
    Long findGoodsCount(GoodsSearchParam goodsSearchParam);

    List<GoodsCommon> findGoodsList(GoodsSearchParam goodsSearchParam);
}
