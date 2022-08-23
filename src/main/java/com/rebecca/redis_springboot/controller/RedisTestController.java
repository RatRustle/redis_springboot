package com.rebecca.redis_springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author weipeng
 * @Date 2022/8/23 22:37
 */
@Component
@RestController
@RequestMapping("/redisTest")
public class RedisTestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public String testRedis(){
        //设置值到redis中
        redisTemplate.opsForValue().set("name", "rebecca");
        //从redis中获取值
        String name = (String) redisTemplate.opsForValue().get("name");
        return name;
    }
}
