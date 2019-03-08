package com.gamelogservice.manageserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamelogservice.manageserver.entity.GameLogManageEntity;

public interface IGameLogManageDao extends JpaRepository<GameLogManageEntity, Long>{

}
