package com.example.CachingUsingRedis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveToCache(String key, Object value, long ttlInSeconds){
        redisTemplate.opsForValue().set(key, value, ttlInSeconds, TimeUnit.SECONDS);
    }

    public Object getFromCache(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void removeFromCache(String key){
        redisTemplate.delete(key);
    }

    public boolean isKeyPresent(String key){
        return redisTemplate.hasKey(key);
    }
}
