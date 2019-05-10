package com.dc.testthymeleaf.dao;

import reactor.core.publisher.Flux;
import com.dc.testthymeleaf.entity.LogFieldMongoEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ILogFieldMongoRepository extends ReactiveMongoRepository<LogFieldMongoEntity,String>{
	public Flux<LogFieldMongoEntity> findByLogBeanId(String logBeanId);
}
