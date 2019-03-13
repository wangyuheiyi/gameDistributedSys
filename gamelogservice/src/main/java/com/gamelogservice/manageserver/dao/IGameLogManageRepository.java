package com.gamelogservice.manageserver.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gamelogservice.manageserver.entity.GameLogManageEntity;
/**
 * dao层主要用于添加某种数据库查询的方式 JpaRepository(jpa的数据持久层)
 * @author Administrator
 *
 */
public interface IGameLogManageRepository extends JpaRepository<GameLogManageEntity, Long>{
	/**
	 * nativeQuery = true，表示用原生的sql语句查询
	 * ?1、?2表示第一个参数和第二个参数
	 * 过于复杂的查询才会使用原生sql查询 引文这种需要手动书写sql
	 * @param Id
	 * @param logservicename
	 * @return
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM t_gamelog_manage WHERE gamecode = ?1 AND logservicename = ?2")
	List<GameLogManageEntity> findByGameCodeAndLogservicename(int gameCode, String logservicename);
}
