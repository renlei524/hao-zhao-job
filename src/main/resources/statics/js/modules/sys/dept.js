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
        showList: true,
        title: null,
        dept:{
            parentName:null,
            parentId:0,
            orderNum:0,
            name:null,
            leader: null
        }
    },
    methods: {
        getDept: function(){
            //加载公司树
            $.get(baseURL + "sys/dept/select", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r.deptList);
                var node = ztree.getNodeByParam("deptId", vm.dept.parentId);
                ztree.selectNode(node);

                vm.dept.parentName = node.name;
            })
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.dept = {parentName:null,parentId:0,orderNum:0, status:1,name:null};
            vm.getDept();

        },
        update: function () {
            var deptId = getDeptId();
            if(deptId == null){
                return ;
            }

            $.get(baseURL + "sys/dept/info/"+deptId, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.dept = r.dept;

                vm.getDept();
            });
        },
        del: function () {
            var deptId = getDeptId();
            if(deptId == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/dept/delete",
                    data: "deptId=" + deptId,
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
        verification : function(){
            if(vm.dept.name == null || vm.dept.name == ""){
                alert("公司名称不能为空");
                return true;
            }
            if(vm.dept.leader == null || vm.dept.leader == ""){
                alert("负责人不能为空");
                return true;
            }
            if(vm.dept.leaderTel == null || vm.dept.leaderTel == ""){
                alert("负责人电话号码不能为空");
                return true;
            }
            if(vm.dept.leaderTel.length != 11 ){
                alert("请输入正确的手机号(11位)");
                return true;
            }
            var reg=/^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
            if(!reg.test(vm.dept.leaderTel)){
                alert("对不起，您输入的电话号码格式不正确!");
                return true;
            }
            return false;
        },
        saveOrUpdate: function (event) {
            $("#text1").blur();
            //验证非空等
            if (vm.verification()) {
                return;
            }
            $("#text1").attr("disabled", "disabled");

            var name = vm.dept.name;
            //判断公司名称是否重复
            $.get(baseURL + "sys/dept/infoByName/"+name, function(r){
                if (r.list.length == 0 || vm.dept.deptId == r.list[0].deptId) {
                    var deptId = vm.dept.deptId == null ? vm.dept.parentId : vm.dept.deptId;
                    //判断此公司的上级公司是否禁用
                    $.get(baseURL + "sys/dept/judgeByStatus/"+deptId, function(r){
                        if (r.bool || vm.dept.status == 0 || vm.dept.parentId ==0){
                            //进行新增or修改操作
                            var url = vm.dept.deptId == null ? "sys/dept/save" : "sys/dept/update";
                            $.ajax({
                                type: "POST",
                                url: baseURL + url,
                                contentType: "application/json",
                                data: JSON.stringify(vm.dept),
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
                            alert("上级公司状态为禁用，无法启用");
                        }
                    });
                }else {
                    alert("您输入的公司名称已存在");
                }
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
                    vm.dept.parentId = node[0].deptId;
                    vm.dept.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            Dept.table.refresh();
            $("#text1").attr("status", "Y");
        }
    }
});

var Dept = {
    id: "deptTable",
    table: null,
    layerIndex: -1
};

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


/**
 * 初始化表格的列
 */
Dept.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        // {title: '公司ID', field: 'deptId', visible: false, align: 'center', valign: 'middle', width: '80px', hidden:'true'},
        {title: '公司名称', field: 'name', align: 'center', sortable: true, width: '180px'},
        {title: '上级公司', field: 'parentName', align: 'center', sortable: true, width: '200px'},
        {title: '负责人', field: 'leader', width: 60 },
        {title: '负责人电话号码', field: 'leaderTel', width: 60 },
        {title: '创建人', field: 'userName', width: 60 },
        {title: '创建时间', field: 'createTime', width: 100 ,formatter:function(value,options,row){
                return new Date(value.createTime).Format('yyyy-MM-dd HH:mm:ss');}},
        {title: '状态', field: 'status', width: 60, formatter: function(value, options, row){
            return (value.status == 0 ? '<span class="label label-danger">禁用</span>' : '<span class="label label-success">正常</span>');
         }}]
        /*{title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'}*/
    return columns;
};


function getDeptId () {
    var selected = $('#deptTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}


$(function () {
    $.get(baseURL + "sys/dept/info", function(r){
        var colunms = Dept.initColumn();
        var table = new TreeTable(Dept.id, baseURL + "sys/dept/list", colunms);
        table.setRootCodeValue(r.deptId);
        table.setExpandColumn(2);
        table.setIdField("deptId");
        table.setCodeField("deptId");
        table.setParentCodeField("parentId");
        table.setExpandAll(false);
        table.init();
        Dept.table = table;
    });
});


/*$(document).on('mouseover','td',function(){

    var that = this;
    var text = $(this).text();
    layer.tips(text, that,{
        tips: 1,
        time: 2000
    });

})*/

$(document).on('mouseenter',function(){

    $(function(){
      //table的最后一列不给其title赋值

      $(".treegrid-tbody tr td").each(function(){

          $(this).attr("title",$(this).text());
          //alert($(this).parent().get(0).rowIndex);输出所在行

      });
    });

})
