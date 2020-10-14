package com.lkz.blog.web.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置类
 */
@Configuration
@MapperScan("com.lkz.blog.web.mapper")
public class MyBatisConfig {
}
