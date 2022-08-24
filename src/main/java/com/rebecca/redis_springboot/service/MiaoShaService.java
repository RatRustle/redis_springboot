package com.rebecca.redis_springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MiaoShaService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 问题 超卖，库存遗留问题
     * @param uid
     * @param prodid
     * @return
     */
    public String miaoSha(String uid,String prodid){
        //1.uid,prodid非空判断
        if (uid==null||prodid==null){
            return "uid或prodid为空";
        }
        //2.连接redis,springboot已集成
        //3.拼接key
        //3.1库存key
        String kcKey = "sk:"+prodid+":qt";
        //3.2秒杀成功用户key
        String userKey = "sk:"+prodid+":user";
        //监视库存
        //redisTemplate.watch(kcKey);
        //4.获取库存，如果库存null，秒杀开始
        Integer kc =(Integer) redisTemplate.opsForValue().get(kcKey);
        if (kc==null){
            return "秒杀还没开始,请等待";
        }
        //5.判断用户是否重复秒杀操作
        Boolean member = redisTemplate.opsForSet().isMember(userKey, uid);
        if (member){
            return "已经秒杀成功了，不能重复秒杀";
        }
        //6.判断如果商品数量小于1，秒杀结束
        if (kc<1){
            return "秒杀已经结束了";
        }
        //7.秒杀过程
        //使用事务
        //redisTemplate.multi();
        //7.1 库存-1
        redisTemplate.opsForValue().decrement(kcKey);
        //7.2把秒杀成功用户添加到清单里面
        redisTemplate.opsForSet().add(userKey,uid);
        return "秒杀成功";
    }
}
