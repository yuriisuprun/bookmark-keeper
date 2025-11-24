package com.suprun.bookmark_keeper_api;

import org.springframework.boot.SpringApplication;

public class TestBookmarkKeeperApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(BookmarkKeeperApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
