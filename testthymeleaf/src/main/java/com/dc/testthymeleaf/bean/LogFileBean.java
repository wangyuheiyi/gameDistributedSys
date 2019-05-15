package com.dc.testthymeleaf.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogFileBean {
	/** 实体类名成*/
	private String beanName;
	/** 实体类描述*/
	private String beanDescribe;
	/** 基类名称*/
	private String fatherBeanName;
	/** 是否是基类*/
	private boolean isBaseBean;
	/** 相关字段数据*/
	private List<LogFieldBean> logFieldList=new ArrayList<LogFieldBean>();
}
