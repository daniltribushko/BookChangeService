package ru.tdd.bookchange.application.utils;

/**
 * @author Tribushko Danil
 * @since 19.06.2025
 * Утилиты для работы с текстом
 */
public class TextUtils {
    private TextUtils() {
    }

    /**
     * Проверка что строка не пуста и не равна null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Проверка на то что строка пуста или равна null
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Перевод первого символа строки в верхний регистр
     */
    public static String firstUpperCase(String str) {
        return oneCharUpperCase(str, 0);
    }

    /**
     * Перевод определенного символа в верхний регистр
     */
    public static String oneCharUpperCase(String str, int index) {
        if (index != 0 && index != str.length() - 1) {
            return str.substring(0, index) +
                    str.substring(index, index + 1).toUpperCase()
                    + str.substring(index + 1);
        } else if (index == 0) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        } else {
            int lastIndex = str.length() - 1;
            return str.substring(0, lastIndex - 1) + str.substring(lastIndex).toUpperCase();
        }
    }
}
