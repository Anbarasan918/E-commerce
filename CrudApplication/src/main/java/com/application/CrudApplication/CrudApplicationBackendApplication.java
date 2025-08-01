package com.application.CrudApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.application.*")
@EnableJpaRepositories(basePackages = "com.application.repository")
@EntityScan(basePackages = "com.application.entity")
public class CrudApplicationBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplicationBackendApplication.class, args);
	}

}
  