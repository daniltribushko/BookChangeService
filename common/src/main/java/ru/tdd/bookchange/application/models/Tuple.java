package ru.tdd.bookchange.application.models;

import java.util.Objects;

/**
 * @author Tribushko Danil
 * @since 19.06.2025
 * @param <V1> первое значение
 * @param <V2> второе значение
 * Класс для хранения пары значений
 */
public class Tuple<V1, V2> {
    private V1 value1;
    private V2 value2;

    public Tuple(V1 value1, V2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public V1 getValue1() {
        return value1;
    }

    public void setValue1(V1 value1) {
        this.value1 = value1;
    }

    public V2 getValue2() {
        return value2;
    }

    public void setValue2(V2 value2) {
        this.value2 = value2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(value1, tuple.value1) && Objects.equals(value2, tuple.value2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2);
    }
}
