package com.gamelogservice.manageserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamelogservice.manageserver.entity.GameLogManageEntity;
/**
 * dao层主要用于添加某种数据库查询的方式 JpaRepository(jpa的数据持久层)
 * @author Administrator
 *
 */
public interface IGameLogManageDao extends JpaRepository<GameLogManageEntity, Long>{

}
