package com.demo.service.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 整合redis组件
 * @Date 2018/12/24 17:26
 */
@Component
public class RedisComponent {

    private Logger log = LoggerFactory.getLogger(RedisComponent.class);

    //默认60秒
    private final static long DEFALUT_TIMEOUT = 60000L;

    // RedisTemplate，可以进行所有的操作
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    public boolean set(String key, Object object) {
        boolean result = false;
        try{
            redisTemplate.opsForValue().set(key, object);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置过期时间
     */
    public boolean set(String key, Object object,long expireTime) {
        boolean result = false;
        try{
            redisTemplate.opsForValue().set(key, object);
            redisTemplate.expire(key,expireTime, TimeUnit.SECONDS);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void del(String key){
        redisTemplate.delete(key);
    }

    /*批量删除对应的value*/
    public void remove(final String... keys){
        for(String key:keys){
            del(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }




}
