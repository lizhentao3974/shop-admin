package com.fh.shop.admin.biz.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.mapper.goods.IGoodsCommonMapper;
import com.fh.shop.admin.mapper.goods.IGoodsImageMapper;
import com.fh.shop.admin.mapper.goods.IGoodsMapper;
import com.fh.shop.admin.param.goods.GoodsAddParam;
import com.fh.shop.admin.param.goods.GoodsSearchParam;
import com.fh.shop.admin.po.goods.Goods;
import com.fh.shop.admin.po.goods.GoodsCommon;
import com.fh.shop.admin.po.goods.GoodsImage;
import com.fh.shop.admin.util.RedisUtil;
import com.fh.shop.admin.vo.goods.GoodsCommonVo;
import com.fh.shop.admin.vo.goods.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("goodsService")
public class IGoodsServiceImpl implements IGoodsService {
    @Autowired
    private IGoodsMapper goodsMapper;
    @Autowired
    private IGoodsCommonMapper goodsCommonMapper;
    @Autowired
    private IGoodsImageMapper goodsImageMapper;

    @Override
    public ServerResponse addGoods(GoodsAddParam goodsAddParam) {
        //添加spu数据
        GoodsCommon goodsCommon = goodsAddParam.getGoodsCommon();
        goodsCommonMapper.insert(goodsCommon);
        Long commonId = goodsCommon.getId();
        String productName = goodsCommon.getProductName();

        //添加sku数据
        String prices = goodsAddParam.getPrices();
        String stocks = goodsAddParam.getStocks();
        String specValueInfos = goodsAddParam.getSpecValueInfos();
        if (StringUtils.isEmpty(prices) || StringUtils.isEmpty(stocks) || StringUtils.isEmpty(specValueInfos)) {
            return ServerResponse.error(ResponseEnum.SKU_INFO_IS_Empty);
        }

        String[] pricesArr = prices.split(",");
        String[] stocksArr = stocks.split(",");
        String[] specValueInfosArr = specValueInfos.split(";");

        //22:1.jpg,2.jpg,3.jpg;33:22.jpg,6.jpg,9.jpg
        String goodsImages = goodsAddParam.getGoodsImages();
        for (int i = 0; i < pricesArr.length; i++) {
            Goods goods = new Goods();
            goods.setPrice(new BigDecimal(pricesArr[i]));
            goods.setStock(Long.parseLong(stocksArr[i]));
            goods.setCommonId(commonId);

            //"55:蓝色,42:16G
            String specIdAndSpecNameInfo = specValueInfosArr[i];
            String[] specIdAndSpecNameArr = specIdAndSpecNameInfo.split(",");

            Long colorId = Long.valueOf(specIdAndSpecNameArr[0].split(":")[0]);
            goods.setColorId(colorId);
            // 45:a.jpg,b.jpg,c.jpg;46:111.jpg,222.jpg
            /*String mainImage = Arrays.stream(goodsImages.split(";"))
                    .filter(x -> Long.valueOf(x.split(":")[0]) == colorId)
                    .map(z -> z.split(":")[1].split(",")[0])
                    .findFirst().get();*/

            String[] imgColorArr = goodsImages.split(";");
            for (int x = 0; x < imgColorArr.length; x++) {
                String[] imgIdArr = imgColorArr[x].split("\\|");
                Long imgId = Long.valueOf(imgIdArr[0]);
                if (colorId.equals(imgId)) {
                    String[] imgArr = imgIdArr[1].split(",");
                    goods.setMainImage(imgArr[0]);
                }
            }

            StringBuilder skuProductName = new StringBuilder();
            StringBuilder skuSpecValueId = new StringBuilder();
            skuProductName.append(productName);
            for (String ss : specIdAndSpecNameArr) {
                skuProductName.append(ss.split(":")[1]);
                skuSpecValueId.append(",").append(ss.split(":")[0]);
            }
            //'sku格式：【spu名字 + sku对应的规格名->如：小米6s红色32G】',
            goods.setProductName(skuProductName.toString());
            //'sku规格值Id,例如：【11,23】',
            goods.setSpecValueId(skuSpecValueId.substring(1));
            //'sku规格值信息，例如：1:红色,3:豪华版',
            goods.setSpecValueInfo(specIdAndSpecNameInfo);
            goodsMapper.insert(goods);
        }
        //往goods_image表中插入数据
        //22:1.jpg,2.jpg,3.jpg;33:22.jpg,6.jpg,9.jpg
        String[] goodsImageColorArr = goodsImages.split(";");
        for (String goodsImageColor : goodsImageColorArr) {
            Long colorId = Long.valueOf(goodsImageColor.split("\\|")[0]);

            String[] goodsImgArr = goodsImageColor.split("\\|")[1].split(",");
            for (String goodsImg : goodsImgArr) {
                GoodsImage goodsImage = new GoodsImage();
                goodsImage.setColorId(colorId);
                goodsImage.setImage(goodsImg);
                goodsImage.setCommonId(commonId);
                goodsImageMapper.insert(goodsImage);
            }

        }

        return ServerResponse.success();
    }

    @Override
    public DataTableResult findGoodsList(GoodsSearchParam goodsSearchParam) {
        Long goodsCount = goodsCommonMapper.findGoodsCount(goodsSearchParam);
        List<GoodsVo> goodsVoList = new ArrayList<>();
        if (goodsCount > 0) {
            List<GoodsCommon> goodsCommonList = goodsCommonMapper.findGoodsList(goodsSearchParam);
            List<GoodsCommonVo> goodsCommonVoList = goodsCommonList.stream().map(x -> {
                GoodsCommonVo goodsCommonVo = new GoodsCommonVo();
                goodsCommonVo.setId(x.getId());
                goodsCommonVo.setBrandName(x.getBrandName());
                goodsCommonVo.setCateName(x.getCateName());
                goodsCommonVo.setIsHot(x.getIsHot());
                goodsCommonVo.setPrice(x.getPrice().toString());
                goodsCommonVo.setProductName(x.getProductName());
                goodsCommonVo.setStatus(x.getStatus());
                goodsCommonVo.setStock(x.getStock());
                goodsCommonVo.setMainImage(x.getMainImage());
                return goodsCommonVo;
            }).collect(Collectors.toList());

            List<Long> idsList = goodsCommonList.stream().map(x -> x.getId()).collect(Collectors.toList());

            QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("commonId", idsList);
            List<Goods> goodsList = goodsMapper.selectList(queryWrapper);
            goodsCommonVoList.forEach(x -> {
                GoodsVo goodsVo = new GoodsVo();
                goodsVo.setGoodsCommon(x);
                List<Goods> goods = goodsList.stream().filter(y -> y.getCommonId() == x.getId()).collect(Collectors.toList());
                goodsVo.setGoodsList(goods);
                goodsVoList.add(goodsVo);
            });
        }

        return new DataTableResult(goodsSearchParam.getDraw(), goodsCount, goodsCount, goodsVoList);
    }

    @Override
    public ServerResponse updateHot(Long goodsCommonId, String hot) {
        //更新SPU
        GoodsCommon goodsCommon = new GoodsCommon();
        goodsCommon.setId(goodsCommonId);
        goodsCommon.setIsHot(hot);
        goodsCommonMapper.updateById(goodsCommon);

        //更新sku

        Goods goods = new Goods();
        goods.setIsHot(hot);

        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("commonId", goodsCommonId);
        goodsMapper.update(goods, goodsQueryWrapper);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateStatus(Long goodsCommonId, String status) {
        //更新spu
        GoodsCommon goodsCommon = new GoodsCommon();
        goodsCommon.setId(goodsCommonId);
        goodsCommon.setStatus(status);
        goodsCommonMapper.updateById(goodsCommon);

        //更新sku
        Goods goods = new Goods();
        goods.setStatus(status);
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("commonId", goodsCommonId);
        goodsMapper.update(goods, goodsQueryWrapper);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteHotProductCache() {
        RedisUtil.delete(SystemConstant.GOODSLIST_KEY);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteGoodsById(Long id) {
        goodsCommonMapper.deleteById(id);

        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("commonId", id);
        goodsMapper.delete(goodsQueryWrapper);
        return ServerResponse.success();
    }
}
