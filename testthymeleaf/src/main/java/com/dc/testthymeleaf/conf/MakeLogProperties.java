package com.dc.testthymeleaf.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "com.dc.logtool") 
@Component
public class MakeLogProperties {
	private String outPutPath;
	private String mainSrc;
	private String targetPath;
	public String getOutPutPath() {
		return outPutPath;
	}

	
	public void setOutPutPath(String outPutPath) {
		this.outPutPath = outPutPath;
	}


	public String getMainSrc() {
		return mainSrc;
	}


	public void setMainSrc(String mainSrc) {
		this.mainSrc = mainSrc;
	}


	public String getTargetPath() {
		return targetPath;
	}


	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	
}
