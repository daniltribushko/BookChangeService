package ru.tdd.bookchange.domen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.tdd.bookchange.domen.entities.AppUser;

import java.util.UUID;

/**
 * @author Tribushko Danil
 * @since 21.06.2025
 * Репозиторий для работы с ролями
 */
@Repository
public interface UserRepository extends JpaRepository<AppUser, UUID>, JpaSpecificationExecutor<AppUser> {
}
