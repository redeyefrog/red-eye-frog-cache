package org.redeyefrog.api.transform;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.redeyefrog.api.dto.common.FrogHeader;
import org.redeyefrog.api.dto.common.FrogResponse;
import org.redeyefrog.enums.StatusCode;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FrogTransformer {

    public static <T> FrogResponse<T> transfer(StatusCode statusCode) {
        return transfer(statusCode, null);
    }

    public static <T> FrogResponse<T> transfer(StatusCode statusCode, T content) {
        return transfer(statusCode.getCode(), statusCode.getDesc(), content);
    }

    public static <T> FrogResponse<T> transfer(String resultCode, String resultDesc, T content) {
        return FrogResponse.<T>builder()
                           .header(FrogHeader.builder()
                                             .resultCode(resultCode)
                                             .resultDesc(resultDesc)
                                             .build())
                           .response(Objects.isNull(content) ? null : content)
                           .build();
    }

}
