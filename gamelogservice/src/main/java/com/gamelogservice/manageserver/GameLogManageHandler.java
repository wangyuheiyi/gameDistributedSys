package com.gamelogservice.manageserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.gamelogservice.manageserver.entity.GameLogManageEntity;
import com.gamelogservice.manageserver.service.ILogManageService;

@Component
public class GameLogManageHandler {
	/** 加载后台服务*/
	@Autowired
    private ILogManageService logManageService;
	
	public Mono<ServerResponse> saveInfo(ServerRequest request){
		Mono<GameLogManageEntity> gameLogManage=request.bodyToMono(GameLogManageEntity.class).flatMap(param->logManageService.saveInfo(param));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(gameLogManage, GameLogManageEntity.class);
	}
	
	public Mono<ServerResponse> findByGameCode(ServerRequest request){
		int gamecode=Integer.valueOf(request.pathVariable("gamecode"));
		Flux<GameLogManageEntity> infoFlux = logManageService.findByGameCode(gamecode);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(infoFlux, GameLogManageEntity.class);
	}
}
