$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/role/list',
        datatype: "json",
        colModel: [
            { label: '角色ID', name: 'roleId', index: "role_id", width: 45, key: true, hidden:true},
            { label: '角色名称', name: 'roleName', index: "role_name", width: 75 },
            { label: '创建人', name: 'userName', sortable: false, width: 75 },
            { label: '角色说明', name: 'remark', width: 100 },
            { label: '创建时间', name: 'createTime', index: "create_time", width: 80,formatter:function(value,options,row){
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


//菜单树
var menu_ztree;
var menu_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "menuId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    },
    check:{
        enable:true,
        nocheckInherit:true
    }
};

//部门结构树
var data_ztree;
var data_setting = {
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
    },
    check:{
        enable:true,
        nocheckInherit:true,
        chkboxType:{ "Y" : "", "N" : "" }
    }
};

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            roleName: null
        },
        showList: true,
        title:null,
        role:{
            deptId:null,
            deptName:null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.role = {deptName:null, deptId:null};
            vm.getMenuTree(null);

            vm.getDataTree();
            window.onload = function (){
              document.body.onkeydown=function(event){
                if(event.keyCode==13){
                    event.keyCod=0; return false;
                }
              }
            }
        },
        update: function () {
            var roleId = getSelectedRow();
            if(roleId == null){
                return ;
            }

            vm.showList = false;
            vm.title = "修改";
            vm.getDataTree();
            vm.getMenuTree(roleId);
        },
        del: function () {
            var roleIds = getSelectedRows();
            if(roleIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/role/delete",
                    contentType: "application/json",
                    data: JSON.stringify(roleIds),
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
        getRole: function(roleId){
            $.get(baseURL + "sys/role/info/"+roleId, function(r){
                vm.role = r.role;

                //勾选角色所拥有的菜单
                var menuIds = vm.role.menuIdList;
                for(var i=0; i<menuIds.length; i++) {
                    var node = menu_ztree.getNodeByParam("menuId", menuIds[i]);
                    menu_ztree.checkNode(node, true, false);
                }

                //勾选角色所拥有的部门数据权限
                var deptIds = vm.role.deptIdList;
                for(var i=0; i<deptIds.length; i++) {
                    var node = data_ztree.getNodeByParam("deptId", deptIds[i]);
                    data_ztree.checkNode(node, true, false);
                }
            });
        },
        verification : function(){
            if(vm.role.roleName == null || vm.role.roleName == ""){
                alert("角色名称不能为空");
                return true;
            }
            if(vm.role.remark == null || vm.role.remark == ""){
                alert("角色说明不能为空");
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

            var roleName = vm.role.roleName;
            $.get(baseURL + "sys/role/infoByRoleName/"+roleName, function(r){
                if (r.list.length == 0 || vm.role.roleId == r.list[0].roleId) {
                    //获取选择的菜单
                    var nodes = menu_ztree.getCheckedNodes(true);
                    var menuIdList = new Array();
                    for(var i=0; i<nodes.length; i++) {
                        menuIdList.push(nodes[i].menuId);
                    }
                    vm.role.menuIdList = menuIdList;

                    //获取选择的数据
                    var nodes = data_ztree.getCheckedNodes(true);
                    var deptIdList = new Array();
                    for(var i=0; i<nodes.length; i++) {
                        deptIdList.push(nodes[i].deptId);
                    }
                    vm.role.deptIdList = deptIdList;

                    var url = vm.role.roleId == null ? "sys/role/save" : "sys/role/update";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.role),
                        success: function(r){
                            if(r.code === 0){
                                alert('操作成功', function(){
                                    vm.reload();
                                });
                            }else{
                                alert(r.msg);
                            }
                        }
                    });
                }else {
                    alert("您输入的角色名称已存在");
                }
                $("#text1").removeAttr("disabled");
            });
        },
        getMenuTree: function(roleId) {
            //加载菜单树
            $.get(baseURL + "sys/menu/list", function(r){
                menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r);
                //展开所有节点
                //menu_ztree.expandAll(true);
                //展开二级节点
                var nodes = menu_ztree.getNodes();
                for (var i = 0; i < nodes.length; i++) { //设置节点展开
                    menu_ztree.expandNode(nodes[i], true, false, true);
                }

                if(roleId != null){
                    vm.getRole(roleId);
                }
            });
        },
        getDataTree: function(roleId) {
            //加载部门树
            $.get(baseURL + "sys/dept/list", function(r){
                data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, r);
                //展开所有节点
                //data_ztree.expandAll(true);
                //展开二级节点
                var nodes = data_ztree.getNodes();
                for (var i = 0; i < nodes.length; i++) { //设置节点展开
                    data_ztree.expandNode(nodes[i], true, false, true);
                }
            });
        },
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = dept_ztree.getSelectedNodes();
                    //选择上级部门
                    vm.role.deptId = node[0].deptId;
                    vm.role.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'roleName': vm.q.roleName},
                page:1
            }).trigger("reloadGrid");
            $("#text1").attr("status", "Y");
        }
    }
});
