package com.challenge.ventas.configuration;

import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();

        config.useSingleServer()
              .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
              .setTimeout(redisProperties.getTimeout());

        return org.redisson.Redisson.create(config);
    }
}
