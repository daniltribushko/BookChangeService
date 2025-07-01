package ru.tdd.bookchange.domen.enums.users;

import ru.tdd.bookchange.domen.enums.RuNameEnum;
import ru.tdd.bookchange.application.utils.TextUtils;

/**
 * @author Tribushko Danil
 * @since 20.06.2025
 * Состояние электронного адреса пользователя
 */
public enum EmailState implements RuNameEnum {
    /**
     * Подтвержден
     */
    CONFIRMED("Подтверждён"),
    /**
     * Не подтвержден
     */
    NON_CONFIRMED("Неподтверждён"),
    /**
     * Удален
     */
    DELETED("Удалён");

    private String ruName;

    EmailState(String ruName) {
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
