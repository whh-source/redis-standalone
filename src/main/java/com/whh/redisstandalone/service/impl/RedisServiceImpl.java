package com.whh.redisstandalone.service.impl;

import com.whh.redisstandalone.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author hipi
 * @author linjiehong
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init(){
        log.info("redis service init");
    }

      /**
     * 集合
     */
    @Override
    public boolean addSet(String key, Object value) {
        boolean ret = true;
        try {
            Boolean contain = redisTemplate.opsForSet().isMember(key, value);
            if (contain == null || Boolean.FALSE.equals(contain)) {
                redisTemplate.opsForSet().add(key, value);
            }
        } catch (Exception e) {
            log.error("set key: {}, value: {} exception: {}", key, value.toString(), e.getMessage());
            ret = false;
        }

        return ret;
    }

    @Override
    public List<Object> popSet(String key, long size) {
        List<Object> object = null;
        try {
            object = redisTemplate.opsForSet().pop(key, size);
        }catch (Exception e){
            log.error("popSet key: {}, exception: {}", key,  e.getMessage());
        }

        return object;
    }

    @Override
    public long countSet(String key) {
        long count = 0;
        try {
            Long tmp = redisTemplate.opsForSet().size(key);
            count =  tmp != null ? tmp : 0;
        } catch (Exception e) {
            log.error("countSet key: {},  exception: {}", key, e.getMessage());
        }

        return count;
    }
    /**
     * // 字符串
     * @param key
     * @return
     */
    @Override
    public boolean set(String key, Object value) {
        boolean ret = true;
        try{
            redisTemplate.opsForValue().set(key, value);
        }catch (Exception e){
            log.error("set key: {}, value: {} exception: {}", key, value.toString(), e.getMessage());
            ret = false;
        }
        return ret;
    }

    @Override
    public boolean set(String key, Object value, long seconds) {
        boolean ret = true;
        try{
            redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("set key: {}, value: {} exception: {}", key, value.toString(), e.getMessage());
            ret = false;
        }
        return ret;
    }

    @Override
    public <T> T get(String key) {
        if (key == null){
            log.error("get key is null ");
            return null;
        }

        try {
            return (T)redisTemplate.opsForValue().get(key);
        }catch (Exception e){
            log.error("get key: {}, exception: {}", key,  e.getMessage());
        }
        return null;
    }

  

    /**
     * 有序集合
     */
    @Override
    public Boolean addMemberScore(String key, Object member, double score) {
        Boolean ret = true;
        try{
            redisTemplate.opsForZSet().add(key, member, score);
        }catch (Exception e){
            ret = false;
            log.error("addMemberScore key: {}, member: {}, exception: {}", member, score, e.getMessage());
        }

        return ret;
    }

    @Override
    public Set<Object> getMemberRangeByScore(String key, double minScore, double maxScore, int offset, int count) {
        return redisTemplate.opsForZSet().rangeByScore(key, minScore, maxScore, offset, count);
    }

    @Override
    public Long removeMember(String key, double score) {
        Long ret = 0L;
        try {
            ret = redisTemplate.opsForZSet().removeRangeByScore(key, score, score);
        }catch (Exception e){
            log.error("removeMember key: {}, score: {},  exception: {}", key, score, e.getMessage());
        }

        return ret;
    }

    @Override
    public Long removeAllMember(String key) {
        Long ret = 0L;
        try {
            ret = redisTemplate.opsForZSet().removeRangeByScore(key, 0, Integer.MAX_VALUE);
        }catch (Exception e){
            log.error("removeAllMember key: {}, exception: {}", key, e.getMessage());
        }

        return ret;
    }
}
