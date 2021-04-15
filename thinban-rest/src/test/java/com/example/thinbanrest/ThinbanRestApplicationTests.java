package com.example.thinbanrest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

//@SpringBootTest
@SpringBootTest(classes = {ThinbanRestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThinbanRestApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * redis延时队列
     */
    @Test
    public void redisJobTest() {
        // 调用 redisTemplate 对象设置一个10s 后过期的键，不出意外 10s 后键过期后会触发事件打印结果
        redisTemplate.boundValueOps("job").set("10s", 10, TimeUnit.SECONDS);
        System.out.println("begin = " + LocalDateTime.now());
        try {
            // 测试需要休眠才能看到结果
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ---------测试结果---------
        // begin = 2020-11-18T00:19:09.272
        // key = job
        // end = 2020-11-18T00:19:19.369
    }
}
