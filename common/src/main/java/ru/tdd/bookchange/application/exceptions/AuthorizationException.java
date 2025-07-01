package ru.tdd.bookchange.application.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Tribushko Danil
 * @since 21.06.2025
 * Ошибка авторизации
 */
public class AuthorizationException extends ApiException {
    public AuthorizationException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public AuthorizationException() {
        super(HttpStatus.UNAUTHORIZED, "Неверный логин или пароль");
    }
}
