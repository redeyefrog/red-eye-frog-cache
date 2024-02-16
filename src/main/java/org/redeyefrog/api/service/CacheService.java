package org.redeyefrog.api.service;

import lombok.extern.slf4j.Slf4j;
import org.redeyefrog.api.dto.fruit.Fruit;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class CacheService {

    public List<Fruit> findAllFruit() {
        Fruit apple = Fruit.builder()
                           .name("Apple")
                           .price(25)
                           .countryOfOrigin("TW")
                           .build();
        Fruit banana = Fruit.builder()
                            .name("Banana")
                            .price(17)
                            .countryOfOrigin("TW")
                            .build();
        Fruit orange = Fruit.builder()
                            .name("Orange")
                            .price(15)
                            .countryOfOrigin("TW")
                            .build();
        Fruit watermelon = Fruit.builder()
                                .name("Watermelon")
                                .price(100)
                                .countryOfOrigin("TW")
                                .build();
        Fruit cherry = Fruit.builder()
                            .name("Cherry")
                            .price(150)
                            .countryOfOrigin("CA")
                            .build();
        return Stream.of(apple, banana, orange, watermelon, cherry)
                     .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "caffeineFruit")
    public List<Fruit> findAllFruitFromCaffeine() {
        log.info("Prepare caffeine cache");
        return findAllFruit();
    }

    @Cacheable(cacheNames = "guavaFruit", cacheManager = "guavaCacheManege")
    public List<Fruit> findAllFruitFromGuava() {
        log.info("Prepare guava cache");
        return findAllFruit();
    }

    @Cacheable(cacheNames = "redisFruit", cacheManager = "redisCacheManager", unless = "#result == null || #result.isEmpty")
    public List<Fruit> findAllFruitFromRedis() {
        log.info("Prepare redis cache");
        return findAllFruit();
    }

}
