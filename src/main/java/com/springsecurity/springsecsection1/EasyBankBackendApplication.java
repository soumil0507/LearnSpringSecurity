package com.springsecurity.springsecsection1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//in case you havent mentioned the respositories in repository package mention the repository package name here
//@EnableJpaRepositories("com.springsecurity.springsecsection1.repository")
//in case you havent mentioned the model's in repository package mention the model's package name here
//@EntityScan("com.springsecurity.springsecsection1.model")
public class EasyBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyBankBackendApplication.class, args);
	}

}
