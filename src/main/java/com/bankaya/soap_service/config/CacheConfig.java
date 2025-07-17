package com.bankaya.soap_service.config;


import com.google.common.cache.CacheBuilder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cachPAeManager(){
        return new ConcurrentMapCacheManager() {
            @Override
            protected org.springframework.cache.Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(name,
                        CacheBuilder.newBuilder()
                                .expireAfterWrite(10, TimeUnit.MINUTES)
                                .maximumSize(100)
                                .build()
                                .asMap(),
                        false);
            }
        };
    }

}
