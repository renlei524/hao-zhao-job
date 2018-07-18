$(function () {

    modal();

    //页面默认显示当天
    var now = new Date(),
        year = now.getFullYear(),
        month = ((now.getMonth() + 1)>9)?(now.getMonth() + 1):("0"+(now.getMonth() + 1)),
        date = translate(now.getDate());
    function translate(prop){
        if(prop <= 9){
            return "0" + prop;
        }else {
            return prop
        }
    }
    var dateString = year+"-"+month+"-"+date;
    $("#startTime-endTime").val(dateString+" - "+dateString);
    vm.q.startTime = dateString;
    vm.q.endTime = dateString;

    //表格显示数据
    $("#jqGrid").jqGrid({
        url: baseURL + 'statistics/merchantDayIncome/list',
        datatype: "json",
        colModel: [
            { label: '商户id', name: 'merchantId', fixed:true, width: 120, hidden:true },
            { label: '分公司名称', name: 'agentName', fixed:true, width: 160},
            { label: '业务员', name: 'salesman', fixed:true, width: 90 },
            { label: '商户名称', name: 'merchantName', fixed:true, width: 200 },
            { label: '手机号码', name: 'phoneNumber', fixed:true, width: 110 ,formatter:function(value,options,row){
                    return (value.substr(0, 3) + '****' + value.substr(7));}},
            { label: '交易金额', name: 'transactionAmount', fixed:true, width: 90 , formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '交易笔数', name: 'transactionPenNumber', fixed:true, width: 90 },
            { label: '微信笔数', name: 'weChatPens', fixed:true, width: 90 },
            { label: '支付宝笔数', name: 'alipayPens', fixed:true, width: 90 },
            { label: '交易人数', name: 'transactionNumber', fixed:true, width: 90 },
            { label: '信用卡金额', name: 'creditCardAmount', fixed:true, width: 90 },
            { label: '信用卡笔数', name: 'creditCardNumber', fixed:true, width: 90 },
            { label: '微信评论', name: 'weChatReview', fixed:true, width: 90 },
            { label: '支付宝评论', name: 'alipayReview', fixed:true, width: 90 },
            { label: '交易日期', name: 'dateOfTransaction', fixed:true, width: 90,formatter:function(value,options,row){
                    return new Date(value).Format('yyyy-MM-dd');} },
            { label: '操作', name: 'num', fixed:true, width: 120, formatter: function(value,options,row){
                    return '<button type="button" onclick="MerchantDayDataDetail('+value+')" data-target="#myModal" class="merchant-code btn btn-info btn-xs" data-toggle="modal" data-target=".bs-example-modal-sm">查看商家明细</button>'
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

            // 强制显示水平
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-y" : "scroll" });
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });

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
            merchantName:null,
            salesman:null,
            startTime:null,
            endTime:null,
            agentName:null,
            merchantId :null,
            dateOfTransaction : null,
            merchantNames:null
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
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                //添加传送数据
                postData: {
                    "salesman": vm.q.salesman, //业务员
                    "merchantName": vm.q.merchantName, //商户名称
                    "endTime": vm.q.endTime, //开始时间
                    "startTime": vm.q.startTime , //结束时间
                    "agentName": vm.q.agentName  //公司名称
                },
                page: 1
            }).trigger("reloadGrid");
        }
    }
});


function MerchantDayDataDetail(id) {
    // var id=$("#jqGrid").jqGrid("getGridParam","selrow");
    vm.q.merchantNames = null;
    var rowData = $("#jqGrid").jqGrid("getRowData",id);
    vm.q.dateOfTransaction = rowData.dateOfTransaction;
    vm.q.merchantId = rowData.merchantId;
    $("#myModalLabel").text("商家 【" + rowData.merchantName + "】 " + rowData.dateOfTransaction + " 交易明细数据");
    // alert(rowData.merchantId+"--------"+rowData.dateOfTransaction);
    commercialNameReload();
}


layui.use('laydate', function(){
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#startTime-endTime' //指定元素
        ,range: true
        ,max: 0
    });
});

function modal(){
    $("#commercialName").jqGrid({
        url: baseURL + 'operation/twborder/list_merchant',
        datatype: "json",
        colModel: [
            { label: '订单编号', name: 'orderId', width:80, index: 'order_id', key: true , hidden:true},
            { label: '用户名称', name: 'userName',width:80},
            { label: '手机号码', name: 'mobile', index: 'mobile'},
            { label: '订单金额', name: 'totalAmount',width:80, index: 'total_amount', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '交易时间', name: 'payTime', index: 'pay_time',width:245},
            { label: '微宝抵扣', name: 'wbDeductible',width:80, index: 'total_fee', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '支付方式', name: 'orderFrom', index: 'order_from',valign: 'middle',formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-success">微信</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-primary">支付宝</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-success">微信反扫</span>';
                    }
                    if(item === 4){
                        return '<span class="label label-primary">支付宝反扫</span>';
                    }
                } },
            { label: '支付类型', name: 'orderType', index: 'order_type',valign: 'middle',formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">扫码支付</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-success">扫码收款</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-success">在线下单</span>';
                    }
                    if(item === 4){
                        return '<span class="label label-success">在线交费</span>';
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
        multiselect: false,
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


$(document).ready(function () {
    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-y" : "scroll" });
    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
 })

/**
 *订单加载
 */
$('#myModal').on('show.bs.modal', function () {
    modal();
});


//商户弹出窗查询按钮代码 d
$("#commercialNameReload").click(function () {
    commercialNameReload();
});



//商户弹出框重加载代码 d
function commercialNameReload() {
    var page = $("#commercialName").jqGrid('getGridParam','page');
    $("#commercialName").jqGrid('setGridParam',{
        postData:{'userName':vm.q.merchantNames,'dateOfTransaction':vm.q.dateOfTransaction,'merchantId':vm.q.merchantId},
        page:1
    }).trigger("reloadGrid");
}