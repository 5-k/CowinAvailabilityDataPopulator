package com.prateek.cowinAvailibility.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "app")
@Primary
public class ConfigValueProperties {

    private String checkByDistrictURL;
    private String checkByPincodeURL;
    private String dateFormat;
    private String redishost;
    private String redispassword;
    private boolean redisssl;
    private int redisport;
    private String cronExp;
    private int cacheExpiryInSeconds;

    public int getCacheExpiryInSeconds() {
        return cacheExpiryInSeconds;
    }

    public void setCacheExpiryInSeconds(int cacheExpiryInSeconds) {
        this.cacheExpiryInSeconds = cacheExpiryInSeconds;
    }

    public String getCheckByDistrictURL() {
        return checkByDistrictURL;
    }

    public void setCheckByDistrictURL(String checkByDistrictURL) {
        this.checkByDistrictURL = checkByDistrictURL;
    }

    public String getCheckByPincodeURL() {
        return checkByPincodeURL;
    }

    public void setCheckByPincodeURL(String checkByPincodeURL) {
        this.checkByPincodeURL = checkByPincodeURL;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getRedishost() {
        return redishost;
    }

    public void setRedishost(String redishost) {
        this.redishost = redishost;
    }

    public String getRedispassword() {
        return redispassword;
    }

    public void setRedispassword(String redispassword) {
        this.redispassword = redispassword;
    }

    public boolean isRedisssl() {
        return redisssl;
    }

    public void setRedisssl(boolean redisssl) {
        this.redisssl = redisssl;
    }

    public int getRedisport() {
        return redisport;
    }

    public void setRedisport(int redisport) {
        this.redisport = redisport;
    }

    public String getCronExp() {
        return cronExp;
    }

    public void setCronExp(String cronExp) {
        this.cronExp = cronExp;
    }

}
