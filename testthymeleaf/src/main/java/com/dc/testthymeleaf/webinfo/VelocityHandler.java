package com.dc.testthymeleaf.webinfo;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import com.dc.testthymeleaf.bean.ResInfoBean;
import com.dc.testthymeleaf.entity.GameLogManageEntity;
import com.dc.testthymeleaf.entity.LogBeanMongoEntity;
import com.dc.testthymeleaf.entity.LogFieldMongoEntity;
import com.dc.testthymeleaf.entity.LogManageMongoEntity;
import com.dc.testthymeleaf.mytest.TestVelocity;
import com.dc.testthymeleaf.service.LogManagerService;

@Component
public class VelocityHandler {
	@Autowired
	TestVelocity testVelocity;
	
	@Autowired
	LogManagerService logManagerService;
	
	public Mono<ServerResponse> creatFile(ServerRequest serverRequest) {
		return ok().contentType(MediaType.TEXT_PLAIN).body(testVelocity.makeFile(),String.class);
	}
	
	public Mono<ServerResponse> initInfo(ServerRequest serverRequest){
		Mono<String> initInfo=Mono.just("wangyu");
		return ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(initInfo,String.class);
	}
	
	/**
	 * 保存数据
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> saveInfo(ServerRequest serverRequest){
		return serverRequest.bodyToMono(GameLogManageEntity.class)
		.flatMap(param-> ok().contentType(MediaType.APPLICATION_STREAM_JSON).
				body(logManagerService.saveInfo(param), ResInfoBean.class));
	}
	
	/**
	 * 查询数据
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> getInfoByGameCode(ServerRequest serverRequest){
		int gamecode=Integer.valueOf(serverRequest.pathVariable("gameCode"));
		return ok().contentType(MediaType.APPLICATION_STREAM_JSON).
				body(logManagerService.findByGamecode(gamecode),ResInfoBean.class);
	}
	
	/**
	 * 保存数据
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> saveInfoMongo(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogManageMongoEntity.class)
		.flatMap(param-> ok().contentType(MediaType.APPLICATION_STREAM_JSON).
				body(logManagerService.saveInfoByMongo(param), ResInfoBean.class));
	}
	
	/**
	 * 查询数据
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> getInfoByGameCodeMongo(ServerRequest serverRequest){
		int gamecode=Integer.valueOf(serverRequest.pathVariable("gameCode"));
		Mono<ResInfoBean> resinfo=logManagerService.findByGamecodeMongo(gamecode);
		return ok().contentType(MediaType.APPLICATION_STREAM_JSON).
				body(resinfo,ResInfoBean.class);
	}
	
	/**
	 * 查询数据bean对象
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> getLogBeanByManageId(ServerRequest serverRequest){
		String logManageId=serverRequest.pathVariable("logManageId");
		return ok().contentType(MediaType.APPLICATION_JSON).
				body(logManagerService.findByLogManageId(logManageId),LogBeanMongoEntity.class);
	}
	
	/**
	 * 插入日志数据对象
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> saveLogBean(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogBeanMongoEntity.class)
				.flatMap(param-> ok().contentType(MediaType.APPLICATION_JSON).
						body(logManagerService.saveLogBeanMongo(param), ResInfoBean.class));
	}
	
	/**
	 * 根据bean的id查询bean中的所有字段
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> getLogFieldByBeanId(ServerRequest serverRequest){
		String logBeanId=serverRequest.pathVariable("logBeanId");
		return ok().contentType(MediaType.APPLICATION_JSON).
				body(logManagerService.findFieldsByLogBeanId(logBeanId),LogFieldMongoEntity.class);
	}
	
	/**
	 * 保存字段数据
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> saveLogField(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogFieldMongoEntity.class)
				.flatMap(param-> ok().contentType(MediaType.APPLICATION_JSON).
						body(logManagerService.saveLogFieldMongo(param), ResInfoBean.class));
	}
	
	public Mono<ServerResponse> deleteLogField(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogFieldMongoEntity.class)
		.flatMap(param-> ok().contentType(MediaType.APPLICATION_JSON).
				body(logManagerService.deleteLogFieldMongo(param), ResInfoBean.class));
	}
}
