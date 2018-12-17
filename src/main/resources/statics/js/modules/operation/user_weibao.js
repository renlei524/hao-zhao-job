$(function () {
    modal();

    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/userweibao/list',
        datatype: "json",
        colModel: [
            { label: 'userId', name: 'userId', index: 'user_id', width: 50, key: true,hidden:true},
            { label: '昵称', name: 'userName', width: 80 },
            { label: '电话', name: 'mobile', width: 80 },
            { label: '领取微宝', name: 'receiveWB',index: 'receiveWB', width: 80 },
            { label: '使用微宝', name: 'useWB', width: 80,index: 'useWB' , formatter: function(item, index){
                    return '<a onclick="vm.details(this)" href="#" data-target="#myModal" data-toggle="modal" style="text-decoration: none;" >'+ item +'</a>'
                } },
            { label: '余额', name: 'weibaoWithdrawals',index: 'weibao_withdrawals', width: 80 }
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

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        tUserWeibao: {},
        q:{
            mobile: null,
            userName: null,
            userId : 0
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.tUserWeibao = {};
        },
        update: function (event) {
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(userId)
        },
        saveOrUpdate: function (event) {
            var url = vm.tUserWeibao.userId == null ? "operation/userweibao/save" : "operation/userweibao/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.tUserWeibao),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var userIds = getSelectedRows();
            if(userIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "operation/userweibao/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
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
        getInfo: function(userId){
            $.get(baseURL + "operation/userweibao/info/"+userId, function(r){
                vm.tUserWeibao = r.tUserWeibao;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    "userName" :vm.q.userName,
                    "mobile" :vm.q.mobile},
                page:1
            }).trigger("reloadGrid");
        },
        details: function(e){
            var id = $(e).parent().parent().attr("id");
            vm.q.userId = id;
            commercialNameReload();
        }
    }
});


function modal(){
    $("#commercialName").jqGrid({
        url: baseURL + 'statistics/wborderbill/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width:200, index: 'order_id', key: true , hidden:true},
            { label: '单号', name: 'orderId',width:250},
            { label: '时间', name: 'createTime', index: 'mobile',width:250},
            { label: '数量', name: 'totalFee',width:250, index: 'total_fee', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '来源', name: 'type', index: 'type',valign: 'middle',width:200,formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">微宝</span>';
                    }
                } }
        ],
        viewrecords: true,
        height: 300,
        rowNum: 10,
        rowList : [10,15,20,30,50],
        rownumbers: true,
        rownumWidth: 50,
        autowidth:true,
        multiselect:false,
        pager: "#commercialNamePager",
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
            // $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    commercialNameReload();
}

/**
 *订单加载
 */
$('#myModal').on('show.bs.modal', function () {
    modal();
});

$(document).ready(function () {
    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-y" : "scroll" });
    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
})



//订单弹出框重加载代码 d
function commercialNameReload() {
    var page = $("#commercialName").jqGrid('getGridParam','page');
    $("#commercialName").jqGrid('setGridParam',{
        postData:{'userId':vm.q.userId},
        page:1
    }).trigger("reloadGrid");
}