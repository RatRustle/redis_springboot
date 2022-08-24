package com.rebecca.redis_springboot.controller;

import com.rebecca.redis_springboot.service.MiaoShaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author weipeng
 * @Date 2022/8/23 22:37
 */
@Component
@RestController
public class RedisTestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MiaoShaService miaoShaService;

    @GetMapping("/redis/test")
    public String testRedis(){
        //设置值到redis中
        redisTemplate.opsForValue().set("name", "rebecca");
        //从redis中获取值
        String name = (String) redisTemplate.opsForValue().get("name");
        return name;
    }

    @GetMapping("/redis/miaoSha")
    public String miaoSha(@RequestParam(value = "prodid") String prodid){
        String uid = UUID.randomUUID().toString();
        String s = miaoShaService.miaoSha(uid, prodid);
        System.out.println(s);
        return s;
    }
}
