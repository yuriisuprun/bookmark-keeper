package com.suprun.bookmarkkeeper_api;

import org.springframework.boot.SpringApplication;

public class TestBookmarkkeeperApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(BookmarkkeeperApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
