package com.xjc.tool;

import com.xjc.tool.mapstruct.Item1;
import com.xjc.tool.mapstruct.Item2;
import com.xjc.tool.mapstruct.ItemMapper;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ToolApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void Converter() {
        Item1 item1 = new Item1();
        item1.setItemId(1);
        item1.setTitle("转换测试");
        Item2 to =  ItemMapper.INSTANCE.to(item1);
        System.out.println(to);
    }

    @Test
    public void Converter2() {
        List<Item1> item1List = new ArrayList<>();
        for(int i=1; i<=10; i++){
            Item1 item1 = new Item1();
            item1.setItemId(i);
            item1.setTitle("转换测试"+i);
            item1List.add(item1);
        }

        List<Item2> item2s = ItemMapper.INSTANCE.toList(item1List);
        System.out.println(item2s);


    }


}
