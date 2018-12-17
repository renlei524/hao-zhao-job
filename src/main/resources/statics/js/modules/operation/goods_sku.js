$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/goodsSku/list',
        datatype: "json",
        colModel: [
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true ,hidden: true},
			{ label: '商品名称', name: 'goodsName', index: 'goods_name', width: 80 },
            { label: '商品编号', name: 'code', index: 'code', width: 80 },
			{ label: '商品详细', name: 'details', index: 'details', width: 80 },
			{ label: '商品标签', name: 'labelName', index: 'label_id', width: 80 },
			{ label: '状态', name: 'status', index: 'status', width: 80, formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">正常</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-warning">暂停销售</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-danger">删除</span>';
                    }
                } },
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
		goodsSku: {labelId: null},
		q: {
            search: null,
            number: null,
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
			vm.goodsSku = {};
			vm.labelId();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getInfo(id)
            vm.labelId();
		},
		importGoodsSku: function(event) {
            $("#importGoodsSkuForm").attr("action", baseURL + "operation/goodsSku/import");
            var form = $("#importGoodsSkuForm");
            form.submit();
		},
		exportGoodsSku: function(event) {
		    window.location.href = baseURL + "operation/goodsSku/export";
		},
		saveOrUpdate: function (event) {
		    $("#goodsSku").blur();
            $("#goodsSku").attr("disabled", "disabled");
			var url = vm.goodsSku.id == null ? "operation/goodsSku/save" : "operation/goodsSku/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.goodsSku),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
					$("#goodsSku").removeAttr("disabled");
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
				    url: baseURL + "operation/goodsSku/delete",
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
			$.get(baseURL + "operation/goodsSku/info/"+id, function(r){
                vm.goodsSku = r.goodsSku;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'search':vm.q.search,'number':vm.q.number,'status':vm.q.status},
                page:page
            }).trigger("reloadGrid");
		},
        labelId: function(){
            var group = $("#labelId");
            group.empty();
            var url = "operation/merchantproductcustomerlabel/select";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                success: function(r){
                    group.append("<option value='-1'>--请选择标签--</option>");
                    for(var i=0;i<r.list.length;i++) {
                        if(vm.goodsSku.labelId == r.list[i].labelId) {
                            group.append("<option value='"+r.list[i].labelId+"' selected>"+r.list[i].labelName+"</option>");
                        } else {
                            group.append("<option value='"+r.list[i].labelId+"'>"+r.list[i].labelName+"</option>");
                        }
                    }
                }
            });
        }
	}
});
