package ru.tdd.bookchange.domen.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Tribushko Danil
 * @since 19.06.2025
 * Базовый класс сущности базы данных
 */
@MappedSuperclass
public abstract class DBEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DBEntity dbEntity = (DBEntity) o;
        return Objects.equals(id, dbEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
