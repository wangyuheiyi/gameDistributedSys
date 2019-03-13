package com.gamelogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//指定自己的工厂类
//@EnableJpaRepositories(basePackages ="com.gamelogservice.dao",repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@SpringBootApplication
public class GamelogserviceApplication {
	 
	public static void main(String[] args) {
		SpringApplication.run(GamelogserviceApplication.class, args);
	}

}
