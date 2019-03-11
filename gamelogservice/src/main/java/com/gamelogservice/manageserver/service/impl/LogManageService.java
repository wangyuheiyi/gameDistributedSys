package com.gamelogservice.manageserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Mono;

import com.gamelogservice.manageserver.dao.IGameLogManageDao;
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
	IGameLogManageDao iGameLogManageDao;

	/**
	 * 页面订阅 当存储完成产生了保存后的对象 订阅者获取这个对象
	 */
	@Override
	public Mono<GameLogManageEntity> add(GameLogManageEntity param)
			throws Exception {
		return Mono.<GameLogManageEntity>create(sink->{
			sink.success(iGameLogManageDao.save(param));
		});
	}
}
