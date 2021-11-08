package com.xjc.tool.executor.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * TestApplicationTests.java
 *
 * @author Xujc
 * @date 2021/11/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestApplicationTests {

    @Autowired
    private AsyncService asyncService;

    @Test
    public void test() throws InterruptedException {
        asyncService.task1();
        asyncService.task2();
        asyncService.task3();
        Thread.currentThread().join();
    }

}
