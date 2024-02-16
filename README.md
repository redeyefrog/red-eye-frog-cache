# Cache

## Redis
* Dependency
```xml
<dependencies>
    <!-- ... other dependency elements ... -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <!-- lettuce pools -->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-pool2</artifactId>
    </dependency>
</dependencies>
```
* Notice
  1. `RedisCacheConfiguration.serializeValuesWith` use `SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())` and bean use lombok `@Builder` must add `@NoArgsConstructor` and `@AllArgsConstructor`.
  2. If not use `RedisCacheConfiguration.serializeValuesWith` bean must implement serializable.
* Reference
  1. [Commands](https://redis.io/commands/)
  2. [Spring Data Redis](https://spring.io/projects/spring-data-redis)
  3. [Introduction to Spring Data Redis](https://www.baeldung.com/spring-data-redis-tutorial)
  4. [Spring Data Redis’s Property-Based Configuration](https://www.baeldung.com/spring-data-redis-properties)
  5. [Common Application Properties - spring.data.redis.*](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.data.spring.data.redis.database)
  6. [Spring Boot Cache with Redis](https://www.baeldung.com/spring-boot-redis-cache)

## Caffeine
* Dependency
```xml
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
</dependency>
```
* Reference
  1. [Spring Boot and Caffeine Cache](https://www.baeldung.com/spring-boot-caffeine-cache)
  2. [A Guide To Caching in Spring](https://www.baeldung.com/spring-cache-tutorial)
  3. [Using Multiple Cache Managers in Spring](https://www.baeldung.com/spring-multiple-cache-managers)

## Guava
* Dependency
```xml
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>${google.guava.version}</version>
</dependency>
```
* Notice
  1. Use `ConcurrentMapCacheManager` and `com.google.common.cache.CacheBuilder` to override createConcurrentMapCache.
