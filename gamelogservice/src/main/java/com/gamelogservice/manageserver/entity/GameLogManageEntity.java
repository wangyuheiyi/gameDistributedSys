package com.gamelogservice.manageserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {@Index(name="INDEX_UID",columnList = "id")})
@Entity(name = "t_gamelog_manage")
public class GameLogManageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 20,unique = true,nullable = false,updatable = false)
	private Long id;

	@Column(length = 300,nullable = false,updatable = false)
	private String logservicename;
}
