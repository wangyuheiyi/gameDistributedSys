//主方法
var mainInfoApp=new Vue({
    el: '#mainInfo',
    data: {
    	isCollapsed: false,
    	logBeans:[],
    	reportUrl:'index.html'
    },
    computed: {
    	rotateIcon:function() {
            return [
                'menu-icon',
                this.isCollapsed ? 'rotate-icon' : ''
            ];
        },
    	menuitemClasses:function() {
            return [
                'menu-item',
                this.isCollapsed ? 'collapsed-menu' : ''
            ]
        }
    },
    methods: {
        collapsedSider:function() {
        	this.$refs.side1.toggleCollapse();
        }
    }
});


function addMenuList(menuInfo){
	console.log(menuInfo);
	if(menuInfo.isBaseBean!="1") mainInfoApp.logBeans.push(menuInfo);
}

function delMenuList(menuInfo){
	for(var i=0;i<mainInfoApp.logBeans.length;i++){
		if(mainInfoApp.logBeans[i].beanName==menuInfo.beanName)
			Vue.delete(mainInfoApp.logBeans,i);
	}
}