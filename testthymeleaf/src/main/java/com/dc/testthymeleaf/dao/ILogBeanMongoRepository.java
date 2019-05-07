package com.dc.testthymeleaf.dao;

import reactor.core.publisher.Flux;
import com.dc.testthymeleaf.entity.LogBeanMongoEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ILogBeanMongoRepository extends ReactiveMongoRepository<LogBeanMongoEntity,String>{
	public Flux<LogBeanMongoEntity> findByLogManageId(String logManageId);
}
