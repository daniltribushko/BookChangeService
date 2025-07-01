package ru.tdd.bookchange.application.exceptions;

import ru.tdd.bookchange.domen.annotations.ClassName;

import java.util.UUID;

/**
 * @author Tribushko Danil
 * @since 21.06.2025
 * Класс исключения о том что объект по идентификатору не найден
 */
public class NotFoundByIdException extends NotFoundException {
    public NotFoundByIdException(UUID id, Class<?> clazz, boolean isFeminine) {
        super(
                String.format(
                        "\"%s\" не найден%s с указанным идентификатором: %s",
                        clazz.isAnnotationPresent(ClassName.class) ?
                                clazz.getAnnotation(ClassName.class).name() :
                                clazz.getSimpleName(),
                        isFeminine ? "а" : "",
                        id
                )
        );
    }
}
