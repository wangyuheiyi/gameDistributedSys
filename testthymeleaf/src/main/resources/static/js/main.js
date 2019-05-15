var gameCode=6;
//主方法
new Vue({
    el: '#managerInfo',
    data: {
    	panelvalue:"",
    	name: "",
    	isCreat:false,
    	isCreatLog:false,
    	dataInfo:Object,
    	tmpBeanIndex:1,
    	logBeans:[],
    	tmpFieldIndex:1,
    	logFields:[]
    },
    mounted:function(){
    	var url = "/findByGameCodeMongo/"+gameCode;
    	_this= this;
    	axios.get(url).then(function(result) {
    		reqAfterInfo(_this,result);
    	});
    },
    methods: {
    	creatNew: function () {
    		this.isCreat=false;
        },
        creatLog: function (){
        	var url = "/creatLog/";
        	_this= this;
        	axios.post(url,_this.dataInfo).then(function(result) {
        		var res=result.data;
        		if(res.status == "0"){
        			_this.$Message.success(res.resStr);
        		}else{
        			_this.$Message.error(res.resStr);
        		}
        	});
        },
        saveInfo: function () {
        	var url = "/saveInfoMongo/";
        	_this= this;
        	axios.post(url,_this.dataInfo).then(function(result) {
        		reqAfterInfo(_this,result);
        	});
        },
        deleteLogManager: function(){
        	var url = "/deleteLogManager/";
        	_this= this;
        	axios.post(url,_this.dataInfo).then(function(result) {
        		reqAfterInfo(_this,result);
        	});
        },
        collapseChange: function(key){
        	//清空记录
        	this.logFields= [];
        	//根据名字获取id
        	var tmpBeanId=null;
        	for(var i=0;i<this.logBeans.length;i++){
    			if(this.logBeans[i].beanName==key[0]) tmpBeanId=this.logBeans[i].id;
    		}
        	if(tmpBeanId!=null) getLogFieldInfo(this,tmpBeanId);
        },
        addlogBean: function (){
        	this.logBeans.push({
        		id: null,
        		logManageId: this.dataInfo.id,
        		beanName: "newbean"+this.tmpBeanIndex,
        		beanDescribe: "",
        		fatherBeanName: "",
        		isBaseBean: '0'
            });
        	this.tmpBeanIndex++;
        },
        saveLogBean: function(logBean){
        	var url = "/saveLogBean/";
        	_this= this;
        	axios.post(url,logBean).then(function(result) {
        		var res=result.data;
        		if(res.status == "0"){
        			_this.$Message.success(res.resStr);
        			for(var i=0;i<_this.logBeans.length;i++){
            			if(_this.logBeans[i].beanName==res.resDate.beanName)
            				Vue.set(_this.logBeans,i,res.resDate);
            		}
        		}else{
        			_this.$Message.error(res.resStr);
        		}
        	});
        },
        deleteLogBean: function(logBean){
        	if(logBean.id==null){
        		for(var i=0;i<_this.logBeans.length;i++){
        			if(_this.logBeans[i].beanName==logBean.beanName)
        				Vue.delete(_this.logBeans,i);
        		}
        	}else{
        		var url = "/deleteLogBean/";
            	_this= this;
            	axios.post(url,logBean).then(function(result) {
            		var res=result.data;
            		if(res.status == "0"){
            			_this.$Message.success(res.resStr);
            			for(var i=0;i<_this.logBeans.length;i++){
                			if(_this.logBeans[i].beanName==logBean.beanName)
                				Vue.delete(_this.logBeans,i);
                		}
            		}else{
            			_this.$Message.error(res.resStr);
            		}
            	});
        	}
        	
        },
        addlogField: function(logBeanId){
        	this.logFields.push({
	        	id: null,
	        	logBeanId:logBeanId,
	        	fieldType:"",
	        	fieldName:"newfield"+ this.tmpFieldIndex,
	        	bigName:"Newfield"+ this.tmpFieldIndex,
	        	comment:""
        	});
        	this.tmpFieldIndex++;
        },
        saveLogField: function(logField){
        	var url = "/saveLogField/";
          	_this= this;
          	axios.post(url,logField).then(function(result) {
          		var res=result.data;
          		if(res.status == "0"){
          			_this.$Message.success(res.resStr);
          			for(var i=0;i<_this.logFields.length;i++){
              			if(_this.logFields[i].fieldName==res.resDate.fieldName)
              				Vue.set(_this.logFields,i,res.resDate);
              		}
          		}else{
          			_this.$Message.error(res.resStr);
          		}
          	});
        },
        deleteLogField: function(logField){
        	if(logField.id==null){
        		for(var i=0;i<_this.logFields.length;i++){
        			if(_this.logFields[i].fieldName==logField.fieldName)
        				Vue.delete(_this.logFields,i);
        		}
        	}else{
        		var url = "/deleteLogField/";
            	_this= this;
            	axios.post(url,logField).then(function(result) {
            		var res=result.data;
            		if(res.status == "0"){
            			_this.$Message.success(res.resStr);
            			for(var i=0;i<_this.logFields.length;i++){
                			if(_this.logFields[i].fieldName==logField.fieldName)
                				Vue.delete(_this.logFields,i);
                		}
            		}else{
            			_this.$Message.error(res.resStr);
            		}
            	});
        	}
        }
    }
});

//主信息请求后的操作
function reqAfterInfo(_this,result){
	 var res=result.data;
	 if(res.status == "0"){
		_this.$Message.success(res.resStr);
		_this.dataInfo = res.resDate;
		if(_this.dataInfo.id==null){
			_this.isCreat=true;
			_this.isCreatLog=false;
			_this.dataInfo.gamecode=gameCode;
    		_this.dataInfo.gamename='临时';
		}
		else{
			_this.isCreat=false;
			_this.isCreatLog=true;
			getLogBeanInfo(_this,_this.dataInfo.id);
		}
	 }else{
		 _this.$Message.error(res.resStr);
	 }
}

//查询实体类
function getLogBeanInfo(_this,logManageId){
	_this.logBeans= [];
	var url = "/findBylogBean/"+logManageId;
	axios.get(url).then(function(result) {
		for(var i=0;i<result.data.length;i++){
			if(result.data[i].id!=null) _this.logBeans.push(result.data[i]);
		}
	});
}

//查询实体类的字段
function getLogFieldInfo(_this,logBeanId){
	var url = "/findBylogField/"+logBeanId;
	axios.get(url).then(function(result) {
		for(var i=0;i<result.data.length;i++){
			if(result.data[i].id!=null) _this.logFields.push(result.data[i]);
		}
	});
}