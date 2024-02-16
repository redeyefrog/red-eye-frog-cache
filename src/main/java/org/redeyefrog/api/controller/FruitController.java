package org.redeyefrog.api.controller;

import org.redeyefrog.api.dto.common.FrogResponse;
import org.redeyefrog.api.dto.fruit.FindFruitQueryCondition;
import org.redeyefrog.api.dto.fruit.FindFruitQueryResult;
import org.redeyefrog.api.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/fruit")
@RestController
public class FruitController {

    @Autowired
    private FruitService fruitService;

    @GetMapping("/find")
    public FrogResponse<FindFruitQueryResult> findFruit(@ModelAttribute FindFruitQueryCondition condition) {
        return fruitService.findFruit(condition);
    }

    @GetMapping("/caffeine/find")
    public FrogResponse<FindFruitQueryResult> findFruitFromCaffeine(@ModelAttribute FindFruitQueryCondition condition) {
        return fruitService.findFruitFromCaffeine(condition);
    }

    @GetMapping("/guava/find")
    public FrogResponse<FindFruitQueryResult> findFruitFromGuava(@ModelAttribute FindFruitQueryCondition condition) {
        return fruitService.findFruitFromGuava(condition);
    }

    @GetMapping("/redis/find")
    public FrogResponse<FindFruitQueryResult> findFruitFromRedis(@ModelAttribute FindFruitQueryCondition condition) {
        return fruitService.findFruitFromRedis(condition);
    }

}
