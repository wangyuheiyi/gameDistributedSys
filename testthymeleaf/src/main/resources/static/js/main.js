var gameCode=6;
//主方法
new Vue({
    el: '#managerInfo',
    data: {
    	name: "",
    	isCreat:false,
    	dataInfo:Object,
    	tmpinfoIndex:1,
    	logBeans:[]
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
        addlogBean: function (){
        	this.logBeans.push({
        		id: null,
        		logManageId: this.dataInfo.id,
        		beanName: "newbean"+this.tmpinfoIndex,
        		beanDescribe: "",
        		fatherBeanName: "",
        		isBaseBean: true
            });
        	this.tmpinfoIndex++;
        },
        saveLogBean: function(logbean){
        	console.log(logbean.isBaseBean);
        	var url = "/saveLogBean/";
        	_this= this;
        	axios.post(url,logbean).then(function(result) {
        		console.log(result.data);
        	});
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
		console.log(result.data.length);
		console.log(result.data[0]);
		for(var tmpLogBean in result.data){
			console.log(tmpLogBean.id);
		}
		//_this.logBeans.push(result.data);
		
		
		//if(result.data.id!=null)
		//	_this.logBeans.push(result.data);
	});
}