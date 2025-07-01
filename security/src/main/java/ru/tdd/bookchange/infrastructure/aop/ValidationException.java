package ru.tdd.bookchange.infrastructure.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tdd.bookchange.application.dto.ExceptionResponse;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Аспект для перехвата ошибок валидации
 */
@RestControllerAdvice
public class ValidationException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> validationExceptionResponse(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(
                        new ExceptionResponse(
                                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                exception.getBindingResult()
                                        .getFieldErrors()
                                        .stream()
                                        .map(FieldError::getDefaultMessage)
                                        .collect(Collectors.joining(",\n")),
                                LocalDateTime.now()
                        )
                );
    }
}
