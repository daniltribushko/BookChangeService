package ru.tdd.bookchange.domen.enums.jpa;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Логический операции
 */
public enum Condition {
    /**
     * Дизъюнкция
     */
    OR,

    /**
     * Конъюнкция
     */
    AND,

    /**
     * Отрицание
     */
    NOT
}
