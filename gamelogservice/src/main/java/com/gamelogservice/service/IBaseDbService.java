package com.gamelogservice.service;

import com.gamelogservice.manageserver.entity.GameLogManageEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 基础数据层的服务接口,所以接口支持响应式的开发
 * @author Administrator
 *
 * @param <T>
 */
public interface IBaseDbService<T> {
	/** 存储一个数据*/
	public Mono<T> saveInfo(T param) throws Exception;
	/** 查询全部*/
	public Flux<T> finaAllInfo() throws Exception;
	/** 删除一个数据*/
	public Mono<T> delete(T param) throws Exception;
}
