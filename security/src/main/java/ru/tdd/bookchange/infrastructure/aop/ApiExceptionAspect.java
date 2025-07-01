package ru.tdd.bookchange.infrastructure.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tdd.bookchange.application.dto.ExceptionResponse;
import ru.tdd.bookchange.application.exceptions.ApiException;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Аспект для возврата исключений
 */
@RestControllerAdvice
public class ApiExceptionAspect {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionResponse> apiException(ApiException exception) {
        return ResponseEntity.status(exception.getCode()).body(
                new ExceptionResponse(
                        exception.getCode(),
                        exception.getMessage(),
                        exception.getTimestamp()
                )
        );
    }
}
