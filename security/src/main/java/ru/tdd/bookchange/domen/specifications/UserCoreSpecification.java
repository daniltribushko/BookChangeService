package ru.tdd.bookchange.domen.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.tdd.bookchange.domen.entities.AppUser;
import ru.tdd.bookchange.domen.utils.CriteriaUtils;

/**
 * @author Tribushko Danil
 * @since 21.06.2025
 * Набор спецификация для работы с пользователями
 */
public interface UserCoreSpecification {
    /**
     * Условие на поиск пользователя по его имени
     */
    static Specification<AppUser> byUsername(String username) {
        return (root, query, cb) ->
                CriteriaUtils.builder(cb, root).equal("username", username);
    }

    static Specification<AppUser> byEmail(String email) {
        return (root, query, cb) ->
                CriteriaUtils.builder(cb, root).equal(root.join("email").get("email"), email);
    }

    static Specification<AppUser> byTelegramUsername(String telegramUsername) {
        return (root, query, cb) ->
                CriteriaUtils.builder(cb, root).equal("telegramUsername", telegramUsername);
    }
}
