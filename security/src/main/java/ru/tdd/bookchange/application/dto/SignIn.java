package ru.tdd.bookchange.application.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Dto запроса на авторизаию пользователя
 */
public class SignIn {
    @NotBlank(message = "Имя пользователя должно быть заполнено")
    private String username;
    @NotBlank(message = "Пароль пользователя должен быть заполнен")
    private String password;

    public SignIn(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
