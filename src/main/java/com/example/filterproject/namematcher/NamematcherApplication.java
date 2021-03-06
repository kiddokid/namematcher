package com.example.filterproject.namematcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EntityScan
@SpringBootApplication(scanBasePackages = "com.example.filterproject")
public class NamematcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamematcherApplication.class, args);
	}
}
