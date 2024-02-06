package org.redeyefrog.api.dto.redis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedisRequest {

    private String key;

    private String content;

    @JsonProperty("expire_seconds")
    private Long expireSeconds;

}
