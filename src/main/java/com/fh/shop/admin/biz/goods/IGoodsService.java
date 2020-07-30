package com.fh.shop.admin.biz.goods;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.goods.GoodsAddParam;
import com.fh.shop.admin.param.goods.GoodsSearchParam;

public interface IGoodsService {
    ServerResponse addGoods(GoodsAddParam goodsAddParam);

    DataTableResult findGoodsList(GoodsSearchParam goodsSearchParam);

    ServerResponse updateHot(Long goodsCommonId, String hot);

    ServerResponse updateStatus(Long goodsCommonId, String status);

    ServerResponse deleteHotProductCache();

    ServerResponse deleteGoodsById(Long id);
}
