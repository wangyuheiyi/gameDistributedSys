package com.dc.testthymeleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import com.dc.testthymeleaf.bean.ResInfoBean;
import com.dc.testthymeleaf.dao.IGameLogManageRepository;
import com.dc.testthymeleaf.dao.ILogManageMongoRepository;
import com.dc.testthymeleaf.entity.GameLogManageEntity;
import com.dc.testthymeleaf.entity.LogManageMongoEntity;

/**
 * 日志管理数据库操作
 * @author Administrator
 *
 */
@Service
public class LogManagerService {

	/** jpamysql*/
	@Autowired 
	IGameLogManageRepository gameLogManageRepository;
	
	/** mongoDb*/
	@Autowired
	ILogManageMongoRepository logManageMongoRepository;
	
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
	
	public Mono<ResInfoBean> findByGamecodeMongo(int gameCode){
		return logManageMongoRepository.findByGamecode(gameCode)
				.flatMap(info-> Mono.just(new ResInfoBean(0,"save is ok",info)))
				.onErrorResume(e-> Mono.just(new ResInfoBean(1,"save is error ! :["+e.getMessage()+"]",new LogManageMongoEntity())));
	}
}
