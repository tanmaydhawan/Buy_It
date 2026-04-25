package com.tanmay.buyit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BuyitApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuyitApplication.class, args);
	}

}
