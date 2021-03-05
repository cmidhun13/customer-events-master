package com.syzegee.customer.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@EnableAutoConfiguration
public class CustomerEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerEventsApplication.class, args);
	}

}
