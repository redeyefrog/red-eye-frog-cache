package org.redeyefrog.handler;

import lombok.extern.slf4j.Slf4j;
import org.redeyefrog.api.dto.common.FrogResponse;
import org.redeyefrog.api.transform.FrogTransformer;
import org.redeyefrog.enums.StatusCode;
import org.redeyefrog.exception.FrogRuntimeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FrogExceptionHandler {

    @ExceptionHandler(value = FrogRuntimeException.class)
    public FrogResponse onFrogRuntimeException(FrogRuntimeException e) {
        log.error(e.getCause().getMessage(), e.getCause());
        return FrogTransformer.transfer(e.getStatusCode());
    }

    @ExceptionHandler(value = Throwable.class)
    public FrogResponse onThrowable(Throwable e) {
        log.error(e.getMessage(), e);
        return FrogTransformer.transfer(StatusCode.SYSTEM_ERROR);
    }

}
