var gameCode=6;
//主方法
new Vue({
    el: '#managerInfo',
    data: {
    	panelvalue:"",
    	name: "",
    	isCreat:false,
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
        saveInfo: function () {
        	var url = "/saveInfoMongo/";
        	_this= this;
        	axios.post(url,_this.dataInfo).then(function(result) {
        		reqAfterInfo(_this,result);
        	});
        },
        collapseChange: function(key){
        	console.log(key[0]);
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
        saveLogBean: function(logbean){
        	var url = "/saveLogBean/";
        	_this= this;
        	axios.post(url,logbean).then(function(result) {
        		var res=result.data;
        		if(res.status == "0"){
        			_this.$Message.success(res.resStr);
        		}else{
        			_this.$Message.error(res.resStr);
        		}
        		for(var i=0;i<_this.logBeans.length;i++){
        			if(_this.logBeans[i].beanName==res.resDate.beanName)
        				Vue.set(_this.logBeans,i,res.resDate);
        		}
        	});
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
			_this.dataInfo.gamecode=gameCode;
    		_this.dataInfo.gamename='临时';
		}
		else{
			_this.isCreat=false;
			getLogBeanInfo(_this,_this.dataInfo.id);
		}
	 }else{
		 _this.$Message.error(res.resStr);
	 }
}

//查询方法
function getLogBeanInfo(_this,logManageId){
	var url = "/findBylogBean/"+logManageId;
	axios.get(url).then(function(result) {
		for(var i=0;i<result.data.length;i++){
			if(result.data[i].id!=null) _this.logBeans.push(result.data[i]);
		}
	});
}