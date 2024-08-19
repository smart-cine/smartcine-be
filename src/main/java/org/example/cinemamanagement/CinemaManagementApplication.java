package org.example.cinemamanagement;

import org.example.cinemamanagement.configuration.KeyExpiredListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@SpringBootApplication
@EnableScheduling
public class CinemaManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(CinemaManagementApplication.class, args);
    }
}
