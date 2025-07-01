package ru.tdd.bookchange.domen.entities;

import jakarta.persistence.*;
import ru.tdd.bookchange.domen.enums.users.EmailState;

/**
 * @author Tribushko Danil
 * @since 20.06.2025
 * Класс электронного адреса пользователя
 */
@Entity
public class Email extends DBEntity {
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "state", nullable = false)
    private EmailState state;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", unique = true)
    private AppUser owner;

    public Email(String email) {
        this.email = email;
        this.state = EmailState.CONFIRMED;
    }

    public Email() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailState getState() {
        return state;
    }

    public void setState(EmailState state) {
        this.state = state;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser user) {
        this.owner = user;
    }
}
