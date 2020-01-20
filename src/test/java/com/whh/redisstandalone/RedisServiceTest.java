package com.whh.redisstandalone;


import com.whh.redisstandalone.service.impl.RedisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisServiceImpl redisService;

    @Test
    public void stringTest(){
        boolean ret = redisService.set("test", "this string");
        Assert.assertTrue(ret);

        String value = redisService.get("test");
        Assert.assertEquals(value, "this string");

        log.info("test value:{}", value);
    }
}
