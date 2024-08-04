package org.example.cinemamanagement.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.time.Duration;
import java.util.Set;

public class RedisService {
    private static Jedis jedisResource = new JedisPool(new JedisPoolConfig(), "localhost", 6379).getResource();
    public static void sadd(String key, String value) {
        Set<String> res = jedisResource.smembers(key);
        if (res.isEmpty()) {
            jedisResource.sadd(key, value);
            jedisResource.expire(key, Duration.ofMinutes(10).getSeconds());
            return;
        }

        jedisResource.sadd(key, value);

    }

    public static void srem(String key, String value) {
        jedisResource.srem(key, value);
    }

    public static void smembers(String key) {
        jedisResource.smembers(key);
    }

    public static void expire(String key, int seconds) {
        jedisResource.expire(key, seconds);
    }

    public static Set<String> keys(String pattern) {
        return jedisResource.keys(pattern);
    }

    public static ScanResult<String> scan(
            String cursor, String pattern, int count
    ) {
        ScanParams scanParams = new ScanParams().match(pattern).count(count);

        return jedisResource.scan(cursor, scanParams);
    }

    public static Jedis getJedisResource() {
        return jedisResource;
    }
}
