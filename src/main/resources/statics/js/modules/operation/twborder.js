$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/twborder/list',
        datatype: "json",
        colModel: [			
			{ label: '订单编号', name: 'orderId', index: 'order_id', width: 200, key: true , hidden:true},
			{ label: '用户名称', name: 'userName', width: 80 },
			{ label: '商家名称', name: 'merchantName', width: 80 },
			{ label: '订单总价即原价', name: 'totalAmount', index: 'total_amount', width: 80 }, 			
			{ label: '实际给商家的结算价', name: 'settleAccounts', index: 'settle_accounts', width: 80 }, 			
			{ label: '实际支付价', name: 'finalAmount', index: 'final_amount', width: 60 },
			{ label: '信用卡支付', name: 'isFinalAmountFrom', index: 'is_final_amount_from',valign: 'middle', width: 60 ,formatter: function(item, index){
                    if(item === "0"){
                        return '<span class="label label-success">否</span>';
                    }
                    if(item === "1"){
                        return '<span class="label label-primary">是</span>';
                    }
                } },
			{ label: '订单状态', name: 'status', index: 'status',valign: 'middle',  width: 55,formatter: function(item, index){
                    if(item === 0){
                        return '<span class="label label-primary">待支付</span>';
                    }
                    if(item === 1){
                        return '<span class="label label-success">已支付</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-warning">已完成</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-primary">已取消</span>';
                    }
                }},
			{ label: '订单来源', name: 'orderFrom', index: 'order_from',valign: 'middle',  width: 55,formatter: function(item, index){
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
			{ label: '订单类型', name: 'orderType', index: 'order_type',valign: 'middle',  width:55,formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">扫码付</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-success">商城</span>';
                    }
                } },
			{ label: '下单时间', name: 'createTime', index: 'create_time', width: 120,formatter:function(value,options,row){
                    return new Date(value).Format('yyyy-MM-dd HH:mm:ss');}},
			{ label: '支付完成时间', name: 'payTime', index: 'pay_time', width: 120,formatter:function(value,options,row){
                    return new Date(value).Format('yyyy-MM-dd HH:mm:ss');}},
			{ label: '用户删除', name: 'userDelete', index: 'user_delete',valign: 'middle',  width: 55,formatter: function(item, index){
                    if(item === true){
                        return '<span class="label label-warning">已删除</span>';
                    }
                    if(item === false){
                        return '<span class="label label-success">未删除</span>';
                    }
                } },
			{ label: '商户删除', name: 'merchantDelete', index: 'merchant_delete',valign: 'middle',  width: 55 ,formatter: function(item, index){
                    if(item === true){
                        return '<span class="label label-warning">已删除</span>';
                    }
                    if(item === false){
                        return '<span class="label label-success">未删除</span>';
                    }
                } },
			{ label: '银行通道类型', name: 'payChannel', index: 'pay_channel',valign: 'middle',  width: 60 ,formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">原生</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-success">点点客</span>';
                    }
                } },
			{ label: '备注', name: 'remark', index: 'remark', width: 50 }
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




var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		tWbOrder: {userName:null},
        q:{
            userName:null
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
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'userName':vm.q.userName},
                page:1
            }).trigger("reloadGrid");
             $("#text1").attr("status", "Y");
		}
	}
});