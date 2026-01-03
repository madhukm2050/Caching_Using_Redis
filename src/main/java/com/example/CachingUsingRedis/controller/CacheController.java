package com.example.CachingUsingRedis.controller;

import com.example.CachingUsingRedis.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @PostMapping("/save")
    public String save(@RequestParam String key, @RequestParam String value, @RequestParam long ttl){
        cacheService.saveToCache(key, value, ttl);
        return "Saved Key "+key;
    }

    @GetMapping("/get")
    public String get(@RequestParam String key){
        Object value = cacheService.getFromCache(key);
        return value != null ? value.toString() : "Not Found";
    }

    @DeleteMapping ("/delete")
    public void delete(@RequestParam String key){
        cacheService.removeFromCache(key);
    }

    @GetMapping("/exists")
    public boolean exists(@RequestParam String key){
        return cacheService.isKeyPresent(key);
    }
}
