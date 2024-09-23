package com.roman.sales_info_remote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SalesInfoRemoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesInfoRemoteApplication.class, args);
	}

}
