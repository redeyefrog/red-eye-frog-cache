package org.redeyefrog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.redeyefrog.enums.RedisCaches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@EnableCaching
@Configuration
public class RedisConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    private GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    @Bean
    CacheManager redisCacheManager(LettuceConnectionFactory connectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
                                                         .withInitialCacheConfigurations(cacheConfigurations())
                                                         .build();
    }

    private Map<String, RedisCacheConfiguration> cacheConfigurations() {
        return Arrays.stream(RedisCaches.values()).collect(Collectors.toMap(RedisCaches::getName, cache -> RedisCacheConfiguration.defaultCacheConfig()
                                                                                                                                  .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                                                                                                                                  .entryTtl(cache.getTtl())));
    }

}
