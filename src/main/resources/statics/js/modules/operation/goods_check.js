$(function () {
    $("#img").css("display", "none");
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/goodscheck/list',
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
			{ label: '审核状态', name: 'status', index: 'status', width: 80, formatter: function(item, index){
                    if(item == 1){
                        return '<span class="label label-success">创建审核中</span>';
                    }
                    if(item == 2){
                        return '<span class="label label-danger">审核不通过</span>';
                    }
                    if(item == 3){
                        return '<span class="label label-primary">修改审核中</span>';
                    }
                    if(item == 4){
                        return '<span class="label label-danger">修改不通过</span>';
                    }
                }  },
			/*{ label: '商品促销', name: 'isPromotion', index: 'is_promotion', width: 80, formatter: function(item, index){
                    if(item == 1){
                        return '<span class="label label-primary">促销 </span>';
                    }
                    if(item == 0){
                        return '<span class="label label-success">不促销</span>';
                    }
                } },
			{ label: '促销价', name: 'promotionalPrice', index: 'promotional_price', width: 80 },
			{ label: '剩余库存', name: 'surplusInventory', index: 'surplus_inventory', width: 80 }, 			
			{ label: '限购数量', name: 'quantityPurchased', index: 'quantity_purchased', width: 80 }, 			
			{ label: '开始时间', name: 'startTime', index: 'start_time', width: 80 }, 			
			{ label: '结束时间', name: 'endTime', index: 'end_time', width: 80 },*/
			{ label: '商品标签', name: 'labelName', index: 'label_id', width: 80 },
			{ label: '是否推荐', name: 'isRecommend', index: 'is_recommend', width: 80, formatter: function(item, index){
                    if(item == 1){
                        return '<span class="label label-primary">推荐 </span>';
                    }else {
                        return '<span class="label label-success">不推荐</span>';
					}
                }  },
			/*{ label: '点赞数量', name: 'thumbsUp', index: 'thumbs_up', width: 80 },*/
            { label: '商品图片', name: 'pictures', index: 'pictures', width: 80 , formatter: function(item, index){
                    return '<img src="'+ item +'" onmouseover = "func(this)" onmouseout = "func1()" height="30px" width="30px">';
                }}
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
		goodsCheck: {merchantName:null,status: null,pictures: null,remark: null},
        q:{
            search: null,status: 0
        }
	},
	methods: {
		query: function () {
			vm.reload();
		},
		checkBitchYes: function(event) {
		    var ids = getSelectedRows();
            if(ids == null){
                return ;
            }
            var url = "operation/goodscheck/batchOperationOk/" + ids;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
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
		checkBitchNo: function(event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }
            var remark = prompt("请输入审批意见!");
            if (remark == null) {
                return;
            }
            if(remark.trim() == ""){
                alert("审批不通过，意见必填！");
                return ;
            }
            if(remark.trim().length > 30){
                alert("输入字数过多！限30字");
                return ;
            }
            var url = "operation/goodscheck/batchOperationNo/" + ids + "/" + remark;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
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
		check: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "审核";
            $("#remark").css("display", "block");
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $("#goodsCheck").blur();
            $("#goodsCheck").attr("disabled", "disabled");
            if(vm.goodsCheck.status == 2 && (vm.goodsCheck.remark == null || vm.goodsCheck.remark == "")) {
                alert("审批不通过，意见必填！");
                $("#goodsCheck").removeAttr("disabled");
                return;
            }
			var url = "operation/goodscheck/check";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.goodsCheck),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
					$("#goodsCheck").removeAttr("disabled");
				}
			});
		},
		getInfo: function(id){
			$.get(baseURL + "operation/goodscheck/info/"+id, function(r){
                vm.goodsCheck = r.goodsCheck;
                vm.goodsCheck.status = 2;
                var imageNginxPath = r.imageNginxPath;
                $("#pictures").attr('src',imageNginxPath + vm.goodsCheck.pictures);
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
	},
	updated: function() {
	    $('input[type=radio][name=status]').change(function() {
            if (this.value == '2') {
                $("#remark").css("display", "block");
            } else {
                $("#remark").css("display", "none");
                vm.goodsCheck.remark = null;
            }
        });
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
