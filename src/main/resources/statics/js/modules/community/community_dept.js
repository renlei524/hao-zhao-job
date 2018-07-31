$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'community/community/list',
        datatype: "json",
        colModel: [
        {label: '部门ID', name: 'deptId', index: "dept_id",width: 75 ,key: true, hidden: true},
        {label: '机构名称', name: 'name', index: "name",width: 75},
        {label: '所属地区', name: 'address',width: 75},
        // {label: '上级社区', name: 'parentName',width: 75},
        {label: '负责人', name: 'leader', index: "leader",width: 75 },
        {label: '联系电话', name: 'leaderTel',  index: "leaderTel",width: 75},
        {label: '创建人', name: 'creUserName',  index: "user_name",width: 75 },
        {label: '创建时间', name: 'creTime',  index: "cre_time",width: 75},
        {label: '状态', name: 'status', sortable:false, index: "status",width: 75, formatter: function(value, options, row){
            return value === 0 ?
                '<span class="label label-danger">禁用</span>' :
                '<span class="label label-success">启用</span>';
         }}
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

var dept_ztree;
var dept_setting = {
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


var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,   dept:{
            parentName:null,
            parentId:0,
            orderNum:0,
            name:null,
            leader: null
        },
        community:{
            communityName:null,
            sysDeptName: null,
            leader:null,
            leaderTel:null,
            province: -1,
            city: -1,
            area: -1,
            town: -1,
            status: 1,

        },
        q:{
            status: "",
            communityName:null
        },
        //新增时联动菜单重置用省、市、区、街道
        selectedProvince: -1,
        selectedCity: -1,
        selectedArea: -1,
        selectedTown: -1
    },
    methods: {
        query: function () {
            vm.reload();
        },
        //新增社区方法
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.community = {province: -1, city : -1, area: -1, town: -1, status: 1};
            vm.getDept();
            vm.getProvince();
            vm.getCity(-1);
            vm.getArea(-1);
            vm.getTown(-1);
            window.onload = function (){
              document.body.onkeydown=function(event){
                if(event.keyCode==13){
                    event.keyCod=0; return false;
                }
              }
            };
        },
        //修改社区方法
        update: function () {
            var communityId = getSelectedRow();
            if(communityId == null){
                return ;
            }
            $.get(baseURL + "community/community/info/"+communityId, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.community = r.community;
                if(vm.community.province == null){
                    vm.community.province = -1;
                }
                if(vm.community.city == null){
                    vm.community.city = -1;
                }
                if(vm.community.area == null){
                    vm.community.area = -1;
                }
                if(vm.community.town == null){
                    vm.community.town = -1;
                }
                vm.getDept();
                vm.getProvince();
                vm.getCity(vm.community.province);
                vm.getArea(vm.community.city);
                vm.getTown(vm.community.area);
            });

        },
        //删除社区方法
        del: function () {
            var deptId = getSelectedRows();
            if(deptId == null){
                return ;
            }
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "community/community/delete",
                    contentType: "application/json",
                    data: JSON.stringify(deptId),
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
            });
        },
        saveOrUpdate: function (event) {
            if (vm.verification()) {
                return;
            }
            if($("#text1").attr("status") == 'N') return ;
        	$("#text1").attr("status", "N");
            var provinceName = null;
            var cityName = null;
            var areaName = null;
            var townName = null;
            if ($("#province").val() != -1) {
                var provinceIndex=document.getElementById("province").selectedIndex;
                provinceName = document.getElementById("province").options[provinceIndex].innerHTML;
            }
            if ($("#city").val() != -1) {
                var cityIndex=document.getElementById("city").selectedIndex;
                cityName = document.getElementById("city").options[cityIndex].innerHTML;
            }
            if ($("#area").val() != -1) {
                var areaIndex=document.getElementById("area").selectedIndex;
                areaName = document.getElementById("area").options[areaIndex].innerHTML;
            }
            if ($("#town").val() != -1) {
                var townIndex=document.getElementById("town").selectedIndex;
                townName = document.getElementById("town").options[townIndex].innerHTML;
            }
            vm.community.address = (provinceName == null ? "" : provinceName) + (cityName == null ? "" : cityName) +
                (areaName == null ? "" : areaName) +  (townName == null ? "" : townName);
            var url = vm.community.deptId == null ? "community/community/save" : "community/community/update";
            if ($("#province").val() != -1) {
                var provinceIndex=document.getElementById("province").selectedIndex;
                provinceName = document.getElementById("province").options[provinceIndex].innerHTML;
                vm.community.address = provinceName;
            }
            if ($("#city").val() != -1) {
                var cityIndex=document.getElementById("city").selectedIndex;
                cityName = document.getElementById("city").options[cityIndex].innerHTML;
                vm.community.address += cityName;
            }
            if ($("#area").val() != -1) {
                var areaIndex=document.getElementById("area").selectedIndex;
                areaName = document.getElementById("area").options[areaIndex].innerHTML;
                vm.community.address += areaName;
            }
            if ($("#town").val() != -1) {
                var townIndex=document.getElementById("town").selectedIndex;
                townName = document.getElementById("town").options[townIndex].innerHTML;
                vm.community.address += townName;
            }
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.community),
                success: function(r){
                    if(r.code === 0){
                        alert('保存成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                        $("#text1").attr("status", "Y");
                    }
                }
            });
        },
        verification : function(){
            if(vm.community.name == null || vm.community.name == ""){
                alert("机构名称不能为空");
                return true;
            }
            if(vm.community.leader == null || vm.community.leader == ""){
                alert("负责人不能为空");
                return true;
            }
            if(vm.community.leaderTel == null || vm.community.leaderTel == ""){
                alert("联系电话不能为空");
                return true;
            }
            if(vm.community.province == null || vm.community.province == "" || vm.community.province == -1){
                alert("请选择所属地区");
                return true;
            }
            if(vm.community.leaderTel.length != 11 ){
                alert("请输入11位的手机号码");
                return true;
            }
            var reg=/^[-+]?\d*$/;
            if(!reg.test(vm.community.leaderTel)){
                alert("对不起，您输入的号码格式不正确!");
                return true;
            }
            return false;
        },
        //加载省下拉框
        getProvince: function(){
            var parentCode=0;
            var group = $("#province");
            group.empty();
            var url = "sys/twbarea/infoList/"+parentCode;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                success: function(r){
                    group.append("<option value='-1'>--请选择--</option>");
                    for(var i=0;i<r.length;i++) {
                        if(vm.community.province == r[i].areaCode) {
                            group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                        } else {
                            group.append("<option value='" + r[i].areaCode + "'>" + r[i].name + "</option>");
                        }
                    }
                }
            });
        },
        //加载市下拉框
        getCity: function(parentCode){
            var group = $("#city");
            if (parentCode <=0 || parentCode == null) {
                    group.empty();
                    group.append("<option value='-1'>--请选择--</option>");
            }else {
                group.empty();
                var url = "sys/twbarea/infoList/"+parentCode;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    success: function(r){
                        group.empty();
                        group.append("<option value='-1'>--请选择--</option>");
                        for(var i=0;i<r.length;i++) {
                            if(vm.community.city == r[i].areaCode) {
                                group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                            } else {
                                group.append("<option value='" + r[i].areaCode + "'>" + r[i].name + "</option>");
                            }
                        }
                    }
                });
            }
        },
        //加载区下拉菜单
        getArea: function(parentCode){
            var group = $("#area");
            if (parentCode <=0 || parentCode == null) {
                    group.empty();
                    group.append("<option value='-1'>--请选择--</option>");
            }else {
                group.empty();
                var url = "sys/twbarea/infoList/"+parentCode;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    success: function(r){
                        group.empty();
                        group.append("<option value='-1'>--请选择--</option>");
                        for(var i=0;i<r.length;i++) {
                            if(vm.community.area == r[i].areaCode) {
                                group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                            } else {
                                group.append("<option value='" + r[i].areaCode + "'>" + r[i].name + "</option>");
                            }
                        }
                    }
                });
            }
        },
        //加载街道下拉菜单
        getTown: function(parentCode){
            var group = $("#town");
            if (parentCode <=0 || parentCode == null) {
                    group.empty();
                    group.append("<option value='-1'>--请选择--</option>");
            }else {
                group.empty();
                //加载乡镇下拉框
                var url = "sys/twbarea/infoList/"+parentCode;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    success: function(r){
                        group.empty();
                        group.append("<option value='-1'>--请选择--</option>");
                        for(var i=0;i<r.length;i++) {
                            if(vm.community.town == r[i].areaCode) {
                                group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                            } else {
                                group.append("<option value='" + r[i].areaCode + "'>" + r[i].name + "</option>");
                            }
                        }
                    }
                });
            }
        },
        //reload
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    "status":vm.q.status,
                    "communityName":vm.q.communityName
                },
                page:page
            }).trigger("reloadGrid");
            $("#text1").attr("status", "Y");
        },
        getDept: function(){
            //加载公司树
            $.get(baseURL + "sys/dept/list", function(r){
                dept_ztree = $.fn.zTree.init($("#deptTree"), dept_setting, r);
                var node = dept_ztree.getNodeByParam("deptId", vm.community.sysDeptId);
                if(node != null){
                    dept_ztree.selectNode(node);
                    vm.community.deptName = node.name;
                    $("#communityId").val(vm.community.deptName);
                }
            })
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
                    var node = dept_ztree.getSelectedNodes();
                    //选择上级公司
                    vm.community.sysDeptId = node[0].deptId;
                    vm.community.sysDeptName = node[0].name;
                    $("#communityId").val(vm.community.sysDeptName);
                    layer.close(index);
                    //隐藏div层
                    $("#deptLayer").hide();
                }
            });
        },
    }
});
//省选择触发事件
$("#province").change(function(){
    vm.selectedProvince = -1;
    vm.selectedCity = -1;
    vm.selectedArea = -1;
    vm.selectedTown = -1;
    vm.selectedProvince =  $("#province").val();
    vm.getCity(vm.selectedProvince);
    vm.getArea(vm.selectedCity);
    vm.getTown(vm.selectedArea);
    vm.community.province = vm.selectedProvince;
    vm.community.city = vm.selectedCity;
    vm.community.area = vm.selectedArea;
    vm.community.town = vm.selectedTown;
})
//市选择触发事件
$("#city").change(function(){
    vm.selectedArea = -1;
    vm.selectedTown = -1;
    vm.selectedCity = $("#city").val();
    vm.getArea(vm.selectedCity);
    vm.getTown(vm.selectedArea);
    vm.community.city = vm.selectedCity;
    vm.community.area = vm.selectedArea;
    vm.community.town = vm.selectedTown;
})
//区选择触发事件
$("#area").change(function(){
    vm.selectedTown = -1;
    vm.selectedArea = $("#area").val();
    vm.getTown(vm.selectedArea);
    vm.community.area = vm.selectedArea;
    vm.community.town = vm.selectedTown;
})
