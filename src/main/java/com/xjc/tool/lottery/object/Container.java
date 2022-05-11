package com.xjc.tool.lottery.object;

public class Container {
    //    容器
    private String goodsName;
    // 商品比重
    private Integer goodsWight;

    public Container() {
    }

    public Container(String goodsName, Integer goodsWight) {
        this.goodsName = goodsName;
        this.goodsWight = goodsWight;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsWight() {
        return goodsWight;
    }

    public void setGoodsWight(Integer goodsWight) {
        this.goodsWight = goodsWight;
    }
}
