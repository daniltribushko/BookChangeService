package ru.tdd.bookchange.application.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Ошибка, о том что объект уже имеется в базе данных
 */
public class AlreadyExistException extends ApiException {
    public AlreadyExistException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
