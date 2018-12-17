$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'statistics/checkAccountsTotal/list',
        datatype: "json",
        colModel: [
            { label: 'merchantFinanceId', name: 'merchantFinanceId', index: '', hidden:true},
            { label: '商户昵称', name: 'merchantName', index: '', width: 80 },
            { label: '商户电话号码', name: 'telephone', index: '', width: 80 },
            { label: '昨日余额', name: 'yesterdayBalance', index: '', width: 80, formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '今日收入', name: 'todayIncome', index: '', width: 80, formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '今日支出', name: 'todayExpend', index: '', width: 80, formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '当前余额', name: 'currentBalance', index: '', width: 80, formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '预警状态', name: '', index: '', width: 80, formatter:function(value) {
                value = "无";
                return value;
            }},
            { label: '错误警报', name: '', index: '', width: 80 ,formatter:function(value) {
                value = "无";
                return value;
            }},
            { label: '操作', name: '', index: '', width: 80, formatter:function(value) {
                    value = "骚操作";
                    return value;
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
            vm.getInfo();
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
            time:null,
            merchantMobile:null
        },
        checkAccountsTotal: {
            yesterdayTotalBlance: null,
            todayTotalIncome: null,
            todayTotalExpend: null,
            currentTotalBalance: null
        }

},
    methods: {
        query: function () {
            vm.reload();
        },
        getInfo: function () {
            $.ajax({
                       url: baseURL + "statistics/checkAccountsTotal/info",
                       data: {
                           "merchantName":vm.q.merchantName,
                           "time": vm.q.time, //时间
                           "merchantMobile": vm.q.merchantMobile //时间
                       },
                       type: "post",
                       async: true,
                       success: function(r) {
                           $("#yesterdayTotalBlance").html(r.checkAccountsTotal.yesterdayTotalBlance);
                           $("#todayTotalIncome").html(r.checkAccountsTotal.todayTotalIncome);
                           $("#todayTotalExpend").html(r.checkAccountsTotal.todayTotalExpend);
                           $("#currentTotalBalance").html(r.checkAccountsTotal.currentTotalBalance);
                       }
                   });
        },
        reload: function (event) {
            vm.showList = true;
            var time = $("#datePicker").val();
            vm.q.time = time;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    "merchantName":vm.q.merchantName,
                    "time": vm.q.time, //时间
                    "merchantMobile": vm.q.merchantMobile //时间
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
        elem: '#datePicker' //指定元素
        ,value: new Date()
        ,change: function(value, date, endDate){
        },
        done: function(value, date, endDate){
        }
    });
});