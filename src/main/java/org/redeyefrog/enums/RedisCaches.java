package org.redeyefrog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@AllArgsConstructor
@Getter
public enum RedisCaches {

    FRUIT("redisFruit", Duration.ofMinutes(2));

    private String name;

    private Duration ttl;

}
