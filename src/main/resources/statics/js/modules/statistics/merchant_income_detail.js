$(function () {
    vm.getProvince();
    $("#jqGrid").jqGrid({
        url: baseURL + 'statistics/merchantIncomeDetail/list',
        datatype: "json",
        colModel: [
            { label: '业务员', name: 'sysUserName', index: 'sysUserName', width: 80 },
            { label: '商户名称', name: 'merchantName', index: 'merchant_name', width: 80 },
            { label: '商户类型', name: 'typeName', index: 'typeName', width: 80 },
            { label: '注册时间', name: 'createTime', index: 'create_time', width: 80,formatter:function(value,options,row){
                    return new Date(value).Format('yyyy-MM-dd HH:mm:ss');} },
            { label: '总收入', name: 'totalIncome', index: 'totalIncome', width: 80 },
            { label: '收入笔数', name: 'incomePen', index: 'incomePen', width: 80 },
            { label: '总支出', name: 'totalExpenditure', index: 'totalExpenditure', width: 80 },
            { label: '支出笔数', name: 'expenditurePens', index: 'expenditurePens', width: 80 },
            { label: '余额', name: 'balance', index: 'balance', width: 80 }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 50,
        autowidth:true,
        multiselect: false,
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


var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        q: {
            province:0,
            city:0,
            area:0,
            town:0,
            merchantName:null,
            sysUserName:null,
            startTime:null,
            endTime:null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            vm.showList = true;
            var time = $("#startTime-endTime").val().split(" - ");
            vm.q.startTime = time[0];
            vm.q.endTime = time[1];
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                //添加传送数据
                postData:{
                    "province" :vm.q.province,
                    "city" :vm.q.city,
                    "area" :vm.q.area,
                    "town" :vm.q.town,
                    "sysUserName" :vm.q.sysUserName,
                    "merchantName" :vm.q.merchantName,
                    "endTime" :vm.q.endTime,
                    "startTime" :vm.q.startTime},
                page:page
            }).trigger("reloadGrid");
        },
        getProvince: function(){
            var parentId=0;
            var group = $("#areaProvince");
            group.empty();
            group.append("<option value='0' selected='selected'>--请选择省--</option>");
            //加载省下拉框
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
        getCity: function(){
            var parentId=$("#areaProvince").val();
            var group = $("#areaCity");
            group.empty();
            group.append("<option value='0' selected='selected'>--请选择市--</option>");
            if (parentId <=0) {

            }else {
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
            }
        },
        getArea: function(){
            var parentId=$("#areaCity").val();
            var group = $("#areaArea");
            group.empty();
            group.append("<option value='0' selected='selected'>--请选择区--</option>");
            if (parentId <=0) {

            }else {
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
            }

        } ,
        getTown: function(){
            var parentId=$("#areaArea").val();
            var group = $("#areaTown");
            group.empty();
            group.append("<option value='0' selected='selected'>--请选择乡镇--</option>");
            if (parentId <=0) {

            }else {
                //加载乡镇下拉框
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
            }

        }
    }
});


$("#areaProvince").change(function(){
    vm.q.city = 0;
    vm.getCity();
    vm.q.area = 0;
    vm.getArea();
    vm.q.town = 0;
    vm.getTown();
})

$("#areaCity").change(function(){
    vm.q.area = 0;
    vm.getArea();
    vm.q.town = 0;
    vm.getTown();
})

$("#areaArea").change(function(){
    vm.q.town = 0;
    vm.getTown();
})


layui.use('laydate', function(){
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#startTime-endTime' //指定元素
        ,range: true
    });
});