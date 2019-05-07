package com.dc.testthymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.dc.testthymeleaf.conf.MakeLogProperties;

@SpringBootApplication
@EnableConfigurationProperties({MakeLogProperties.class})
public class TestthymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestthymeleafApplication.class, args);
	}

}
