package com.gamelogservice.manageserver.service;

import reactor.core.publisher.Flux;

import com.gamelogservice.manageserver.entity.GameLogManageEntity;
import com.gamelogservice.service.IBaseDbService;

public interface ILogManageService extends IBaseDbService<GameLogManageEntity>{
	/** 根据sql查询出玩家信息*/
	public Flux<GameLogManageEntity> findBySql(int gameCode,String logservicename) throws Exception;
}
