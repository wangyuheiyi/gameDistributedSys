package com.dc.testthymeleaf.dao;

import reactor.core.publisher.Mono;
import com.dc.testthymeleaf.entity.LogManageMongoEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ILogManageMongoRepository extends ReactiveMongoRepository<LogManageMongoEntity,Long>{
	public Mono<LogManageMongoEntity> findByGamecode(int gamecode);
}
