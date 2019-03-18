package com.gamelogservice.manageserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {@Index(name="INDEX_ID",columnList = "id")},uniqueConstraints = {@UniqueConstraint(columnNames="logservicename")})
@Entity(name = "t_gamelog_manage")
public class GameLogManageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20,unique = true,nullable = false,updatable = false)
	private long id;
	
	/** 游戏服代码*/
	@Column(length = 11,nullable = false,updatable = false)
	private int gamecode;
	
	/** 游戏名称*/
	@Column(length = 100,nullable = false,updatable = false)
	private String gamename;

	/** 日志服名称*/
	@Column(length = 100,nullable = false)
	private String logservicename;
}
