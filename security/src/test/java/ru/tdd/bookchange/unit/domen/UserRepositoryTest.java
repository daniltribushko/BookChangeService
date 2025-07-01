package ru.tdd.bookchange.unit.domen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tdd.bookchange.TestContainersConfig;
import ru.tdd.bookchange.domen.entities.AppUser;
import ru.tdd.bookchange.domen.entities.Email;
import ru.tdd.bookchange.domen.enums.users.UserState;
import ru.tdd.bookchange.domen.repositories.UserRepository;
import ru.tdd.bookchange.domen.specifications.UserCoreSpecification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestContainersConfig.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UUID[] testIds;

    @BeforeEach
    void setUp() {
        AppUser testUser1 = AppUser.builder()
                .state(UserState.ACTIVE)
                .telegramUsername("test_user")
                .username("test_user")
                .password("123")
                .email(new Email("test@email.com"))
                .build();

        AppUser testUser2 = AppUser.builder()
                .state(UserState.ACTIVE)
                .telegramUsername("uSeR2")
                .username("User_fOr_TESt")
                .password("123")
                .build();

        AppUser testUser3 = AppUser.builder()
                .state(UserState.DELETED)
                .username("deLEtEd-UsER")
                .password("123")
                .build();

       userRepository.saveAll(List.of(testUser1, testUser2, testUser3));

       testIds = new UUID[]{testUser1.getId(), testUser2.getId(), testUser3.getId()};
    }

    @Test
    void saveUser() {
        long initialCount = userRepository.count();
        AppUser newUser = AppUser.builder()
                .state(UserState.ACTIVE)
                .telegramUsername("new_user")
                .username("newuser")
                .password("newpass123")
                .email(new Email("new@email.com"))
                .build();

        userRepository.save(newUser);

        Assertions.assertEquals(initialCount + 1, userRepository.count());
    }


    @Test
    void findUserById() {
        Optional<AppUser> foundUser1 = userRepository.findById(testIds[0]);
        Optional<AppUser> foundUser2 = userRepository.findById(testIds[1]);
        Optional<AppUser> notFoundUser1 = userRepository.findById(UUID.randomUUID());
        Optional<AppUser> notFoundUser2 = userRepository.findById(UUID.randomUUID());

        Assertions.assertTrue(foundUser1.isPresent());
        Assertions.assertTrue(foundUser2.isPresent());
        Assertions.assertTrue(notFoundUser1.isEmpty());
        Assertions.assertTrue(notFoundUser2.isEmpty());
        Assertions.assertEquals(foundUser1.get().getId(), testIds[0]);
        Assertions.assertEquals(foundUser2.get().getId(), testIds[1]);
    }
    @Test
    void deleteUser() {
        long expectedCount = userRepository.count() - 1;

        Optional<AppUser> userForDelete = userRepository.findById(testIds[2]);
        Assertions.assertTrue(userForDelete.isPresent());
        userRepository.delete(userForDelete.get());

        long actualCount = userRepository.count();
        Assertions.assertEquals(expectedCount, actualCount);
    }

    @Test
    void updateUser() {
        UUID id = testIds[2];

        Optional<AppUser> userForUpdateOpt = userRepository.findById(id);
        Assertions.assertTrue(userForUpdateOpt.isPresent());
        AppUser userForUpdate = userForUpdateOpt.get();

        userForUpdate.setUsername("NEW_USERNAME");
        userForUpdate.setPassword("new_password");

        AppUser newUser = userRepository.save(userForUpdate);

        Assertions.assertEquals("NEW_USERNAME", newUser.getUsername());
        Assertions.assertEquals("new_password", newUser.getPassword());
    }

    @Test
    void findByUsernameTest() {
        Optional<AppUser> foundUser1 = userRepository.findOne(
                UserCoreSpecification.byUsername("test_user")
        );

        Optional<AppUser> foundUser2 = userRepository.findOne(
                UserCoreSpecification.byUsername("deLEtEd-UsER")
        );

        Optional<AppUser> notFoundUser1 = userRepository.findOne(
                UserCoreSpecification.byUsername("not_found_User")
        );

        Optional<AppUser> notFoundUser2 = userRepository.findOne(
                UserCoreSpecification.byUsername("user")
        );

        Assertions.assertTrue(foundUser1.isPresent());
        Assertions.assertTrue(foundUser2.isPresent());
        Assertions.assertTrue(notFoundUser1.isEmpty());
        Assertions.assertTrue(notFoundUser2.isEmpty());
    }

    @Test
    void findByEmailTest() {
        Optional<AppUser> foundUser1 = userRepository.findOne(
                UserCoreSpecification.byEmail("test@email.com")
        );

        Optional<AppUser> notFoundUser1 = userRepository.findOne(
                UserCoreSpecification.byEmail("non")
        );

        Optional<AppUser> notFoundUser2 = userRepository.findOne(
                UserCoreSpecification.byEmail("email")
        );

        Assertions.assertTrue(foundUser1.isPresent());
        Assertions.assertTrue(notFoundUser1.isEmpty());
        Assertions.assertTrue(notFoundUser2.isEmpty());
    }

    @Test
    void findByTelegramUsernameTest() {
        Optional<AppUser> foundUser1 = userRepository.findOne(
                UserCoreSpecification.byTelegramUsername("test_user")
        );

        Optional<AppUser> foundUser2 = userRepository.findOne(
                UserCoreSpecification.byTelegramUsername("uSeR2")
        );

        Optional<AppUser> notFoundUser1 = userRepository.findOne(
                UserCoreSpecification.byTelegramUsername("non_foundUser")
        );

        Optional<AppUser> notFoundUser2 = userRepository.findOne(
                UserCoreSpecification.byTelegramUsername("NonFoundUser")
        );

        Assertions.assertTrue(foundUser1.isPresent());
        Assertions.assertTrue(foundUser2.isPresent());
        Assertions.assertTrue(notFoundUser1.isEmpty());
        Assertions.assertTrue(notFoundUser2.isEmpty());
    }
}