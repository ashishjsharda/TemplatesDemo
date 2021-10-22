package com.example.templatedemo;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Component
public class PlaneFinderPoller {

    private WebClient webClient=WebClient.create("http://localhost:7634/aircraft\"");
    private final RedisConnectionFactory redisConnectionFactory;
    private final RedisOperations<String, Aircraft> redisOperations;

    public PlaneFinderPoller(RedisConnectionFactory redisConnectionFactory, RedisOperations<String, Aircraft> redisOperations) {

        this.redisConnectionFactory = redisConnectionFactory;
        this.redisOperations = redisOperations;
    }
}
