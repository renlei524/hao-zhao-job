$(function () {
    $("#img").css("display", "none");
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/goods/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true },
			{ label: '商户名称', name: 'merchantName', index: 'merchant_id', width: 80 },
			{ label: '商品名称', name: 'goodsName', index: 'goods_name', width: 80 }, 			
			/*{ label: '商品详细', name: 'details', index: 'details', width: 80 },*/
			/*{ label: '成本价', name: 'originalPrice', index: 'original_price', width: 80 },*/
			{ label: '原价', name: 'marketPrice', index: 'market_price', width: 80 },
			{ label: '现价', name: 'discountPrice', index: 'discount_price', width: 80 },
			/*{ label: '商品总库存', name: 'total', index: 'total', width: 80 },*/
			{ label: '商品总销量', name: 'sales', index: 'sales', width: 80 },
			{ label: '月销量', name: 'monthSales', index: 'month_sales', width: 80 },
			{ label: '状态', name: 'status', index: 'status', width: 80 , formatter: function(item, index){
                    if(item == 5){
                        return '<span class="label label-success">正常使用</span>';
                    }
                    if(item == 6){
                        return '<span class="label label-primary">暂停销售</span>';
                    }
                    if(item == 7){
                        return '<span class="label label-primary">售罄</span>';
                    }
                    if(item == 8){
                        return '<span class="label label-danger">删除</span>';
                    }
                } },
			/*{ label: '商品促销', name: 'isPromotion', index: 'is_promotion', width: 80 , formatter: function(item, index){
                    if(item == 1){
                        return '<span class="label label-primary">促销 </span>';
                    }
                    if(item == 0){
                        return '<span class="label label-success">不促销</span>';
                    }
                }},
            { label: '剩余库存', name: 'surplusInventory', index: 'surplus_inventory', width: 80 },
            { label: '限购数量', name: 'quantityPurchased', index: 'quantity_purchased', width: 80 },
            { label: '促销价', name: 'promotionalPrice', index: 'promotional_price', width: 80 },
            { label: '开始时间', name: 'startTime', index: 'start_time', width: 80 },
            { label: '结束时间', name: 'endTime', index: 'end_time', width: 80 },*/
            { label: '是否推荐', name: 'isRecommend', index: 'is_recommend', width: 80, formatter: function(item, index){
                    if(item == 1){
                        return '<span class="label label-primary">推荐 </span>';
                    }else {
                        return '<span class="label label-success">不推荐</span>';
                    }
                }  },
            /*{ label: '点赞数量', name: 'thumbsUp', index: 'thumbs_up', width: 80 },*/
			{ label: '商品标签', name: 'labelName', index: 'label_id', width: 80 },
            { label: '商品图片', name: 'pictures', index: 'pictures', width: 80 , formatter: function(item, index){
                    return '<img src="'+item +'" onmouseover = "func(this)" onmouseout = "func1()" height="30px" width="30px">';
                }}

        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
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
		goods: {},
		q:{
            search: null,
            status: 0
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
		    $("#goodsSaveOrUpdate").blur();
            $("#goodsSaveOrUpdate").attr("disabled", "disabled");
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
					$("#goodsSaveOrUpdate").removeAttr("disabled");
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
                postData:{
                    "search" :vm.q.search,
                    "status" :vm.q.status},
                page:page
            }).trigger("reloadGrid");
		}
	}
});

function  func(e) {
    // $("#img").css("display", "");
    $("#img").attr("src", e.src);
    $("#img").css("transform", "scale(3.5)");
    $("#img").css("display", "none");
    $("#img").fadeIn(200);
    $("#img").css("margin-left",getPos(e).left - 250 + "px");
    $("#img").css("margin-top",-480 + getMousePos().y + "px");
}
function  func1() {
    $("#img").fadeOut(200);
    $("#img").css("margin-top","-800px");
    // $("#img").css("display", "none");
}

//获取图片距离页面边距
function getPos(obj){
    var l = t = 0;
    while(obj){
        l+=obj.offsetLeft; //取到定位父级的距离
        t+=obj.offsetTop;  //取到定位父级的距离
        obj=obj.offsetParent;  //把obj的定位父级变成obj

    }
return {left:l,top:t};
}

//获取鼠标位置(相对于浏览器窗口)
function getMousePos(event) {
    var e = event || window.event;
    return {'x':e.clientX,'y':e.clientY}
}