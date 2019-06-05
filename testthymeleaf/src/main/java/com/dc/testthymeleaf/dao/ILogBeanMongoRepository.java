package com.dc.testthymeleaf.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

import com.dc.testthymeleaf.entity.LogBeanMongoEntity;

public interface ILogBeanMongoRepository extends ReactiveMongoRepository<LogBeanMongoEntity,String>{
	public Flux<LogBeanMongoEntity> findByLogManageId(String logManageId);
}
