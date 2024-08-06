package org.example.cinemamanagement.configuration;

import org.example.cinemamanagement.service.impl.RedisServiceImpl;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPubSub;

@Configuration
public class KeyExpiredListener extends JedisPubSub {
    public KeyExpiredListener() {
//        RedisServiceImpl.getJedisResource().psubscribe(this, "__keyevent@0__:expired");
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe "
                + pattern + " " + subscribedChannels);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        try {
            System.out.println("Received message: " + message + " on channel: " + channel);
            // how to get the value of expired key
//            System.out.println(RedisServiceImpl.getJedisResource().get(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}