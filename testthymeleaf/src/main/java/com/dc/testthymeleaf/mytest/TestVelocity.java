package com.dc.testthymeleaf.mytest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import com.dc.testthymeleaf.TestthymeleafApplication;
import com.dc.testthymeleaf.bean.LogBeanField;
import com.dc.testthymeleaf.bean.LogManagerBean;
import com.dc.testthymeleaf.conf.MakeLogProperties;

@Component
public class TestVelocity {
	private static Logger logger=LoggerFactory.getLogger(TestthymeleafApplication.class);
	
	@Autowired
	MakeLogProperties makeLogProperties;
	
	
	public Mono<String> makeFile(){
		LogManagerBean logFileBean=new LogManagerBean();
		logFileBean.setObjName("mynewtest");
		logFileBean.setServicePackage("com.dc.mynewtest.service");
		logFileBean.setBeanPackage("com.dc.mynewtest.bean");
		logFileBean.setObjPath(makeLogProperties.getOutPutPath()+"\\"+logFileBean.getObjName());
		logFileBean.setServicePath(makeLogProperties.getOutPutPath()+"\\"+logFileBean.getObjName()
				+makeLogProperties.getMainSrc()+"\\"+logFileBean.getServicePackage().replaceAll("\\.","/"));
		logFileBean.setBeanPath(makeLogProperties.getOutPutPath()+"\\"+logFileBean.getObjName()
				+makeLogProperties.getMainSrc()+"\\"+logFileBean.getBeanPackage().replaceAll("\\.","/"));
		logFileBean.setTargetPath(makeLogProperties.getOutPutPath()+"\\"+logFileBean.getObjName()+makeLogProperties.getTargetPath());
		logFileBean.setCreatSucc(true);
		logFileBean.setChannelName("mytestsend");
		logFileBean.setMvnCom("mvncom.bat");
		//maven数据
		logFileBean.setCodeVersion("0.0.1-RELEASE");
		logFileBean.setCodeGroupId("com.dc");
		logFileBean.setCodeArtifactId("autosendrmq");
		logFileBean.setMavenId("mymaven");
		logFileBean.setReleaseMavenPath("http://111.230.26.190:8081/repository/maven-releases/");
		logFileBean.setSnapshotMavenPath("http://111.230.26.190:8081/repository/maven-snapshots/");
		
		//异步处理文件生成
		TestVelocity tv=new TestVelocity();
		Mono.just(logFileBean).publishOn(Schedulers.elastic()).doOnNext(tv::creatFilePath)
		.doOnNext(i->{
			try {
			tv.generateTemplate(i);
		} catch (Exception e) {
			Mono.error(e);
		}}).doOnNext(tv::runMvncom).subscribe(tv::test,error -> System.err.println("Error: " + error));
		logger.info("creat file is over");
		return Mono.just("creat file");
	}
	
	private LogManagerBean creatFilePath(LogManagerBean logFileBean){
		if(!creatFilePathByStr(logFileBean.getServicePath())) throw new RuntimeException("Can't create dir");
		if(!creatFilePathByStr(logFileBean.getBeanPath())) throw new RuntimeException("Can't create dir");
		if(!creatFilePathByStr(logFileBean.getTargetPath())) throw new RuntimeException("Can't create dir");
		return logFileBean;
	}
	
	/**
	 * 穿件文件路径
	 * @param path
	 * @return
	 */
	private boolean creatFilePathByStr(String path){
		File _srcDist=new File(path);
		if(!_srcDist.exists())
			if(!_srcDist.mkdirs())	return false;
		return true;
	}
	
	public void test(LogManagerBean logFileBean){
		logger.info("creat file is over=========="+logFileBean.toString());
	}
	
	/**
	 * 生成文件
	 * @param logFileBean
	 */
	private LogManagerBean generateTemplate(LogManagerBean logFileBean) throws IOException{
		VelocityContext _context=new VelocityContext();
		_context.put("packageName",logFileBean.getServicePackage());
		_context.put("channelName",logFileBean.getChannelName());
		File targetFile=new File(logFileBean.getServicePath(),"SendSource.java");
		creatFile(_context,"/templates/SendSourceClass.vm",targetFile);
		VelocityContext _sinkSendcontext=new VelocityContext();
		_sinkSendcontext.put("packageName",logFileBean.getServicePackage());
		_sinkSendcontext.put("packageBeanName",logFileBean.getBeanPackage());
		_sinkSendcontext.put("channelName",logFileBean.getChannelName());
		_sinkSendcontext.put("father","BaseLogMessage");
		_sinkSendcontext.put("fatherName","BaseLogMessage".toLowerCase());
		File sinkSendFile=new File(logFileBean.getServicePath(),"SinkSend.java");
		creatFile(_sinkSendcontext,"/templates/SinkSendClass.vm",sinkSendFile);
		
		VelocityContext _baseLogContext=new VelocityContext();
		_baseLogContext.put("packageBeanName",logFileBean.getBeanPackage());
		_baseLogContext.put("className","BaseLogMessage");
		List<LogBeanField> logBeanFieldList=new ArrayList<LogBeanField>();
		LogBeanField logBeanField=new LogBeanField();
		logBeanField.setFieldType("long");
		logBeanField.setFieldName("userId");
		logBeanField.setBigName("UserId");
		logBeanField.setComment("玩家id");
		logBeanFieldList.add(logBeanField);
		LogBeanField logBeanField1=new LogBeanField();
		logBeanField1.setFieldType("String");
		logBeanField1.setFieldName("name");
		logBeanField1.setBigName("Name");
		logBeanField1.setComment("玩家姓名");
		logBeanFieldList.add(logBeanField1);
		LogBeanField logBeanField2=new LogBeanField();
		logBeanField2.setFieldType("int");
		logBeanField2.setFieldName("level");
		logBeanField2.setBigName("Level");
		logBeanField2.setComment("玩家等级");
		logBeanFieldList.add(logBeanField2);
		LogBeanField logBeanField3=new LogBeanField();
		logBeanField3.setFieldType("int");
		logBeanField3.setFieldName("vip");
		logBeanField3.setBigName("Vip");
		logBeanField3.setComment("玩家vip等级");
		logBeanFieldList.add(logBeanField3);
		File targetBaseLogFile=new File(logFileBean.getBeanPath(),"BaseLogMessage.java");
		_baseLogContext.put("fields",logBeanFieldList);
		creatFile(_baseLogContext,"/templates/SendBeanClass.vm",targetBaseLogFile);
		
		VelocityContext _userLoginLogContext=new VelocityContext();
		_userLoginLogContext.put("packageBeanName",logFileBean.getBeanPackage());
		_userLoginLogContext.put("className","UserLoginLogMessage");
		_userLoginLogContext.put("father","BaseLogMessage");
		List<LogBeanField> userLoginBeanFieldList=new ArrayList<LogBeanField>();
		LogBeanField userLoginBeanField=new LogBeanField();
		userLoginBeanField.setFieldType("long");
		userLoginBeanField.setFieldName("lastLoginTime");
		userLoginBeanField.setBigName("LastLoginTime");
		userLoginBeanField.setComment("玩家最后登陆时间");
		userLoginBeanFieldList.add(userLoginBeanField);
		LogBeanField userLoginBeanField1=new LogBeanField();
		userLoginBeanField1.setFieldType("int");
		userLoginBeanField1.setFieldName("loginDay");
		userLoginBeanField1.setBigName("LoginDay");
		userLoginBeanField1.setComment("玩家登录天数");
		userLoginBeanFieldList.add(userLoginBeanField1);
		LogBeanField userLoginBeanField2=new LogBeanField();
		userLoginBeanField2.setFieldType("long");
		userLoginBeanField2.setFieldName("creatTime");
		userLoginBeanField2.setBigName("CreatTime");
		userLoginBeanField2.setComment("玩家注册时间");
		userLoginBeanFieldList.add(userLoginBeanField2);
		File targetUserLoginLogFile=new File(logFileBean.getBeanPath(),"UserLoginLogMessage.java");
		_userLoginLogContext.put("fields",userLoginBeanFieldList);
		creatFile(_userLoginLogContext,"/templates/SendBeanClass.vm",targetUserLoginLogFile);
		
		//生成pom文件
		VelocityContext _pomContext=new VelocityContext();
		_pomContext.put("codeGroupId", logFileBean.getCodeGroupId());
		_pomContext.put("codeArtifactId", logFileBean.getCodeArtifactId());
		_pomContext.put("codeVersion", logFileBean.getCodeVersion());
		_pomContext.put("objName", logFileBean.getObjName());
		_pomContext.put("mavenId", logFileBean.getMavenId());
		_pomContext.put("releaseMavenPath", logFileBean.getReleaseMavenPath());
		_pomContext.put("snapshotMavenPath", logFileBean.getSnapshotMavenPath());
		File pomFile=new File(logFileBean.getObjPath(),"pom.xml");
		creatFile(_pomContext,"/templates/pom.vm",pomFile);
		
		//生成命令执行文件
		File mvnComFile=new File(logFileBean.getObjPath(),logFileBean.getMvnCom());
		VelocityContext _mvnComContext=new VelocityContext();
//		_mvnComContext.put("objPath", logFileBean.getObjPath());
		creatFile(_mvnComContext,"/templates/mvncmd.vm",mvnComFile);
		return logFileBean;
	}
	
	/**
	 * 穿件文件
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
	 * 
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
           
            System.out.println(result);  
            }   
        catch (Exception e) {  
            e.printStackTrace();
            throw new RuntimeException("mvnCom error"+e.getMessage());
        }finally{
        	try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }  
		return logFileBean;
	}
}
