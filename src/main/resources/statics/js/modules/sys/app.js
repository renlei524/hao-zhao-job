$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/app/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', index: 'id', width: 50, key: true ,hidden:true},
            { label: '版本号', name: 'version', index: 'version', width: 80 },
            { label: '备注', name: 'remark', index: 'remark', width: 80 },
            { label: '创建人', name: 'userName', index: 'user_id', width: 80 },
            { label: '创建时间', name: 'createTime', index: 'create_time', width: 80 ,formatter:function(value,options,row){
                    return new Date(value).Format('yyyy-MM-dd HH:mm:ss');}},
            { label: '发布时间', name: 'publishTime', index: 'publish_time', width: 80 ,formatter:function(value,options,row){
                    return new Date(value).Format('yyyy-MM-dd HH:mm:ss');}},
            { label: '是否强制更新', name: 'isForceUpdate', index: 'is_force_update', width: 80 , formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">是</span>';
                    }
                    if(item === 0){
                        return '<span class="label label-success">否</span>';
                    }
                }},
            { label: 'APP类型', name: 'appType', index: 'app_type', width: 80 , formatter: function(item, index){
                    if(item === 0){
                        return '<span class="label label-primary">安卓</span>';
                    }
                    if(item === 1){
                        return '<span class="label label-success">ios</span>';
                    }
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



var checkbutton=false;
var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        sysApp: {
            id:null,
            version:null,
            remark:null,
            publishTime:null,
            isForceUpdate:0,
            appType:0,
            appUrl:null}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.sysApp = {
                id:null,
                version:null,
                remark:null,
                publishTime:null,
                isForceUpdate:0,
                appType:0,
                appUrl:null
            };
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        verification : function(){
            if(vm.sysApp.version == null || vm.sysApp.version == ""){
                alert("版本号不能为空");
                return true;
            }
            if($("#publishTime").val() == null || $("#publishTime").val() == ""){
                alert("发布时间不能为空");
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

            var url = vm.sysApp.id == null ? "sys/app/save" : "sys/app/update";
            var file = $("#file")[0].files[0];
            //创建内存中的表单对象
            var form = new FormData();
            //向其中添加要传输的数据
            form.append("file", file);
            $.ajax({
                url:baseURL + "sys/app/uploadApp",//请求地址
                data: form,     //请求参数
                type: 'POST',   //请求类型
                async:false,
                dataType: 'json',//服务器返回的数据类型
                contentType: false,//没有设置任何内容类型头信息
                processData: false, //见jQuery_api详解
                success: function(obj){ //成功时回调函数,obj表示服务器返回的数据
                checkbutton=true;
                    if (obj.path != null) {
                        vm.sysApp.appUrl = obj.path;
                    }
                    vm.sysApp.publishTime = $("#publishTime").val();
                    $.ajax({
                        type: "POST",
                        url: baseURL + url,
                        contentType: "application/json",
                        data: JSON.stringify(vm.sysApp),
                        success: function(r){
                            if(r.code === 0){
                                alert('操作成功', function(index){
                                    vm.reload();
                                    var file = document.getElementById('file');
                                    file.outerHTML = file.outerHTML;
                                });
                            }else{
                                alert(r.msg);
                            }
                            $("#text1").removeAttr("disabled");
                        }
                    });

                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/app/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function(id){
            $.get(baseURL + "sys/app/info/"+id, function(r){
                r.sysApp.publishTime = new Date(r.sysApp.publishTime).Format('yyyy-MM-dd');
                vm.sysApp = r.sysApp;
                $("#publishTime").val(r.sysApp.publishTime)
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
        },
        file: function () {
            var file = $("#file")[0].files[0];
            //创建内存中的表单对象
            var form = new FormData();
            //向其中添加要传输的数据
            form.append("file", file);
            $.ajax({
                url:baseURL + "sys/app/uploadApp",//请求地址
                data: form,     //请求参数
                type: 'POST',   //请求类型
                dataType: 'json',//服务器返回的数据类型
                contentType: false,//没有设置任何内容类型头信息
                processData: false, //见jQuery_api详解
                success: function(obj){ //成功时回调函数,obj表示服务器返回的数据
                    vm.sysApp.appUrl = obj.path;
                }
            });
        }
    }
});


layui.use('laydate', function(){
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#publishTime' //指定元素
    });
});
