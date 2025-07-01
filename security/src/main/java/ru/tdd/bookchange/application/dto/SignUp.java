package ru.tdd.bookchange.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Dto запроса на регистрацию пользователя
 */
public class SignUp {
    @NotBlank(message = "Имя пользователя обязательно для заполнения")
    private String username;

    @NotBlank(message = "Пароль обязателен для заполнения")
    private String password;

    @JsonProperty(value = "confirm_password")
    private String confirmPassword;

    @Email(message = "Поле \"email\" должен иметь формат электронного адреса")
    private String email;

    @JsonProperty(value = "telegram_username")
    private String telegramUsername;

    public SignUp(
            String username,
            String password,
            String confirmPassword,
            String email,
            String telegramUsername
    ) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.telegramUsername = telegramUsername;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelegramUsername() {
        return telegramUsername;
    }

    public void setTelegramUsername(String telegramUsername) {
        this.telegramUsername = telegramUsername;
    }
}
