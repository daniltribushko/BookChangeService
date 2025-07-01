package ru.tdd.bookchange.application.services;

import ru.tdd.bookchange.application.dto.JwtTokenResponse;
import ru.tdd.bookchange.application.dto.SignIn;
import ru.tdd.bookchange.application.dto.SignUp;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Сервис для авторизации и регистрации пользователей
 */
public interface AuthService {

    /**
     * Авторизация пользователя
     */
    JwtTokenResponse signIn(SignIn signIn);

    /**
     * Регистраниця нового пользователя
     */
    JwtTokenResponse signUp(SignUp signUp);
}
