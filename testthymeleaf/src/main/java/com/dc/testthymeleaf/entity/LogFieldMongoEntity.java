package com.dc.testthymeleaf.entity;

import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
@Document(collection = "logfield")
public class LogFieldMongoEntity {
	@Id
    private String id;
	
	/** 外键关联对应实体类id*/
	private String logBeanId;
	
	/** 字段类型*/
	private String fieldType;
	
	/** 字段名称*/
	@Indexed(unique = true)
	private String fieldName;
	
	/** 字段名字大写字母*/
	private String bigName;
	
	/** 字段注释*/
	private String comment;
	
	@Override
	public String toString(){
		return "id:["+id+"],logBeanId:["+logBeanId+"],fieldType["+fieldType+"],fieldName["+fieldName+"],bigName["+bigName+"]";
	}
}
