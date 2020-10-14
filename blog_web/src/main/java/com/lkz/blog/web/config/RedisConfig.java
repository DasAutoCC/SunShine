package com.lkz.blog.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redisTemplate是单例的全局变量，在这里我们把redisTemplate的key序列化类型改为String，其他地方就都是String了
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate setRedisTemplateSerializer(RedisTemplate redisTemplate){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
