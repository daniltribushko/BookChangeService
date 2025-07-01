package ru.tdd.bookchange.domen.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.tdd.bookchange.domen.annotations.ClassName;
import ru.tdd.bookchange.domen.conventors.RoleConverter;
import ru.tdd.bookchange.domen.enums.users.Role;
import ru.tdd.bookchange.domen.enums.users.UserState;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * @author Tribushko Danil
 * @since 19.06.2025
 * Класс пользователя системы
 */
@Entity
@ClassName(name = "Пользователь системы")
public class AppUser extends EntityVersion implements UserDetails {
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "telegram_username")
    private String telegramUsername;

    @Column(name = "telegram_id")
    private Long telegramId;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private UserState state;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private Email email;

    @Convert(converter = RoleConverter.class)
    private Set<Role> roles;

    @Column(name = "last_date_online")
    private LocalDateTime lastDateOnline;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private String username;
        private String password;
        private String telegramUsername;
        private Long telegramId;
        private Email email;
        private UserState state;
        private LocalDateTime lastDateOnline = LocalDateTime.now();
        private Set<Role> roles;
        private LocalDateTime updateDate = LocalDateTime.now();

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder telegramUsername(String telegramUsername) {
            this.telegramUsername = telegramUsername;
            return this;
        }

        public Builder telegramId(Long telegramId) {
            this.telegramId = telegramId;
            return this;
        }

        public Builder lastDateOnline(LocalDateTime lastDateOnline) {
            this.lastDateOnline = lastDateOnline;
            return this;
        }

        public Builder state(UserState state) {
            this.state = state;
            return this;
        }

        public Builder roles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public Builder updateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public AppUser build() {
            AppUser appUser = new AppUser();
            appUser.id = this.id;
            appUser.username = this.username;
            appUser.password = this.password;
            appUser.telegramUsername = this.telegramUsername;
            appUser.telegramId = this.telegramId;
            appUser.lastDateOnline = this.lastDateOnline;
            appUser.roles = this.roles;
            appUser.updateDate = this.updateDate;
            appUser.setEmail(this.email);
            appUser.state = this.state;
            return appUser;
        }
    }

    public AppUser() {
        super();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserState.BANNED != state;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return UserState.ACTIVE == state;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).toList();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelegramUsername() {
        return telegramUsername;
    }

    public void setTelegramUsername(String telegramUsername) {
        this.telegramUsername = telegramUsername;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        if (email != null) {
            email.setOwner(this);
        }
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public LocalDateTime getLastDateOnline() {
        return lastDateOnline;
    }

    public void setLastDateOnline(LocalDateTime lastDateOnline) {
        this.lastDateOnline = lastDateOnline;
    }
}
