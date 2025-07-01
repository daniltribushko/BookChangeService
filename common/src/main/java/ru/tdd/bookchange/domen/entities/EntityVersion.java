package ru.tdd.bookchange.domen.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 19.06.2025
 * Класс сущности в бд содержащий дату хранения и обновления
 */
@MappedSuperclass
public abstract class EntityVersion extends DBEntity {
    @Column(name = "creation_date", nullable = false)
    protected final LocalDateTime creationDate;

    @Column(name = "update_date", nullable = false)
    protected LocalDateTime updateDate;

    public EntityVersion() {
        this.creationDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
