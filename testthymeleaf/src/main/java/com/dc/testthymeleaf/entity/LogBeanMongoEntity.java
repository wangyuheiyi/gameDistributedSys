package com.dc.testthymeleaf.entity;

import javax.persistence.Column;
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
@Document(collection = "logbean")
public class LogBeanMongoEntity {
	@Id
    private String id;
	
	/** 外键关联*/
	private String logManageId;
	
	/** 实体类名成*/
	@Indexed(unique = true)
	private String beanName;
	
	/** 实体类描述*/
	@Column(length = 100)
	private String beanDescribe;
	
	/** 基类名称*/
	@Column(length = 100)
	private String fatherBeanName;
	
	/** 是否是基类*/
	@Column(length = 11)
	private int isBaseBean;
	
	@Override
	public String toString(){
		return "id:["+id+"],beanName:["+beanName+"],beanDescribe["+beanDescribe+"],fatherBeanName["+fatherBeanName+"],isBaseBean["+isBaseBean+"]";
	}
}
