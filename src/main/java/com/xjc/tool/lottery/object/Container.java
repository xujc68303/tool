package com.xjc.tool.lottery.object;

public class Container {
    //    容器
    private String goodsName;
    // 商品比重
    private Double goodsWight;

    public Container() {
    }

    public Container(String goodsName, Double goodsWight) {
        this.goodsName = goodsName;
        this.goodsWight = goodsWight;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsWight() {
        return goodsWight;
    }

    public void setGoodsWight(Double goodsWight) {
        this.goodsWight = goodsWight;
    }
}
