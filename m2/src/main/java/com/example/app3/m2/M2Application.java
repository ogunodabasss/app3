package com.example.app3.m2;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableRabbit
@SpringBootApplication
public class M2Application {

	public static void main(String[] args) {
		SpringApplication.run(M2Application.class, args);
	}

}
