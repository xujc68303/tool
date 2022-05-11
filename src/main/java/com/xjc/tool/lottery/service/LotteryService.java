package com.xjc.tool.lottery.service;

import com.xjc.tool.lottery.object.Container;
import com.xjc.tool.lottery.object.Goods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
        Goods goods1 = new Goods("b站一年大会员", 10);
        goodsList.add(goods1);
        Goods goods2 = new Goods("地平线5豪华版", 10);
        goodsList.add(goods2);
        Goods goods3 = new Goods("10元红包", 15);
        goodsList.add(goods3);
        Goods goods4 = new Goods("lv包", 1);
        goodsList.add(goods4);
        Goods goods5 = new Goods("macbook pro", 1);
        goodsList.add(goods5);
        Goods goods6 = new Goods("rtx3080显卡", 2);
        goodsList.add(goods6);
        Goods goods7 = new Goods("瑞幸咖啡10元代金卷", 20);
        goodsList.add(goods7);
        Goods goods8 = new Goods("谢谢惠顾", 50);
        goodsList.add(goods8);
        goodsList = goodsList.stream()
                .sorted(Comparator.comparing(Goods::getWight))
                .collect(Collectors.toList());

        List<Container> containers = goodsList.stream()
                .map(goods -> {
                    Container container = new Container();
                    container.setGoodsName(goods.getName());
                    container.setGoodsWight(goods.getWight());
                    return container;
                }).collect(Collectors.toList());

        int pre = 0 , random = ThreadLocalRandom.current().nextInt(1,
                goodsList.stream()
                        .map(Goods::getWight)
                        .reduce(0, Integer::sum));


        for (Container container : containers) {
            Integer goodsWight = container.getGoodsWight();
            if (random >= pre && random < goodsWight) {
                return container.getGoodsName();
            }
            pre = goodsWight;
        }
        return containers.get(containers.size() - 1).getGoodsName();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            String draw = draw();
            System.out.println(draw);
        }
    }

}


