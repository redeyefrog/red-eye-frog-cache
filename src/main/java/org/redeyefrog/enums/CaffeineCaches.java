package org.redeyefrog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@AllArgsConstructor
@Getter
public enum CaffeineCaches {

    FRUIT("caffeineFruit", Duration.ofMinutes(3));

    private String name;

    private Duration ttl;

}
