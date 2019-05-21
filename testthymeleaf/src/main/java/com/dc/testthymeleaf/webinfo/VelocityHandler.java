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
import com.dc.testthymeleaf.service.ReceiverFileService;
import com.dc.testthymeleaf.service.SendFileService;

@Component
public class VelocityHandler {
	@Autowired
	TestVelocity testVelocity;
	
	@Autowired
	LogManagerService logManagerService;
	
	@Autowired
	SendFileService sendFileService;
	
	@Autowired
	ReceiverFileService receiverFileService;
	
	
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
	public Mono<ServerResponse> saveLogManager(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogManageMongoEntity.class)
		.flatMap(param-> ok().contentType(MediaType.APPLICATION_STREAM_JSON).
				body(logManagerService.saveLogManager(param), ResInfoBean.class));
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
	 * 删除整个的日志信息
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> deleteLogManager(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogManageMongoEntity.class)
				.flatMap(param-> ok().contentType(MediaType.APPLICATION_STREAM_JSON).
						body(logManagerService.deleteLogManager(param), ResInfoBean.class));
	}
	
	/**
	 * 查询数据bean对象
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> getLogBeanByManage(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogManageMongoEntity.class)
				.flatMap(param-> ok().contentType(MediaType.APPLICATION_JSON).
						body(logManagerService.findByLogManage(param), LogBeanMongoEntity.class));
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
	 * 删除bean对象
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> deleteLogBean(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogBeanMongoEntity.class)
				.flatMap(param-> ok().contentType(MediaType.APPLICATION_JSON).
						body(logManagerService.deleteLogMongoMongo(param), ResInfoBean.class));
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
	
	/**
	 * 删除字段信息
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> deleteLogField(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogFieldMongoEntity.class)
		.flatMap(param-> ok().contentType(MediaType.APPLICATION_JSON).
				body(logManagerService.deleteLogFieldMongo(param), ResInfoBean.class));
	}
	
	/**
	 * 创建发送服务日志文件
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> creatSendFile(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogManageMongoEntity.class)
				.flatMap(param-> ok().contentType(MediaType.APPLICATION_JSON).
						body(sendFileService.creatSendObjFile(param), ResInfoBean.class));
	}
	
	/**
	 * 创建接收服务日志文件
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> creatReceiverFile(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogManageMongoEntity.class)
				.flatMap(param-> ok().contentType(MediaType.APPLICATION_JSON).
						body(receiverFileService.creatReceiverObjFile(param), ResInfoBean.class));
	}
	
	/**
	 * 运行mvn数据
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> runMvnCom(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogManageMongoEntity.class)
				.flatMap(param-> ok().contentType(MediaType.APPLICATION_JSON).
						body(sendFileService.runCom(param), ResInfoBean.class));
	}
	
	/**
	 * 运行命令操作
	 * @param serverRequest
	 * @return
	 */
	public Mono<ServerResponse> canMvnCom(ServerRequest serverRequest){
		return serverRequest.bodyToMono(LogManageMongoEntity.class)
				.flatMap(param-> ok().contentType(MediaType.APPLICATION_JSON).
						body(sendFileService.canCom(param), ResInfoBean.class));
	}
}
