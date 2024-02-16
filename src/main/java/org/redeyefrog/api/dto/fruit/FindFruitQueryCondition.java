package org.redeyefrog.api.dto.fruit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindFruitQueryCondition {

    private String name;

    private String countryOfOrigin;

}
