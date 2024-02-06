package org.redeyefrog.api.dto.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RedisResponse {

    private String key;

    private String content;

}
