package org.redeyefrog.exception;

import lombok.Getter;
import org.redeyefrog.enums.StatusCode;

@Getter
public class FrogRuntimeException extends RuntimeException {

    private StatusCode statusCode;

    public FrogRuntimeException(StatusCode statusCode, Throwable cause) {
        this(statusCode.getDesc(), cause);
        this.statusCode = statusCode;
    }

    public FrogRuntimeException(String message, Throwable cause) {
        super(message, cause, true, false);
    }

}
