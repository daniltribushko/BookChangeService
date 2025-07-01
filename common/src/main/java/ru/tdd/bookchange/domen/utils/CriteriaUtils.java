package ru.tdd.bookchange.domen.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.tdd.bookchange.application.models.Tuple;
import ru.tdd.bookchange.domen.entities.DBEntity;
import ru.tdd.bookchange.domen.enums.jpa.Condition;
import ru.tdd.bookchange.application.utils.TextUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @param <T> класс сущности базы данных
 * @author Tribushko Danil
 * @since 19.06.2025
 */
public class CriteriaUtils<T extends DBEntity> {

    private CriteriaBuilder cb;
    private Root<T> root;

    private CriteriaUtils() {
    }

    public static <T extends DBEntity> CriteriaUtils<T> builder(CriteriaBuilder cb, Root<T> root) {
        CriteriaUtils<T> criteriaUtils = new CriteriaUtils<T>();
        criteriaUtils.cb = cb;
        criteriaUtils.root = root;

        return criteriaUtils;
    }

    /**
     * Поиск по совпадению по одному значению
     */
    public Predicate like(String field, String value) {
        if (TextUtils.isNotEmpty(value)) {
            return cb.like(
                    cb.lower(root.get(field)),
                    getStringPredicate(value),
                    '_'
            );
        } else {
            return cb.conjunction();
        }
    }

    /**
     * Поиск по совпадению по одному значению
     */
    public Predicate like(Expression<String> field, String value) {
        if (TextUtils.isNotEmpty(value)) {
            return cb.like(
                    cb.lower(field),
                    getStringPredicate(value),
                    '_'
            );
        } else {
            return cb.conjunction();
        }
    }

    /**
     * поиск по отсуствую совпадения по одному элементу
     */
    public Predicate nptLike(String field, String value) {
        if (TextUtils.isNotEmpty(value)) {
            return cb.not(
                    cb.like(
                            cb.lower(root.get(field)),
                            getStringPredicate(value),
                            '_'
                    )
            );
        } else {
            return cb.conjunction();
        }
    }

    /**
     * Поиск по совпадению по нескольким параметрам с коньюкцией
     */
    public Predicate like(String field, String... values) {
        if (values.length == 0) {
            return cb.conjunction();
        } else {
            Predicate[] predicates = (Predicate[]) Arrays
                    .stream(values)
                    .map(value -> cb.like(cb.lower(root.get(field)), getStringPredicate(value)))
                    .toArray();
            return cb.and(predicates);
        }
    }

    /**
     * Поиск по совпадению по нескольким параметрам с указанием типа логической операции
     */
    public Predicate like(Condition condition, Tuple<String, String>... values) {
        if (values.length == 0) {
            return cb.conjunction();
        } else {
            Predicate[] predicates = (Predicate[]) Arrays
                    .stream(values)
                    .map(value ->
                            cb.like(
                                    root.get(value.getValue1()),
                                    getStringPredicate(value.getValue2())
                            )
                    )
                    .toArray();
            switch (condition) {
                case Condition.OR -> {
                    return cb.or(predicates);
                }
                case Condition.NOT -> throw new RuntimeException("Дизьюнкция поддерживает только 1 условие");
                default -> {
                    return cb.and(predicates);
                }
            }
        }
    }

    /**
     * Поиск в границах даты и времени
     *
     * @param field название поля в таблицу с типом timestamp
     * @param start левая граница даты с временем
     * @param end   правая граница даты с временем
     */
    public Predicate dateRange(String field, LocalDateTime start, LocalDateTime end) {
        return dateRange(field, start.toLocalDate(), end.toLocalDate());
    }

    /**
     * Поиск по равенству строки
     */
    public Predicate equal(String field, String value) {
        if (value != null) {
            return cb.equal(
                    root.get(field), value
            );
        } else {
            return cb.conjunction();
        }
    }

    /**
     * Поиск по равенству строки
     */
    public Predicate equal(Expression<String> field, String value) {
        if (value != null) {
            return cb.equal(
                    field, value
            );
        } else {
            return cb.conjunction();
        }
    }

    /**
     * Поиск в границах даты
     *
     * @param field название поля в таблицу с типом date
     * @param start левая граница даты
     * @param end   правая граница даты
     */
    public Predicate dateRange(String field, LocalDate start, LocalDate end) {
        if (start != null && end != null) {
            return cb.between(root.get(field), start, end);
        } else if (start != null) {
            return cb.greaterThanOrEqualTo(root.get(field), start);
        } else if (end != null) {
            return cb.lessThanOrEqualTo(root.get(field), end);
        } else {
            return cb.conjunction();
        }
    }

    private String getStringPredicate(String value) {
        return "%" + value.toLowerCase() + "%";
    }
}
