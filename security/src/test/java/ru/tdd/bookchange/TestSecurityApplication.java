package ru.tdd.bookchange;

import org.springframework.boot.SpringApplication;

public class TestSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.from(SecurityApplication::main).with(TestContainersConfig.class).run(args);
    }
}
