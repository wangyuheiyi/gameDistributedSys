package com.dc.testthymeleaf.webinfo;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
	@Autowired
    private VelocityHandler velocityHandler;
	
	 @Bean
     public RouterFunction<ServerResponse> timerRouter() {
//		 return RouterFunctions.route(RequestPredicates.GET("/init").and(predicatesAccept),velocityHandler::initInfo)
//				 .andRoute(RequestPredicates.GET("/creatFile").and(predicatesAccept), velocityHandler::creatFile);
		 
		 return route(GET("/init").and(accept(MediaType.APPLICATION_JSON)), velocityHandler::initInfo)
				 .andRoute(GET("/findByGameCode/{gameCode}").and(accept(MediaType.APPLICATION_JSON)), velocityHandler::getInfoByGameCode)
				 .andRoute(POST("/saveInfo").and(accept(MediaType.APPLICATION_JSON)), velocityHandler::saveInfo)
				 .andRoute(GET("/findByGameCodeMongo/{gameCode}").and(accept(MediaType.APPLICATION_JSON)), velocityHandler::getInfoByGameCodeMongo)
				 .andRoute(POST("/saveInfoMongo").and(accept(MediaType.APPLICATION_JSON)), velocityHandler::saveInfoMongo)
				 .andRoute(GET("/findBylogBean/{logManageId}").and(accept(MediaType.APPLICATION_JSON)), velocityHandler::getLogBeanByManageId)
				 .andRoute(POST("/saveLogBean").and(accept(MediaType.APPLICATION_JSON)), velocityHandler::saveLogBean)
				 .andRoute(POST("/deleteLogBean").and(accept(MediaType.APPLICATION_JSON)), velocityHandler::deleteLogBean)
				 .andRoute(GET("/findBylogField/{logBeanId}").and(accept(MediaType.APPLICATION_JSON)), velocityHandler::getLogFieldByBeanId)
				 .andRoute(POST("/saveLogField").and(accept(MediaType.APPLICATION_JSON)), velocityHandler::saveLogField)
				 .andRoute(POST("/deleteLogField").and(accept(MediaType.APPLICATION_JSON)), velocityHandler::deleteLogField);
	 }
}
