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
    private Integer wight ; //权重

    public Goods() {
    }

    public Goods(String name, int wight) {
        this.name = name;
        this.wight = wight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWight() {
        return wight;
    }

    public void setWight(int wight) {
        this.wight = wight;
    }
}
