package com.gamelogservice.manageserver.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gamelogservice.dao.impl.BaseJpaDaoImpl;
import com.gamelogservice.manageserver.entity.GameLogManageEntity;

@Repository
public class GameLogManageDao extends BaseJpaDaoImpl<GameLogManageEntity, Long>{
	@Autowired
	public GameLogManageDao(Class<GameLogManageEntity> domainClass,
			EntityManager em) {
		super(domainClass, em);
	}
}
