package org.redeyefrog.api.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FrogResponse<T> {

    @JsonProperty("Frog_Header")
    private FrogHeader header;

    @JsonProperty("Frog_Response")
    private T response;

}
