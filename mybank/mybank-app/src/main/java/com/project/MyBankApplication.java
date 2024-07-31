package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.project.*"})
public class MyBankApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyBankApplication.class, args);
	}
}
