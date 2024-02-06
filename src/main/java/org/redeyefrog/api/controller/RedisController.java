package org.redeyefrog.api.controller;

import org.redeyefrog.api.dto.common.FrogRequest;
import org.redeyefrog.api.dto.common.FrogResponse;
import org.redeyefrog.api.dto.redis.RedisRequest;
import org.redeyefrog.api.dto.redis.RedisResponse;
import org.redeyefrog.api.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/redis")
@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/add")
    public FrogResponse<RedisResponse> addRecord(@RequestBody FrogRequest<RedisRequest> request) {
        return redisService.addRecord(request);
    }

    @GetMapping("/get/{key}")
    public FrogResponse<RedisResponse> getRecord(@PathVariable String key) {
        return redisService.getRecord(key);
    }

    @DeleteMapping("/delete/{key}")
    public FrogResponse<RedisResponse> deleteRecord(@PathVariable String key) {
        return redisService.deleteRecord(key);
    }

}
