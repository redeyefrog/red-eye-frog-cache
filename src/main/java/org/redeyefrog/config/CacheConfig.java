package org.redeyefrog.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
import org.redeyefrog.enums.CaffeineCaches;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

@EnableCaching
@Configuration
public class CacheConfig {

    @Primary
    @Bean
    CacheManager caffeineCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        for (CaffeineCaches cache : CaffeineCaches.values()) {
            caffeineCacheManager.registerCustomCache(cache.getName(), Caffeine.newBuilder()
                                                                              .expireAfterWrite(cache.getTtl())
//                                                                              .expireAfterWrite(3, TimeUnit.MINUTES)
                                                                              .build());
        }
        return caffeineCacheManager;
    }

    @Bean
    CacheManager guavaCacheManege() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(name,
                        CacheBuilder.newBuilder()
                                    .expireAfterWrite(Duration.ofMinutes(1))
                                    .build().asMap(),
                        false);
            }
        };
    }

}
