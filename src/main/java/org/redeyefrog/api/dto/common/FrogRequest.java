package org.redeyefrog.api.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FrogRequest<T> {

    @JsonProperty("Frog_Request")
    private T request;

}
