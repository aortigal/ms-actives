package com.bank.msactives;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsActivesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsActivesApplication.class, args);
	}

}
