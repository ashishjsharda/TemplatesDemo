package com.example.templatedemo;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Component
public class PlaneFinderPoller {

    private WebClient webClient=WebClient.create("http://localhost:7634/aircraft\"");
    private final RedisConnectionFactory redisConnectionFactory;
    private final AircraftRepository repository;

    public PlaneFinderPoller(RedisConnectionFactory redisConnectionFactory, AircraftRepository repository) {

        this.redisConnectionFactory = redisConnectionFactory;
        this.repository = repository;
    }
    @Scheduled(fixedRate = 1000)
    private void pollPlanes(){
        redisConnectionFactory.getConnection().serverCommands().flushDb();
        webClient.get().retrieve().bodyToFlux(Aircraft.class)
                .filter(plane->!plane.getReg().isEmpty()).toStream().forEach(repository::save);
        repository.findAll().forEach(System.out::println);
    }
}
