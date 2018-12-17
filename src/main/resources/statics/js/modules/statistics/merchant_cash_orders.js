$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'statistics/tmerchantcashorders/list',
        datatype: "json",
        colModel: [			
			{ label: '订单号', name: 'cashOrderId', index: 'cash_order_id', width: 50, key: true},
			{ label: '商户名称', name: 'merchantName', index: 'merchant_id', width: 80 },
            { label: '提现人', name: 'toAccountName', index: 'to_account_name', width: 80 },
            { label: '提现卡号', name: 'toBankCard', index: 'to_bank_card', width: 80 },
			{ label: '提现金额', name: 'totalAmount', index: 'total_amount', width: 80 },
			{ label: '提现时间', name: 'createTime', index: 'create_time', width: 80 },
            { label: '到账时间', name: 'finishDate', index: 'finish_date', width: 80 },
			{ label: '提现状态', name: 'status', index: 'status', width: 80, formatter: function(item, index){
                    if(item === 2){
                        return '<span class="label label-success">已到账</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-primary">待处理</span>';
                    }
                    if(item === 4){
                        return '<span class="label label-danger">失败</span>';
                    }
                    if(item === 5){
                        return '<span class="label label-warning">已申请</span>';
                    }
                } }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25, 
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
		tMerchantCashOrders: {},
        q:{
            merchantName: null,
            status : 0,
            startTime: null,
            endTime: null
        }
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tMerchantCashOrders = {};
		},
		update: function (event) {
			var cashOrderId = getSelectedRow();
			if(cashOrderId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(cashOrderId)
		},
		saveOrUpdate: function (event) {
			var url = vm.tMerchantCashOrders.cashOrderId == null ? "statistics/tmerchantcashorders/save" : "statistics/tmerchantcashorders/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.tMerchantCashOrders),
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
			var cashOrderIds = getSelectedRows();
			if(cashOrderIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "statistics/tmerchantcashorders/delete",
                    contentType: "application/json",
				    data: JSON.stringify(cashOrderIds),
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
		getInfo: function(cashOrderId){
			$.get(baseURL + "statistics/tmerchantcashorders/info/"+cashOrderId, function(r){
                vm.tMerchantCashOrders = r.tMerchantCashOrders;
            });
		},
		reload: function (event) {
			vm.showList = true;
            var time = $("#startTime-endTime").val().split(" - ");
            vm.q.startTime = time[0];
            vm.q.endTime = time[1];
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    "merchantName" :vm.q.merchantName,
                    "status" :vm.q.status,
                    "endTime" :vm.q.endTime,
                    "startTime" :vm.q.startTime},
                page:page
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