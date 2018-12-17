$(function () {
    var now = new Date(),
        year = now.getFullYear(),
        month = ((now.getMonth() + 1) > 9) ? (now.getMonth() + 1) : ("0" + (now.getMonth() + 1)),
        date = translate(now.getDate());
    function translate(prop) {
        if (prop <= 9) {
            return "0" + prop;
        } else {
            return prop
        }
    }
    var dateString = year + "-" + month + "-" + date;
    $("#startTime-endTime").val(dateString + " - " + dateString);
    vm.q.startTime = dateString;
    vm.q.endTime = dateString;
    $("#jqGrid").jqGrid({
        url: baseURL + 'statistics/merchantActive/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', index: 'id', hidden: true },
            { label: '起始时间', name: 'startDate', index: '', hidden:true},
            { label: '结束时间', name: 'endDate', index: '', hidden:true},
            { label: '商户名称', name: 'merchantName', index: 'merchant_name', width: 80 },
            { label: '现金金额', name: 'cashAmount', index: 'cash_amount', width: 80, formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '微宝金额', name: 'weiBaoAmount', index: 'wei_bao_amount', width: 80, formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '优惠券金额', name: 'couponAmount', index: 'coupon_amount', width: 80, formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '总单数', name: 'totalBills', index: 'total_bills', width: 80 , formatter: function (value, grid, rows, state) {
                return "<a href=\"#\" data-toggle=\"modal\" data-target=\"#myModal\" style=\"color:#f60\" onclick=\"billsDetails(" + rows.id + ")\">" + (value == null ? 0 : value) + "</a>"
            }}
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
        q: {
            merchantName:null,
            startTime:null,
            endTime:null
        },
        id: null,
        startTimeSearch:null,
        endTimeSearch:null
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
                postData:{
                    "merchantName":vm.q.merchantName,
                    "endTime": vm.q.endTime, //开始时间
                    "startTime": vm.q.startTime //结束时间
                },
                page:1
            }).trigger("reloadGrid");
        },
        searchBills: function () {
            var page = $("#billDetails").jqGrid('getGridParam','page');
            $("#billDetails").jqGrid('setGridParam',{
                postData:{
                    "id":vm.id,
                    "startTime":vm.startTimeSearch,
                    "endTime":vm.endTimeSearch
                },
                page:1
            }).trigger("reloadGrid");
        }
    }
});

layui.use('laydate', function(){
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#startTime-endTime' //指定元素
        ,range: true
        ,max: 0
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
function billsDetails(id) {
    var rowData = $("#jqGrid").jqGrid("getRowData",id);
    vm.id = rowData.id;
    vm.startTimeSearch = rowData.startDate;
    vm.endTimeSearch = rowData.endDate;
    vm.searchBills();
}
$('#myModal').on('show.bs.modal', function (id) {
    $("#billDetails").jqGrid({
        url: baseURL + 'operation/twborder/bills',
        datatype: "json",
        colModel: [
            { label: '用户名称', name: 'userName'},
            { label: '用户电话', name: 'mobile'},
            { label: '金额', name: 'wbDeductible', index: '', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '下单时间', name: 'createTime',formatter:function(value,options,row){
                    return new Date(value).Format('yyyy-MM-dd HH:mm:ss');}},


            { label: '订单类型', name: 'type', index: '',valign: 'middle',formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">扫码支付</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-primary">扫码收款</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-primary">在线下单</span>';
                    }
                    if(item === 4){
                        return '<span class="label label-success">在线交费</span>';
                    }
            }},
        ],
        viewrecords: true,
        height: 235,
        rowNum: 6,
        rownumbers: false,
        rownumWidth: 45,
        autowidth:true,
        multiselect: false,
        pager: "#billDetailsPager",
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
        postData:{
            "id":vm.id,
            "startTime":vm.startTimeSearch,
            "endTime":vm.endTimeSearch
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#billDetails").closest(".ui-billDetails-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});