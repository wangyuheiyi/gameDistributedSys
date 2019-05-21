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
import com.dc.testthymeleaf.util.LogUtil;

/**
 * 日志管理数据库操作
 * @author Administrator
 *
 */
@Service
public class LogManagerService {

	private static Logger logger=LoggerFactory.getLogger(TestthymeleafApplication.class);
	
	@Autowired
	LogUtil util;
	
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
	public Mono<ResInfoBean> saveLogManager(LogManageMongoEntity param){
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
	 * 删除整个的日志信息
	 * @param param
	 * @return
	 */
	public Mono<ResInfoBean> deleteLogManager(LogManageMongoEntity param){
		return logBeanMongoRepository.findByLogManageId(param.getId())
		.flatMap(logBean -> lLogFieldMongoRepository.findByLogBeanId(logBean.getId())
				.flatMap(logField ->lLogFieldMongoRepository.delete(logField)).onErrorResume(e->{
					logger.error("=====delete Field is error======="+e.getMessage());
					return Flux.empty();
				}).then(Mono.just(logBean))
		).flatMap(newLogBean->logBeanMongoRepository.delete(newLogBean)).onErrorResume(e->{
			logger.error("=====delete Bean is error======="+e.getMessage());
			return Flux.empty();
		}).then(Mono.just(param))
		.flatMap(logManage ->logManageMongoRepository.delete(logManage)).then(Mono.just(new ResInfoBean(0,"delete LogManager is ok",new LogBeanMongoEntity())))
		.onErrorResume(e-> Mono.just(new ResInfoBean(1,"delete LogManager is error ! :["+e.getMessage()+"]",new LogBeanMongoEntity())));
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
	 * 获取游戏对象的列表
	 * @param logManageId
	 * @return
	 */
	public Flux<LogBeanMongoEntity> findByLogManage(LogManageMongoEntity param){
		return logBeanMongoRepository.findByLogManageId(param.getId())
				//如果没有查询到就传经基础类
		   		.defaultIfEmpty(initLogBeanMongoEntity(param))
		   		.onErrorResume(e->{
		   			logger.error(e.getMessage());
		   			return Flux.just(new LogBeanMongoEntity());
		   		});
	}
	
	/**
	 * 默认初始化一个基类
	 * @param param
	 * @return
	 */
	private LogBeanMongoEntity initLogBeanMongoEntity(LogManageMongoEntity param){
		if(util.isStrNullOrEmpty(param.getBaseLogClassName())) return new LogBeanMongoEntity();
		LogBeanMongoEntity logBeanMongoEntity=new LogBeanMongoEntity();
		logBeanMongoEntity.setLogManageId(param.getId());
		logBeanMongoEntity.setBeanName(param.getBaseLogClassName());
		logBeanMongoEntity.setBeanDescribe("基类");
		logBeanMongoEntity.setIsBaseBean("1");
		return logBeanMongoEntity;
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
	 * 删除所有实体bean下所有的字段，再删除bean本身的数据
	 * @param logBeanMongoEntity
	 * @return
	 */
	public Mono<ResInfoBean> deleteLogMongoMongo(LogBeanMongoEntity logBeanMongoEntity){
		return lLogFieldMongoRepository.findByLogBeanId(logBeanMongoEntity.getId())
		.flatMap(logField->lLogFieldMongoRepository.delete(logField)).onErrorResume(e->{
			logger.error("=====delete Field is error======="+e.getMessage());
			return Flux.empty();
		}).then(Mono.just(logBeanMongoEntity))
		.flatMap(pram->logBeanMongoRepository.delete(pram))
		.then(Mono.just(new ResInfoBean(0,"delete bean is ok",Mono.empty())))
		.onErrorResume(e-> Mono.just(new ResInfoBean(1,"delete bean is error ! :["+e.getMessage()+"]",Mono.empty())));
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
	
	/**
	 * 删除实体类中的某一个字段
	 * @param logFieldMongoEntity
	 * @return
	 */
	public Mono<ResInfoBean> deleteLogFieldMongo(LogFieldMongoEntity logFieldMongoEntity){
		return lLogFieldMongoRepository.delete(logFieldMongoEntity).then(Mono.just(new ResInfoBean(0,"delete field is ok",Mono.empty())))
				.onErrorResume(e-> Mono.just(new ResInfoBean(1,"delete field is error ! :["+e.getMessage()+"]",Mono.empty())));
	}
}
