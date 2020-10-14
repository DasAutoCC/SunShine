package com.lkz.blog.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolExecutorsConfig {

    @Bean
    public ExecutorService createNewExecutor(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        return executorService;
    }
}
