package com.dc.testthymeleaf.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResInfoBean {
	/** 请求状态*/
	private int status;
	/** 返回信息*/
	private String resStr;
	/** 返回数据*/
	private Object resDate;
}
