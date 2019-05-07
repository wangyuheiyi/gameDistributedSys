package com.dc.testthymeleaf.entity;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 日志总体管理类（各种动态信息填写）mongoDB对应
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "logmanage")
public class LogManageMongoEntity {
	@Id
    private String id;
	
	/** 游戏服代码*/
	@Indexed(unique = true)
	private int gamecode;
	
	/** 游戏名称*/
	private String gamename;

	/** 日志服名称*/
	private String logservicename;
	
	/** 发送服务项目名称*/
	private String sendObjName;
	
	/** 发送服务中服务代码命名空间*/
	private String sendServicePackage;
	
	/** 发送服务中实体类代码命名空间*/
	private String sendBeanPackage;
	
	/** 日志消息流的通道名*/
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
