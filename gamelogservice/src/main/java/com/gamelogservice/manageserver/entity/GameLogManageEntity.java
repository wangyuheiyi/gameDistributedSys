package com.gamelogservice.manageserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {@Index(name="INDEX_UID",columnList = "id")})
@Entity(name = "t_gamelog_manage")
public class GameLogManageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20,unique = true,nullable = false,updatable = false)
	private long id;
	
	/** 游戏服代码*/
	@Column(length = 11,nullable = false,updatable = false)
	private int gameCode;

	/** 日志服名称*/
	@Column(length = 300,nullable = false)
	private String logservicename;
}
