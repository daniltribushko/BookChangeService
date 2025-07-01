package ru.tdd.bookchange.domen.enums.users;

import ru.tdd.bookchange.domen.enums.RuNameEnum;
import ru.tdd.bookchange.application.utils.TextUtils;

/**
 * @author Tribushko Danil
 * @since 21.06.2025
 * Состояние пользователя
 */
public enum UserState implements RuNameEnum {
    ACTIVE("Активен"),
    BANNED("Заблокирован"),
    DELETED("Удален");

    private String ruName;

    UserState(String ruName) {
        this.ruName = ruName;
    }

    @Override
    public String getEngName() {
        return TextUtils.firstUpperCase(name());
    }

    @Override
    public String getRuName() {
        return ruName;
    }
}
