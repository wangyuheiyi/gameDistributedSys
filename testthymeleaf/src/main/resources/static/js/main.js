var gameCode=6;
//主方法
new Vue({
    el: '#mainInfo',
    data: {
    	isCollapsed: false,
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
