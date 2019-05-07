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
 * 日志类字段管理
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {@Index(name="INDEX_ID",columnList = "id")},uniqueConstraints = {@UniqueConstraint(columnNames="fieldName")})
@Entity(name = "t_gamelog_field")
public class GameLogFieldEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20,unique = true,nullable = false,updatable = false)
	private long id;
	
	/** 外键关联对应实体类id*/
	@Column(length = 20,nullable = false,updatable = false)
	private long logBeanId;
	
	/** 字段类型*/
	@Column(length = 50,nullable = false)
	private String fieldType;
	
	/** 字段名称*/
	@Column(length = 50,nullable = false)
	private String fieldName;
	
	/** 字段名字大写字母*/
	@Column(length = 50,nullable = false)
	private String bigName;
	
	/** 字段注释*/
	@Column(length = 150)
	private String comment;
	
	@Override
	public String toString(){
		return "id:["+id+"],logBeanId:["+logBeanId+"],fieldType["+fieldType+"],fieldName["+fieldName+"],bigName["+bigName+"]";
	}
}
