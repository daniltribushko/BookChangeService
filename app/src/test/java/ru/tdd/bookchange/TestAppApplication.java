
package ru.tdd.bookchange;

import org.springframework.boot.SpringApplication;

public class TestAppApplication {

	public static void main(String[] args) {
		SpringApplication.from(AppApplication::main).with(TestContainersConfig.class).run(args);
	}

}

