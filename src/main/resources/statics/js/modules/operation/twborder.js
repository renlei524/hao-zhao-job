$(function () {

    /**
     * 默认显示当前时间
     * @type {Date}
     */
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


    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/twborder/list',
        datatype: "json",
        colModel: [			
			{ label: '订单号', name: 'orderId', index: 'order_id', key: true},
			{ label: '商家名称', name: 'merchantName'},
			{ label: '用户名称', name: 'userName'},
			{ label: '用户电话', name: 'mobile'},
			{ label: '订单总价', name: 'totalAmount', index: 'total_amount', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
			{ label: '商家结算价', name: 'settleAccounts', index: 'settle_accounts', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
			{ label: '优惠卷使用', name: 'couponsOffset',formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
			{ label: '微宝使用', name: 'wbDeductible', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
			{ label: '实付金额', name: 'finalAmount', index: 'final_amount', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
			{ label: '订单状态', name: 'status', index: 'status',valign: 'middle',formatter: function(item, index){
                    if(item === -2){
                        return '<span class="label label-primary">已退款</span>';
                    }
                    if(item === -1){
                        return '<span class="label label-primary">已取消</span>';
                    }
                    if(item === 0){
                        return '<span class="label label-primary">待支付</span>';
                    }
                    if(item === 1){
                        return '<span class="label label-success">已支付</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-warning">已结算</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-warning">已成功</span>';
                    }
                }},
			{ label: '订单来源', name: 'orderFrom', index: 'order_from',valign: 'middle',formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">微信</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-success">支付宝</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-primary">微信反扫</span>';
                    }
                    if(item === 4){
                        return '<span class="label label-success">支付宝反扫</span>';
                    }
                } },
			{ label: '订单类型', name: 'orderType', index: 'order_type',valign: 'middle',formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">扫码付</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-success">商城</span>';
                    }
                } },
			{ label: '下单时间', name: 'createTime', index: 'create_time',formatter:function(value,options,row){
                    return new Date(value).Format('yyyy-MM-dd HH:mm:ss');}},
			{ label: '支付完成时间', name: 'payTime', index: 'pay_time',formatter:function(value,options,row){
                    return value != null ? new Date(value).Format('yyyy-MM-dd HH:mm:ss') : "";}},
			{ label: '银行通道类型', name: 'payChannel', index: 'pay_channel',valign: 'middle' ,formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">原生</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-success">点点客</span>';
                    }
                } }
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
		tWbOrder: {userName:null},
        q:{
            userName:null,
            merchantName:null,
            startTime:null,
            endTime:null,
            orderId:null
        }
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tWbOrder = {};
		},
		update: function (event) {
			var orderId = getSelectedRow();
			if(orderId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(orderId)
		},
		saveOrUpdate: function (event) {
            $("#text1").blur();
            $("#text1").attr("disabled", "disabled");
			var url = vm.tWbOrder.orderId == null ? "operation/twborder/save" : "operation/twborder/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.tWbOrder),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
                    $("#text1").removeAttr("disabled");
				}
			});
		},
		del: function (event) {
			var orderIds = getSelectedRows();
			if(orderIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "operation/twborder/delete",
                    contentType: "application/json",
				    data: JSON.stringify(orderIds),
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
		getInfo: function(orderId){
			$.get(baseURL + "operation/twborder/info/"+orderId, function(r){
                vm.tWbOrder = r.tWbOrder;
            });
		},
		reload: function (event) {
			vm.showList = true;
            var time = $("#startTime-endTime").val().split(" - ");
            vm.q.startTime = time[0];
            vm.q.endTime = time[1];
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'userName':vm.q.userName,//用户名称 && 用户电话号码
                    "merchantName": vm.q.merchantName, //商户名称
                    "endTime": vm.q.endTime, //开始时间
                    "startTime": vm.q.startTime, //结束时间
                    "orderId" : vm.q.orderId //订单号
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