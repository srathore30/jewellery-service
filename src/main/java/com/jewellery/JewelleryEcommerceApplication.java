package com.jewellery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
//@EnableJpaRepositories(basePackages = "com.jewellery.repositories")

public class JewelleryEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JewelleryEcommerceApplication.class, args);
	}

}
