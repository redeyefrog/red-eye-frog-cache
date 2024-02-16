package org.redeyefrog.api.service;

import org.apache.commons.lang3.StringUtils;
import org.redeyefrog.api.dto.common.FrogResponse;
import org.redeyefrog.api.dto.fruit.FindFruitQueryCondition;
import org.redeyefrog.api.dto.fruit.FindFruitQueryResult;
import org.redeyefrog.api.dto.fruit.Fruit;
import org.redeyefrog.api.transform.FrogTransformer;
import org.redeyefrog.enums.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FruitService {

    @Autowired
    private CacheService cacheService;

    public FrogResponse<FindFruitQueryResult> findFruit(FindFruitQueryCondition condition) {
        List<Fruit> fruitList = filterFruitList(cacheService.findAllFruit(), condition);
        return FrogTransformer.transfer(StatusCode.SUCCESS, FindFruitQueryResult.builder()
                                                                                .fruitList(fruitList)
                                                                                .build());
    }

    public FrogResponse<FindFruitQueryResult> findFruitFromCaffeine(FindFruitQueryCondition condition) {
        List<Fruit> fruitList = filterFruitList(cacheService.findAllFruitFromCaffeine(), condition);
        return FrogTransformer.transfer(StatusCode.SUCCESS, FindFruitQueryResult.builder()
                                                                                .fruitList(fruitList)
                                                                                .build());
    }

    public FrogResponse<FindFruitQueryResult> findFruitFromGuava(FindFruitQueryCondition condition) {
        List<Fruit> fruitList = filterFruitList(cacheService.findAllFruitFromGuava(), condition);
        return FrogTransformer.transfer(StatusCode.SUCCESS, FindFruitQueryResult.builder()
                                                                                .fruitList(fruitList)
                                                                                .build());
    }

    public FrogResponse<FindFruitQueryResult> findFruitFromRedis(FindFruitQueryCondition condition) {
        List<Fruit> fruitList = filterFruitList(cacheService.findAllFruitFromRedis(), condition);
        return FrogTransformer.transfer(StatusCode.SUCCESS, FindFruitQueryResult.builder()
                                                                                .fruitList(fruitList)
                                                                                .build());
    }

    private List<Fruit> filterFruitList(List<Fruit> fruitList, FindFruitQueryCondition condition) {
        if (CollectionUtils.isEmpty(fruitList)) {
            return Collections.EMPTY_LIST;
        }
        return fruitList.stream()
                        .filter(fruit -> StringUtils.isNotBlank(condition.getName()) ? StringUtils.containsIgnoreCase(fruit.getName(), condition.getName()) : true)
                        .filter(fruit -> StringUtils.isNotBlank(condition.getCountryOfOrigin()) ? StringUtils.equalsIgnoreCase(fruit.getCountryOfOrigin(), condition.getCountryOfOrigin()) : true)
                        .collect(Collectors.toList());
    }

}
