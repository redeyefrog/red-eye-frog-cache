package org.redeyefrog.api.dto.fruit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Fruit implements Serializable {

    private String name;

    private Integer price;

    @JsonProperty("Country_of_Origin")
    private String countryOfOrigin;

}
