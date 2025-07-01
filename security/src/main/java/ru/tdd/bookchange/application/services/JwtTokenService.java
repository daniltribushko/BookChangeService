package ru.tdd.bookchange.application.services;

import io.jsonwebtoken.Claims;
import ru.tdd.bookchange.domen.entities.AppUser;

/**
 * @author Tribushko Danil
 * @since 21.06.2025
 * Сервис для работы с jwt токенами
 */
public interface JwtTokenService {
    /**
     * Генерация jwt токена
     */
    String generateToken(AppUser user);

    /**
     * Чтение jwt токена
     */
    Claims parseToken(String token);

    /**
     * Проверка валидности токена
     */
    boolean validateToken(String token, AppUser user);
}
