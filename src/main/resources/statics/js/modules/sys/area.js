

//地区结构树
var ztree;
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "areaCode",
            pIdKey: "parentCode",
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
        q:{
            Name:null
        },
        showList: true,
        title: null,
        twbarea:{
            areaCode:null,
            parentCode:null,
            parentName:null,
            name:null,
            sort:null,
            id:null
        }
    },
    methods: {

        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.twbarea = { areaCode:null, parentCode:null, name:null, sort:0,parentName:null,id:null};
            vm.getTwbarea();
        },
        getProvinces: function(){
            var parentId=0;
            //加载省下拉框
            var url = "sys/twbarea/infoList/"+parentId;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.twbarea),
                success: function(r){
                    var group = $("#areaProvince");
                    group.empty();
                    group.append("<option value='0'>请选择省</option>");
                    for(var i=0;i<r.length;i++) {
                            group.append("<option value='"+r[i].areaCode+"'>"+r[i].name+"</option>");
                    }
                }
            });
        },
        getCity :function(){
            var parentId=$("#areaProvince").val();
            var group = $("#areaCity");
            group.empty();
            group.append("<option value='-1'>请选择市</option>");
            vm.getList(parentId);
            if (parentId ==0)
                return;
            //加载市下拉框
            var url = "sys/twbarea/infoList/"+parentId;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.twbarea),
                success: function(r){
                    for(var i=0;i<r.length;i++) {
                        group.append("<option value='"+r[i].areaCode+"'>"+r[i].name+"</option>");
                    }
                }
            });
        },
        getTown :function(){
            var parentId=$("#areaCity").val();
            var group = $("#areaTown");
            group.empty();
            group.append("<option value='-1'>请选择区</option>");
            vm.getList(parentId);
            if (parentId ==0){
                return;
            }else if(parentId == -1){
                vm.getCity();
            }
            //加载区下拉框
            var url = "sys/twbarea/infoList/"+parentId;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.twbarea),
                success: function(r){
                    for(var i=0;i<r.length;i++) {
                        group.append("<option value='"+r[i].areaCode+"'>"+r[i].name+"</option>");
                    }
                }
            });
        },
        update: function () {
            var areaId = getAreaId();
            if(areaId == null){
                return ;
            }
            vm.getTwbarea();
            $.get(baseURL + "sys/twbarea/info/"+areaId, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.twbarea = r.area ;
            });
        },
        del: function () {
            var areaId  = getAreaId();
            console.log(areaId);
            if(areaId == null){
                return ;
            }
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/twbarea/delete",
                    data: "areaId=" + areaId,
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
            $("#text1").blur();
            if(vm.twbarea.name == null || vm.twbarea.name == ""){
                alert("地区名称不能为空");
                return true;
            }

            $("#text1").attr("disabled", "disabled");
            var name = vm.twbarea.name;
            var parentCode = vm.twbarea.parentCode;
            $.get(baseURL + "sys/twbarea/infoByName/"+(name == "" ? null : name)+"/"+parentCode, function(r){
                if (r.list.length == 0 || r.list[0].id == vm.twbarea.id) {
                    var url = vm.twbarea.sort == 0 ? "sys/twbarea/save" : "sys/twbarea/update";
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.twbarea),
                        success: function(r){
                            if(r.code === 0){
                                alert('操作成功', function(){
                                    vm.reload();
                                });
                            }else{
                                alert(r.msg);
                                $("#text1").attr("status", "Y");
                            }
                            $("#text1").removeAttr("disabled");
                        }
                    });
                }else {
                    alert("此地区名称已存在");
                }
            });
        },
        reload: function () {
            vm.showList = true;
            Area.table.refresh();
            $("#text1").attr("status", "Y");
        },
        getList:function (parentCode) {
            var colunms = Area.initColumn();
            var table = new TreeTable(Area.id, baseURL + "sys/twbarea/infoList/"+parentCode, colunms);
            table.setExpandColumn(2);
            //设置记录返回的id值
            table.setIdField("id");
            //设置记录分级的字段
            table.setCodeField("id");
            // //设置记录分级的父级字段
            table.setParentCodeField("parentCode");
            table.setExpandAll(false);
            table.init();
            Area.table = table;
        },
        twbareaTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择地区",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#twbareaLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级地区
                    vm.twbarea.parentCode = node[0].areaCode;
                    vm.twbarea.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
        getTwbarea: function(){
            //加载地区树
            $.get(baseURL + "sys/twbarea/list", function(r){
                ztree = $.fn.zTree.init($("#twbareaTree"),setting, r.page);
                var node = ztree.getNodeByParam("areaCode", vm.twbarea.parentCode);
                if(node != null){
                    ztree.selectNode(node);
                    vm.twbarea.parentName = node.name;
                }
            })
        }
    }
});
var Area = {
    id: "areaTable",
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
Area.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '地区名称', field: 'name', align: 'center', sortable: true, width: '180px'},
        {title: '上级地区名称', field: 'parentName', align: 'center', sortable: true, width: '180px'},
         ]
    return columns;
};
//获取当前选择行areaId
function getAreaId () {
    var selected = $('#areaTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}
$(document).ready(function(){
    // $("#province").change(function () {
    // });
    vm.getProvinces();
    vm.getList(0);
    $("#areaProvince").change(function(){
        vm.getCity();
        var group = $("#areaTown");
        group.empty();
        group.append("<option value='-1'>请选择区</option>");
    })

    $("#areaCity").change(function(){
        vm.getTown();
    })

    $("#areaTown").change(function(){
        var parentId=$("#areaTown").val();
        vm.getList(parentId);
        if (parentId == -1){
            vm.getTown();
        }
    })
})