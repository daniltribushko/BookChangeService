package ru.tdd.bookchange.domen.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Tribushko Danil
 * @since 21.06.2025
 * Аннотация для ханения названий моделей
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassName {
    String name() default "";
}
