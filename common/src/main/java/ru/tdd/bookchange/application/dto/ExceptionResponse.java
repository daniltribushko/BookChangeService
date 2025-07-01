package ru.tdd.bookchange.application.dto;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Dto исключения
 */
public class ExceptionResponse {
    private int code;
    private String message;
    private LocalDateTime timestamp;

    public ExceptionResponse(int code, String message, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
