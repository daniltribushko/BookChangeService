package ru.tdd.bookchange.application.enums;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Класс времени для работы с милисекундами
 */
public enum Time {
    SECOND(1000),
    MINUTE(60 * SECOND.milliseconds),
    HOUR(60 * MINUTE.milliseconds),
    DAY(24 * HOUR.milliseconds);

    private final int milliseconds;

    Time(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public int getMilliseconds() {
        return milliseconds;
    }
}
