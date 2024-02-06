package org.redeyefrog.api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.redeyefrog.api.dto.common.FrogRequest;
import org.redeyefrog.api.dto.common.FrogResponse;
import org.redeyefrog.api.dto.redis.RedisRequest;
import org.redeyefrog.api.dto.redis.RedisResponse;
import org.redeyefrog.api.transform.FrogTransformer;
import org.redeyefrog.enums.StatusCode;
import org.redeyefrog.factory.RedisFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    @Autowired
    private RedisFactory redisFactory;

    public FrogResponse<RedisResponse> addRecord(FrogRequest<RedisRequest> request) {
        RedisRequest redisRequest = request.getRequest();
        redisFactory.set(redisRequest.getKey(), request, Duration.ofSeconds(redisRequest.getExpireSeconds()));
        return FrogTransformer.transfer(StatusCode.SUCCESS, RedisResponse.builder()
                                                                         .key(redisRequest.getKey())
                                                                         .content(redisRequest.getContent())
                                                                         .build());
    }

    public FrogResponse<RedisResponse> getRecord(String key) {
        if (redisFactory.hasKey(key)) {
            FrogRequest<RedisRequest> request = redisFactory.get(key, new TypeReference<FrogRequest<RedisRequest>>() {
            });
            RedisRequest redisRequest = request.getRequest();
            return FrogTransformer.transfer(StatusCode.SUCCESS, RedisResponse.builder()
                                                                             .key(redisRequest.getKey())
                                                                             .content(redisRequest.getContent())
                                                                             .build());
        } else {
            return FrogTransformer.transfer(StatusCode.DATA_NOT_EXIST);
        }
    }

    public FrogResponse<RedisResponse> deleteRecord(String key) {
        long count = redisFactory.delete(key);
        StatusCode statusCode = count > 0 ? StatusCode.SUCCESS : StatusCode.DATA_NOT_EXIST;
        return FrogTransformer.transfer(statusCode);
    }

}
