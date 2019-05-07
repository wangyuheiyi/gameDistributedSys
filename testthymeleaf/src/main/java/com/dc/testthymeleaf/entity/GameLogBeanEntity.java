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
 * 日志类实体管理
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {@Index(name="INDEX_ID",columnList = "id")},uniqueConstraints = {@UniqueConstraint(columnNames="beanName")})
@Entity(name = "t_gamelog_bean")
public class GameLogBeanEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20,unique = true,nullable = false,updatable = false)
	private long id;
	
	/** 外键关联*/
	@Column(length = 20,nullable = false,updatable = false)
	private long logManageId;
	
	/** 实体类名成*/
	@Column(length = 50,nullable = false)
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
