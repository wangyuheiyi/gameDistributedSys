package com.gamelogservice.manageserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GameLogManageRouter {
	/** 加载具体操作的类*/
	@Autowired
	GameLogManageHandler gameLogManageHandler;
	
	@Bean
	public RouterFunction<ServerResponse> logManageRouter(){
		RequestPredicate predicatesContent=RequestPredicates.contentType(MediaType.APPLICATION_STREAM_JSON);
		RequestPredicate predicatesAccept=RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON).and(predicatesContent);
		return RouterFunctions.route().path("/logManage", 
				builder->builder.POST("/saveInfo",predicatesAccept,gameLogManageHandler::saveInfo)
				.GET("/findByGameCode/{gameCode}",gameLogManageHandler::findByGameCode)).build();
	}
}
