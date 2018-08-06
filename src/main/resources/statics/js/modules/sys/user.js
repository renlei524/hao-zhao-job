$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true, hidden:true},
			{ label: '账号', name: 'userName', width: 75 },
            { label: '员工姓名', name: 'realName', width: 75 },
            { label: '所属公司', name: 'deptName', sortable: false, width: 75 },
			{ label: '邮箱', name: 'email', width: 90 },
			{ label: '手机号', name: 'mobile', width: 100 },
			{ label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 85,formatter:function(value,options,row){
                    return new Date(value).Format('yyyy-MM-dd HH:mm:ss');}}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 50,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var  pwd = null;
var  bool =true;

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            userName: null,
            deptName:null,
            mobile:null
        },
        showList: true,
        title:null,
        roleList:{},
        user:{
            status:1,
            deptId:null,
            deptName:null,
            roleIdList:[]
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.roleList = {};
            vm.user = {deptName:null, deptId:null, status:1, roleIdList:[]};
            $("#userName").removeAttr("readonly");
            //获取角色信息
            this.getRoleList();
            pwd = null;
            vm.getDept();
            window.onload = function (){
              document.body.onkeydown=function(event){
                if(event.keyCode==13){
                    event.keyCod=0; return false;
                }
              }
            }
        },
        getDept: function(){
            //加载公司树
            $.get(baseURL + "sys/dept/list", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                if(node != null){
                    ztree.selectNode(node);

                    vm.user.deptName = node.name;
                }
            })
        },
        update: function () {
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }

            vm.showList = false;
            vm.title = "修改";
            $("#userName").attr("readonly",true);
            vm.getUser(userId);
            //获取角色信息
            this.getRoleList();
            var password = document.getElementById("password");
            password.type = "password";
            $("#i_eyeslash").show();
            $("#i_eye").hide();
            bool  = false;
        },
        del: function () {
            var userIds = getSelectedRows();
            if(userIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        verification : function(){
            if(vm.user.realName == null || vm.user.realName == ""){
                alert("员工姓名不能为空");
                return true;
            }
            if(vm.user.deptName == null || vm.user.deptName == ""){
                alert("所属公司不能为空");
                return true;
            }
            if(vm.user.userName == null || vm.user.userName == ""){
                alert("账号不能为空");
                return true;
            }
            if(vm.user.userName.length < 6 || vm.user.userName.length > 12){
                alert("账号长度限制为6-12位");
                return true;
            }
            var reg=/^[a-zA-Z]+$/;
            if(!reg.test(vm.user.userName)){
                alert("对不起，您输入的账号格式不正确!(只能是英文字母)");
                return true;
            }
            if(vm.user.password == null || vm.user.password == ""){
                alert("密码不能为空");
                return true;
            }
            if(vm.user.password.length < 6 || vm.user.password.length > 18 && bool == true){
                alert("密码长度限制为6-18位");
                return true;
            }
            var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
            if(reg.test(vm.user.password)){
                alert("对不起，您输入的密码格式不正确!(不能包含中文)");
                return true;
            }
            if(vm.user.email == null || vm.user.email == ""){
                alert("邮箱不能为空");
                return true;
            }
            reg=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            if(!reg.test(vm.user.email)){
                alert("对不起，您输入的邮箱格式不正确!");
                return true;
            }
            if(vm.user.mobile == null || vm.user.mobile == ""){
                alert("手机号不能为空");
                return true;
            }
            if(vm.user.mobile.length != 11 ){
                alert("请输入正确的手机号(11位)");
                return true;
            }
            var reg=/^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
            if(!reg.test(vm.user.mobile)){
                alert("对不起，您输入的手机号格式不正确!");
                return true;
            }
            return false;
        },
        saveOrUpdate: function () {

            $("#text1").blur();
            //验证非空等
            if (vm.verification()) {
                return;
            }

            $("#text1").attr("disabled", "disabled");

            var userName = vm.user.userName;
            //判断账号是否重复
            $.get(baseURL + "sys/user/infoByUserName/"+userName, function(r){
                if (r.list.length == 0 || r.list[0].userId == vm.user.userId) {
                    var deptId = vm.user.deptId;
                    //判断所属公司是否禁用
                    $.get(baseURL + "sys/user/judgeDeptByStatus/"+deptId, function(r){
                        if (r.bool || vm.user.status == 0) {
                            if (pwd == vm.user.password) {
                                vm.user.password = null;
                            }
                            var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
                            $.ajax({
                                type: "POST",
                                url: baseURL + url,
                                contentType: "application/json",
                                data: JSON.stringify(vm.user),
                                success: function(r){
                                    if(r.code === 0){
                                        alert('操作成功', function(){
                                            vm.reload();
                                        });
                                    }else{
                                        if (vm.user.password == null) {
                                            vm.user.password = pwd;
                                        }
                                        alert(r.msg);
                                    }
                                }
                            });
                        }else {
                            alert("员工所属公司的为禁用状态，无法启用");
                        }
                    });
                }else {
                    alert("您输入的账号已存在");
                }
                $("#text1").removeAttr("disabled");
            });
        },
        getUser: function(userId){
            $.get(baseURL + "sys/user/info/"+userId, function(r){
                vm.user = r.user;
                pwd = vm.user.password;
                vm.getDept();
            });
        },
        getRoleList: function(){
            $.get(baseURL + "sys/role/select", function(r){
                vm.roleList = r.list;
            });
        },
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择公司",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级公司
                    vm.user.deptId = node[0].deptId;
                    vm.user.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'userName': vm.q.userName},
                page:1
            }).trigger("reloadGrid");
            $("#text1").attr("status", "Y");
        }
    }
});

//密码框显示隐藏内容
function hideShowPsw(){
    var password = document.getElementById("password");

    if (password.type == "password" && pwd != vm.user.password) {
        password.type = "text";
        $("#i_eye").show();
        $("#i_eyeslash").hide();
    }else {
        password.type = "password";
        $("#i_eyeslash").show();
        $("#i_eye").hide();
    }

}

//清空密码框
function  empty() {
    if (!bool){
        vm.user.password = null;
        bool = true;
    }
}

