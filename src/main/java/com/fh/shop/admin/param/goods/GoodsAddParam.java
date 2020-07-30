package com.fh.shop.admin.param.goods;

import com.fh.shop.admin.po.goods.GoodsCommon;

import java.io.Serializable;

public class GoodsAddParam implements Serializable {

    private GoodsCommon goodsCommon = new GoodsCommon();

    private String prices;

    private String stocks;

    private String specValueInfos;

    private String goodsImages;

    public String getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(String goodsImages) {
        this.goodsImages = goodsImages;
    }

    public GoodsCommon getGoodsCommon() {
        return goodsCommon;
    }

    public GoodsAddParam setGoodsCommon(GoodsCommon goodsCommon) {
        this.goodsCommon = goodsCommon;
        return this;
    }

    public String getPrices() {
        return prices;
    }

    public GoodsAddParam setPrices(String prices) {
        this.prices = prices;
        return this;
    }

    public String getStocks() {
        return stocks;
    }

    public GoodsAddParam setStocks(String stocks) {
        this.stocks = stocks;
        return this;
    }

    public String getSpecValueInfos() {
        return specValueInfos;
    }

    public GoodsAddParam setSpecValueInfos(String specValueInfos) {
        this.specValueInfos = specValueInfos;
        return this;
    }
}
