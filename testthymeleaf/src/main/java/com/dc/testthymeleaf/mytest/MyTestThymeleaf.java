package com.dc.testthymeleaf.mytest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.dc.testthymeleaf.TestthymeleafApplication;
@Component
public class MyTestThymeleaf {
	private static Logger logger=LoggerFactory.getLogger(TestthymeleafApplication.class);
	@Autowired
	private ResourceLoader resourceLoader;
//	@Bean
	public void makeFile(){
		logger.info("-=========================- test");
		SpringTemplateEngine engine = new SpringTemplateEngine();
		//创建字符输出流并且自定义输出文件的位置和文件名
		FileWriter writer=null;
        try {
//        	File sourceFile = ResourceUtils.getFile("classpath:templates/output/"); 在liunx下无效
//        	Resource resource = resourceLoader.getResource("classpath:");
//        	logger.info("-=========================- test1"+resource.getURI());
//        	logger.info("-=========================- test1"+resource.getURL());
        	
        	ClassPathResource resource = new ClassPathResource("templates/mvncom.bat");
    		try {
    			logger.info("-=========================- test1"+resource.getFile().getPath());
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
//        	if(!upload.exists()) upload.mkdirs();
        	writer = new FileWriter("E:\\wangyu\\myworkspace\\resources\\test.java");
			Context context = new Context();
			//放入数据
	        context.setVariable("package","mytest");
	        engine.process("test",context,writer);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
