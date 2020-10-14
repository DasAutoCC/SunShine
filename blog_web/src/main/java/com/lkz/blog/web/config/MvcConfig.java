package com.lkz.blog.web.config;

import com.lkz.blog.web.controller.filters.ImageFilter;
import com.lkz.blog.web.controller.filters.RepeatSubmissionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MvcConfig {

    /**
     * 文件上传解析器
     * @return
     */
    @Bean
    public CommonsMultipartResolver up(){
        CommonsMultipartResolver s = new CommonsMultipartResolver();
        s.setMaxUploadSize(10000000);
        return s;
    }

    public void test(){

    }

    /**
     * 重复提交过滤器
     * @param repeatSubmissionFilter
     * @param configProperties
     * @return
     */
    @Bean
    public FilterRegistrationBean addRepeatFilter(RepeatSubmissionFilter repeatSubmissionFilter,BlogConfigProperties configProperties){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(repeatSubmissionFilter);
        filterRegistrationBean.addUrlPatterns(configProperties.getRepeatSubmissionAddresses());
        return filterRegistrationBean;
    }

    /**
     * 防盗链过滤器
     * @param imageFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean addImageFilter(ImageFilter imageFilter){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(imageFilter);
        filterRegistrationBean.addUrlPatterns("/protect/*");
        return filterRegistrationBean;
    }

}
