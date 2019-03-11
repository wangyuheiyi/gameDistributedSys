package com.gamelogservice.manageserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import com.gamelogservice.manageserver.entity.GameLogManageEntity;
import com.gamelogservice.manageserver.service.ILogManageService;

/**
 * 页面处理的类
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/logManage")
public class GameLogManageController {
	/** 加载后台服务*/
	 @Autowired
     private ILogManageService logManageService;
	 
	 //插入方法无需返回值
	 @PostMapping(path = "",consumes = MediaType.APPLICATION_STREAM_JSON_VALUE)
	 public Mono<Void> addGameLogManage(@RequestBody GameLogManageEntity param){
		try {
			return logManageService.add(param).then();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	 }
}
