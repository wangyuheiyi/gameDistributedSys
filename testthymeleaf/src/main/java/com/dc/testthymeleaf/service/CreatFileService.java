package com.dc.testthymeleaf.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import com.dc.testthymeleaf.TestthymeleafApplication;
import com.dc.testthymeleaf.bean.LogFieldBean;
import com.dc.testthymeleaf.bean.LogFileBean;
import com.dc.testthymeleaf.bean.LogManagerBean;
import com.dc.testthymeleaf.bean.ResInfoBean;
import com.dc.testthymeleaf.conf.MakeLogProperties;
import com.dc.testthymeleaf.entity.LogBeanMongoEntity;
import com.dc.testthymeleaf.entity.LogFieldMongoEntity;
import com.dc.testthymeleaf.entity.LogManageMongoEntity;
@Service
public class CreatFileService {
	private static Logger logger=LoggerFactory.getLogger(TestthymeleafApplication.class);
	
	@Autowired
	MakeLogProperties makeLogProperties;
	
	@Autowired
	LogManagerService logManagerService;
	
	public Mono<ResInfoBean> creatSendObjFile(LogManageMongoEntity logManageEntity){
		return creatLogFileBean(logManageEntity)
		.publishOn(Schedulers.elastic()) //切换到io线程操作
		.doOnNext(info->creatFilePath(info)) //创建文件夹
		.doOnNext(info->generateLogManageByTempl(info)) //生成发送服务器的操作类
		.doOnNext(info->creatLogBean(info)) //穿件类实体
		.flatMap(info-> Mono.just(new ResInfoBean(0,"creat file is ok",info)))
		.onErrorResume(e-> Mono.just(new ResInfoBean(1,"creat file is error ! :["+e.getMessage()+"]",new LogManageMongoEntity())));
	}
	
	/**
	 * 数据创建文件路径
	 * @param logFileBean
	 * @return
	 */
	private void creatFilePath(LogManagerBean logFileBean){
		if(!creatFilePathByStr(logFileBean.getServicePath())) throw new RuntimeException("Can't create dir");
		if(!creatFilePathByStr(logFileBean.getBeanPath())) throw new RuntimeException("Can't create dir");
		if(!creatFilePathByStr(logFileBean.getTargetPath())) throw new RuntimeException("Can't create dir");
	}
	
	/**
	 * 创建文件路径
	 * @param path
	 * @return
	 */
	private boolean creatFilePathByStr(String path){
		File _srcDist=new File(path);
		if(!_srcDist.exists())
			if(!_srcDist.mkdirs())	return false;
		return true;
	}
	
	/**
	 * 数据前期初始化准备的流
	 * @param logManageEntity
	 * @return
	 */
	private Mono<LogManagerBean> creatLogFileBean(LogManageMongoEntity logManageEntity){
		LogManagerBean logFileBean=new LogManagerBean();
		logFileBean.setId(logManageEntity.getId());
		logFileBean.setObjName(logManageEntity.getSendObjName());
		logFileBean.setServicePackage(logManageEntity.getSendServicePackage());
		logFileBean.setBeanPackage(logManageEntity.getSendBeanPackage());
		logFileBean.setObjPath(makeLogProperties.getOutPutPath()+"\\"+logFileBean.getObjName());
		logFileBean.setServicePath(makeLogProperties.getOutPutPath()+"\\"+logFileBean.getObjName()
				+makeLogProperties.getMainSrc()+"\\"+logFileBean.getServicePackage().replaceAll("\\.","/"));
		logFileBean.setBeanPath(makeLogProperties.getOutPutPath()+"\\"+logFileBean.getObjName()
				+makeLogProperties.getMainSrc()+"\\"+logFileBean.getBeanPackage().replaceAll("\\.","/"));
		logFileBean.setTargetPath(makeLogProperties.getOutPutPath()+"\\"+logFileBean.getObjName()+makeLogProperties.getTargetPath());
		logFileBean.setCreatSucc(true);
		logFileBean.setChannelName(logManageEntity.getChannelName());
		logFileBean.setMvnCom(logManageEntity.getMvnCom());
		//maven数据
		logFileBean.setCodeVersion(logManageEntity.getCodeVersion());
		logFileBean.setCodeGroupId(logManageEntity.getCodeGroupId());
		logFileBean.setCodeArtifactId(logManageEntity.getCodeArtifactId());
		logFileBean.setMavenId(logManageEntity.getMavenId());
		logFileBean.setReleaseMavenPath(logManageEntity.getReleaseMavenPath());
		logFileBean.setSnapshotMavenPath(logManageEntity.getSnapshotMavenPath());
		return Mono.just(logFileBean);
	} 
	
	/**
	 * 根据模板生成发送服务文件
	 * @param logFileBean
	 */
	private void generateLogManageByTempl(LogManagerBean logFileBean){
		VelocityContext _context=new VelocityContext();
		_context.put("packageName",logFileBean.getServicePackage());
		_context.put("channelName",logFileBean.getChannelName());
		File targetFile=new File(logFileBean.getServicePath(),"SendSource.java");
		VelocityContext _sinkSendcontext=new VelocityContext();
		_sinkSendcontext.put("packageName",logFileBean.getServicePackage());
		_sinkSendcontext.put("packageBeanName",logFileBean.getBeanPackage());
		_sinkSendcontext.put("channelName",logFileBean.getChannelName());
		_sinkSendcontext.put("father","BaseLogMessage");
		_sinkSendcontext.put("fatherName","BaseLogMessage".toLowerCase());
		File sinkSendFile=new File(logFileBean.getServicePath(),"SinkSend.java");
		try {
			creatFile(_context,"/templates/SendSourceClass.vm",targetFile);
			creatFile(_sinkSendcontext,"/templates/SinkSendClass.vm",sinkSendFile);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private void creatLogBean(LogManagerBean logFileBean){
		Flux<LogFileBean> fLogFileBean= logManagerService.findByLogManageId(logFileBean.getId())
		.flatMap(info->{
			return logManagerService.findFieldsByLogBeanId(info.getId())
			.flatMap(logField->creatLogBeanField(logField))
			.collectList().flatMap(logFields-> creatLogFileBean(info,logFields));
		});
		fLogFileBean.doOnNext(loginfo->generateLogFile(logFileBean.getBeanPackage(),logFileBean.getBeanPath(),loginfo));
	}
	
	/**
	 * 创建实体类的对象
	 * @param logBeanMongoEntity
	 * @param logFields
	 * @return
	 */
	private Mono<LogFileBean> creatLogFileBean(LogBeanMongoEntity logBeanMongoEntity,List<LogFieldBean> logFields){
		LogFileBean logFileBean=new LogFileBean();
		logFileBean.setBeanName(logBeanMongoEntity.getBeanName());
		logFileBean.setBeanDescribe(logBeanMongoEntity.getBeanDescribe());
		logFileBean.setFatherBeanName(logBeanMongoEntity.getFatherBeanName());
		if(logBeanMongoEntity.getIsBaseBean()!=null&&"1".equals(logBeanMongoEntity.getIsBaseBean()))
		{
			logFileBean.setBaseBean(true);
		}else{
			logFileBean.setBaseBean(false);
		}
		return Mono.just(logFileBean);
	}
	
	/**
	 * 创建字段信息
	 * @param logField
	 * @return
	 */
	private Mono<LogFieldBean> creatLogBeanField(LogFieldMongoEntity logField){
		LogFieldBean logBeanField=new LogFieldBean();
		logBeanField.setFieldType(logField.getFieldType());
		logBeanField.setFieldName(logField.getFieldName());
		logBeanField.setBigName(logField.getBigName());
		logBeanField.setComment(logField.getComment());
		return Mono.just(logBeanField);
	}
	
	/**
	 * 生成日志实体类
	 * @param beanPackage
	 * @param beanPath
	 * @param logFileBean
	 */
	public void generateLogFile(String beanPackage,String beanPath,LogFileBean logFileBean){
		VelocityContext logContext=new VelocityContext();
		logContext.put("packageBeanName",beanPackage);
		logContext.put("className",logFileBean.getBeanName());
		logContext.put("beanDescribe",logFileBean.getBeanDescribe());
		if(logFileBean.isBaseBean())
			logContext.put("father",logFileBean.getFatherBeanName());
		
		File targetLogFile=new File(beanPath,logFileBean.getBeanName()+".java");
		logContext.put("fields",logFileBean.getLogFieldList());
		try {
			creatFile(logContext,"/templates/SendBeanClass.vm",targetLogFile);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 创建文件
	 * @param _context
	 * @param templPath
	 * @param targetFile
	 * @throws IOException 
	 */
	private void creatFile(VelocityContext _context,String templPath,File targetFile) throws IOException{
		StringWriter _readWriter=new StringWriter();
		Velocity.setProperty("resource.loader", "class");
		Velocity.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.mergeTemplate(templPath,"UTF-8",_context,_readWriter);
		Writer _fileWriter=null;
		try{
			_fileWriter = new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8");
			_fileWriter.write(_readWriter.toString());
		}catch(IOException e){
			throw new IOException(e.getMessage());
		}finally{
			if(_fileWriter!=null) _fileWriter.close();
		}
	}
	
	/**
	 * 执行命令语句
	 * @param logFileBean
	 * @return
	 */
	private LogManagerBean runMvncom(LogManagerBean logFileBean){
		 logger.info("start mvncommond");
		if(logFileBean.getMvnCom()==null||logFileBean.getMvnCom().equals("")){
			throw new RuntimeException("commond file is null");
		}
		Process ps=null;
		BufferedReader br =null;
		try { 
			//去项目的指定目录执行命令
			File dir = new File(logFileBean.getObjPath());
            ps = Runtime.getRuntime().exec(logFileBean.getObjPath()+"\\"+logFileBean.getMvnCom(),null,dir);  
//            ps.waitFor();
            br = new BufferedReader(new InputStreamReader(ps.getInputStream()));  
            StringBuffer sb = new StringBuffer();  
            String line;  
            while ((line = br.readLine()) != null) {  
                sb.append(line).append("\n");  
            }  
            String result = sb.toString();  
            logger.debug("mvncmd=========["+result+"]");
            }   
        catch (Exception e) {  
        	logger.error("runMvncom error :"+e.getMessage());
            throw new RuntimeException("mvnCom error"+e.getMessage());
        }finally{
        	try {
				br.close();
			} catch (IOException e) {
				logger.error("runMvncom error :"+e.getMessage());
			}
        }  
		return logFileBean;
	}
}
