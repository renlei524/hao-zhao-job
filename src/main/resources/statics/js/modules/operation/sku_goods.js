$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/skuGoods/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', index: 'id', width: 80 , hidden:true},
			{ label: '商品名称', name: 'goodsName', index: 'goods_name', width: 80 },
			{ label: '商品详细', name: 'details', index: 'details', width: 80 },

            { label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
                    return value === 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">正常</span>';
                }},
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
			{ label: '开始时间', name: 'createTime', index: 'create_time', width: 80 },
			{ label: '创建人', name: 'userName', index: 'user_id', width: 80 },
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
        q:{
            goodsName:null
        },
        skuGoods:{
            goodsName: null
        },
		showList: true,
		title: null,

	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.skuGoods = {
                status:1
			};
			window.onload = function (){
              document.body.onkeydown=function(event){
                if(event.keyCode==13){
                    event.keyCod=0; return false;
                }
              }
            }
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
		if($("#text1").attr("status") == 'N') return ;
        $("#text1").attr("status", "N");
			var url = vm.skuGoods.id == null ? "operation/skuGoods/save" : "operation/skuGoods/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.skuGoods),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
						$("#text1").attr("status", "Y");
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
				    url: baseURL + "operation/skuGoods/delete",
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
			$.get(baseURL + "operation/skuGoods/info/"+id, function(r){
                vm.skuGoods = r.skuGoods;
            });
		},
		reload: function (event) {

			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'goodsName': vm.q.goodsName},
                page:1
            }).trigger("reloadGrid");
            $("#text1").attr("status", "Y");
		}
	}
});