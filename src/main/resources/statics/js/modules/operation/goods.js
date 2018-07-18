$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/goods/list',
        datatype: "json",
        colModel: [
            { label: '商品id', name: 'id', index: 'id', width: 80 ,hidden:true},
			{ label: '商品名称', name: 'goodsName', index: 'goods_name', width: 80 },
			{ label: '商品详细', name: 'details', index: 'details', width: 80 }, 			
			{ label: '成本价', name: 'originalPrice', index: 'original_price', width: 80 }, 			
			{ label: '市场价', name: 'marketPrice', index: 'market_price', width: 80 }, 			
			{ label: '折后价', name: 'discountPrice', index: 'discount_price', width: 80 }, 			
			{ label: '商品总库存', name: 'total', index: 'total', width: 80 }, 			
			{ label: '商品销量', name: 'sales', index: 'sales', width: 80 }, 			
			{ label: '卡券种类', name: 'status',index: 'status', align: 'center', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){
                    if(item === 0){
                        return '<span class="label label-primary">正常</span>';
                    }
                    if(item === 1){
                        return '<span class="label label-success">暂停销售</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-warning">删除</span>';
                    }
                }},
			{ label: '商品图片', name: 'pictures', index: 'pictures', width: 80 },
			{ label: '商品促销', name: 'isPromotion', width: 80, formatter: function(value, options, row){
                    return value === 0 ?
                        '<span class="label label-danger">促销</span>' :
                        '<span class="label label-success">不促销</span>';
                }},
			{ label: '促销价', name: 'promotionalPrice', index: 'promotional_price', width: 80 },
			{ label: '剩余库存', name: 'surplusInventory', index: 'surplus_inventory', width: 80 }, 			
			{ label: '限购数量', name: 'quantityPurchased', index: 'quantity_purchased', width: 80 }, 			
			{ label: '开始时间', name: 'startTime', index: 'start_time', width: 80 }, 			
			{ label: '结束时间', name: 'endTime', index: 'end_time', width: 80 }, 			
			{ label: '', name: 'tradeContent', index: 'trade_content', width: 80 }, 			
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		goods: {
			goodsName:null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.goods = {};
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
		saveOrUpdate: function (event) {
			var url = vm.goods.id == null ? "operation/goods/save" : "operation/goods/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.goods),
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
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}

			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "operation/goods/delete",
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
			$.get(baseURL + "operation/goods/info/"+id, function(r){
                vm.goods = r.goods;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				//添加传送数据 yh
                postData:{'goodsName': vm.goods.goodsName},
                page:1
            }).trigger("reloadGrid");
		}
	}
});