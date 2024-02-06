package org.redeyefrog.factory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redeyefrog.enums.StatusCode;
import org.redeyefrog.exception.FrogRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisFactory {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Set the value.
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        set(key, value, Duration.ZERO);
    }

    /**
     * Set the value with expiration timeout.
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        try {
//            redisTemplate.opsForValue().set(key, value, timeout, unit);
            redisTemplate.boundValueOps(key).set(value, timeout, unit);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Set the value with expiration timeout.
     *
     * @param key
     * @param value
     * @param timeout
     */
    public void set(String key, Object value, Duration timeout) {
        try {
            if (timeout.isZero()) {
                redisTemplate.boundValueOps(key).set(value);
            } else {
//                redisTemplate.opsForValue().set(key, value, timeout);
                redisTemplate.boundValueOps(key).set(value, timeout);
            }
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Set absent key and value with expiration timeout.
     * Only set the key if it does not already exist.
     * Like "set hello world nx" if hello does not exist return true.
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     * @return
     */
    public boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
        try {
            return redisTemplate.boundValueOps(key).setIfAbsent(value, timeout, unit);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Set absent key and value with expiration timeout.
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public boolean setIfAbsent(String key, Object value, Duration timeout) {
        try {
            return redisTemplate.boundValueOps(key).setIfAbsent(value, timeout);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Set present key and value with expiration timeout.
     * Only set the key if it already exists.
     * Like "set hello world xx" if hello already exists return true.
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     * @return
     */
    public boolean setIfPresent(String key, Object value, long timeout, TimeUnit unit) {
        try {
            return redisTemplate.boundValueOps(key).setIfPresent(value, timeout, unit);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Set present key and value with expiration timeout.
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public boolean setIfPresent(String key, Object value, Duration timeout) {
        try {
            return redisTemplate.boundValueOps(key).setIfPresent(value, timeout);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Get the value.
     *
     * @param key
     * @param clazz
     * @param <V>
     * @return
     */
    public <V> V get(String key, Class<V> clazz) {
        try {
            Object value = redisTemplate.boundValueOps(key).get();
            return (V) objectMapper.convertValue(value, clazz);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Get the value.
     *
     * @param key
     * @param typeReference
     * @param <V>
     * @return
     */
    public <V> V get(String key, TypeReference<V> typeReference) {
        try {
            Object value = redisTemplate.boundValueOps(key).get();
            return (V) objectMapper.convertValue(value, typeReference);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Return the value and delete the key
     *
     * @param key
     * @param typeReference
     * @param <V>
     * @return
     */
    public <V> V getAndDelete(String key, TypeReference<V> typeReference) {
        try {
            Object value = redisTemplate.boundValueOps(key).getAndDelete();
            return (V) objectMapper.convertValue(value, typeReference);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Determine if given key exists.
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Delete given keys.
     *
     * @param keys
     * @return
     */
    public long delete(String... keys) {
        try {
            return redisTemplate.delete(Arrays.asList(keys));
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Set the value of a hash key at the bound key.
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void put(String key, String hashKey, Object value) {
        put(key, hashKey, value, Duration.ZERO);
    }

    /**
     * Set the value of a hash key at the bound key with expiration timeout.
     *
     * @param key
     * @param hashKey
     * @param value
     * @param timeout
     */
    public void put(String key, String hashKey, Object value, Duration timeout) {
        put(key, Collections.singletonMap(hashKey, value), timeout);
    }

    /**
     * Set multiple hash fields to multiple values using data provided in map at the bound key with expiration timeout.
     *
     * @param key
     * @param hashKeyMap
     * @param timeout
     */
    public void put(String key, Map<String, Object> hashKeyMap, Duration timeout) {
        try {
            BoundHashOperations<String, String, Object> boundHashOperations = redisTemplate.boundHashOps(key);
            boundHashOperations.putAll(hashKeyMap);
            if (!timeout.isNegative() && !timeout.isZero()) {
                boundHashOperations.expire(timeout);
            }
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Set absent hash key and value at the bound key.
     *
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public boolean putIfAbsent(String key, String hashKey, Object value) {
        try {
            return redisTemplate.boundHashOps(key).putIfAbsent(hashKey, value);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Get value for given key from the hash at the bound key.
     *
     * @param key
     * @param hashKey
     * @param clazz
     * @param <V>
     * @return
     */
    public <V> V get(String key, String hashKey, Class<V> clazz) {
        try {
            Object value = redisTemplate.boundHashOps(key).entries().get(hashKey);
            return (V) objectMapper.convertValue(value, clazz);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Get value for given key from the hash at the bound key.
     *
     * @param key
     * @param hashKey
     * @param typeReference
     * @param <V>
     * @return
     */
    public <V> V get(String key, String hashKey, TypeReference<V> typeReference) {
        try {
            Object value = redisTemplate.boundHashOps(key).entries().get(hashKey);
            return (V) objectMapper.convertValue(value, typeReference);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Determine if given hash key exists at the bound key.
     *
     * @param key
     * @param hashKey
     * @return
     */
    public boolean hasHashKey(String key, String hashKey) {
        try {
            return redisTemplate.boundHashOps(key).hasKey(hashKey);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Delete given hash keys at the bound key.
     *
     * @param key
     * @param hashKeys
     * @return
     */
    public long deleteHashKeys(String key, String... hashKeys) {
        try {
            return redisTemplate.boundHashOps(key).delete(hashKeys);
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

    /**
     * Set the expiration for given key as a date timestamp.
     *
     * @param key
     * @param expireTime
     * @return
     */
    public boolean expireAt(String key, LocalDateTime expireTime) {
        try {
            return redisTemplate.expireAt(key, expireTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            throw new FrogRuntimeException(StatusCode.REDIS_ERROR, e);
        }
    }

}
