package ru.tdd.bookchange;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestContainersConfig.class)
class SecurityApplicationTests {

    @Test
    void contextLoads() {
    }
}
