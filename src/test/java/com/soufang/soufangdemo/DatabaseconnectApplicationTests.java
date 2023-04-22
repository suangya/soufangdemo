package com.soufang.soufangdemo;

import com.soufang.soufangdemo.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class Experiment5ApplicationTests {

    @Autowired
    DataSource dataSource;
    private RedisService redisService;

    @Test
    void contextLoads() throws Exception{
        System.out.println();
    }
}