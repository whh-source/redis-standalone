package com.whh.redisstandalone.service;

import java.util.List;
import java.util.Set;

/**
 * @author hipi
 */
public interface RedisService {
    boolean addSet(String key, Object value);

    List<Object> popSet(String key, long size);

    long countSet(String key);

    boolean set(String key, Object value, long seconds);

    boolean set(String key, Object value);

    <T extends Object> T get(String key);

    Boolean addMemberScore(String key, Object member, double score);

    Set<Object> getMemberRangeByScore(String key, double minScore, double maxScore, int offset, int count);

    Long removeMember(String key, double score);

    Long removeAllMember(String key);
}
