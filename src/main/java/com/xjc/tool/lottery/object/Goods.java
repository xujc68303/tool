package com.xjc.tool.lottery.object;

import lombok.Data;

/**
 * @Author jiachenxu
 * @Date 2022/5/11
 * @Descripetion
 */
@Data
public class Goods {

    private String name; //商品名
    private Double wight ; //权重

    public Goods() {
    }

    public Goods(String name, Double wight) {
        this.name = name;
        this.wight = wight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWight() {
        return wight;
    }

    public void setWight(Double wight) {
        this.wight = wight;
    }
}
