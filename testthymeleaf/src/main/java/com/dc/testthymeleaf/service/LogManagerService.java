package com.dc.testthymeleaf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.dc.testthymeleaf.TestthymeleafApplication;
import com.dc.testthymeleaf.bean.ResInfoBean;
import com.dc.testthymeleaf.dao.IGameLogManageRepository;
import com.dc.testthymeleaf.dao.ILogBeanMongoRepository;
import com.dc.testthymeleaf.dao.ILogFieldMongoRepository;
import com.dc.testthymeleaf.dao.ILogManageMongoRepository;
import com.dc.testthymeleaf.entity.GameLogManageEntity;
import com.dc.testthymeleaf.entity.LogBeanMongoEntity;
import com.dc.testthymeleaf.entity.LogFieldMongoEntity;
import com.dc.testthymeleaf.entity.LogManageMongoEntity;

/**
 * 日志管理数据库操作
 * @author Administrator
 *
 */
@Service
public class LogManagerService {

	private static Logger logger=LoggerFactory.getLogger(TestthymeleafApplication.class);
	/** jpamysql*/
	@Autowired 
	IGameLogManageRepository gameLogManageRepository;
	
	/** ============mongoDb============*/
	@Autowired
	ILogManageMongoRepository logManageMongoRepository;
	
	@Autowired
	ILogBeanMongoRepository logBeanMongoRepository;
	
	@Autowired
	ILogFieldMongoRepository lLogFieldMongoRepository;
	
	/**
	 * 保存数据库数据
	 * @param param
	 * @return
	 */
	public Mono<ResInfoBean> saveInfo(GameLogManageEntity param){
//		return Mono.<GameLogManageEntity>create(sink->{
//			try {
//				sink.success(gameLogManageRepository.save(param));
//			} catch (Exception e) {
//				sink.error(e);
//			}
//			
//		});
		Mono<ResInfoBean> resInfo=null;
		try {
			GameLogManageEntity gameLogManageEntity=gameLogManageRepository.save(param);
			resInfo=Mono.just(new ResInfoBean(0,"save is ok",gameLogManageEntity));
		} catch (Exception e) {
			resInfo=Mono.just(new ResInfoBean(1,"save is error ! :["+e.getMessage()+"]",param));
		}
		return resInfo;
	}
	
	/**
	 * 根据gameCode获取数据
	 * @param gameCode
	 * @return
	 */
	public Mono<ResInfoBean> findByGamecode(int gameCode){
		try {
			GameLogManageEntity gameLogManageEntity=gameLogManageRepository.findByGamecode(gameCode);
			if(gameLogManageEntity==null) gameLogManageEntity=new GameLogManageEntity();
			return Mono.just(new ResInfoBean(0,"get data info",gameLogManageEntity));
		} catch (Exception e) {
			return Mono.just(new ResInfoBean(1,"get data info error ! :["+e.getMessage()+"]",new GameLogManageEntity()));
		}
	}
	
	/**
	 * 保存数据库数据 MongoDB
	 * @param param
	 * @return
	 */
	public Mono<ResInfoBean> saveInfoByMongo(LogManageMongoEntity param){
		return logManageMongoRepository.save(param)
				.flatMap(info-> Mono.just(new ResInfoBean(0,"save is ok",info)))
				.onErrorResume(e-> Mono.just(new ResInfoBean(1,"save is error ! :["+e.getMessage()+"]",new LogManageMongoEntity())));
	}
	
	/**
	 * 查询数据库中的数据MongoDB
	 * @param gameCode
	 * @return
	 */
	public Mono<ResInfoBean> findByGamecodeMongo(int gameCode){
		return logManageMongoRepository.findByGamecode(gameCode)
				.defaultIfEmpty(new LogManageMongoEntity())
				.flatMap(info-> Mono.just(new ResInfoBean(0,"get data info ok",info)))
				.onErrorResume(e-> {return Mono.just(new ResInfoBean(1,"get data info error ! :["+e.getMessage()+"]",new LogManageMongoEntity()));});
	}
	
	/**
	 * 获取游戏对象的列表
	 * @param logManageId
	 * @return
	 */
	public Flux<LogBeanMongoEntity> findByLogManageId(String logManageId){
		return logBeanMongoRepository.findByLogManageId(logManageId)
		   		.defaultIfEmpty(new LogBeanMongoEntity())
		   		.onErrorResume(e->{
		   			logger.error(e.getMessage());
		   			return Flux.just(new LogBeanMongoEntity());
		   		});
	}
	
	/**
	 * 保存日志实体对象
	 * @param logBeanMongoEntity
	 * @return
	 */
	public Mono<ResInfoBean> saveLogBeanMongo(LogBeanMongoEntity logBeanMongoEntity){
		return logBeanMongoRepository.save(logBeanMongoEntity)
				.flatMap(info-> Mono.just(new ResInfoBean(0,"save is ok",info)))
				.onErrorResume(e-> Mono.just(new ResInfoBean(1,"save is error ! :["+e.getMessage()+"]",new LogBeanMongoEntity())));
	}
	
	/**
	 * 根据实体id查询实体中所有的字段
	 * @param logBeanId
	 * @return
	 */
	public Flux<LogFieldMongoEntity> findFieldsByLogBeanId(String logBeanId){
		return lLogFieldMongoRepository.findByLogBeanId(logBeanId)
		   		.defaultIfEmpty(new LogFieldMongoEntity())
		   		.onErrorResume(e->{
		   			logger.error(e.getMessage());
		   			return Flux.just(new LogFieldMongoEntity());
		   		});
	}
	
	/**
	 * 保存实体类字段
	 * @param logFieldMongoEntity
	 * @return
	 */
	public Mono<ResInfoBean> saveLogFieldMongo(LogFieldMongoEntity logFieldMongoEntity){
		return lLogFieldMongoRepository.save(logFieldMongoEntity)
				.flatMap(info-> Mono.just(new ResInfoBean(0,"save is ok",info)))
				.onErrorResume(e-> Mono.just(new ResInfoBean(1,"save is error ! :["+e.getMessage()+"]",new LogFieldMongoEntity())));
	} 
	
	public Mono<ResInfoBean> deleteLogFieldMongo(LogFieldMongoEntity logFieldMongoEntity){
		return lLogFieldMongoRepository.delete(logFieldMongoEntity)
				.flatMap(info-> Mono.just(new ResInfoBean(0,"delete field is ok",info)));
	}
}
