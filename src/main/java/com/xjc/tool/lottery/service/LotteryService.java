package com.xjc.tool.lottery.service;

import com.xjc.tool.lottery.object.Goods;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author jiachenxu
 * @Date 2022/5/11
 * @Descripetion
 */
public class LotteryService {

    // https://www.cnblogs.com/fuhui-study-footprint/p/12573165.html

    public static String draw() {
        // db
        List<Goods> goodsList = new ArrayList<>();
        Goods goods1 = new Goods("b站一年大会员", 10D);
        goodsList.add(goods1);
        Goods goods2 = new Goods("地平线5豪华版", 6D);
        goodsList.add(goods2);
        Goods goods3 = new Goods("10元红包", 15D);
        goodsList.add(goods3);
        Goods goods4 = new Goods("lv包", 1D);
        goodsList.add(goods4);
        Goods goods5 = new Goods("macbook pro", 1D);
        goodsList.add(goods5);
        Goods goods6 = new Goods("rtx3080显卡", 2D);
        goodsList.add(goods6);
        Goods goods7 = new Goods("瑞幸咖啡10元代金卷", 20D);
        goodsList.add(goods7);
        Goods goods8 = new Goods("谢谢惠顾", 50D);
        goodsList.add(goods8);


        double pre = 0D, random = ThreadLocalRandom.current().nextDouble(1D, goodsList.stream().map(Goods::getWight).reduce(0D, Double::sum));

        for (Goods container : goodsList) {
            Double goodsWight = container.getWight();
            if (random >= pre && random < goodsWight) {
                return container.getName();
            }
            pre = goodsWight;
        }
        return goodsList.get(goodsList.size() - 1).getName();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            String draw = draw();
            System.out.println(draw);
        }
    }

}


