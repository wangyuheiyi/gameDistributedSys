package com.dc.testthymeleaf.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogManagerBean {
	/** id*/
	private String id;
	/** 项目名称*/
	private String objName;
	/** 项目路径*/
	private String objPath;
	/** 服务命名空间*/
	private String servicePackage;
	/** 对象命名空间*/
	private String beanPackage;
	/** 服务代码路径*/
	private String servicePath;
	/** 对象代码路径*/
	private String beanPath;
	/** 生成路径路径*/
	private String targetPath;
	/** 判断路径是否都创建成功*/
	private boolean isCreatSucc;
	/** 默认基类的名称*/
	private String baseLogClassName;
	/** 消息同步渠道名*/
	private String channelName;
	/** 执行的命令地址*/
	private String mvnCom;
	/** 代码版本*/
	private String codeVersion;
	/** 代码版本*/
	private String codeGroupId;
	/** 代码版本*/
	private String codeArtifactId;
	/** mavenId 要和配置文件中的一致*/
	private String mavenId;
	/** release版本上传路径*/
	private String releaseMavenPath;
	/** snapshot版本上传路径*/
	private String snapshotMavenPath;
	
	

	@Override
	public String toString(){
		return "objName:["+objName+"],servicePath:["+servicePath+"],beanPath:["+beanPath+"],servicePackage:["+servicePackage+"],targetPath:["+targetPath+"],isCreatSucc:["+isCreatSucc+"]";
	}
	
}
