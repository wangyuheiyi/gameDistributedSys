package com.dc.testthymeleaf.entity;

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

/**
 * 日志总体管理类（各种动态信息填写）
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {@Index(name="INDEX_ID",columnList = "id")},uniqueConstraints = {@UniqueConstraint(columnNames="gamecode")})
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
	@Column(length = 100,nullable = false)
	private String gamename;

	/** 日志服名称*/
	@Column(length = 100,nullable = false)
	private String logservicename;
	
	/** 发送服务项目名称*/
	@Column(length = 20,nullable = false)
	private String sendObjName;
	
	/** 发送服务中服务代码命名空间*/
	@Column(length = 100,nullable = false)
	private String sendServicePackage;
	
	/** 发送服务中实体类代码命名空间*/
	@Column(length = 100,nullable = false)
	private String sendBeanPackage;
	
	/** 日志消息流的通道名*/
	@Column(length = 20,nullable = false)
	private String channelName;
	/** 执行的命令文件*/
	private String mvnCom;
	/////////////////发送服务信息///////////////////
	/** 代码版本*/
	private String codeVersion;
	/** 代码GroupId*/
	private String codeGroupId;
	/** 代码ArtifactId*/
	private String codeArtifactId;
	/** mavenId 要和配置文件中的一致*/
	private String mavenId;
	/** release版本上传路径*/
	private String releaseMavenPath;
	/** snapshot版本上传路径*/
	private String snapshotMavenPath;
	
	@Override
	public String toString(){
		return "id:["+id+"],gamecode:["+gamecode+"],gamename["+gamename+"],logservicename["+logservicename+"]";
	}
}
