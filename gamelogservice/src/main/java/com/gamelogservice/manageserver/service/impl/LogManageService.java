package com.gamelogservice.manageserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import com.gamelogservice.manageserver.dao.IGameLogManageRepository;
import com.gamelogservice.manageserver.entity.GameLogManageEntity;
import com.gamelogservice.manageserver.service.ILogManageService;

/**
 * log服务器的主要配置数据的处理
 * @author Administrator
 * @Transactional spring 的事物注解。其本质是对方法前后进行拦截，然后在目标方法开始之前创建或者加入一个事务，在执行完目标方法之后根据执行情况提交或者回滚事务。
 * @Transactional 可以作用于接口、接口方法、类以及类方法上。当作用于类上时，该类的所有 public 方法将都具有该类型的事务属性，同时，我们也可以在方法级别使用该标注来覆盖类级别的定义。
 */
@Service("logManageService")
@Transactional
public class LogManageService implements ILogManageService{
	
	/** 加载数据库处理的类*/
	@Autowired
	IGameLogManageRepository iGameLogManageDao;

	
	@Override
	public Mono<GameLogManageEntity> saveInfo(GameLogManageEntity param){
		return Mono.<GameLogManageEntity>create(sink->{
			sink.success(iGameLogManageDao.save(param));
		});
	}

	/**
	 * 根据自定义的sql查询
	 */
	@Override
	public Flux<GameLogManageEntity> findBySql(int gameCode,String logservicename){
		return Flux.<GameLogManageEntity>create(sink->{ 
			for(GameLogManageEntity gameLogManageEntity:iGameLogManageDao.findByCustomSql(gameCode, logservicename)){
				sink.next(gameLogManageEntity);
			}
			sink.complete();
		}).subscribeOn(Schedulers.elastic());
	}

	/**
	 * 查询所有数据
	 */
	@Override
	public Flux<GameLogManageEntity> finaAllInfo(){
		return Flux.<GameLogManageEntity>create(sink->{ 
			for(GameLogManageEntity gameLogManageEntity:iGameLogManageDao.findAll()){
				sink.next(gameLogManageEntity);
			}
			sink.complete();
		}).subscribeOn(Schedulers.elastic());
	}

	/**
	 * 根据游戏id查询所有数据
	 */
	@Override
	public Flux<GameLogManageEntity> findByGameCode(int gameCode) {
		return Flux.<GameLogManageEntity>create(sink->{ 
			for(GameLogManageEntity gameLogManageEntity:iGameLogManageDao.findByGamecode(gameCode)){
				sink.next(gameLogManageEntity);
			}
			sink.complete();
		}).subscribeOn(Schedulers.elastic());
	}


	@Override
	public Mono<GameLogManageEntity> delete(GameLogManageEntity param){
		// TODO Auto-generated method stub
		return null;
	}
}
