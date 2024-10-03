package com.application.CrudApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.application.*")
public class CrudApplicationBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplicationBackendApplication.class, args);
	}

}
  