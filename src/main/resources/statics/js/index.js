//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<li>',
        '	<a v-if="item.type === 0" href="javascript:;">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '		<i class="fa fa-angle-left pull-right"></i>',
        '	</a>',
        '	<ul v-if="item.type === 0" class="treeview-menu">',
        '		<menu-item :item="item" v-for="item in item.list"></menu-item>',
        '	</ul>',

        '	<a v-if="item.type === 1 && item.parentId === 0" :href="\'#\'+item.url">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '	</a>',

        '	<a v-if="item.type === 1 && item.parentId != 0" :href="\'#\'+item.url"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
        '</li>'
    ].join('')
});

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 120);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
	el:'#rrapp',
	data:{
		user:{},
		menuList:{},
		main:"main.html",
		password:'',
		newPassword:'',
        navTitle:null
	},
	methods: {
		getMenuList: function (event) {
			$.getJSON("sys/menu/nav?_"+$.now(), function(r){
				vm.menuList = r.menuList;
			});
		},
		getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.user = r.user;
				vm.connectSocket(r.user.userName, r.host, r.port);
			});
		},
		connectSocket: function(userName, host, port) {
            var socket = io.connect('http://' + host + ':' + port + '?userName=' + userName, { transports: [ 'websocket' ] });

            socket.on('connect', function () {
                console.log('连接');
            });

            socket.on('runningTask', function (data) {
                console.log("收到全服数据");
                console.log(data);
            });

            socket.on('taskResult', function (data) {
                console.log("收到个人数据");
                console.log(data);
            });

            socket.emit('messageevent', "发送信息");

            socket.on('disconnect', function () {
                console.log("断开");
            });
		},
        verification : function(){
            if(vm.password == null || vm.password == ""){
                layer.alert("原密码不能为空");
                return true;
            }
            if(vm.newPassword == null || vm.newPassword == ""){
                layer.alert("新密码不能为空");
                return true;
            }
            if(vm.newPassword.length < 6 || vm.newPassword.length > 18){
                layer.alert("新密码长度限制为6-18位");
                return true;
            }
            var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
            if(reg.test(vm.newPassword)){
                layer.alert("对不起，您输入的新密码格式不正确!(不能包含中文)");
                return true;
            }
            return false;
        },
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['730px', '230px'],
				shadeClose: false,
                scrollbar: false,
				resize: false,
                fixed: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
                iframe: {
                    scrolling:'no'
                },
				btn1: function (index) {
                    //验证非空等
                    if (vm.verification()) {
                        return;
                    }
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
					    url: "sys/user/password",
					    data: data,
					    dataType: "json",
					    success: function(result){
					    console.log(result)
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
	            }
			});
		},
        donate: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['806px', '467px'],
                closeBtn: 1,
                shadeClose: false,
                content: ['http://www.scxxwb.com/donate.jpg', 'no']
            });
        }
	},
	created: function(){
		this.getMenuList();
		this.getUser();
	},
	updated: function(){
		//路由
		var router = new Router();
		routerList(router, vm.menuList);
		router.start();
	}
});


var preUrl = null;
function routerList(router, menuList){
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
			routerList(router, menu.list);
		}else if(menu.type == 1){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;

				//替换iframe的url
			    vm.main = url.replace('#', '');

			    //导航菜单展开
			    $(".treeview-menu li").removeClass("active");
			    $("a[href='"+url+"']").parents("li").addClass("active");
			    $("a[href='"+url+"']").unbind("click").click(function() {
			        if(url == preUrl) {
			            window.location.reload();
			        }
			    });
                preUrl = url;

			    vm.navTitle = $("a[href='"+url+"']").text();
			});
		}
	}
}
