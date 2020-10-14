package com.lkz.blog.web.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 定义了博客中用到的可能需要动态配置的属性
 */
@Component
@ConfigurationProperties(prefix = "blog.config")
public class BlogConfigProperties {
    /**
     * Domains allowed to pass image filters
     */
    private String[] domainNames = {"localhost"};

    /**
     * Do not allow duplicate addresses
     */
    private String[] repeatSubmissionAddresses;

    /**
     * Duplicate submission is not allowed within the specified time（seconds）
     */
    private int repeatSubmissionSeconds=60;

    /**
     * 是否需要使用分布式session
     */
    private boolean redisRemember = false;

    /**
     * 是否需要自定义的登录和自定义EntryPoint，（需要自定义登陆页面）
     */
    private boolean customizerLoginPageAndResp = false;

    /**
     * 默认创建的男性头像地址,可配置多个,可以是完整地址也可以是相对跟路径的地址
     */
    private String[] defaultManHeadURL = {"/imageService/man.jpg"};
    /**
     * 默认创建的女性头像地址，可配置多个,可以是完整地址也可以是相对跟路径的地址
     */
    private String[] defaultGirlHeadURL = {"/imageService/girl.jpg"};


    public String[] getDefaultManHeadURL() {
        return defaultManHeadURL;
    }

    public void setDefaultManHeadURL(String[] defaultManHeadURL) {
        this.defaultManHeadURL = defaultManHeadURL;
    }

    public String[] getDefaultGirlHeadURL() {
        return defaultGirlHeadURL;
    }

    public void setDefaultGirlHeadURL(String[] defaultGirlHeadURL) {
        this.defaultGirlHeadURL = defaultGirlHeadURL;
    }

    public boolean isRedisRemember() {
        return redisRemember;
    }

    public void setRedisRemember(boolean redisRemember) {
        this.redisRemember = redisRemember;
    }

    public boolean isCustomizerLoginPageAndResp() {
        return customizerLoginPageAndResp;
    }

    public void setCustomizerLoginPageAndResp(boolean customizerLoginPageAndResp) {
        this.customizerLoginPageAndResp = customizerLoginPageAndResp;
    }
    public String[] getRepeatSubmissionAddresses() {
        return repeatSubmissionAddresses;
    }

    public void setRepeatSubmissionAddresses(String[] repeatSubmissionAddresses) {
        this.repeatSubmissionAddresses = repeatSubmissionAddresses;
    }

    public int getRepeatSubmissionSeconds() {
        return repeatSubmissionSeconds;
    }

    public void setRepeatSubmissionSeconds(int repeatSubmissionSeconds) {
        this.repeatSubmissionSeconds = repeatSubmissionSeconds;
    }

    public String[] getDomainNames() {
        return domainNames;
    }

    public void setDomainNames(String[] domainNames) {
        this.domainNames = domainNames;
    }
}
