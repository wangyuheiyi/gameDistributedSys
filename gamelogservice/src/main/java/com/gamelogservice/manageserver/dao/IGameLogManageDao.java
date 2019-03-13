package com.gamelogservice.manageserver.dao;

import com.gamelogservice.dao.repository.IBaseJpaRepository;
import com.gamelogservice.manageserver.entity.GameLogManageEntity;
/**
 * dao层主要用于添加某种数据库查询的方式 JpaRepository(jpa的数据持久层)
 * @author Administrator
 *
 */
public interface IGameLogManageDao extends IBaseJpaRepository<GameLogManageEntity, Long>{

}
