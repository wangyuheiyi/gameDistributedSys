package com.gamelogservice.service;

import reactor.core.publisher.Mono;

/**
 * 基础数据层的服务接口,所以接口支持响应式的开发
 * @author Administrator
 *
 * @param <T>
 */
public interface IBaseDbService<T> {
	/** 存储一个数据*/
	public Mono<T> add(T param) throws Exception;
}
