$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'community/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true, hidden: true },
            { label: '用户名', name: 'userName', width: 75 },
            { label: '员工姓名', name: 'realName', width: 75 },
            { label: '所属社区', name: 'deptName', sortable: false, width: 75 },
			{ label: '邮箱', name: 'email', width: 90 },
			{ label: '手机号', name: 'mobile', width: 100 },
			{ label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 85}
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
            communityName:null
        },
        showList: true,
        title:null,
        roleList:{},
        user:{
            status:1,
            deptId:null,
            deptName:null,
            roleIdList:[],
            realName:null
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

            //获取角色信息
            this.getRoleList();

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
            //加载社区树
            $.get(baseURL + "community/community/load", function(r){
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

            vm.getUser(userId);
            //获取角色信息
            this.getRoleList();
        },
        del: function () {
            var userIds = getSelectedRows();
            if(userIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "community/user/delete",
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
            if(vm.user.userName == null || vm.user.userName == ""){
                alert("用户名不能为空");
                return true;
            }
            if(vm.user.deptName == null || vm.user.deptName == ""){
                alert("请选择所属社区");
                return true;
            }
            var email = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*$/
            if(!email.test(vm.user.email)){
                alert("邮箱格式不正确");
                return true;
            }
            var reg=/^1[0-9]{10}$/;
            if(!reg.test(vm.user.mobile)){
                alert("正确的手机号！");
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
            var url = vm.user.userId == null ? "community/user/save" : "community/user/update";
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
                        alert(r.msg);
                    }
                    $("#text1").removeAttr("disabled");
                }
            });
        },
        getUser: function(userId){
            $.get(baseURL + "community/user/info/"+userId, function(r){
                vm.user = r.user;
                vm.user.password = null;

                vm.getDept();
            });
        },
        getRoleList: function(){
            $.get(baseURL + "community/role/select", function(r){
                vm.roleList = r.list;
            });
        },
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择社区",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级社区
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
                page:page
            }).trigger("reloadGrid");
            $("#text1").attr("status", "Y");
        }
    }
});
$("#communityName").click(function () {
    vm.q.communityName = null;
    communityModalReload();
});
//社区  弹出窗代码 d
$('#myModal').on('shown.bs.modal', function () {
// $(function () {
    $("#community").jqGrid({
        url: baseURL + 'community/community/list',
        datatype: "json",
        colModel: [
            { label: '社区ID', name: 'deptId', hidden:true},
            { label: '社区名称', name: 'name' },
            { label: '负责人', name: 'leader'},
            { label: '负责人电话', name: 'leaderTel'},
            { label: '状态', name: 'status', formatter: function(item, index){
                    if(item === 0){
                        return '<span class="label label-primary">禁用</span>';
                    }
                    if(item === 1){
                        return '<span class="label label-success">正常</span>';
                    }}}
        ],
        viewrecords: true,
        height: 235,
        rowNum: 6,
        rowList : [10,15,20,30,50],
        rownumbers: true,
        rownumWidth: 50,
        autowidth:true,
        multiselect: true,
        pager: "#communityPager",
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
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-y" : "hidden" });
        }

    });
});

//社区 弹出 窗关闭按钮代码 d
$("#community-window-close-button").click(function () {
    var rowKey = getCommunitySelecteRow();
    if(rowKey == null){
        return ;
    }
    var grid = $("#community");
    var rowKey = grid.getGridParam("selrow");
    var communityDate =  $("#community").getRowData(rowKey);
    vm.user.deptName = communityDate.name;
    vm.user.deptId = communityDate.deptId;
    $("#communityName").val(communityDate.name);
    $('#myModal').modal('hide');
});

//社区 弹出 窗查询按钮代码 d
$("#communityNameSearch").click(function () {
    communityModalReload();
});

//社区弹出框重加载代码 d
function communityModalReload() {
    var page = $("#community").jqGrid('getGridParam','page');
    $("#community").jqGrid('setGridParam',{
        postData:{'communityName':vm.q.communityName},
        page:1
    }).trigger("reloadGrid");
}

//社区  弹出窗查询选择条件为一条限制 d
function getCommunitySelecteRow() {
    var grid = $("#community");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }
    return selectedIDs[0];
}