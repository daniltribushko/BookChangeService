package ru.tdd.bookchange.domen.conventors;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.tdd.bookchange.domen.enums.users.Role;
import ru.tdd.bookchange.application.utils.TextUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Tribushko Danil
 * @since 21.06.2025
 * Конвертер роли пользователя
 */
@Converter
public class RoleConverter implements AttributeConverter<Set<Role>, String> {
    @Override
    public String convertToDatabaseColumn(Set<Role> attribute) {
        return attribute == null ? null : attribute.stream().map(Role::toString).collect(Collectors.joining(","));
    }

    @Override
    public Set<Role> convertToEntityAttribute(String dbData) {
        if (TextUtils.isEmpty(dbData)) {
            return Set.of();
        } else {
            String[] roles = dbData.split(",");
            return roles.length != 0 ?
                    Arrays.stream(dbData.split(","))
                            .map(Role::valueOf).collect(Collectors.toSet()) :
                    Set.of();
        }
    }
}
