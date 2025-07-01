package ru.tdd.bookchange.application.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 21.06.2025
 * Базовый класс исключения
 */
public abstract class ApiException extends RuntimeException {
    protected int code;
    protected LocalDateTime timestamp;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.code = status.value();
        this.timestamp = LocalDateTime.now();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
